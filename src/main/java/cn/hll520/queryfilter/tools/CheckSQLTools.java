package cn.hll520.queryfilter.tools;

import cn.hll520.queryfilter.entiry.IFieldFilter;
import cn.hll520.queryfilter.entiry.IFieldSort;
import cn.hll520.queryfilter.entiry.Operate;
import cn.hll520.queryfilter.entiry.QueryFilterException;
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
    private static final String RGE = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr"
            + "|ascii|declare|exec|count|master|into|drop|execute)\\b)";

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
        if (fieldSort == null || checkSQLInjection(fieldSort.acquireFieldName())) {
            return fieldSort;
        }
        return null;
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
                    + "可能会导致SQL注入已被拦截，请检查或格式化增强语句！";
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
        if (fieldFilter == null) {
            return null;
        }
        fieldFilter.receiveFieldName(checkSQLBranch(fieldFilter.acquireFieldName()));
        // 默认为like
        if (fieldFilter.acquireOperate() == null) {
            fieldFilter.receiveOperate(Operate.like);
        }
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
}
