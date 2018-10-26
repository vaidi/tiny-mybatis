package cn.leegq.course6;

import java.util.List;

/**
 * @author Li GQ 2018/10/21
 */
public interface SqlSession {

   <T> List<T> selectList(String statement, Object parameter);

    <T> T getMapper(Class<T> type);
}
