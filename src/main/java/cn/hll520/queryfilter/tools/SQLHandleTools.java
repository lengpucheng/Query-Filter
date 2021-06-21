package cn.hll520.queryfilter.tools;

import cn.hll520.queryfilter.QueryFilterException;
import cn.hll520.queryfilter.tools.handle.HandleTools;
import cn.hll520.queryfilter.tools.handle.HandleToolsMySQL;

import java.util.Locale;

/**
 * 描述： SQL处理工具类 自适应不同版本数据库
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:37
 */
public class SQLHandleTools {
    /**
     * 是否已经修改过版本
     */
    private static boolean IS_PUT_TYPE = false;
    /**
     * 版本型号
     */
    private static String SQL_TYPE = "MYSQL";
    /**
     * 当前数据库版本的工具库
     */
    private static HandleTools handleTools = null;

    /**
     * 手动修改数据库版本
     *
     * @param type 数据库版本 英文
     */
    public static void setDBType(String type) {
        if (IS_PUT_TYPE) {
            throw new QueryFilterException("已经修改过数据库版本，无法再次修改");
        }
        IS_PUT_TYPE = true;
        SQL_TYPE = type.toUpperCase(Locale.ROOT);
    }

    /**
     * 带参数构造
     *
     * @param type 数据库版本
     * @return 切换数据库工具
     */
    public static HandleTools build(String type) {
        if (!IS_PUT_TYPE) {
            setDBType(type);
            handleTools = initialize();
        }
        return handleTools;
    }

    /**
     * 构建工具库
     *
     * @return 对应版本的工具库
     */
    public static HandleTools build() {
        if (handleTools == null) {
            handleTools = initialize();
        }
        return handleTools;
    }

    /**
     * 初始化数据库处理工具
     *
     * @return 处理工具
     */
    private static HandleTools initialize() {
        switch (SQL_TYPE) {
            case "MYSQL":
                return new HandleToolsMySQL();
            case "ORACLE":
                System.out.println("目前不支持Oracle");
                break;
            case "SQLSERVER":
                System.out.println("目前不支持SQLServer");
            default:
                break;
        }
        return null;
    }
}
