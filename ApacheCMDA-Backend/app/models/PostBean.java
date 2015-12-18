package models;

/**
 * Created by qiuzhexin on 11/15/15.
 */
// post object for testing
public class PostBean {
    private String content;
    private String likes;
    private String email;

    public PostBean() {
    }

    public PostBean(String content, String likes, String email) {
        this.content = content;
        this.likes = likes;
        this.email = email;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public String getLikes() {
        return likes;
    }

    public String getEmail() {
        return email;
    }
}
