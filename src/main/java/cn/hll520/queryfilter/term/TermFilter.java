package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.term.entiry.FieldFilter;
import cn.hll520.queryfilter.term.entiry.Operate;
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
public class TermFilter implements ITermFilter<FieldFilter> {
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
     * 添加单个过滤条件
     *
     * @param fieldName 字段名
     * @param operate   操作
     * @param value     内容
     * @return this
     */
    public ITermFilter<FieldFilter> addFieldTerm(String fieldName, Operate operate, String value) {
        return addFieldTerm(new FieldFilter(fieldName, operate, value));
    }
}
