package cn.hll520.queryfilter.term.entiry;

import lombok.Data;

/**
 * 描述： 字段过滤条件 默认实现
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午4:53
 */
@Data
public class FieldFilter implements IFieldFilter {
    /**
     * 带过滤字段名称
     */
    private String field;

    /**
     * 字段过滤操作
     */
    private Operate operate;

    /**
     * 字段过滤内容
     */
    private String value;

    public FieldFilter() {
    }

    public FieldFilter(String field, Operate operate, String value) {
        this.field = field;
        this.operate = operate;
        this.value = value;
    }

    /**
     * 获取待过滤字段名称
     *
     * @return 获取待过字段滤名称 可null
     */
    @Override
    public String acquireFieldName() {
        return this.getField();
    }

    /**
     * 获取过滤操作
     *
     * @return 过滤操作方法 可null
     */
    @Override
    public Operate acquireOperate() {
        return this.getOperate();
    }

    /**
     * 获取过滤匹配内容
     *
     * @return 过滤匹配内容 可null
     */
    @Override
    public String acquireFieldValue() {
        return this.getValue();
    }

    /**
     * 接收处理后的字段名称
     *
     * @param fieldName 处理后的字段名称
     */
    @Override
    public void receiveFieldName(String fieldName) {
        this.setField(fieldName);
    }

    /**
     * 接收处理后的过滤操作
     *
     * @param operate 过滤操作
     */
    @Override
    public void receiveOperate(Operate operate) {
        this.setOperate(operate);
    }

    /**
     * 接收处理后的过滤内容
     *
     * @param fieldValue 过滤内容
     */
    @Override
    public void receiveFieldValue(String fieldValue) {
        this.setValue(fieldValue);
    }
}
