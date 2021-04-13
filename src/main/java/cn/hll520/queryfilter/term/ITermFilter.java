package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.fieldmap.IFieldMap;
import cn.hll520.queryfilter.term.entiry.IFieldFilter;
import jdk.nashorn.internal.objects.annotations.Function;

import java.util.List;

import static cn.hll520.queryfilter.tools.FieldMapTools.map;

/**
 * 描述： 条件过滤接口
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:09
 */
public interface ITermFilter<T extends IFieldFilter> extends ITerm {

    /**
     * 默认构造一个过滤条件
     *
     * @return 过滤条件
     */
    static TermFilter build() {
        return new TermFilter();
    }

    /**
     * 获取过滤条件
     *
     * @return 过滤条件集合 可null
     */
    @Function
    List<T> acquireTermFilters();

    /**
     * 添加单个条件
     *
     * @param t 过滤条件
     * @return this
     */
    default ITermFilter<T> addFieldTerm(T t) {
        if (acquireTermFilters() != null) {
            acquireTermFilters().add(t);
        }
        return this;
    }

    /**
     * 转换字段
     *
     * @param map 字段转换接口
     * @return 转换后的this
     */
    default ITermFilter<T> fieldMap(IFieldMap map) {
        List<T> ts = acquireTermFilters();
        if (ts != null && map != null) {
            for (T t : ts) {
                map(map, t);
            }
        }
        return this;
    }
}
