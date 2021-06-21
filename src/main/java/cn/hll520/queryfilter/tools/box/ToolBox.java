package cn.hll520.queryfilter.tools.box;

import java.sql.Connection;
import java.util.List;

/**
 * 描述：工具箱
 *
 * @author LPC_MI
 * @version 1.0 2021/6/21
 * @since 2021/6/21-17:07
 */
public class ToolBox {
    /**
     * 链接对象
     */
    private Connection connection;

    /**
     * 参数集合
     */
    private List<Parameter> parameters;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
