package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.entiry.IFieldSort;

import java.util.List;

/**
 * 描述： 条件排序接口
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:12
 */
public interface ITermSort extends ITerm{
    /**
     * 获取条件排序 条件集
     *
     * @return 排序条件集 可null
     */
    List<IFieldSort> acquireTermSorts();
}
