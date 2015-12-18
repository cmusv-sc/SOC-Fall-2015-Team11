package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Comment on a post
 * Created by qiuzhexin on 12/1/15.
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade =  {CascadeType.DETACH}) // Detached: need to manually delete all if post deleted
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER, cascade =  {CascadeType.DETACH}) // Detached: need to manually delete all if user deleted
    @JoinColumn(name = "commenterId", referencedColumnName = "id")
    private User commenter;

    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Comment() {
    }

    public Comment(Post post, User commenter, String comment, Date createTime) {
        this.post = post;
        this.commenter = commenter;
        this.comment = comment;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public User getCommenter() {
        return commenter;
    }

    public String getComment() {
        return comment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", post=" + post +
                ", commenter=" + commenter +
                ", comment='" + comment + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
