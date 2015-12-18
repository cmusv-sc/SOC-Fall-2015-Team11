package models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by qiuzhexin on 11/20/15.
 */
public interface FollowRepository extends CrudRepository<Follow, Long> {
    // find follow relationships of a user who is a followee
    List<Follow> findByFollowee(User followee);
    // find follow relationships by follower
    List<Follow> findByFollower(User follower);
}
