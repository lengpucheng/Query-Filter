package cn.hll520.queryfilter.handle.impl;

import cn.hll520.queryfilter.handle.IQueryFilterHandler;
import cn.hll520.queryfilter.term.ITerm;
import cn.hll520.queryfilter.term.ITermPage;
import cn.hll520.queryfilter.tools.box.ToolBox;



/**
 * 描述： 分页处理器
 * <p>如果每页大小为 -1 则表示不分页 </p>
 * <p>如果页数为负数 x 表示 offset |x| </p>
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午9:09
 */
public class PageFilterHandler implements IQueryFilterHandler {
    /**
     * 处理SQL语句
     *
     * @param term       条件
     * @param sql        sql语句
     * @param toolBox 工具箱
     */
    @Override
    public void handle(ITerm term, StringBuilder sql, ToolBox toolBox) {
        if (!(term instanceof ITermPage)) {
            return;
        }
        ITermPage pageTerm = (ITermPage) term;
        // Todo 待反查出总数量

        // 每页大小
        Long size = pageTerm.acquireSize();
        if (size != null && size != -1) {
            size = size < 1 ? 1 : size;
            sql.append(" limit ").append(size);
            // 偏移量
            Long pageNum = pageTerm.acquirePageNum();
            if (pageNum != null) {
                // 小于1 视为 offset 去 abs绝对值  否则乘以大小进行分页
                pageNum = pageNum < 1 ? Math.abs(pageNum) : (pageNum - 1) * size;
                sql.append(" offset ").append(pageNum);
            }
        }

    }
}
