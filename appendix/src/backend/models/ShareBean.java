package models;

/**
 * Bean for share testing
 * Created by qiuzhexin on 11/16/15.
 */
public class ShareBean {
    private String sharerEmail;
    private String postId;

    public ShareBean() {
    }

    public String getSharerEmail() {
        return sharerEmail;
    }

    public String getPostId() {
        return postId;
    }

    public void setSharerEmail(String sharerEmail) {
        this.sharerEmail = sharerEmail;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
