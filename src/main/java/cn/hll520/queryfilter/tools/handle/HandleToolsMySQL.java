package cn.hll520.queryfilter.tools.handle;

/**
 * 描述： MYSQL的处理工具
 *
 * @author LPC_MI
 * @version 1.0 2021/6/21
 * @since 2021/6/21-15:55
 */
public class HandleToolsMySQL implements HandleTools {
    /**
     * 预处理字段
     *
     * @param str 待处理内容
     * @return 处理后的内容
     */
    @Override
    public String handleField(String str) {
        // 处理反引号
        str = str.replace("`", "``");
        // 使用反引号包裹
        return "`" + str + "`";
    }

    /**
     * 预处理引号
     *
     * @param str 待处理内容
     * @return 处理引号
     */
    @Override
    public String handleQuota(String str) {
        return str.replace("'", "''");
    }
}
