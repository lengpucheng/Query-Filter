package cn.hll520.queryfilter.handle;

import cn.hll520.queryfilter.term.ITerm;
import cn.hll520.queryfilter.tools.box.ToolBox;

import java.sql.Connection;

import static cn.hll520.queryfilter.tools.SQLCheckTools.removeBranch;


/**
 * 描述： 条件过滤处理器 用于进行语句增强
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:21
 */
@FunctionalInterface
public interface IQueryFilterHandler {
    /**
     * 增强SQL语句
     *
     * @param term    条件
     * @param sql     原始待增强SQL语句
     * @param toolBox 工具集
     * @return 增强后的SQL语句 <b>不可为null</b>
     */
    default String enhanceSQL(ITerm term, String sql, ToolBox toolBox) {
        // 若为null就直接返回
        if (term == null || sql == null) {
            return sql;
        }
        // 获取去除头尾空格和分号的 SQL 语句
        StringBuilder sqlEnhance = new StringBuilder(removeBranch(sql)).append(" ");
        // 处理增强语句
        handle(term, sqlEnhance, toolBox);
        // 添加最后的;
        sqlEnhance.append(" ;");
        return sqlEnhance.toString();
    }

    /**
     * 处理SQL语句
     *
     * @param term    条件
     * @param sql     sql语句
     * @param toolBox 工具集
     */
    void handle(ITerm term, StringBuilder sql, ToolBox toolBox);
}
