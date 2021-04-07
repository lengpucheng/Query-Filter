package cn.hll520.queryfilter.handle.impl;

import cn.hll520.queryfilter.term.ITermPage;
import org.springframework.stereotype.Component;

import static cn.hll520.queryfilter.tools.SQLHandleTools.removeBranch;

/**
 * 描述： 分页处理器
 * <p>如果每页大小为 -1 则表示不分页 </p>
 * <p>如果页数为负数 x 表示 offset |x| </p>
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午9:09
 */
@Component
public class PageFilterHandler {
    /**
     * 进行SQL语句分页增强
     *
     * @param pageTerm 分页条件
     * @param sql      sql语句
     * @return 增强后的语句
     */
    public String enhancePageSQL(ITermPage pageTerm, String sql) {
        // 若为null就直接返回
        if (pageTerm == null || sql == null) {
            return sql;
        }
        // Todo 待反查出总数量
        // 获取去除头尾空格和分号的 SQL 语句
        StringBuilder sqlEdit = removeBranch(sql).append(" ");
        // 每页大小
        Integer size = pageTerm.acquireSize();
        if (size != null && size != -1) {
            size = size < 1 ? 1 : size;
            sqlEdit.append(" limit ").append(size);
            // 偏移量
            Integer pageNum = pageTerm.acquirePageNum();
            if (pageNum != null) {
                // 小于1 视为 offset 去 abs绝对值  否则乘以大小进行分页
                pageNum = pageNum < 1 ? Math.abs(pageNum) : (pageNum - 1) * size;
                sqlEdit.append(" offset ").append(pageNum);
            }
        }
        // 添加最后的;
        sqlEdit.append(" ;");
        return sqlEdit.toString();
    }
}
