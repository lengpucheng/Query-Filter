package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.entiry.IFieldFilter;
import cn.hll520.queryfilter.entiry.Operate;
import cn.hll520.queryfilter.fieldmap.IFieldMap;
import cn.hll520.queryfilter.object.FieldFilter;
import cn.hll520.queryfilter.object.TermFilter;

import java.util.List;

import static cn.hll520.queryfilter.tools.FieldMapTools.map;

/**
 * 描述： 条件过滤接口
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:09
 */
public interface ITermFilter extends ITerm {

    /**
     * 获取过滤条件
     *
     * @return 过滤条件集合 可null
     */
    List<IFieldFilter> acquireTermFilters();


    /**
     * 默认构造一个过滤条件
     *
     * @return 过滤条件
     */
    static ITermFilter build() {
        return new TermFilter();
    }

    /**
     * 添加单个过滤条件
     *
     * @param fieldName 字段名
     * @param operate   操作
     * @param value     内容
     * @return this
     */
    default ITermFilter addFieldTerm(String fieldName, Operate operate, String value) {
        List<IFieldFilter> fieldFilters = acquireTermFilters();
        if (fieldFilters != null) {
            fieldFilters.add(new FieldFilter(fieldName, operate, value));
        }
        return this;
    }

    /**
     * 转换字段
     *
     * @param map 字段转换接口
     * @return 转换后的this
     */
    default ITermFilter fieldMap(IFieldMap map) {
        return map(map, this);
    }
}
