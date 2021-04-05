package cn.hll520.queryfilter.fieldmap;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述： 字段名称转换器
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午10:57
 */
public interface IFieldMap {
    /**
     * 字段转换方法
     *
     * @param fieldName 字段转换名称
     * @return 转换后的字段名称
     */
    default String fieldMap(String fieldName) {
        Map<String, String> map = this.fieldMap(new HashMap<>());
        if (map.containsKey(fieldName)) {
            return map.get(fieldName);
        }
        return defaultMap(fieldName);
    }

    /**
     * 设置字段映射
     *
     * @param map 字段映射
     * @return 字段映射集 <b>不可以为null</b>
     */
    default Map<String, String> fieldMap(Map<String, String> map) {
        if (map == null) {
            return new HashMap<>();
        }
        return map;
    }

    /**
     * 默认转换方式
     *
     * @param fieldName 字段名称
     * @return 转换后的名称
     */
    default String defaultMap(String fieldName) {
        return fieldName;
    }
}
