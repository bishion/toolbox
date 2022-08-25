package cn.bishion.pier.web;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.util.List;
import java.util.UUID;

public class MybatisUtil {
    private static final Logger log = LoggerFactory.getLogger(MybatisUtil.class);
    private static final Configuration configuration = new Configuration();
    private static DocumentBuilder documentBuilder;

    static {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(false);
        try {
            documentBuilder = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    /**
     * 该方法主要用来解析动态sql,可以使用mybatis的所有标签
     * 解析和赋值的方式都是由mybatis 完成的
     * 赋值绑定几乎完全使用该类 {@link  org.apache.ibatis.scripting.defaults.DefaultParameterHandler#setParameters(java.sql.PreparedStatement)}
     *
     * @param xmlSQL          eg:  <select> mybatisXML sql 语句</select>
     * @param parameterObject 对应的参数
     * @return 解析后的sql 语句
     */
    public static String parseDynamicXMLFormXmlStr(String xmlSQL, Object parameterObject) {

        log.info("原始sqlXml:{} , params:{}", xmlSQL, JSON.toJSONString(parameterObject));
        //解析成xml
        Document doc = parseXMLDocument(xmlSQL);
        if (doc == null) {
            return null;
        }
        //走mybatis 流程 parse成Xnode
        XNode xNode = new XNode(new XPathParser(doc, false), doc.getFirstChild(), null);
        // 之前的所有步骤 都是为了构建 XMLScriptBuilder 对象,
        XMLScriptBuilder xmlScriptBuilder = new XMLScriptBuilder(configuration, xNode);

        //解析 静态xml 和动态的xml
        SqlSource sqlSource = xmlScriptBuilder.parseScriptNode();
        MappedStatement ms = new MappedStatement.Builder(configuration, UUID.randomUUID().toString(), sqlSource, null).build();

        //将原始sql 与 参数绑定
        BoundSql boundSql = ms.getBoundSql(parameterObject);

        //获得 预编译后的 sql
        String resultSql = boundSql.getSql();
        //将'  ？  '和"  ？  " 替换为 ？
        String executeSql = resultSql.replaceAll("(\'\\s*\\?\\s*\')|(\"\\s*\\?\\s*\")", "?");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    executeSql = executeSql.replaceFirst("[?]", value instanceof String ? "'" + value + "'" : String.valueOf(value));
                }
            }
        }
        //格式化 sql 移除多余空格
        log.info("removeExtraWhitespace -> executeSql: {}", SqlSourceBuilder.removeExtraWhitespaces(executeSql));
        return executeSql;
    }


    private static Document parseXMLDocument(String xmlString) {
        if (StringUtils.isBlank(xmlString)) {
            log.error("动态解析的xmlString 不能为空!!");
            return null;
        }
        try {
            return documentBuilder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            log.error("XML解析异常,请检查XML格式是否正确,errMsg:{}",e.getMessage());
        }
        return null;

    }

}
