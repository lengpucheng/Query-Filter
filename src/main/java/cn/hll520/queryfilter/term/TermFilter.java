package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.term.entiry.IFieldFilter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：字段过滤默认条件
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午10:46
 */
@Data
public class TermFilter implements ITermFilter {
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
}
