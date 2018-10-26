package cn.leegq.course7;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class ParameterHandler {
    private final BoundSql boundSql;

    public ParameterHandler(BoundSql boundSql){
        this.boundSql=boundSql;
    }
    public void setParameters(PreparedStatement ps)
            throws SQLException{
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String propertyName = parameterMapping.getProperty();
            Class javaType=parameterMapping.getJavaType();
            if(javaType==Integer.class){
                ps.setInt(i+1,(Integer)boundSql.getParameterObject());
            }
            if(javaType==String.class){
                ps.setString(i+1,(String)boundSql.getParameterObject());
            }
        }
    }
}
