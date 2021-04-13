package cn.hll520.queryfilter.handle;

import cn.hll520.queryfilter.term.ITerm;

import java.sql.Connection;

import static cn.hll520.queryfilter.tools.SQLHandleTools.removeBranch;

/**
 * 描述： 条件过滤处理器 用于进行语句增强
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:21
 */
public interface IQueryFilterHandler {
    /**
     * 增强SQL语句
     *
     * @param term       条件
     * @param sql        原始待增强SQL语句
     * @param connection 链接对象
     * @return 增强后的SQL语句 <b>不可为null</b>
     */
    default String enhanceSQL(ITerm term, String sql, Connection connection) {
        // 若为null就直接返回
        if (term == null || sql == null) {
            return sql;
        }
        // 获取去除头尾空格和分号的 SQL 语句
        StringBuilder sqlEdit = removeBranch(sql).append(" ");


        // 处理
        handle(term, sqlEdit, connection);

        // 添加最后的;
        sqlEdit.append(" ;");
        return sqlEdit.toString();
    }

    /**
     * 处理SQL语句
     *
     * @param term       条件
     * @param sql        sql语句
     * @param connection 链接对象
     */
    void handle(ITerm term, StringBuilder sql, Connection connection);
}
