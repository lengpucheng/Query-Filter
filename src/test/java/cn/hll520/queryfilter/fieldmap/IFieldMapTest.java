package cn.hll520.queryfilter.fieldmap;


import org.junit.Test;

import java.util.Map;

/**
 * 描述：转换测试
 *
 * @author lpc
 * @version 1.0 2021/4/7
 * @since 2021/4/7-下午9:49
 */
public class IFieldMapTest {

    @Test
    public void humpLine() {
        String name = IFieldMapLineToHump.build().fieldMap("query_filter_source_code");
        System.out.println(name);
        System.out.println(IFieldMapHumpToLine.build().fieldMap(name));
    }

    @Test
    public void fieldMap() {
        IFieldMap map = new IFieldMapHumpToLine() {
            @Override
            public Map<String, String> fieldMap(Map<String, String> map) {
                map.put("userName", "username");
                map.put("passWord", "password");
                return map;
            }
        };
        System.out.println(map.fieldMap("userName"));
        System.out.println(map.fieldMap("userInfoName"));
        System.out.println(map.fieldMap("passWord"));
        System.out.println(map.fieldMap("password"));
        System.out.println(map.fieldMap("pass_Word"));
    }

    @Test
    public void addFix() {
        IFieldMap map = new IFieldMapLineToHump() {
            @Override
            public String fieldPrefix() {
                return "app_";
            }

            @Override
            public String fieldSuffix() {
                return "_fix";
            }
        };
        System.out.println(map.fieldMap("user_name"));
        System.out.println(map.fieldMap("user_pass"));
        System.out.println(map.fieldMap("_pass_word_"));
    }

}