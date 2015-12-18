package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import models.*;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.follow;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for processing request concerning sharing a post
 * Created by qiuzhexin on 11/16/15.
 */
@Named
@Singleton
public class FollowController extends Controller {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Inject
    public FollowController(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

   // provide form for adding a new follow
    public Result newFollow() {
        return ok(follow.render(""));
    }

    // follow a user
    public Result addFollow() {
//        /* for debug */
//        FollowBean followBean = Form.form(FollowBean.class).bindFromRequest().get();
//        String followerEmail = followBean.getFollowerEmail();
//        String followeeEmail = followBean.getFolloweeEmail();
        /* Chengshen's method */
//        DynamicForm dynamicForm = Form.form().bindFromRequest();
//        String followerEmail = dynamicForm.get("follower");
//        String followeeEmail = dynamicForm.get("followee");
        /* JSON method */
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("Follow not created, expecting Json data");
            return badRequest("Follow not created, expecting Json data");
        }

        // Parse JSON file
        String followerEmail = json.path("followerEmail").asText();
        String followeeEmail = json.path("followeeEmail").asText();
        System.out.println("receive follower: " + followerEmail);
        System.out.println("receive followee: " + followeeEmail);

        try {
            User follower = userRepository.findByEmail(followerEmail);
            User followeee = userRepository.findByEmail(followeeEmail);
            Follow newFollow = new Follow(follower, followeee);
            Follow savedFollow = followRepository.save(newFollow);
            System.out.println("Follow relationship saved: "
                    + savedFollow.toString());
            return created(new Gson().toJson(savedFollow.getId()));
        } catch (PersistenceException pe) {
            pe.printStackTrace();
            System.out.println("Post not saved: follower email:" + followerEmail);
            return badRequest("Post not saved: follower email:" + followerEmail);
        }

    }

    // get all followers of a user by id
    public Result getFollowersByUserId(Long id, String format) {
        if (id == null) {
            System.out.println("User id is null or empty!");
            return badRequest("User id is null or empty!");
        }

        User user = userRepository.findOne(id);
        List<Follow> follows = followRepository.findByFollowee(user);
        if (follows == null) {
            System.out.println("Follows not found with user id: " + id);
            return notFound("Follows not found with user id: " + id);
        }

        // get a list of users who are following the request user
        List<User> followers = new ArrayList<>();
        for (Follow follow : follows) {
            followers.add(follow.getFollower());
        }

        // package to JSON response
        String result = new String();
        if (format.equals("json")) {
            result = new Gson().toJson(followers);
        }
        return ok(result);
    }


    // revoke following
    public Result deleteFollowById(Long id) {
        Follow follow = followRepository.findOne(id);
        if (follow == null)
            return notFound("follow not found: id -> " + id);

        followRepository.delete(id);

        return ok("follow relationship is deleted: id ->" + id);
    }

    // get all followers
    public Result getAllFollow() {
        List<Follow> follows = new ArrayList<>();
        for (Follow follow : followRepository.findAll()) {
            follows.add(follow);
        }

        String result = new String();
        result = new Gson().toJson(follows);

        return ok(result);
    }
}
