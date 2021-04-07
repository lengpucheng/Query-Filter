package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.entiry.Operate;
import cn.hll520.queryfilter.entiry.SortTerm;
import cn.hll520.queryfilter.fieldmap.IFieldMap;
import cn.hll520.queryfilter.object.QueryFilter;

import static cn.hll520.queryfilter.tools.FieldMapTools.map;

/**
 * 描述： 条件过滤器接口
 * <p>包含全量过滤条件</p>
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:20
 */
public interface IQueryFilter extends ITermFilter, ITermSort, ITermPage {

    /**
     * 默认构造一个空的查询过滤
     *
     * @return 查询过滤条件
     */
    static IQueryFilter build() {
        return new QueryFilter();
    }

    /**
     * 初始化分页属性
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return this
     */
    IQueryFilter initializePage(int pageNum, int pageSize);

    /**
     * 转换字段
     *
     * @param map 字段转换接口
     * @return 转换后的this
     */
    default IQueryFilter fieldMap(IFieldMap map) {
        return map(map, this);
    }

    /**
     * 添加单个排序内容
     *
     * @param fieldName 字段名称
     * @param sort      排序方法
     * @return this
     */
    default IQueryFilter addFieldTerm(String fieldName, SortTerm sort) {
        return (IQueryFilter) ITermSort.super.addFieldTerm(fieldName, sort);
    }

    /**
     * 添加单个过滤条件
     *
     * @param fieldName 字段名
     * @param operate   操作
     * @param value     内容
     * @return this
     */
    default IQueryFilter addFieldTerm(String fieldName, Operate operate, String value) {
        return (IQueryFilter) ITermFilter.super.addFieldTerm(fieldName, operate, value);
    }
}
