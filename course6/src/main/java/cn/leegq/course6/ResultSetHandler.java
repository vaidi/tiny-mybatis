package cn.leegq.course6;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class ResultSetHandler {
    protected final ParameterHandler parameterHandler;
    private final ResultMap resultMap;

    protected ResultSetHandler(ParameterHandler parameterHandler,ResultMap resultMap){
        this.resultMap=resultMap;
        this.parameterHandler=parameterHandler;
    }

    public <E> List<E> handleResultSets(Statement stmt) throws SQLException{
        List<E> list=new ArrayList<>();
        ResultSet rs = stmt.getResultSet();
        Object resultObject=null;
        Class type=resultMap.getType();
        while(rs.next()){
            try {
                Constructor constructor = resultMap.getType().getDeclaredConstructor();
                resultObject=constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException();
            }
            for(ResultMapping mapping:resultMap.getResultMappings()){
                String column=mapping.getColumn();
                if(mapping.getJavaType()==String.class){
                    ObjUtil.setValue(resultObject, type, mapping.getProperty(), mapping.getJavaType(),rs.getString(column) );
                }else if(mapping.getJavaType()==Integer.class){
                    ObjUtil.setValue(resultObject, type, mapping.getProperty(), mapping.getJavaType(),rs.getInt(column) );
                }
            }
            list.add((E)resultObject);
        }
        rs.close();
        return list;
    }


}
