package cn.hll520.queryfilter.tools;

import cn.hll520.queryfilter.fieldmap.IFieldMap;
import cn.hll520.queryfilter.term.entiry.IField;

/**
 * 描述： 字段转换工具
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午11:02
 */
public class FieldMapTools {
    /**
     * 字段转换
     *
     * @param fieldMap 转换器
     * @param field    字段
     * @return 转换后的字段
     */
    public static IField map(IFieldMap fieldMap, IField field) {
        if (fieldMap != null && field != null && field.acquireFieldName() != null) {
            field.receiveFieldName(fieldMap.fieldMap(field.acquireFieldName()));
        }
        return field;
    }
}
