package cn.leegq.course5;

import java.util.List;

/**
 * @author Li GQ 2018/10/20
 */
public class ResultMap {
    private Class type;
    private List<ResultMapping> resultMappings;


    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public List<ResultMapping> getResultMappings() {
        return resultMappings;
    }

    public void setResultMappings(List<ResultMapping> resultMappings) {
        this.resultMappings = resultMappings;
    }

}
