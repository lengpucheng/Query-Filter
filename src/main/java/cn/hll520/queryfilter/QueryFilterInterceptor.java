package cn.hll520.queryfilter;

import cn.hll520.queryfilter.handle.IQueryFilterHandler;
import cn.hll520.queryfilter.handle.InterceptorHandler;
import cn.hll520.queryfilter.handle.impl.DefaultQueryFilterHandler;
import cn.hll520.queryfilter.term.ITerm;
import cn.hll520.queryfilter.term.ITermQuery;
import cn.hll520.queryfilter.tools.box.Parameter;
import cn.hll520.queryfilter.tools.box.ToolBox;
import lombok.Data;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 描述： SQL插件拦截器
 * <p>拦截单参数为 {@link ITerm} 的查询语句</p>
 * <p>拦截多尝试中包含 参数名为 <b>queryFilter</b> 且类型为{@link ITerm}的语句</p>
 * <p>默认使用{@link cn.hll520.queryfilter.handle.impl.DefaultQueryFilterHandler}</p>
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午4:49
 */
@Data
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare",
                args = {Connection.class, Integer.class})
})
public class QueryFilterInterceptor implements Interceptor {

    /**
     * 默认增强语句处理器
     */
    private final IQueryFilterHandler handler = new DefaultQueryFilterHandler();

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
    private String enhance(String sql, Object params, SqlCommandType type, String methodName, Invocation invocation) {
        ToolBox toolBox = new ToolBox();
        // 获取参数
        ITermQuery term = null;
        if (params instanceof ITermQuery) {
            term = (ITermQuery) params;
        } else if (params instanceof Map) {
            try {
                Map map = (Map) params;
                if (map.containsKey("queryFilter"))
                    term = (ITermQuery) map.get("queryFilter");

                // 获取参数集合
                List<ParameterMapping> parameter = ((StatementHandler) invocation.getTarget())
                        .getBoundSql().getParameterMappings();
                // 设置参数集合
                toolBox.setParameters(new ArrayList<>());
                if (parameter != null) {
                    parameter.forEach(para -> toolBox.getParameters().add(
                            new Parameter(para.getProperty(), map.get(para.getProperty()))));
                }

            } catch (Exception e) {
                // 若出现异常继续之前操作
                return sql;
            }
        }
        // 获取待增强的connection
        toolBox.setConnection((Connection) invocation.getArgs()[0]);

        // 增强SQL
        return handler.enhanceSQL(term, sql, toolBox);
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
