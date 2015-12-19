package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Follow to store a follow relationship of two users
 * Created by qiuzhexin on 11/20/15.
 */
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH}) // need to manually delete
    @JoinColumn(name = "followerId", referencedColumnName = "id")
    private User follower;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH}) // need to manually delete
    @JoinColumn(name = "sharerId", referencedColumnName = "id")
    private User followee;

    public Follow() {
    }

    public Follow(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }

    public long getId() {
        return id;
    }

    public User getFollower() {
        return follower;
    }

    public User getFollowee() {
        return followee;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", follower=" + follower +
                ", followee=" + followee +
                '}';
    }
}
