package cn.leegq.course1;

import cn.leegq.course.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class UserMapper {
    /**
     * 1)获取数据源配置
     * 2)创建连接
     * 3)开启事务
     * 4)parse sql
     * 5)设置参数
     * 6)执行
     * 7)解析resultSet
     * 8)关闭
     * @param minAge
     * @return
     */
    public List<User> listUsers(int minAge) {
        List<User> users=new ArrayList<>();
        String URL = "jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8";
        String USER = "root";
        String PASSWORD = "11111";
        Connection conn=null;
        PreparedStatement pstm=null;
        ResultSet rs=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //2.获得数据库链接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false);
            //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            pstm = conn.prepareStatement("select * from user where age> ? ");
            pstm.setInt(1,minAge);
            rs = pstm.executeQuery();
            //4.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                User user=new User();
                user.setUid(rs.getInt("uid"));
                user.setAge(rs.getInt("age"));
                user.setName(rs.getString("name"));
                users.add(user);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(rs!=null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(pstm!=null)
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return users;
    }
}
