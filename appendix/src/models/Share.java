package models;

import javax.persistence.*;
import java.util.Date;

/**
 * share table to store the share relationships of posts and users
 * Created by qiuzhexin on 11/16/15.
 */

@Entity
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH}) // need to manually delete
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH}) // need to manually delete
    @JoinColumn(name = "sharerId", referencedColumnName = "id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


    public Share() {
    }

    public Share(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Share{" +
                "id=" + id +
                ", post=" + post +
                ", user=" + user +
                ", createTime=" + createTime +
                '}';
    }
}
