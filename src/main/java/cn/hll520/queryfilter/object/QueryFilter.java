package cn.hll520.queryfilter.object;

import cn.hll520.queryfilter.entiry.IFieldFilter;
import cn.hll520.queryfilter.entiry.IFieldSort;
import cn.hll520.queryfilter.fieldmap.IFieldMap;
import cn.hll520.queryfilter.term.ITermQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static cn.hll520.queryfilter.tools.FieldMapTools.map;

/**
 * 描述： 默认全量条件过滤
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午10:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryFilter extends TermPage implements ITermQuery {
    /**
     * 字段排序条件集
     */
    private List<IFieldSort> sorts;
    /**
     * 字段过滤条件集
     */
    private List<IFieldFilter> filters;

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
     * 转换字段
     *
     * @param map 字段转换接口
     * @return 转换后的this
     */
    public ITermQuery fieldMap(IFieldMap map) {
        return map(map, this);
    }
}
