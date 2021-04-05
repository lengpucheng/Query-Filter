package cn.hll520.queryfilter.entiry;

/**
 * 描述： 过滤操作
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午4:56
 */
public enum Operate {
    /**
     * 小于等于
     */
    lte,
    /**
     * 大于等于
     */
    gte,
    /**
     * 等于
     */
    eq,
    /**
     * 包含
     */
    contains,
    /**
     * 不等于
     */
    neq,
    /**
     * 大于 great
     */
    gt,
    /**
     * 小于 left
     */
    lt,
    /**
     * 存在
     */
    in,
    /**
     * 不存在
     */
    nin,
    /**
     * 相似
     */
    like
}
