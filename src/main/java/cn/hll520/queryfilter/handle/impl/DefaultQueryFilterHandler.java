package cn.hll520.queryfilter.handle.impl;

import cn.hll520.queryfilter.handle.IQueryFilterHandler;
import cn.hll520.queryfilter.term.*;
import org.springframework.stereotype.Service;

/**
 * 描述： 默认的过滤查询处理器
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午9:05
 */
@Service
public class DefaultQueryFilterHandler implements IQueryFilterHandler {

    /**
     * 分页处理器
     */
    private final PageFilterHandler pageFilterHandler;
    /**
     * 排序处理器
     */
    private final FieldSortHandler fieldSortHandler;

    /**
     * 字段过滤处理器
     */
    private final FieldFilterHandler fieldFilterHandler;

    /**
     * 其他处理器
     */
    private IQueryFilterHandler otherHandler;

    /**
     * 构造 用于自动注入
     *
     * @param pageFilterHandler  分页处理器
     * @param fieldSortHandler   排序处理器
     * @param fieldFilterHandler 过滤处理器
     */
    public DefaultQueryFilterHandler(PageFilterHandler pageFilterHandler,
                                     FieldSortHandler fieldSortHandler,
                                     FieldFilterHandler fieldFilterHandler) {
        this.pageFilterHandler = pageFilterHandler;
        this.fieldSortHandler = fieldSortHandler;
        this.fieldFilterHandler = fieldFilterHandler;
    }


    /**
     * 设置其他处理器
     *
     * @param otherHandler 其他处理器
     */
    public void setOtherHandler(IQueryFilterHandler otherHandler) {
        this.otherHandler = otherHandler;
    }

    /**
     * 增强SQL语句
     *
     * @param term 条件
     * @param sql  原始待增强SQL语句
     * @return 增强后的SQL语句 <b>不可为null</b>
     */
    @Override
    public String enhanceSQL(ITerm term, String sql) {
        // 如果条件为null 不做处理
        if (term == null) {
            return sql;
        } else if (term instanceof ITermQuery) {
            // 如果是全量条件
            return enhanceQuerySQL((ITermQuery) term, sql);
        } else if (term instanceof ITermFilter) {
            // 如果是字段过滤条件
            return fieldFilterHandler.enhanceFilterSQL((ITermFilter) term, sql);
        } else if (term instanceof ITermSort) {
            // 如果是是排序条件
            return fieldSortHandler.enhanceSortSQL((ITermSort) term, sql);
        } else if (term instanceof ITermPage) {
            // 如果是分页条件
            return pageFilterHandler.enhancePageSQL((ITermPage) term, sql);
        } else {
            // 如果是其他条件
            if (otherHandler != null) {
                return otherHandler.enhanceSQL(term, sql);
            }
            return sql;
        }
    }

    /**
     * @param term 全量查询过滤条件
     * @param sql  待增强sql语句
     * @return 增强后的sql语句
     */
    private String enhanceQuerySQL(ITermQuery term, String sql) {
        if (term == null) {
            return sql;
        }
        // 先对字段进行过滤
        sql = fieldFilterHandler.enhanceFilterSQL(term, sql);
        // 排序
        sql = fieldSortHandler.enhanceSortSQL(term, sql);
        // 分页
        sql = pageFilterHandler.enhancePageSQL(term, sql);
        // 返回
        return sql;
    }
}
