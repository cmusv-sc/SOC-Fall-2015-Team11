package controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.util.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import javafx.geometry.Pos;
import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.post;
/**
 * The main set of web services.
 */
@Named
@Singleton
public class PostController extends Controller {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ShareRepository shareRepository;
    private final FollowRepository followRepository;

    // We are using constructor injection to receive a repository to support our
    // desire for immutability.
    @Inject
    public PostController(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository, ShareRepository shareRepository, FollowRepository followRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.shareRepository = shareRepository;
        this.followRepository = followRepository;
    }

    // provide form for adding a new post
    public Result newPost() {
        return ok(post.render("hello"));
    }

    // add a post to the database using http POST method
    public Result addPost() {
//        String content = json.path("content").asText();
      /* for debug */
//        PostBean postBean = Form.form(PostBean.class).bindFromRequest().get();
//        String content = postBean.getContent();
//        int likes = Integer.parseInt(postBean.getLikes());
//        String email = postBean.getEmail();
//        Date createTime = new Date();

        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Post not created, expecting Json data");
            return badRequest("Post not created, expecting Json data");
        }

        // parse the json object
        String email = json.path("email").asText();
        String content = json.path("content").asText();
        String privacy = json.path("privacy").asText();
        String lon = json.path("lon").asText();
        String lat = json.path("lat").asText();

        int likes = 0;
        Date createTime = new Date();

        try {
            User user = userRepository.findByEmail(email);
            Post newPost = new Post(user, content, likes, createTime, privacy, lat, lon);
            Post savedPost = postRepository.save(newPost);
            System.out.println("Post saved: "
                    + savedPost.getUser().toString());
            return created(new Gson().toJson(savedPost.getUser().toString()));
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            System.out.println("Post not saved: " + content);
            return badRequest("Post not saved: " + content);
        }
    }

    // get a post by post id
    public Result getPost(Long id, String format) {
        if (id == null) {
            System.out.println("Post id is null or empty!");
            return badRequest("Post id is null or empty!");
        }

        Post post = postRepository.findOne(id);

        if (post == null) {
            System.out.println("Post not found with with id: " + id);
            return notFound("Post not found with with id: " + id);
        }
        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(post);
        }

        return ok(result);
    }

    // get all posts by a user id in a time descending order
    public Result getAllPostsByUserId(Long id, String format) {
        if (id == null) {
            System.out.println("User id is null or empty!");
            return badRequest("User id is null or empty!");
        }

        User user = userRepository.findOne(id);
        List<Post> posts = postRepository.findByUserOrderByCreateTimeDesc(user);
        if (posts == null) {
            System.out.println("Posts not found with user id: " + id);
            return notFound("Posts service not found with user id: " + id);
        }

        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(posts);
        }

        return ok(result);
    }

    // like a post
    public Result likeToPost() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Post not created, expecting Json data");
            return badRequest("Post not created, expecting Json data");
        }

        String usrEmail = json.path("email").asText();
        long postId = json.path("postId").asLong();

        System.out.println("get user email " + usrEmail);
        System.out.println("get post id " + postId);

        Post post = postRepository.findOne(postId);
        User user = userRepository.findByEmail(usrEmail);

        if (user != null) {
            if (post.addLike(usrEmail)) {
                System.out.println("add like to post " + postId + ", user " + usrEmail);
                postRepository.save(post);
                return ok("add like successful.");
            } else
                return badRequest("user already liked");
        } else {
            System.out.println("cannot add like to post " + postId + ", user " + usrEmail);
            return badRequest("user doesn't exist");
        }
    }

    // delete a post by postId
    public Result deletePostByPostId(Long id) {
        // find the post regarding the id
        Post post = postRepository.findOne(id);
        if (post == null) {
            System.out.println("The post to delete doesn't exist: " + id);
            return badRequest("The post to delete doesn't exist " + id);
        }

        // delete the comments associated with the post
        List<Comment> comments = commentRepository.findByPost(post);
        commentRepository.delete(comments);

        // delete shares associated with the post
        List<Share> shares = shareRepository.findByPost(post);
        shareRepository.delete(shares);

        // delete the post
        postRepository.delete(id);
        System.out.println("Post is deleted: " + id);
        return ok("Post is deleted: " + id);
    }

    // edit a post by postId
    public Result editPostByPostId() {
        // read json body from the request
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Cant not edit post, expecting Json data");
            return badRequest("Cant not edit post, expecting Json data");
        }

        // parse the json object
        String newContent = json.path("newContent").asText();
        Long postId = json.path("postId").asLong();

        System.out.println("receive new content: " + newContent);
        System.out.println("receive post: " + postId);

        try {
            // get the post to be edited
            Post editPost = postRepository.findOne(postId);
            if (editPost == null) {
                System.out.println("post to edit doesn't exist " + postId);
                return badRequest("post to edit doesn't exist: " + postId);
            }
            editPost.setContent(newContent);
            Post editedPost = postRepository.save(editPost);
            System.out.println("post edited: " + editedPost.toString());
            return created(new Gson().toJson(editedPost.toString()));
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            System.out.println("edited post not saved:\n" + json.toString());
            return badRequest("edited post not saved:\n" + json.toString());
        }
    }

    // get top 10 most popular posts
    public Result getTopTenPostsByPopularity(String format) {
        // find top posts
        List<Post> topPosts = postRepository.getPostsOrderByLikes();
        List<Post> topTenPosts = new ArrayList<>();

        // truncate the size if more than 10 posts
        if (topPosts != null && topPosts.size() > 10) {
            for (int i = 0; i < 10; i++) {
                topTenPosts.add(topPosts.get(i));
            }
        } else {
            topTenPosts = topPosts;
        }

        // format the result in json
        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(topTenPosts);
        }

        return ok(result);
    }

    // delete all posts by user email
    public Result deleteAllPostsByUserId(Long id) {
        // find the user with the id
        User user = userRepository.findOne(id);
        // find all posts assoiated with the user
        List<Post> posts = postRepository.findByUserOrderByCreateTimeDesc(user);
        if (posts == null) {
            System.out.println("No posts associted with the queried user id: " + id);
            return  notFound("No posts associted with the queried user id: " + id);
        }
        // delete the comments and posts
        for (Post p : posts) {
            deletePostByPostId(p.getId());
        }

        return ok("Posts are deleted with user: " + id);
    }

    // find all posts of my followees
    public Result findFolloweePostsByUserIdOrderByTime(Long id, String format) {
        // find the user corresponding to the id
        User follower = userRepository.findOne(id);
        if (follower == null) {
            System.out.println("User not found corresponding to id: " + id);
            return notFound("User not found corresponding to id: " + id);
        }

        // get all followed posts by the follower
        // get follow relationships
        List<Follow> follows = followRepository.findByFollower(follower);
        List<Long> postIds = new ArrayList<>();
        List<Post> posts = postRepository.findByUserOrderByCreateTimeDesc(follower);
        // get the follower's self posts
        for (Post p : posts) {
            postIds.add(p.getId());
        }
        // get all followee posts from a user's id
        for (Follow f : follows) {
            User followee = f.getFollowee();
            posts = postRepository.findByUserOrderByCreateTimeDesc(followee);
            for (Post p : posts) {
                postIds.add(p.getId());
            }
        }

        // find all posts
        List<Post> followedPosts = Lists.newArrayList(postRepository.findAll(postIds));
        // sort the post by time order
        Collections.sort(followedPosts);

        // format the result in json
        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(followedPosts);
        }

        return ok(result);
    }
}
