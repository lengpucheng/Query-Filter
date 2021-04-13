package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.fieldmap.IFieldMap;
import cn.hll520.queryfilter.term.entiry.IFieldSort;
import jdk.nashorn.internal.objects.annotations.Function;

import java.util.List;

import static cn.hll520.queryfilter.tools.FieldMapTools.map;

/**
 * 描述： 条件排序接口
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:12
 */
public interface ITermSort<T extends IFieldSort> extends ITerm {
    /**
     * 默认构造一个空排序条件
     *
     * @return 排序条件
     */
    static TermSort build() {
        return new TermSort();
    }

    /**
     * 获取条件排序 条件集
     *
     * @return 排序条件集 可null
     */
    @Function
    List<T> acquireTermSorts();

    /**
     * 添加一个排序条件
     *
     * @param t 排序条件
     * @return this
     */
    default ITermSort<T> addFieldTerm(T t) {
        if (acquireTermSorts() != null) {
            acquireTermSorts().add(t);
        }
        return this;
    }

    /**
     * 转换字段
     *
     * @param map 字段转换接口
     * @return 转换后的this
     */
    default ITermSort<T> fieldMap(IFieldMap map) {
        List<T> ts = acquireTermSorts();
        if (ts != null && map != null) {
            for (T t : ts) {
                map(map, t);
            }
        }
        return this;
    }
}
