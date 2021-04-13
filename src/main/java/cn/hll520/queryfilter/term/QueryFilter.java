package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.term.entiry.FieldFilter;
import cn.hll520.queryfilter.term.entiry.FieldSort;
import cn.hll520.queryfilter.term.entiry.Operate;
import cn.hll520.queryfilter.term.entiry.SortTerm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 默认全量条件过滤
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午10:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryFilter extends TermPage implements ITermQuery<FieldFilter, FieldSort> {
    /**
     * 字段排序条件集
     */
    private List<FieldSort> sorts = new ArrayList<>();
    /**
     * 字段过滤条件集
     */
    private List<FieldFilter> filters = new ArrayList<>();

    /**
     * 获取过滤条件
     *
     * @return 过滤条件集合 可null
     */
    @Override
    public List<FieldFilter> acquireTermFilters() {
        return this.getFilters();
    }

    /**
     * 获取条件排序 条件集
     *
     * @return 排序条件集 可null
     */
    @Override
    public List<FieldSort> acquireTermSorts() {
        return this.getSorts();
    }

    /**
     * 初始化分页参数
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return this
     */
    @Override
    public QueryFilter initializePage(long pageNum, long pageSize) {
        return (QueryFilter) super.initializePage(pageNum, pageSize);
    }


    /**
     * 添加单个过滤条件
     *
     * @param fieldName 字段名
     * @param operate   操作
     * @param value     内容
     * @return this
     */
    public QueryFilter addFieldTerm(String fieldName, Operate operate, String value) {
        return (QueryFilter) addFieldTerm(new FieldFilter(fieldName, operate, value));
    }

    /**
     * 添加单个排序条件
     *
     * @param fieldName 排序字段名称
     * @param sort      排序方法
     * @return this
     */
    public QueryFilter addFieldTerm(String fieldName, SortTerm sort) {
        return (QueryFilter) addFieldTerm(new FieldSort(fieldName, sort));
    }
}
