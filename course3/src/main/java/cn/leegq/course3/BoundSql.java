package cn.leegq.course3;

import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class BoundSql {
    private final String sql;
    private final List<ParameterMapping> parameterMappings;
    private final Object parameterObject;

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
}
