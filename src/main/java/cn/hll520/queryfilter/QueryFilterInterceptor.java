package cn.hll520.queryfilter;

import cn.hll520.queryfilter.handle.IQueryFilterHandler;
import cn.hll520.queryfilter.handle.InterceptorHandler;
import cn.hll520.queryfilter.term.ITerm;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * 描述： SQL插件拦截器
 * <p>拦截单参数为 {@link ITerm} 的查询语句</p>
 * <p>拦截多尝试中包含 参数名为 <b>queryFilter</b> 且类型为{@link ITerm}的语句</p>
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午4:49
 */
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare",
                args = {Connection.class, Integer.class})
})
public class QueryFilterInterceptor implements Interceptor {

    /**
     * 处理器
     */
    private final IQueryFilterHandler handler;

    /**
     * 构造器 用于进行构造或自动注入
     * <p>默认使用{@link cn.hll520.queryfilter.handle.impl.DefaultQueryFilterHandler}</p>
     *
     * @param handler 条件过滤处理器
     */
    public QueryFilterInterceptor(IQueryFilterHandler handler) {
        this.handler = handler;
    }


    /**
     * 拦截 SQL 语句
     *
     * @param invocation 方法调用
     * @return 继续执行
     * @throws Throwable 执行错误
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 对查询进行过滤
        return InterceptorHandler.intercept(invocation, this::enhance, SqlCommandType.SELECT);

    }


    /**
     * 对需要增强的SQL语句进行参数甄别 pageFilter 语句
     *
     * @param sql        原始语句
     * @param params     参数
     * @param type       方法类型
     * @param methodName 方法名称
     * @return 增强后的语句
     */
    private String enhance(String sql, Object params, SqlCommandType type, String methodName) {
        ITerm term = null;
        // 获取参数
        if (params instanceof ITerm) {
            term = (ITerm) params;
        } else if (params instanceof Map) {
            try {
                Map map = (Map) params;
                if (map.containsKey("queryFilter"))
                    term = (ITerm) map.get("queryFilter");
            } catch (Exception e) {
                // 若出现异常继续之前操作
                return sql;
            }
        }
        // 增强SQL
        return handler.enhanceSQL(term, sql);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
