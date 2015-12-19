package model;

import java.util.Date;
import java.util.List;

import myutil.Constants;

public class Post {

    private long id;

    private User user;
    private String privacy;
    private String longitude;
    private String latitude;
    private String content;
    private int likes;
    private boolean isLiked;
    private Date createTime;
    private List<String> likeUsers;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    private List<Comment> comments;
    private static final String ADD_POST_CALL = Constants.BACKEND_URL + "newPost";

    public Post() {
    }
    public Post(long id, User user, String content, int likes, Date createTime, List<Comment> comments) {
        this.id = id;
        this.longitude = "100";
        this.latitude = "100";
        this.user = user;
        this.content = content;
        this.likes = likes;
        this.createTime = createTime;
        this.comments = comments;
    }
    public Post(long id, User user, String content, int likes, Date createTime) {
        this.id = id;
        this.longitude = "100";
        this.latitude = "100";
        this.user = user;
        this.content = content;
        this.likes = likes;
        this.createTime = createTime;
    }
    public Post(long id, User user, String content, int likes, Date createTime, String privacy, List<Comment> comments) {
        this.id = id;
        this.longitude = "100";
        this.latitude = "100";
        this.user = user;
        this.content = content;
        this.likes = likes;
        this.createTime = createTime;
        this.privacy = privacy;
        this.comments = comments;
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

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public List<String> getLikeUsers() {
        return likeUsers;
    }

    public void setLikeUsers(List<String> likeUsers) {
        this.likeUsers = likeUsers;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    /**
     * Create a new POST
     *
     * @param jsonData
     * @return the response from the API server
     */

}
