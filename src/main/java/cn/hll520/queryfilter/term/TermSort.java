package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.term.entiry.FieldSort;
import cn.hll520.queryfilter.term.entiry.SortTerm;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 默认排序条件
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午10:45
 */
@Data
public class TermSort implements ITermSort<FieldSort> {
    /**
     * 字段排序条件集
     */
    private List<FieldSort> sorts = new ArrayList<>();

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
     * 添加单个排序条件
     *
     * @param fieldName 排序字段名称
     * @param sort      排序方法
     * @return this
     */
    public ITermSort<FieldSort> addFieldTerm(String fieldName, SortTerm sort) {
        return addFieldTerm(new FieldSort(fieldName, sort));
    }
}
