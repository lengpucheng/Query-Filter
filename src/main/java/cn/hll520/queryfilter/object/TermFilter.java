package cn.hll520.queryfilter.object;

import cn.hll520.queryfilter.entiry.IFieldFilter;
import cn.hll520.queryfilter.fieldmap.IFieldMap;
import cn.hll520.queryfilter.term.ITermFilter;

import static cn.hll520.queryfilter.tools.FieldMapTools.map;

import lombok.Data;

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
     * 转换字段
     *
     * @param map 字段转换接口
     * @return 转换后的this
     */
    public ITermFilter fieldMap(IFieldMap map) {
        return map(map, this);
    }
}
