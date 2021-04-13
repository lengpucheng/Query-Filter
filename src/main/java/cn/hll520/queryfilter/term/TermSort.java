package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.term.entiry.IFieldSort;
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
public class TermSort implements ITermSort {
    /**
     * 字段排序条件集
     */
    private List<IFieldSort> sorts = new ArrayList<>();

    /**
     * 获取条件排序 条件集
     *
     * @return 排序条件集 可null
     */
    @Override
    public List<IFieldSort> acquireTermSorts() {
        return this.getSorts();
    }
}
