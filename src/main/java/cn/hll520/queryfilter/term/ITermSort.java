package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.entiry.IFieldSort;
import cn.hll520.queryfilter.entiry.SortTerm;
import cn.hll520.queryfilter.fieldmap.IFieldMap;
import cn.hll520.queryfilter.object.FieldSort;
import cn.hll520.queryfilter.object.TermSort;

import java.util.List;

import static cn.hll520.queryfilter.tools.FieldMapTools.map;

/**
 * 描述： 条件排序接口
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:12
 */
public interface ITermSort extends ITerm {
    /**
     * 获取条件排序 条件集
     *
     * @return 排序条件集 可null
     */
    List<IFieldSort> acquireTermSorts();

    /**
     * 默认构造一个空排序条件
     *
     * @return 排序条件
     */
    static ITermSort build() {
        return new TermSort();
    }

    /**
     * 添加单个排序条件
     *
     * @param fieldName 排序字段名称
     * @param sort      排序方法
     * @return this
     */
    default ITermSort addFieldTerm(String fieldName, SortTerm sort) {
        List<IFieldSort> fieldSorts = this.acquireTermSorts();
        if (fieldSorts != null) {
            fieldSorts.add(new FieldSort(fieldName, sort));
        }
        return this;
    }

    /**
     * 转换字段
     *
     * @param map 字段转换接口
     * @return 转换后的this
     */
    default ITermSort fieldMap(IFieldMap map) {
        return map(map, this);
    }
}
