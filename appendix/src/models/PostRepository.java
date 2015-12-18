package models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;

/**
 * Repository to achieve post CRUD functions
 * Created by qiuzhexin on 11/5/15.
 */

public interface PostRepository extends CrudRepository<Post, Long> {
    // find post by user
    List<Post> findByUserOrderByCreateTimeDesc(User user);

    // get all posts given a user id in time descending order
    @Query(value = "select p.* from Post p, User u where p.creatorId=u.id order by p.createTime desc", nativeQuery = true)
    List<Post> getPostByUserOrderByCreateTimeDesc();

    // get posts by likes
    @Query(value = "select * from Post order by likes desc", nativeQuery = true)
    List<Post> getPostsOrderByLikes();

    // get posts whose contents contain keyword
    @Query(value = "select * from Post where lower(content) like ?1 order by createTime", nativeQuery = true)
    List<Post> getPostsContainsKeywordOrderByCreateTime(String keyword);

}
