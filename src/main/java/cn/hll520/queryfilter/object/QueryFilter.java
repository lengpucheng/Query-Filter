package cn.hll520.queryfilter.object;

import cn.hll520.queryfilter.entiry.IFieldFilter;
import cn.hll520.queryfilter.entiry.IFieldSort;
import cn.hll520.queryfilter.term.IQueryFilter;
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
public class QueryFilter extends TermPage implements IQueryFilter {
    /**
     * 字段排序条件集
     */
    private List<IFieldSort> sorts = new ArrayList<>();
    /**
     * 字段过滤条件集
     */
    private List<IFieldFilter> filters = new ArrayList<>();

    /**
     * 获取过滤条件
     *
     * @return 过滤条件集合 可null
     */
    @Override
    public List<IFieldFilter> acquireTermFilters() {
        return this.getFilters();
    }

    /**
     * 获取条件排序 条件集
     *
     * @return 排序条件集 可null
     */
    @Override
    public List<IFieldSort> acquireTermSorts() {
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
    public IQueryFilter initializePage(int pageNum, int pageSize) {
        return (IQueryFilter) super.initializePage(pageNum, pageSize);
    }

}
