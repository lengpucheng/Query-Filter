package cn.hll520.queryfilter.term;

import cn.hll520.queryfilter.object.TermPage;

/**
 * 描述： 条件分页接口
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午5:13
 */
public interface ITermPage extends ITerm {

    /**
     * 获取当前页数
     *
     * @return 当前页数
     */
    Integer acquirePageNum();

    /**
     * 获取每页大小
     *
     * @return 每页大小
     */
    Integer acquireSize();

    /**
     * 构建一个分页条件
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return 分页条件
     */
    static ITermPage build(int pageNum, int pageSize) {
        return new TermPage(pageNum, pageSize);
    }

    /**
     * 设置总数目
     *
     * @param total 总数目
     */
    default void receiveTotal(Long total) {
        // 待进行设置
    }

    /**
     * 初始化分页属性
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return this
     */
    ITermPage initializePage(int pageNum, int pageSize);
}
