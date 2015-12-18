package models;

import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

/**
 * operations on Comment table
 * Created by qiuzhexin on 12/1/15.
 */

@Named
@Singleton
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByPostOrderByCreateTimeAsc(Post post);
    List<Comment> findByPost(Post post);
}
