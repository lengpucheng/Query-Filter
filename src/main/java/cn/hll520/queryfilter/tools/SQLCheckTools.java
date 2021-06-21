package cn.hll520.queryfilter.tools;

import cn.hll520.queryfilter.QueryFilterException;
import cn.hll520.queryfilter.term.entiry.*;
import cn.hll520.queryfilter.tools.handle.HandleTools;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.hll520.queryfilter.tools.SQLHandleTools.build;

/**
 * 描述： SQL语句检查工具 检查SQL恶意注入
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午9:22
 */
@Slf4j
public class SQLCheckTools {

    /**
     * 正则检验SQL 匹配所有前后带空格的关键字
     */
    private static final String RGE = "|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr"
            + "|ascii|declare|exec|count|master|drop|execute|like|order|limit|offset|from|where|set)\\b)";

    /**
     * 正则检验格式 SQL
     */
    private static final Pattern SQL_PATTERN = Pattern.compile(RGE, Pattern.CASE_INSENSITIVE);

    /**
     * SQL处理工具
     */
    private static final HandleTools sqlHandle = build();

    /**
     * 检查排序字段是否不含恶意
     *
     * @param sort 字段排序
     * @return 如果通过检验返回本身 否则返回 null
     */
    public static IFieldSort checkSQLSort(IFieldSort sort) {
        if (sort == null || sort.acquireFieldName() == null) {
            return null;
        }
        // 字段处理
        checkField(sort);
        // 默认降序
        if (sort.acquireSort() == null) {
            sort.receiveSort(SortTerm.desc);
        }
        return sort;
    }

    /**
     * 处理过滤条件 避免SQL注入
     *
     * @param fieldFilter 过滤条件
     * @return 处理后的过滤条件
     */
    public static IFieldFilter checkSQLFilter(IFieldFilter fieldFilter) {
        // 检验判空和字段
        if (fieldFilter == null || fieldFilter.acquireFieldName() == null
                || fieldFilter.acquireValue() == null) {
            return null;
        }
        // 字段处理
        checkField(fieldFilter);
        // 默认为like
        if (fieldFilter.acquireOperate() == null) {
            fieldFilter.receiveOperate(Operate.like);
        }
        // 处理引号
        if (fieldFilter.acquireValue() == null) {
            fieldFilter.receiveValue(sqlHandle.handleQuota(fieldFilter.acquireValue()));
        }
        return fieldFilter;
    }

    /**
     * 进行字段检验
     *
     * @param field 待检验字段
     */
    public static void checkField(IField field) {
        if (field == null) {
            return;
        }
        try {
            // 检验SQL字段
            checkSQLInjection(field.acquireFieldName());
        } catch (QueryFilterException e) {
            // 对恶意注入字段进行处理
            field.receiveFieldName(sqlHandle.handleField(field.acquireFieldName()));
        }
    }

    /**
     * 检查字符串内容中是否包含SQL注入语句
     *
     * @param str 待检测内容
     * @return 是否通过检查 <b>不包含返回true</b>
     */
    public static boolean checkSQLInjection(String str) {
        if (str == null) {
            return true;
        }
        Matcher matcher = SQL_PATTERN.matcher(str);
        if (matcher.find()) {
            String error = "条件[" + str + "]中包含SQL注入语句——>{" + matcher.group() + "} 已被拦截处理";
            log.error(error);
            throw new QueryFilterException("条件中包含SQL注入语句或关键字", error, str);
        }
        return true;
    }

    /**
     * 移除末尾的分号和空格
     *
     * @param sql 语句
     * @return 移除分号和空格后的sql语句
     */
    public static String removeBranch(String sql) {
        // 去除两边空格
        sql = sql.trim();
        int length = sql.length();
        Character branch = null;
        // 取最后一个字符
        if (length > 1) {
            branch = sql.charAt(length - 1);
        }
        // 如果是分号就移除
        if (branch != null && branch == ';') {
            sql = sql.substring(0, length - 1).trim();
            // 继续判断
            return removeBranch(sql);
        }
        return sql;
    }

    /**
     * 是否包含where
     *
     * @param sql sql语句
     * @return 是否包含
     */
    public static boolean isHaveWhere(StringBuilder sql) {
        return sql.toString().toLowerCase(Locale.ROOT).contains(" where ");
    }
}
