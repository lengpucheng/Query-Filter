package cn.hll520.queryfilter.entiry;

/**
 * 描述： 字段排序条件接口
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午4:58
 */
public interface IFieldSort {

    /**
     * 获取待排序字段名称
     *
     * @return 待排序字段名称 可null
     */
    String acquireFieldName();

    /**
     * 获取排序方法
     *
     * @return 可null
     */
    SortTerm acquireSortTerm();

    /**
     * 接收字段名称
     *
     * @param fieldName 字段名称
     */
    void receiveFieldName(String fieldName);
}
