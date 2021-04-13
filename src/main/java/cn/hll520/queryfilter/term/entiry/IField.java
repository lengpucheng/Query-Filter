package cn.hll520.queryfilter.term.entiry;

/**
 * 描述： 字段接口
 *
 * @author lpc
 * @version 1.0 2021/4/9
 * @since 2021/4/9-下午10:26
 */
public interface IField {

    /**
     * 获取待过滤字段名称
     *
     * @return 获取待过字段滤名称 可null
     */
    String acquireFieldName();

    /**
     * 接收处理后的字段名称
     *
     * @param fieldName 处理后的字段名称
     */
    void receiveFieldName(String fieldName);
}
