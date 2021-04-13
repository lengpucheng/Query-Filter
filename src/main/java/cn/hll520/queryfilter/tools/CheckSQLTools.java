package cn.hll520.queryfilter.tools;

import cn.hll520.queryfilter.QueryFilterException;
import cn.hll520.queryfilter.term.entiry.IFieldFilter;
import cn.hll520.queryfilter.term.entiry.IFieldSort;
import cn.hll520.queryfilter.term.entiry.Operate;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述： SQL语句检查工具 检查SQL恶意注入
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午9:22
 */
@Slf4j
public class CheckSQLTools {

    /**
     * 正则检验SQL
     */
    private static final String RGE = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)"
            + "|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr"
            + "|ascii|declare|exec|count|master|drop|execute|like|order|limit"
            + "|offset|from|where|set)\\b)";

    /**
     * 正则检验格式 SQL
     */
    private static final Pattern SQL_PATTERN = Pattern.compile(RGE, Pattern.CASE_INSENSITIVE);


    /**
     * 检查排序字段是否不含恶意
     *
     * @param fieldSort 字段排序
     * @return 如果通过检验返回本身 否则返回 null
     */
    public static IFieldSort checkSQLSort(IFieldSort fieldSort) {
        if (fieldSort == null || fieldSort.acquireFieldName() == null) {
            return null;
        }
        try {
            checkSQLInjection(fieldSort.acquireFieldName());
        } catch (QueryFilterException e) {
            // 如果包含SQL 注入或 相同内容 使用 ` 引用 (MySQL)
            fieldSort.receiveFieldName(treatSQLWordMysql(fieldSort.acquireFieldName()));
        }
        return fieldSort;
    }

    /**
     * 检查字符串内容中是否包含SQL注入语句
     *
     * @param str 增强字符串
     * @return 是否通过检查 <b>不包含返回true</b>
     */
    public static boolean checkSQLInjection(String str) {
        if (str == null) {
            return true;
        }
        Matcher matcher = SQL_PATTERN.matcher(str);
        if (matcher.find()) {
            String error = "### 在待增强语句中包含如下内容： " + matcher.group()
                    + "可能会导致SQL注入,已被自动拦截货处理，请检查或格式化增强语句！";
            log.error(error);
            throw new QueryFilterException("未能通过SQL语句注入检测！", error, str);
        }
        return true;
    }


    /**
     * 处理过滤条件 避免SQL注入
     *
     * @param fieldFilter 过滤条件
     * @return 处理后的过滤条件
     */
    public static IFieldFilter checkSQLFilter(IFieldFilter fieldFilter) {
        if (fieldFilter == null || fieldFilter.acquireFieldName() == null
                || fieldFilter.acquireFieldValue() == null) {
            return null;
        }
        // 检查字段
        try {
            checkSQLInjection(fieldFilter.acquireFieldName());
        } catch (QueryFilterException e) {
            // 如果包含SQL 注入或 相同内容 使用 ` 引用 (MySQL)
            fieldFilter.receiveFieldName(treatSQLWordMysql(fieldFilter.acquireFieldName()));
        }
        // 默认为like
        if (fieldFilter.acquireOperate() == null) {
            fieldFilter.receiveOperate(Operate.like);
        }
        // 处理Value
        fieldFilter.receiveFieldValue(checkSQLBranch(fieldFilter.acquireFieldValue()));
        return fieldFilter;
    }

    /**
     * 检验SQL中的分号
     *
     * @param str 待检验字符串
     * @return 将‘ 替换为 ’‘ 后的 字符串
     */
    public static String checkSQLBranch(String str) {
        if (str == null) {
            return null;
        }
        // 将一个 ‘ 替换为 两个 ’ 避免SQL注入
        return str.replace("'", "''");
    }

    /**
     * 单字段中包含保留字时的处理方法
     *
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String treatSQLWordMysql(String str) {
        // 将所有 ` 替换为 ``
        str = str.replace("`", "``");
        // 使用 `` 包裹
        str = "`" + str + "`";
        return str;
    }
}
