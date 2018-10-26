package cn.leegq.course4;

import cn.leegq.course.domain.User;

import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class Main {
    public static void main(String[] args) {
        UserMapper userMapper=new UserMapper();
        List<User> users=userMapper.listUsers(90);
        System.out.println(users);
    }
}
