package cn.leegq.course7;

import cn.leegq.course.domain.User;
import cn.leegq.course7.mapper.UserMapper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        ClassPathXmlApplicationContext configApplicationContext =
                new ClassPathXmlApplicationContext("spring.xml");
        UserMapper service=configApplicationContext.getBean(UserMapper.class);
        List<User> users=service.listUsers(90);
        System.out.println(users);
    }
}
