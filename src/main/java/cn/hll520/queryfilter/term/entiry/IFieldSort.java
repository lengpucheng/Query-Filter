package cn.hll520.queryfilter.term.entiry;

/**
 * 描述： 字段排序条件接口
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午4:58
 */
public interface IFieldSort extends IField {
    /**
     * 获取排序方法
     *
     * @return 可null
     */
    SortTerm acquireSort();

    /**
     * 接收排序方式
     *
     * @param sort 排序方式
     */
    void receiveSort(SortTerm sort);
}
