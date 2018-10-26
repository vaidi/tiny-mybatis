package cn.leegq.course6;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Li GQ 2018/10/21
 */
public class Configuration {
    public static void main(String[] args) {
        Configuration conf=Configuration.getInstance();
        System.out.println(conf.boundSqlMap);
        System.out.println(conf.resultMaps);
    }

    protected  Map<String, ResultMap> resultMaps = new HashMap();
    protected  Map<String, BoundSql> boundSqlMap = new HashMap();
    protected  Map<String, ResultMap> statementResultMaps = new HashMap();

    private static Configuration configuration;

    public static Configuration getInstance() {
        if (configuration == null) {
            synchronized (Configuration.class) {
                configuration = build();
            }
        }
        return configuration;
    }

    private static Configuration build() {
        Configuration conf=new Configuration();
        try{
            InputStream in = Configuration.class.getClassLoader().getResourceAsStream("mybatis.xml");
            Reader reader = new InputStreamReader(in);
            Document doc = createDocument(new InputSource(reader));
            Element root = doc.getDocumentElement();
            NodeList mappers = root.getElementsByTagName("mappers");
            Element mappersEl = (Element) mappers.item(0);
            NodeList list = mappersEl.getElementsByTagName("mapper");
            for (int i = 0; i < list.getLength(); i++) {
                Element each = (Element) list.item(i);
                Reader mapperReader = new InputStreamReader(Configuration.class.getClassLoader()
                        .getResourceAsStream(each.getAttribute("resource")));
                Document mapperDoc = createDocument(new InputSource(mapperReader));
                parseMapper(mapperDoc,conf);
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
        return conf;
    }

    private static void parseMapper(Document document,Configuration conf) throws Exception{
        Map<String, ResultMap> resultMaps = new HashMap<>();
        Map<String, BoundSql> boundSqlMap = new HashMap<>();
        Map<String, ResultMap> statementResultMaps = new HashMap();

        Element mapper = document.getDocumentElement();
        String namespace=mapper.getAttribute("namespace");
        NodeList resultMapNodes=mapper.getElementsByTagName("resultMap");

        for(int i=0;i<resultMapNodes.getLength();i++){
            Element resultMapEle = (Element) resultMapNodes.item(i);
            String id=resultMapEle.getAttribute("id");
            String type=resultMapEle.getAttribute("type");
            Class c=null;
            try {
                c=Class.forName(type);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("错误的命名空间");
            }
            NodeList resultNodes=resultMapEle.getElementsByTagName("result");
            ResultMap resultMap=new ResultMap();
            List<ResultMapping> rms=new ArrayList<>();
            resultMap.setType(c);
            resultMap.setResultMappings(rms);
            for(int j=0;j<resultNodes.getLength();j++){
                Element resultEle=(Element)resultNodes.item(j);
                ResultMapping mapping=new ResultMapping(resultEle.getAttribute("column"),
                        resultEle.getAttribute("property"),Class.forName(resultEle.getAttribute("javaType")),null);
                rms.add(mapping);
            }
            resultMaps.put(namespace+"."+id,resultMap);
        }

        NodeList selectNodes=mapper.getElementsByTagName("select");
        for(int i=0;i<selectNodes.getLength();i++){
            Element select =(Element)selectNodes.item(i);
            String sql=select.getTextContent();
            String id=select.getAttribute("id");
            String resultMap=select.getAttribute("resultMap");
            //通过复杂方式获取，这里简化
            List<ParameterMapping> paramMappings=new ArrayList<>();
            ParameterMapping parameterMapping=new ParameterMapping("id",Integer.class);
            paramMappings.add(parameterMapping);

            BoundSql boundSql=new BoundSql(sql,paramMappings,null);
            boundSqlMap.put(namespace+"."+id,boundSql);
            statementResultMaps.put(namespace+"."+id,resultMaps.get(namespace+"."+resultMap));
        }
        conf.boundSqlMap=boundSqlMap;
        conf.resultMaps=resultMaps;
        conf.statementResultMaps=statementResultMaps;
    }

    private static Document createDocument(InputSource inputSource) {
        // important: this must only be called AFTER common constructor
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(false);
            factory.setCoalescing(false);
            factory.setExpandEntityReferences(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(inputSource);
        } catch (Exception e) {
            throw new RuntimeException("Error creating document instance.  Cause: " + e, e);
        }
    }
}
