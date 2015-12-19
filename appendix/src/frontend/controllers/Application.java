package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import myutil.APICall;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;

import views.html.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import model.Post;
import model.User;
import myutil.APICall;
import play.mvc.*;

import views.html.*;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {
    final static Form<Post> new_post = Form.form(Post.class);
    final static Form<Post> new_main_post = Form.form(Post.class);
    private static final Gson gson = new Gson();

    public Result index(String name) {
        if (name.equals("favicon.ico")) {
            return ok("");
        }
        User user = APICall.getRemoteJson("users/" + name, User.class);
        List<Post> posts = APICall.getRemotePostJsonArray("post/user/" + name);
        List<Post> otherPosts = APICall.getRemotePostJsonArray("share/user/" + name);
        List<Post> popularPosts = APICall.getRemotePostJsonArray("post/popular");
        for (Post post: otherPosts) {
            if (post.getLikeUsers().indexOf(user.getEmail()) != -1) {
                post.setLiked(true);
            } else {
                post.setLiked(false);
            }
        }
        for (Post post: popularPosts) {
            if (post.getLikeUsers().indexOf(user.getEmail()) != -1) {
                post.setLiked(true);
            } else {
                post.setLiked(false);
            }
        }
        /*User user = new User(1, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        User user1 = new User(2, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        User user2 = new User(3, "wcyz666", "aaa", "Zhexin", "Qiu", "ccc", "student", "student", "lu.li@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        List<Comment> comments = new ArrayList<>();
        Post post = new Post(4, user1, "bbbbbbbbbbbbb", 1, new Date(), "public", comments);
        Comment comment = new Comment(1, "aaaaa", post, user1, new Date());
        comments.add(comment);
        comments.add(new Comment(2, "aaaaa", post, user1, new Date()));
        List<Post> posts = new ArrayList<Post>();
        posts.add(new Post(4, user1, "bbbbbbbbbbbbb", 1, new Date(), "public", comments));
        posts.add(new Post(5, user1, "bbbbbbbbbbbbb", 1, new Date(), "private", comments));
        List<Post> otherPosts = new ArrayList<Post>();
        otherPosts.add(new Post(1, user1, "bbbbbbbbb bbbbbbbbbbb", 1, new Date(), comments));
        otherPosts.add(new Post(2, user2, "bbbbbbbbb cccccc", 1, new Date(), comments));
        otherPosts.add(new Post(3, user1, "bbbbbbb dddddddddd", 1, new Date(), comments));

        List<Post> popularPosts = new ArrayList<Post>();
        popularPosts.add(new Post(1, user1, "bbbbbbbbb bbbbbbbbbbb", 1, new Date()));
        popularPosts.add(new Post(2, user2, "bbbbbbbbb cccccc", 1, new Date()));
        popularPosts.add(new Post(3, user1, "bbbbbbb dddddddddd", 1, new Date()));*/
        return ok(index.render("Your new application is ready.", user, posts, otherPosts, popularPosts));
    }

    public Result follower(Integer id) {
        //List<User> list = new ArrayList<>();
        //list.add(new User(1, "wcyz666", "aaa", "lu", "li", "ccc", "student", "student", "lu.li@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master"));
        //list.add(new User(2, "wcyz666", "aaa", "Clare", "liu", "ccc", "student", "student", "lu.li@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master"));
        //list.add(new User(3, "wcyz666", "aaa", "Zhexin", "Qiu", "ccc", "student", "student", "lu.li@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master"));
        List<User> list = APICall.getRemoteUserJsonArray("follow/user/" + id);
        return ok(Json.toJson(list));
    }

    public Result likePost() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        HashMap<String, String> postDataParams = new HashMap<>();

        postDataParams.put("postId", dynamicForm.get("postId"));
        postDataParams.put("email", dynamicForm.get("email"));
        return ok(Json.toJson(APICall.postMessages("post/like", Json.toJson(postDataParams) )));
    }

    public Result follow() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        HashMap<String, String> postDataParams = new HashMap<>();

        postDataParams.put("followerEmail", dynamicForm.get("follower"));
        postDataParams.put("followeeEmail", dynamicForm.get("followee"));
        ;
        return ok(Json.toJson(APICall.postMessages("follow", Json.toJson(postDataParams))));
    }

    public Result newPost() {
        Form<Post> form = new_post.bindFromRequest();
        HashMap<String, String> postDataParams = new HashMap<>();

        postDataParams.put("content", form.field("postContent").value());
        postDataParams.put("likes", "0");
        postDataParams.put("lon", form.field("longitude").value());
        postDataParams.put("lat", form.field("latitude").value());
        if (form.field("privacy").value() != null) {
            postDataParams.put("privacy", "public");
        } else {
            postDataParams.put("privacy", "private");
        }
        postDataParams.put("email", form.field("email").value());
        String id = form.field("authorId").value();

        APICall.postMessages("post", Json.toJson(postDataParams));
        return  redirect("/" + id);
    }

    public Result editPost() {
        DynamicForm form = Form.form().bindFromRequest();
        HashMap<String, String> postDataParams = generatePostData(form);
        postDataParams.put("newContent", form.field("newContent").value());
        APICall.postMessages("post/edit", Json.toJson(postDataParams));
        return ok("");
    }

    public HashMap<String, String> generatePostData(DynamicForm form) {
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("postId", form.field("postId").value());
        return postDataParams;
    }

    public Result sharePost() {
        DynamicForm form = Form.form().bindFromRequest();
        HashMap<String, String> postDataParams = generatePostData(form);
        postDataParams.put("sharerEmail", form.field("email").value());
        APICall.postMessages("share", Json.toJson(postDataParams));
        //System.out.println(Json.toJson(postDataParams));
        return ok("");
    }

    public Result deletePost(Integer id) {
        Form form = Form.form().bindFromRequest();
        APICall.requestURL("post/delete/" + id);
        return ok("");
    }

    public Result mainPage(Integer id) {
        /* if (name.equals("favicon.ico")) {
            return ok("");
        } */
        User user = APICall.getRemoteJson("users/" + id, User.class);
        String title = "Personalize Personalized Main Page of " + user.getUserName();
        List<Post> popularPosts = APICall.getRemotePostJsonArray("post/popular");
        List<Post> allPosts = APICall.getRemotePostJsonArray("post/follow/" + id);
        for (Post post: allPosts) {
            if (post.getLikeUsers().indexOf(user.getEmail()) != -1) {
                post.setLiked(true);
            } else {
                post.setLiked(false);
            }
        }
        for (Post post: popularPosts) {
            if (post.getLikeUsers().indexOf(user.getEmail()) != -1) {
                post.setLiked(true);
            } else {
                post.setLiked(false);
            }
        }/*
        User user = new User(1, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        User user1 = new User(2, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        User user2 = new User(3, "wcyz666", "aaa", "Zhexin", "Qiu", "ccc", "student", "student", "lu.li@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");

        List<Post> posts = new ArrayList<Post>();
        List<Comment> comments = new ArrayList<>();
        Post post = new Post(4, user1, "bbbbbbbbbbbbb", 1, new Date(), "public", comments);
        Comment comment = new Comment(1, "aaaaa", post, user1, new Date());
        comments.add(comment);
        posts.add(new Post(4, user1, "bbbbbbbbbbbbb", 1, new Date(), "public", comments));
        posts.add(new Post(4, user1, "bbbbbbbbbbbbb", 1, new Date(), "private", comments));
        List<Post> otherPosts = new ArrayList<Post>();
        otherPosts.add(new Post(1, user1, "bbbbbbbbb bbbbbbbbbbb", 1, new Date(), comments));
        otherPosts.add(new Post(2, user2, "bbbbbbbbb cccccc", 1, new Date(), comments));
        otherPosts.add(new Post(3, user1, "bbbbbbb dddddddddd", 1, new Date()));
        List<Post> popularPosts = new ArrayList<Post>();
        popularPosts.add(new Post(1, user1, "bbbbbbbbb bbbbbbbbbbb", 1, new Date()));
        popularPosts.add(new Post(2, user2, "bbbbbbbbb cccccc", 1, new Date()));
        popularPosts.add(new Post(3, user1, "bbbbbbb dddddddddd", 1, new Date()));
        return ok(mainPage.render("aaaaaaa", user, popularPosts, otherPosts));*/
        return ok(mainPage.render("Personal Page", user, popularPosts, allPosts));
    }
    public Result newMainPost() {
        Form<Post> form = new_post.bindFromRequest();
        HashMap<String, String> postDataParams = new HashMap<>();

        postDataParams.put("content", form.field("postContent").value());
        postDataParams.put("likes", "0");
        postDataParams.put("lon", form.field("longitude").value());
        postDataParams.put("lat", form.field("latitude").value());
        if (form.field("privacy").value() != null) {
            postDataParams.put("privacy", "public");
        } else {
            postDataParams.put("privacy", "private");
        }
        postDataParams.put("email", form.field("email").value());
        String id = form.field("authorId").value();

        APICall.postMessages("post", Json.toJson(postDataParams));
        return redirect("/home/person/" + id);
    }


    public Result searchPost() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();

        String keyWords = dynamicForm.field("srch-term").value();
        //List<User> searchUser = APICall.getRemoteUserJsonArray("search/user/fuzzy/" + keyWords);
        List<Post> searchPosts = APICall.getRemotePostJsonArray("search/post/" + keyWords);
        //User user = new User(1, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        //User user1 = new User(2, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        //User user2 = new User(3, "wcyz666", "aaa", "Zhexin", "Qiu", "ccc", "student", "student", "lu.li@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");

        List<User> searchUsers = new ArrayList<>();
        //<Post> searchPosts = new ArrayList<Post>();
        //searchPosts.add(new Post(1, user1, "bbbbbbbbb bbbbbbbbbbb", 1, new Date()));
        //searchPosts.add(new Post(2, user2, "bbbbbbbbb cccccc", 1, new Date()));
        //searchPosts.add(new Post(3, user1, "bbbbbbb dddddddddd", 1, new Date()));

        return ok(search.render(searchUsers, searchPosts));
    }
    public Result searchFuzzyUser() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String keyWords = dynamicForm.field("srch-term").value();
        List<User> searchUsers = APICall.getRemoteUserJsonArray("search/user/fuzzy/" + keyWords);
        //User user = new User(1, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        //User user1 = new User(2, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        //User user2 = new User(3, "wcyz666", "aaa", "Zhexin", "Qiu", "ccc", "student", "student", "lu.li@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");

        //List<User> searchUsers = new ArrayList<>();
        //searchUsers.add(user);
        //searchUsers.add(user1);
        //searchUsers.add(user2);
        List<Post> searchPosts = new ArrayList<Post>();

        return ok(search.render(searchUsers, searchPosts));
    }

    public Result searchExactUser() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        HashMap<String, String> postData = new HashMap<>();
        postData.put("firstName", dynamicForm.field("firstname").value());
        postData.put("lastName", dynamicForm.field("lastname").value());
        postData.put("email", dynamicForm.field("email").value());

        List<User> searchUsers = gson.fromJson(APICall.postMessages("user/autocomplete/exact", Json.toJson(postData)), new TypeToken<List<User>>() {}.getType());
        //User user = new User(1, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        //User user1 = new User(2, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        //User user2 = new User(3, "wcyz666", "aaa", "Zhexin", "Qiu", "ccc", "student", "student", "lu.li@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");

        //List<User> searchUsers = new ArrayList<>();
        //searchUsers.add(user);
        //searchUsers.add(user1);
        //searchUsers.add(user2);
        List<Post> searchPosts = new ArrayList<Post>();

        return ok(search.render(searchUsers, searchPosts));
    }

    public Result autoCompleteLastname() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String keyWords = dynamicForm.field("query").value();
        /*User user = new User(1, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        User user1 = new User(2, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        User user2 = new User(3, "wcyz666", "aaa", "Zhexin", "Qiu", "ccc", "student", "student", "lu.li@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        System.out.println(keyWords);
        List<User> searchUsers = new ArrayList<>();
        List<String> lastnames = new ArrayList<>();
        searchUsers.add(user);
        searchUsers.add(user1);
        searchUsers.add(user2);*/

        List<String> lastnames = new ArrayList<>();
        List<User> searchUsers = APICall.getRemoteUserJsonArray("user/autocomplete/lastname/" + keyWords);
        for (User userItem: searchUsers) {
            lastnames.add(userItem.getLastName());
        }
        HashMap<String, Object> resultJSON = new HashMap<>();
        resultJSON.put("query", keyWords);
        resultJSON.put("suggestions", lastnames);
        return ok(Json.toJson(resultJSON));
    }

    public Result autoCompleteFirstname() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String keyWords = dynamicForm.field("query").value();
        User user = new User(1, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        User user1 = new User(2, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        User user2 = new User(3, "wcyz666", "aaa", "Zhexin", "Qiu", "ccc", "student", "student", "lu.li@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        System.out.println(keyWords);
        List<User> searchUsers = new ArrayList<>();
        List<String> lastnames = new ArrayList<>();
        searchUsers.add(user);
        searchUsers.add(user1);
        searchUsers.add(user2);

        //List<String> lastnames = new ArrayList<>();
       // List<User> searchUsers = APICall.getRemoteUserJsonArray("user/autocomplete/firstname/" + keyWords);
        for (User userItem: searchUsers) {
            lastnames.add(userItem.getFirstName());
        }
        HashMap<String, Object> resultJSON = new HashMap<>();
        resultJSON.put("query", keyWords);
        resultJSON.put("suggestions", lastnames);
        return ok(Json.toJson(resultJSON));
    }

    public Result autoCompleteEmail() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String keyWords = dynamicForm.field("query").value();
        /*User user = new User(1, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        User user1 = new User(2, "wcyz666", "aaa", "cheng", "wang", "ccc", "student", "student", "wcyz666@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        User user2 = new User(3, "wcyz666", "aaa", "Zhexin", "Qiu", "ccc", "student", "student", "lu.li@126.com", "aaaa", "6506607607", "sssss", "sss,sss,ss", "Master");
        System.out.println(keyWords);
        List<User> searchUsers = new ArrayList<>();
        searchUsers.add(user);
        searchUsers.add(user1);
        searchUsers.add(user2);*/
        List<String> lastnames = new ArrayList<>();
        List<User> searchUsers = APICall.getRemoteUserJsonArray("user/autocomplete/email/" + keyWords);
        for (User userItem: searchUsers) {
            lastnames.add(userItem.getEmail());
        }
        HashMap<String, Object> resultJSON = new HashMap<>();
        resultJSON.put("query", keyWords);
        resultJSON.put("suggestions", lastnames);
        return ok(Json.toJson(resultJSON));
    }

    public Result newComment() {
        DynamicForm form = Form.form().bindFromRequest();
        HashMap<String, String> postDataParams = generatePostData(form);
        postDataParams.put("email", form.field("email").value());
        postDataParams.put("postId", form.field("postId").value());
        postDataParams.put("comment", form.field("comment").value());
        APICall.postMessages("comment", Json.toJson(postDataParams));
        //System.out.println(Json.toJson(postDataParams));
        return ok("");
    }

    public Result getCommentsByPost(Integer id) {
        List<Comment> comments = APICall.getRemoteCommentJsonArray("comment/post/" + id);
        return ok(Json.toJson(comments));
    }
}
