package cn.bishion.pier.web;

import cn.bishion.pier.entity.UserInfo;
import cn.bishion.pier.mapper.UserInfoMapper;
import cn.bishion.toolkit.common.dto.PageResult;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserInfoMapper userInfoMapper;

    @GetMapping("/list/page")
    public PageResult<UserInfo> listPage(){
//        IPage<UserInfo> page = new Page<>(1,2);
//        Map<String, String> param= new HashMap<>();
//        param.put("username","1");
//        IPage<UserInfo> userInfoIPage = userInfoMapper.selectAll(page, "1");
//        return PageDTO.of(userInfoIPage.getTotal(), userInfoIPage.getRecords());
        return null;
    }
    @GetMapping("/list")
    public List<Map> list(){
        Map<String, String> param= new HashMap<>();
        param.put("username","1");
        List<Map> list = userInfoMapper.selectMap("1");
        return list;
    }
 @GetMapping("/list1")
    public List<Map> list1(){
        Map<String, String> param= new HashMap<>();
        param.put("username","1");
        List<Map> list = userInfoMapper.selectMap1("1");
        return list;
    }

    @GetMapping("/list/sql")
    public String listSql() throws JSQLParserException {
        String str ="<select id=\"selectMap\" resultType=\"java.util.LinkedHashMap\" parameterType=\"string\">\n" +
                "        select * from user_info\n" +
                "        <where>\n" +
                "            <if test=\"username !=null\">\n" +
                "                username=#{username}\n" +
                "            </if>\n" +
                "        </where>\n" +
                "\n" +
                "    </select>";
        Map<String,String> param = new HashMap<String,String>(){
            {
                put("username","1");
            }
        };
        String sql = MybatisUtil.parseDynamicXMLFormXmlStr(str, param);
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        plainSelect.setSelectItems(COUNT_SELECT_ITEM);
        System.out.println(plainSelect);
        return sql;
    }

    protected static final List<SelectItem> COUNT_SELECT_ITEM = Collections.singletonList(defaultCountSelectItem());

    private static SelectItem defaultCountSelectItem() {
        Function function = new Function();
        function.setName("COUNT");
        function.setAllColumns(true);
        return new SelectExpressionItem(function);
    }
}
