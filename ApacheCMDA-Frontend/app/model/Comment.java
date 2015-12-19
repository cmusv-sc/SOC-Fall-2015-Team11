package model;

import java.util.Date;

public class Comment {
    private long id;
    private String comment;
    private Post post;
    private User commenter;
    private Date createTime;

    public Comment(long id, String comment, Post post, User commenter, Date createTime) {
        this.id = id;
        this.comment = comment;
        this.post = post;
        this.commenter = commenter;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}


