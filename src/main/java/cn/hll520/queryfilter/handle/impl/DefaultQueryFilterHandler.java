package cn.hll520.queryfilter.handle.impl;

import cn.hll520.queryfilter.handle.IQueryFilterHandler;
import cn.hll520.queryfilter.term.*;

import java.sql.Connection;

/**
 * 描述： 默认的过滤查询处理器
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午9:05
 */
public class DefaultQueryFilterHandler implements IQueryFilterHandler {

    /**
     * 分页处理器
     */
    private final PageFilterHandler pageFilterHandler = new PageFilterHandler();
    /**
     * 排序处理器
     */
    private final FieldSortHandler fieldSortHandler = new FieldSortHandler();

    /**
     * 字段过滤处理器
     */
    private final FieldFilterHandler fieldFilterHandler = new FieldFilterHandler();

    /**
     * 其他处理器
     */
    private IQueryFilterHandler otherHandler;


    /**
     * 设置其他处理器
     *
     * @param otherHandler 其他处理器
     */
    public void setOtherHandler(IQueryFilterHandler otherHandler) {
        this.otherHandler = otherHandler;
    }

    /**
     * 处理SQL语句
     *
     * @param term       条件
     * @param sql        sql语句
     * @param connection 链接对象
     */
    public void handle(ITerm term, StringBuilder sql, Connection connection) {
        // 如果条件为null 不做处理
        if (term != null) {
            if (term instanceof ITermQuery) {
                // 如果是全量条件
                enhanceQuerySQL(term, sql, connection);
            } else if (term instanceof ITermFilter) {
                // 如果是字段过滤条件
                fieldFilterHandler.handle(term, sql, connection);
            } else if (term instanceof ITermSort) {
                // 如果是是排序条件
                fieldSortHandler.handle(term, sql, connection);
            } else if (term instanceof ITermPage) {
                // 如果是分页条件
                pageFilterHandler.handle(term, sql, connection);
            } else {
                // 如果是其他条件
                if (otherHandler != null) {
                    otherHandler.handle(term, sql, connection);
                }
            }
        }
    }

    /**
     * @param term       全量查询过滤条件
     * @param sql        待增强sql语句
     * @param connection 链接对象
     */
    private void enhanceQuerySQL(ITerm term, StringBuilder sql, Connection connection) {
        if (term == null) {
            return;
        }
        // 先对字段进行过滤
        fieldFilterHandler.handle(term, sql, connection);
        // 排序
        fieldSortHandler.handle(term, sql, connection);
        // 分页
        pageFilterHandler.handle(term, sql, connection);
    }
}
