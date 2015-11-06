package models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by qiuzhexin on 11/5/15.
 */
public interface PostRepository extends CrudRepository<Post, Long> {
    // find post by user
    List<Post> findAllByUser(User user);

}
