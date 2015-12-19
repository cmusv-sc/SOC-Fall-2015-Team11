package models;

/**
 * bean for follow testing
 * Created by qiuzhexin on 11/20/15.
 */
public class FollowBean {
    private String followerEmail;
    private String followeeEmail;

    public FollowBean() {
    }

    public String getFollowerEmail() {
        return followerEmail;
    }

    public String getFolloweeEmail() {
        return followeeEmail;
    }

    public void setFollowerEmail(String followerEmail) {
        this.followerEmail = followerEmail;
    }

    public void setFolloweeEmail(String followeeEmail) {
        this.followeeEmail = followeeEmail;
    }
}
