package cn.hll520.queryfilter.entiry;

/**
 * 描述： 字段过滤条件接口
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午4:57
 */
public interface IFieldFilter {

    /**
     * 获取待过滤字段名称
     *
     * @return 获取待过字段滤名称 可null
     */
    String acquireFieldName();

    /**
     * 获取过滤操作
     *
     * @return 过滤操作方法 可null
     */
    Operate acquireOperate();

    /**
     * 获取过滤匹配内容
     *
     * @return 过滤匹配内容 可null
     */
    String acquireFieldValue();

    /**
     * 接收处理后的字段名称
     *
     * @param fieldName 处理后的字段名称
     */
    void receiveFieldName(String fieldName);

    /**
     * 接收处理后的过滤操作
     *
     * @param operate 过滤操作
     */
    void receiveOperate(Operate operate);

    /**
     * 接收处理后的过滤内容
     *
     * @param fieldValue 过滤内容
     */
    void receiveFieldValue(String fieldValue);
}
