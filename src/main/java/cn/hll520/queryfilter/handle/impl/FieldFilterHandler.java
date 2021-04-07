package cn.hll520.queryfilter.handle.impl;

import cn.hll520.queryfilter.entiry.IFieldFilter;
import cn.hll520.queryfilter.entiry.Operate;
import cn.hll520.queryfilter.entiry.QueryFilterException;
import cn.hll520.queryfilter.term.ITermFilter;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.hll520.queryfilter.tools.CheckSQLTools.checkSQLFilter;
import static cn.hll520.queryfilter.tools.SQLHandleTools.*;

/**
 * 描述： 字段过滤处理器
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午9:57
 */
@Component
public class FieldFilterHandler {


    /**
     * 增强sql语句进行字段过滤
     *
     * @param filterTerm 过滤条件集
     * @param sql        待增强sql语句
     * @return 增强后的sql语句
     */
    public String enhanceFilterSQL(ITermFilter filterTerm, String sql) {
        // 若为null就直接返回
        if (filterTerm == null || sql == null) {
            return sql;
        }
        // 获取去除头尾空格和分号的 SQL 语句
        StringBuilder sqlEnhance = removeBranch(sql).append(" ");

        // 添加过滤条件集
        List<IFieldFilter> fieldFilters = filterTerm.acquireTermFilters();
        if (fieldFilters != null) {
            // 是否紧跟where (是否需要添加And)
            boolean isFist = true;
            // 遍历添加条件
            for (IFieldFilter fieldFilter : fieldFilters) {
                isFist = !addFilter(sqlEnhance, checkSQLFilter(fieldFilter), isFist);
            }
        }

        // 添加最后的;
        sqlEnhance.append(" ;");
        return sqlEnhance.toString();
    }


    /**
     * 添加过滤条件进行
     *
     * @param sql         sql 语句
     * @param filedFilter 过滤条件
     * @param isFist      是否紧跟where
     * @return 是否添加成功
     */
    private boolean addFilter(StringBuilder sql, IFieldFilter filedFilter, boolean isFist) {
        if (filedFilter == null || filedFilter.acquireFieldName() == null
                || filedFilter.acquireFieldValue() == null) {
            return false;
        }

        // sql 语句 构造
        if (isFist && !isWhere(sql)) {
            // 如果是第一次添加并且非紧跟where 进行where 检查
            checkContainWhere(sql);
        } else {
            //  否则 添加 and
            sql.append(" and ");
        }


        Operate operator = filedFilter.acquireOperate();
        // 若操作符为设置默认为 like
        operator = operator == null ? Operate.like : operator;

        // 添加条件
        sql.append("`").append(filedFilter.acquireFieldName()).append("`");
        // 添加操作
        switch (operator) {
            case eq:
                sql.append("=");
                break;
            case neq:
                sql.append("!=");
                break;
            case gt:
                sql.append(">");
                break;
            case gte:
                sql.append(">=");
                break;
            case lt:
                sql.append("<");
                break;
            case lte:
                sql.append("<=");
                break;
            case in:
                sql.append(" in ");
                break;
            case nin:
                sql.append(" not in ");
                break;
            case like:
                sql.append(" like ");
                break;
            case contains:
                throw new QueryFilterException("暂时不支持使用 [contains] 进行字段过滤",
                        "使用 [contains] 进行过滤操作时 形式为 contains(filed,value) 可能导致拼接移除" +
                                " 若需使用 包含或者模糊匹配过滤 请使用 [like] 并将 value 设置为 %value% 格式", sql.toString());
        }
        // 添加内容
        sql.append("'").append(filedFilter.acquireFieldValue()).append("' ");
        return true;
    }

}
