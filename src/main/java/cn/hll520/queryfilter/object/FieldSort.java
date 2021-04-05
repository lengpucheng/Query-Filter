package cn.hll520.queryfilter.object;

import cn.hll520.queryfilter.entiry.IFieldSort;
import cn.hll520.queryfilter.entiry.SortTerm;
import lombok.Data;

/**
 * 描述： 字段排序条件默认实现
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午4:54
 */
@Data
public class FieldSort implements IFieldSort {

    /**
     * 待排序字段
     */
    private String filed;

    /**
     * 排序方法
     */
    private SortTerm sort;

    /**
     * 获取待排序字段名称
     *
     * @return 待排序字段名称 可null
     */
    @Override
    public String acquireFieldName() {
        return this.filed;
    }

    /**
     * 获取排序方法
     *
     * @return 可null
     */
    @Override
    public SortTerm acquireSortTerm() {
        return this.sort;
    }

    /**
     * 接收字段名称
     *
     * @param fieldName 字段名称
     */
    @Override
    public void receiveFieldName(String fieldName) {
        this.setFiled(fieldName);
    }
}
