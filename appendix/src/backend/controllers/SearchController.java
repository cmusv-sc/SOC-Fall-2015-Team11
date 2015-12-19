package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import models.Post;
import models.PostRepository;
import models.User;
import models.UserRepository;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

/**
 * Controller for search functions for front-end
 * Created by qiuzhexin on 12/1/15.
 */
@Named
@Singleton
public class SearchController extends Controller {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Inject
    public SearchController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // criteria search for user
    public Result searchUsingCriteria() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("cannot search, expecting Json data");
            return badRequest("cannot search, expecting Json data");
        }

        // parse the json object
        String firstName = json.path("firstName").asText();
        String lastName = json.path("lastName").asText();
        if (firstName == null || lastName == null) {
            System.out.println("first name or last name is not provided");
            return badRequest("first name or last name is not provided");
        }
        String email = json.path("email").asText();
        email = email == null ? "" : email;
        String affiliation = json.path("affiliation").asText();
        affiliation = affiliation == null ? "" : affiliation;
        String title = json.path("title").asText();
        title = title == null ? "" : title;

        // search the user table for satisfying the criteria
        List<User> users = userRepository
                .getUsersByCriteria(firstName, lastName, email, affiliation, title);

        String result = new Gson().toJson(users);

        return ok(result);
    }

    // fuzzy search for user
    public Result searchUserFuzzy(String partial, String format) {
        // search the user table for satisfying the criteria
        List<User> users = userRepository
                .getUsersByFuzzySearch(partial);

        String result = new String();
        if (format.equals("json"))
            result = new Gson().toJson(users);

        return ok(result);
    }

    // search key word in post content
    public Result searchPostContentByKeyword(String keyword, String format) {
        // search for keyword in all posts
        keyword = keyword.toLowerCase();
        keyword = "%" + keyword + "%";
        List<Post> posts = postRepository.getPostsContainsKeywordOrderByCreateTime(keyword);

        String result = new String();
        if (format.equals("json"))
            result = new Gson().toJson(posts);

        return ok(result);
    }
}
