package controllers;

import model.Post;
import model.User;
import myutil.APICall;
import play.mvc.*;

import views.html.*;

import java.util.List;

public class Application extends Controller {

    public Result index(String name) {
        User user = APICall.getRemoteJson("user/" + name, User.class);
        List<Post> posts = APICall.getRemoteJson("post/" + name, List.class);
        List<Post> otherPosts = APICall.getRemoteJson("post/" + name, List.class);
        return ok(index.render("Your new application is ready.", user, posts, otherPosts));
    }

}
