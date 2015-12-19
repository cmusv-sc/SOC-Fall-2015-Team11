package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Post table to store posts created by each user
 * Created by qiuzhexin on 11/5/15.
 */
@Entity
public class Post implements Comparable<Post> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH}) // Detached: need to manually delete all if users deleted
    @JoinColumn(name = "creatorId", referencedColumnName = "id")
    private User user;

    private String content;

    private int likes;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private ArrayList<String> likeUsers = new ArrayList<>();

    private String privacy;

    private String latitude;
    private String longitude;

    public Post() {
    }

    public Post(long id, User user, String content, int likes, Date createTime, String privacy, String latitude, String longitude) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.likes = likes;
        this.createTime = createTime;
        this.privacy = privacy;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Post(User user, String content, int likes, Date createTime, String privacy, String latitude, String longitude) {
        this.user = user;
        this.content = content;
        this.likes = likes;
        this.createTime = createTime;
        this.latitude = latitude;
        this.privacy = privacy;
        this.longitude = longitude;
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

    public ArrayList<String> getLikeUsers() {
        return likeUsers;
    }

    public void setLikeUsers(ArrayList<String> likeUsers) {
        this.likeUsers = likeUsers;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean addLike(String usrEmail) {
        if (likeUsers.contains(usrEmail))
            return false;
        likes++;
        likeUsers.add(usrEmail);
        return true;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", createTime=" + createTime +
                ", likeUsers=" + likeUsers +
                ", privacy='" + privacy + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    @Override
    public int compareTo(Post o) {
        int other = Integer.parseInt(String.valueOf(o.getId()));
        int my = Integer.parseInt(String.valueOf(id));
        // in time desc order
        return other - my;
    }
}
