package cn.hll520.queryfilter.object;

import cn.hll520.queryfilter.entiry.IFieldSort;
import cn.hll520.queryfilter.fieldmap.IFieldMap;
import cn.hll520.queryfilter.term.ITermSort;
import lombok.Data;

import java.util.List;

import static cn.hll520.queryfilter.tools.FieldMapTools.map;

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
    private List<IFieldSort> sorts;

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
    public ITermSort fieldMap(IFieldMap map) {
        return map(map, this);
    }
}
