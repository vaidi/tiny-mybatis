package cn.leegq.course6;

import cn.leegq.course.domain.User;

import java.util.List;

/**
 *
 * @author Li GQ 2018/10/20
 */
public interface UserMapper {

    public List<User> listUsers(int id);
}
