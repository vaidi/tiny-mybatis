package cn.leegq.course7;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Li GQ 2018/10/23
 */
public class MapperProxy<T> implements InvocationHandler{
    private Class<T> type;
    private SqlSession sqlSession;

    public MapperProxy(Class<T> type, SqlSession sqlSession) {
        this.type = type;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return sqlSession.selectList(type.getName() + "." + method.getName(), args[0]);
    }
}
