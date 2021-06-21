package cn.hll520.queryfilter.term.entiry;

/**
 * 描述： 字段过滤条件接口
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午4:57
 */
public interface IFieldFilter extends IField {


    /**
     * 获取过滤操作
     *
     * @return 过滤操作方法 可null
     */
    Operate acquireOperate();

    /**
     * 接收处理后的过滤操作
     *
     * @param operate 过滤操作
     */
    void receiveOperate(Operate operate);

    /**
     * 获取过滤匹配内容
     *
     * @return 过滤匹配内容 可null
     */
    String acquireValue();

    /**
     * 接收处理后的过滤内容
     *
     * @param fieldValue 过滤内容
     */
    void receiveValue(String fieldValue);
}
