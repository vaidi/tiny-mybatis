package cn.leegq.course7;

import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class BoundSql {
    private   String sql;
    private  List<ParameterMapping> parameterMappings;
    private  Object parameterObject;

    public BoundSql(String sql, List<ParameterMapping> parameterMappings,Object parameterObject) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject= parameterObject;
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public Object getParameterObject() {
        return parameterObject;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }

    public void setParameterObject(Object parameterObject) {
        this.parameterObject = parameterObject;
    }
}
