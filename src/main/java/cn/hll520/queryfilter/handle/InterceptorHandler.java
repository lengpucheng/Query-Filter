package cn.hll520.queryfilter.handle;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * 描述： SQL拦截处理器
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:42
 */
public class InterceptorHandler {

    /**
     * 拦截SQL语句进行处理
     *
     * @param invocation 执行对象
     * @param enhance    语句增强器
     * @param type       拦截增强的类型
     * @return 继续执行
     * @throws Throwable 执行错误
     */
    public static Object intercept(Invocation invocation, ISQLEnhance enhance, SqlCommandType type) throws Throwable {
        // enhance 为null 继续执行 不增强
        if (enhance == null) {
            return invocation.proceed();
        }
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // 获取源数据
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        // 获取 映射语句
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // 获取方法的类型
        SqlCommandType sqlCmdType = mappedStatement.getSqlCommandType();

        // 当设置了类型 并且不是指定类型时候 不拦截
        if (type != null && type != sqlCmdType) {
            return invocation.proceed();
        }

        // 获取方法的全限定名, 或者通过boundSql.getParameterObject()获取
        String methodAllName = mappedStatement.getId();
        // 截取方法名
        String methodName;
        try {
            methodName = methodAllName.substring(methodAllName.lastIndexOf(".") + 1);
        } catch (Exception e) {
            methodName = null;
        }

        // 获取SQL 绑定
        BoundSql bound = statementHandler.getBoundSql();
        // 获取 SQL 语句
        String sql = bound.getSql();
        // 获取参数
        Object params = statementHandler.getParameterHandler().getParameterObject();

        // 参数不为空时 进行SQL 增强
        if (params != null) {
            sql = enhance.enhance(sql, params, sqlCmdType, methodName, invocation);
        }

        // 刷新SQL 语句
        metaObject.setValue("delegate.boundSql.sql", sql);
        // 继续mapper操作
        return invocation.proceed();
    }
}
