package cn.leegq.course7.mapper;

import cn.leegq.course.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
@Repository
public interface UserMapper {

    public List<User> listUsers(int id);
}
