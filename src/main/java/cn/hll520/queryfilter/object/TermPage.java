package cn.hll520.queryfilter.object;

import cn.hll520.queryfilter.term.ITermPage;
import lombok.Data;

/**
 * 描述： 分页默认实现对象
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午10:42
 */
@Data
public class TermPage implements ITermPage {
    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private Long total;

    public TermPage() {
    }

    public TermPage(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    /**
     * 获取当前页数
     *
     * @return 当前页数
     */
    @Override
    public Integer acquirePageNum() {
        return this.getPageNum();
    }

    /**
     * 获取每页大小
     *
     * @return 每页大小
     */
    @Override
    public Integer acquireSize() {
        return this.getPageSize();
    }

    /**
     * 设置总数目
     *
     * @param total 总数目
     */
    @Override
    public void receiveTotal(Long total) {
        this.setTotal(total);
    }

    /**
     * 初始化分页属性
     *
     * @param pageNum  页数
     * @param pageSize 每页大小
     * @return this
     */
    @Override
    public ITermPage initializePage(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        return this;
    }
}
