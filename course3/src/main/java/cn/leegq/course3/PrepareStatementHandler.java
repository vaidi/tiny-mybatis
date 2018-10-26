package cn.leegq.course3;

import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 设置参数，执行sql，返回结果一次搞定
 * @author Li GQ 2018/10/20
 */
public class PrepareStatementHandler implements StatementHandler {
    private final BoundSql boundSql;
    Class<?> resultType;

    protected PrepareStatementHandler(BoundSql boundSql,Class<?> resultType) {
        this.boundSql=boundSql;
        this.resultType=resultType;
    }

    /**
     * 设置参数，statement.setXX 操作必须得知道参数类型
     * @param statement
     * @throws SQLException
     */
    public void parameterize(PreparedStatement statement) throws SQLException {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String propertyName = parameterMapping.getProperty();
            Class javaType=parameterMapping.getJavaType();
            if(javaType==Integer.class){
                statement.setInt(i+1,(Integer)boundSql.getParameterObject());
            }
            if(javaType==String.class){
                statement.setString(i+1,(String)boundSql.getParameterObject());
            }
        }
    }

    /**
     * 这里认为sql列和java对象属性对应
     * @param connection
     * @param <E>
     * @return
     * @throws SQLException
     */
    @Override
    public <E> List<E> query(Connection connection) throws SQLException {
        PreparedStatement pstm=connection.prepareStatement(boundSql.getSql());
        parameterize(pstm);
        pstm.execute();
        List<E> list=new ArrayList<>();
        ResultSet rs = pstm.getResultSet();
        Object resultObject=null;
        ResultSetMetaData resultSetMetaData=rs.getMetaData();
        int columnCount=resultSetMetaData.getColumnCount();
        while(rs.next()){
            try {
                Constructor constructor = resultType.getDeclaredConstructor();
                resultObject=constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException();
            }
            for(int i=1;i<=columnCount;i++){
                String columnName=resultSetMetaData.getColumnName(i);
                Class fieldType=ObjUtil.getFieldType(resultType, columnName);
                if(fieldType==Integer.class){
                    ObjUtil.setValue(resultObject,resultType,columnName,fieldType,rs.getInt(i));
                }else if(fieldType==String.class){
                    ObjUtil.setValue(resultObject,resultType,columnName,fieldType,rs.getString(i));
                }
            }
            list.add((E) resultObject);
        }
        rs.close();
        return list;
    }
}
