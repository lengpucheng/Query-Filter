package cn.hll520.queryfilter.tools;

/**
 * 描述： SQL处理相关工具
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:37
 */
public class SQLHandleTools {
    /**
     * 移除末尾的分号和空格
     *
     * @param sql 语句
     * @return 移除分号和空格后的sql语句
     */
    public static StringBuilder removeBranch(String sql) {
        // 去除两边空格
        sql = sql.trim();
        int length = sql.length();
        Character branch = null;
        // 取最后一个字符
        if (length > 1) {
            branch = sql.charAt(length - 1);
        }
        // 如果是分号就移除
        if (branch != null && branch == ';') {
            sql = sql.substring(0, length - 1).trim();
            // 继续判断
            return removeBranch(sql);
        }
        return new StringBuilder(sql);
    }

    /**
     * 判断是否紧跟Where
     *
     * @param sql sql语句
     * @return 是否紧跟where 是返回true
     */
    public static boolean isWhere(StringBuilder sql) {
        int length = sql.length();
        // 如果小于4（即不可能包括where)
        if (length < 4) {
            return false;
        }
        // 获取最后四个
        String term = sql.substring(length - 4);
        // 判断是否紧跟where
        return "where".equalsIgnoreCase(term);
    }

    /**
     * 检查是否包含 where
     * <p>若不包含where 将会在 最后补上where</p>
     *
     * @param sql sql语句
     */
    public static void checkContainWhere(StringBuilder sql) {
        // 如果包含就跳过
        if (sql.toString().contains(" where")) {
            return;
        }
        // 否则添加 where
        sql.append(" where ");
    }

}
