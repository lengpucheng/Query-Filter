package cn.hll520.queryfilter.handle;

import jdk.nashorn.internal.objects.annotations.Function;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * 描述： SQL语句增强器
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:42
 */
public interface ISQLEnhance {
    /**
     * 增强sql
     *
     * @param sql        待增强SQL
     * @param params     原始方法参数
     * @param type       类型
     * @param methodName 方法名称
     * @return 增强后的SQL
     */
    @Function
    String enhance(String sql, Object params, SqlCommandType type, String methodName);
}
