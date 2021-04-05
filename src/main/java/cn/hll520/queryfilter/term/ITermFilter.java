package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.entiry.IFieldFilter;

import java.util.List;

/**
 * 描述： 条件过滤接口
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:09
 */
public interface ITermFilter extends ITerm{

    /**
     * 获取过滤条件
     *
     * @return 过滤条件集合 可null
     */
    List<IFieldFilter> acquireTermFilters();
}
