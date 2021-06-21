package cn.hll520.queryfilter.handle.impl;

import cn.hll520.queryfilter.QueryFilterException;
import cn.hll520.queryfilter.handle.IQueryFilterHandler;
import cn.hll520.queryfilter.term.ITerm;
import cn.hll520.queryfilter.term.ITermFilter;
import cn.hll520.queryfilter.term.entiry.IFieldFilter;
import cn.hll520.queryfilter.term.entiry.Operate;

import java.sql.Connection;
import java.util.List;

import static cn.hll520.queryfilter.tools.SQLCheckTools.checkSQLFilter;
import static cn.hll520.queryfilter.tools.SQLCheckTools.isHaveWhere;

/**
 * 描述： 字段过滤处理器
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午9:57
 */
@SuppressWarnings("all")
public class FieldFilterHandler implements IQueryFilterHandler {

    /**
     * 处理SQL语句
     *
     * @param term       条件
     * @param sql        sql语句
     * @param connection 链接对象
     */
    @Override
    public void handle(ITerm term, StringBuilder sql, Connection connection) {
        if (!(term instanceof ITermFilter)) {
            return;
        }
        ITermFilter filterTerm = (ITermFilter) term;

        // 添加过滤条件集
        List<IFieldFilter> fieldFilters = filterTerm.acquireTermFilters();
        if (fieldFilters != null) {
            // 是否紧跟where (是否需要添加And)
            boolean isFist = true;
            // 遍历添加条件
            for (IFieldFilter fieldFilter : fieldFilters) {
                isFist = !addFilter(sql, checkSQLFilter(fieldFilter), isFist);
            }
        }
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
        if (filedFilter == null || filedFilter.acquireFieldName() == null || filedFilter.acquireValue() == null) {
            return false;
        }

        // 如果是第一次添加且不含where 就添加where
        if (isFist && !isHaveWhere(sql)) {
            sql.append(" where ");
        } else {
            // 否则添加 and
            sql.append(" and ");
        }

        Operate operator = filedFilter.acquireOperate();
        // 若操作符为设置默认为 like
        operator = operator == null ? Operate.like : operator;

        // 添加条件
        sql.append(filedFilter.acquireFieldName());
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
                                " 若需使用 包含或者模糊匹配过滤 请使用 [like] 并将 value 设置为 %value% 格式",
                        sql.toString());
        }
        // 添加内容
        sql.append("'").append(filedFilter.acquireValue()).append("' ");
        return true;
    }


}
