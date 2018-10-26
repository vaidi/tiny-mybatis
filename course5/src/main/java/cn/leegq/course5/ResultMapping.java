package cn.leegq.course5;

public class ResultMapping {
    private String column;
    private String property;
    private Class javaType;
    private Class jdbcType;

    public ResultMapping(String column, String property, Class javaType, Class jdbcType) {
        this.column = column;
        this.property = property;
        this.javaType = javaType;
        this.jdbcType = jdbcType;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Class getJavaType() {
        return javaType;
    }

    public void setJavaType(Class javaType) {
        this.javaType = javaType;
    }

    public Class getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(Class jdbcType) {
        this.jdbcType = jdbcType;
    }
}