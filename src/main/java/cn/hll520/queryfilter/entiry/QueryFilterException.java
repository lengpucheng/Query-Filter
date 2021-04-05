package cn.hll520.queryfilter.entiry;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 描述： 查询过滤异常
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryFilterException extends RuntimeException {
    /**
     * 详情信息
     */
    private String desc;
    /**
     * 错误SQL语句 可能为null
     */
    private String sql;

    public QueryFilterException() {
        super();
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public QueryFilterException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public QueryFilterException(String message, String desc, String sql) {
        super(message);
        this.desc = desc;
        this.sql = sql;
    }


    @Override
    public void printStackTrace() {
        System.err.println(LocalDateTime.now() + "----->[" + super.getMessage() + "]");
        System.err.println("##详情信息 ----->" + desc);
        System.err.println("##原始SQL语句 ----->" + sql);
        super.printStackTrace();
    }
}
