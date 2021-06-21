package cn.hll520.queryfilter.handle;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;

/**
 * 描述： SQL语句增强器
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:42
 */
@FunctionalInterface
public interface ISQLEnhance {
    /**
     * 增强sql
     *
     * @param sql        待增强SQL
     * @param params     原始方法参数
     * @param type       类型
     * @param methodName 方法名称
     * @param invocation 目标方法
     * @return 增强后的SQL
     */
    String enhance(String sql, Object params, SqlCommandType type, String methodName, Invocation invocation);
}
