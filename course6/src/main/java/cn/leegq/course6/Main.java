package cn.leegq.course6;

import cn.leegq.course.domain.User;

import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class Main {
    /**
     * 关键两部
     * 1)加载配置Configuration
     * 2)获取SqlSession(它对接口做了代理)
     * @param args
     */
    public static void main(String[] args) {
        SqlSession sqlSession=SqlSessionFactory.openSession();
        UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
        List<User> users=userMapper.listUsers(90);
        System.out.println(users);
    }
}
