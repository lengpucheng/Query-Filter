package cn.hll520.queryfilter.fieldmap;

/**
 * 描述： 默认驼峰转下划线转换器
 *
 * @author lpc
 * @version 1.0 2021/4/7
 * @since 2021/4/7-下午9:26
 */
public interface IFieldMapHumpToLine extends IFieldMap {
    /**
     * 获取一个当前接口的实例化对象
     *
     * @return 当前接口
     */
    static IFieldMapHumpToLine build() {
        return new IFieldMapHumpToLine() {
        };
    }

    /**
     * 默认转换方式--驼峰转下划线
     *
     * @param fieldName 字段名称
     * @return 转换后的名称
     */
    @Override
    default String defaultMap(String fieldName) {
        String str = fieldName;
        char[] chars = fieldName.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'A' && chars[i] <= 'Z') {
                str = fieldName.substring(0, i);
                str += "_";
                str += (char) (chars[i] + 32);
                if (i != fieldName.length() - 1) {
                    str += defaultMap(fieldName.substring(i + 1));
                    break;
                }
            }
        }
        return str;
    }
}
