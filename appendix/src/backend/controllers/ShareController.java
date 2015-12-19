package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.share;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import java.util.*;

/**
 * Controller for processing request concerning sharing a post
 * Created by qiuzhexin on 11/16/15.
 */
@Named
@Singleton
public class ShareController extends Controller {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ShareRepository shareRepository;

    @Inject
    public ShareController(PostRepository postRepository, UserRepository userRepository, ShareRepository shareRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.shareRepository = shareRepository;
    }

    // provide form for adding a new share
    public Result newShare() {
        return ok(share.render(""));
    }

    // share a post of others
    public Result addSharePost() {
//        /* for debug */
//        ShareBean shareBean = Form.form(ShareBean.class).bindFromRequest().get();
//        String sharerEmail = shareBean.getSharerEmail();
//        Long postId = Long.parseLong(shareBean.getPostId());
        /* JSON method */
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Share not created, expecting Json data");
            return badRequest("Share not created, expecting Json data");
        }

        // Parse the JSON object
        String sharerEmail = json.path("sharerEmail").asText();
        Long postId = json.path("postId").asLong();

        try {
            User sharer = userRepository.findByEmail(sharerEmail);
            Post post = postRepository.findOne(postId);
            if (sharer == null) {
                System.out.println("Can't find user with email: " + sharerEmail);
                return notFound("Can't find user with email: " + sharerEmail);
            }
            if (post == null) {
                System.out.println("Can't find post with id: " + postId);
                return notFound("Can't find post with id: " + postId);
            }

            Share share = new Share(post, sharer);
            Share savedShare = shareRepository.save(share);
            System.out.println("Share relationship saved: "
                    + savedShare.toString());
            return created(new Gson().toJson(savedShare.getId()));
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            System.out.println("Post not saved: sharer email:" + sharerEmail);
            return badRequest("Post not saved: sharer email:" + sharerEmail);
        }
    }

    // get all posts shared by a user in time descending order provided user id
    public Result getSharedPostsByUserId(Long id, String format) {
        if (id == null) {
            System.out.println("User id is null or empty!");
            return badRequest("User id is null or empty!");
        }

        User user = userRepository.findOne(id);
        List<Share> shares = shareRepository.findDistinctByUserOrderByCreateTimeDesc(user);
        if (shares == null) {
            System.out.println("Shares not found with user id: " + id);
            return notFound("Shares service not found with user id: " + id);
        }

        // get a list of distinct post ids
        Set<Long> postIds = new LinkedHashSet<>();
        for (Share share : shares) {
            postIds.add(share.getPost().getId());
        }
        // get a list of posts
        List<Post> posts = new ArrayList<>();
        for (Long postId : postIds) {
            posts.add(postRepository.findOne(postId));
        }
        Collections.sort(posts);

        // get a list of creators associated with the posts
        List<User> users = new ArrayList<>();
        for (Post post : posts) {
            System.out.println("user: " + post.getUser());
            users.add(post.getUser());
        }

        // package to JSON response
        String result_posts = new String();

        if (format.equals("json")) {
            result_posts = new Gson().toJson(posts);
        }
        
        return ok(result_posts);
    }


    // delete shares by postId
    public Result deleteShareByPostId(Long postId) {
        Post post = postRepository.findOne(postId);
        List<Share> shares = shareRepository.findByPost(post);
        shareRepository.delete(shares);
        return ok("delete");
    }

    // delete all shares
    public Result deleteAllShares() {
//        User user = userRepository.findOne(1L);
//        Post newPost = new Post(14, user, "casdoc", 0, new Date(), "public", "12", "23");
//        Post savedPost = postRepository.save(newPost);
//        System.out.println("saved post: " + savedPost.toString());
        shareRepository.deleteAll();
        return ok("delete all shares");
    }

    // get all share relationships for debug
    public Result getAllShares() {
        return TODO;
    }
}
