package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.fieldmap.IFieldMap;
import cn.hll520.queryfilter.term.entiry.IFieldFilter;
import cn.hll520.queryfilter.term.entiry.IFieldSort;

/**
 * 描述： 条件过滤器接口
 * <p>包含全量过滤条件</p>
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:20
 */
public interface ITermQuery<F extends IFieldFilter, S extends IFieldSort>
        extends ITermFilter<F>, ITermSort<S>, ITermPage {

    /**
     * 默认构造一个空的查询过滤
     *
     * @return 查询过滤条件
     */
    static QueryFilter build() {
        return new QueryFilter();
    }

    /**
     * 初始化分页属性
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return this
     */
    ITermQuery<F, S> initializePage(long pageNum, long pageSize);

    /**
     * 转换字段
     *
     * @param map 字段转换接口
     * @return 转换后的this
     */
    default ITermQuery<F, S> fieldMap(IFieldMap map) {
        ITermFilter.super.fieldMap(map);
        ITermSort.super.fieldMap(map);
        return this;
    }

    /**
     * 添加单个条件
     *
     * @param f 过滤条件
     * @return this
     */
    @Override
    default ITermQuery<F, S> addFieldTerm(F f) {
        ITermFilter.super.addFieldTerm(f);
        return this;
    }

    /**
     * 添加一个排序条件
     *
     * @param s 排序条件
     * @return this
     */
    @Override
    default ITermQuery<F, S> addFieldTerm(S s) {
        ITermSort.super.addFieldTerm(s);
        return this;
    }
}
