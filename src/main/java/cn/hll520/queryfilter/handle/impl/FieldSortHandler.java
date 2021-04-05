package cn.hll520.queryfilter.handle.impl;

import cn.hll520.queryfilter.entiry.IFieldSort;
import cn.hll520.queryfilter.term.ITermSort;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.hll520.queryfilter.tools.CheckSQLTools.checkSQLSort;
import static cn.hll520.queryfilter.tools.SQLHandleTools.removeBranch;

/**
 * 描述： 字段排序处理器
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午9:21
 */
@Component
public class FieldSortHandler {


    /**
     * 增强sql语句进行排序
     *
     * @param sortTerm 排序条件集
     * @param sql      待增强sql语句
     * @return 增强后的sql语句
     */
    public String enhanceSortSQL(ITermSort sortTerm, String sql) {
        // 若为null就直接返回
        if (sortTerm == null || sql == null) {
            return sql;
        }
        // 获取去除头尾空格和分号的 SQL 语句
        StringBuilder sqlEnhance = removeBranch(sql).append(" ");
        // 获取排序条件集合
        List<IFieldSort> fieldSorts = sortTerm.acquireTermSorts();
        if (fieldSorts != null) {
            // 是否是第一次添加条件
            boolean isFist = true;
            for (IFieldSort fieldSort : fieldSorts) {
                // 检验条件添加
                isFist = !addSort(sqlEnhance, checkSQLSort(fieldSort), isFist) && isFist;
            }
        }
        // 添加最后的;
        sqlEnhance.append(" ;");
        return sqlEnhance.toString();
    }

    /**
     * 像SQL 语句 后添加 排序条件
     *
     * @param sql       sql 语句
     * @param fieldSort 字段排序条件
     * @param isFist    是否是第一次添加
     * @return 是否添加成功
     */
    private boolean addSort(StringBuilder sql, IFieldSort fieldSort, boolean isFist) {
        // 过滤条件不存在就返回
        if (fieldSort == null || fieldSort.acquireFieldName() == null) {
            return false;
        }
        if (isFist) {
            // 如果是第一次添加 补充 order by 关键字
            sql.append("order by ");
        } else {
            // 否则 , 分割
            sql.append(" , ");
        }
        // 排序字段
        sql.append(fieldSort.acquireFieldName());
        // 排序方式
        if (fieldSort.acquireSortTerm() != null) {
            sql.append(" ").append(fieldSort.acquireSortTerm());
        }
        // 添加成功返回 true
        return true;
    }

}
