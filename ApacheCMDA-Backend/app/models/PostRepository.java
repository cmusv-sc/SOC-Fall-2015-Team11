package models;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by qiuzhexin on 11/5/15.
 */
public interface PostRepository extends CrudRepository<Post, Long> {
    // find post by user
    Post findByUser(User user);
    // inser a post

}
