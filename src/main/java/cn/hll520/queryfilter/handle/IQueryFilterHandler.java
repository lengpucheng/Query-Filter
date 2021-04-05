package cn.hll520.queryfilter.handle;

import cn.hll520.queryfilter.term.ITerm;

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
     * @param term 条件
     * @param sql  原始待增强SQL语句
     * @return 增强后的SQL语句 <b>不可为null</b>
     */
    String enhanceSQL(ITerm term, String sql);
}
