package cn.leegq.course7;

/**
 * @author Li GQ 2018/10/20
 */
public class ParameterMapping {

    private String property;
    private Class<?> javaType ;


    public ParameterMapping(String property, Class<?> javaType) {
        this.property = property;
        this.javaType = javaType;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }
}
