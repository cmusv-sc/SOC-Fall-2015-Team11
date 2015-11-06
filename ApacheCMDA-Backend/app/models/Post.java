package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by qiuzhexin on 11/5/15.
 */
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
    private User user;

    private String content;

    private int likes;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Post() {
    }

    public Post(User user, String content, int likes, Date createTime) {
        this.user = user;
        this.content = content;
        this.likes = likes;
        this.createTime = createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
