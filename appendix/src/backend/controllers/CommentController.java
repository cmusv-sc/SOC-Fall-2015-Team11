package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import models.*;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

/**
 * operations on comments on post
 * Created by qiuzhexin on 12/1/15.
 */
@Named
@Singleton
public class CommentController extends Controller {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Inject
    public CommentController(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    // add a comment to a post
    public Result addComment() {
        // read http request with json body
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Comment not created, expecting Json data");
            return badRequest("Comment not created, expecting Json data");
        }

        // parse the json object
        String usrEmail = json.path("email").asText();
        String comment = json.path("comment").asText();
        long postId = json.path("postId").asLong();

        System.out.println("receive email: " + usrEmail);
        System.out.println("receive post: " + postId);
        System.out.println("receive comment: " + comment);

        try {
            // get user and post related to the email and post
            User commenter = userRepository.findByEmail(usrEmail);
            Post post = postRepository.findOne(postId);
            Date createTime = new Date();
            // create a comment on the post
            Comment newComment = new Comment(post, commenter, comment, createTime);
            Comment savedComment = commentRepository.save(newComment);
            System.out.println("Comment saved: "
                    + savedComment.toString());
            return created(new Gson().toJson(savedComment.toString()));
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            System.out.println("comment not saved:\n" + json.toString());
            return badRequest("comment not saved:\n" + json.toString());
        }
    }

    // return all comments associated with a post in time ascending order
    public Result getAllCommentsByPostIdOrderByTimeAsc(Long id, String format) {
        // validate the post id
        if (id == null) {
            System.out.println("Post id is null or empty!");
            return badRequest("Post id is null or empty!");
        }

        // find corresponding post
        Post post = postRepository.findOne(id);
        if (post == null) {
            System.out.println("Post not found with with id: " + id);
            return notFound("Post not found with with id: " + id);
        }

        // find comments on the post
        List<Comment> comments = commentRepository.findByPostOrderByCreateTimeAsc(post);
        String result = new String();
        if (format.equals("json"))
            result = new Gson().toJson(comments);

        return ok(result);
    }

    // delete comment by comment id
    public Result deleteCommentByCommentId(Long id) {
        return TODO;
    }
}
