package cn.hll520.queryfilter.fieldmap;

/**
 * 描述： 默认下划线转驼峰转换器
 *
 * @author lpc
 * @version 1.0 2021/4/7
 * @since 2021/4/7-下午9:38
 */
public interface IFieldMapLineToHump extends IFieldMap {

    /**
     * 获取一个当前接口的实例化对象
     *
     * @return 当前接口
     */
    static IFieldMapLineToHump build() {
        return new IFieldMapLineToHump() {
        };
    }

    /**
     * 默认转换方式
     *
     * @param fieldName 字段名称
     * @return 转换后的名称
     */
    @Override
    default String defaultMap(String fieldName) {
        String str = fieldName;
        char[] chars = fieldName.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '_') {
                str = fieldName.substring(0, i);
                if (i != fieldName.length() - 1) {
                    str += (char) (chars[i + 1] - 32);
                    if (i != fieldName.length() - 2) {
                        str += defaultMap(fieldName.substring(i + 2));
                        break;
                    }
                }
            }
        }
        return str;
    }
}
