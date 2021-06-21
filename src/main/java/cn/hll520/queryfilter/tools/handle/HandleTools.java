package cn.hll520.queryfilter.tools.handle;

/**
 * 描述：SQL语句预处理工具
 *
 * @author LPC_MI
 * @version 1.0 2021/6/21
 * @since 2021/6/21-15:53
 */
public interface HandleTools {
    /**
     * 预处理字段
     *
     * @param str 待处理内容
     * @return 处理后的内容
     */
    String handleField(String str);

    /**
     * 预处理引号
     *
     * @param str 待处理内容
     * @return 处理引号
     */
    String handleQuota(String str);
}
