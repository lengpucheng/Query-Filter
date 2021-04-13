package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.fieldmap.IFieldMap;


/**
 * 描述： 用于标记条件
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:26
 */
public interface ITerm {

    /**
     * 全量条件
     *
     * @return 全量条件
     */
    static QueryFilter queryFilter() {
        return ITermQuery.build();
    }

    /**
     * 排序
     *
     * @return 排序条件
     */
    static TermSort sort() {
        return ITermSort.build();
    }

    /**
     * 过滤
     *
     * @return 过滤条件
     */
    static TermFilter filter() {
        return ITermFilter.build();
    }

    /**
     * 分页
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return 条件
     */
    static ITermPage page(int pageNum, int pageSize) {
        return ITermPage.build(pageNum, pageSize);
    }

    /**
     * 转换字段
     *
     * @param map 字段转换接口
     * @return 转换后的this
     */
    default ITerm fieldMap(IFieldMap map) {
        return this;
    }
}
