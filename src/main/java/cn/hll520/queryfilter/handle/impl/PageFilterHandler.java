package cn.hll520.queryfilter.handle.impl;

import cn.hll520.queryfilter.handle.IQueryFilterHandler;
import cn.hll520.queryfilter.term.ITerm;
import cn.hll520.queryfilter.term.ITermPage;
import cn.hll520.queryfilter.tools.box.Parameter;
import cn.hll520.queryfilter.tools.box.ToolBox;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;


/**
 * 描述： 分页处理器
 * <p>如果每页大小为 -1 则表示不分页 </p>
 * <p>如果页数为负数 x 表示 offset |x| </p>
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午9:09
 */
@Slf4j
public class PageFilterHandler implements IQueryFilterHandler {
    /**
     * 处理SQL语句
     *
     * @param term    条件
     * @param sql     sql语句
     * @param toolBox 工具箱
     */
    @Override
    public void handle(ITerm term, StringBuilder sql, ToolBox toolBox) {
        if (!(term instanceof ITermPage)) {
            return;
        }
        ITermPage pageTerm = (ITermPage) term;
        // 每页大小
        Long size = pageTerm.acquireSize();

        // 反查出总数量
        if (toolBox != null && toolBox.getConnection() != null && size != null) {
            pageTerm.receiveTotal(totalCount(toolBox, sql.toString()));
        }

        // 分页
        if (size != null && size != -1) {
            size = size < 1 ? 1 : size;
            sql.append(" limit ").append(size);
            // 偏移量
            Long pageNum = pageTerm.acquirePageNum();
            if (pageNum != null) {
                // 小于1 视为 offset 去 abs绝对值  否则乘以大小进行分页
                pageNum = pageNum < 1 ? Math.abs(pageNum) : ((pageNum - 1) * size);
                sql.append(" offset ").append(pageNum);
            }
        }
    }

    /**
     * 计算未分页前的总数
     *
     * @param toolBox 工具箱
     * @param sql     sql原始语句
     * @return long
     */
    private Long totalCount(ToolBox toolBox, String sql) {
        long total = 0L;

        // 转换成小写
        sql = sql.toLowerCase(Locale.ROOT);
        // 取 FROM
        sql = sql.substring(sql.indexOf(" form "));

        // 取得order by
        int index = sql.lastIndexOf(" order by ");
        if (index != -1) {
            sql = sql.substring(0, index);
        }

        // 拼接
        sql = "select count(*) " + sql + " ;";
        try {
            PreparedStatement preparedStatement = toolBox.getConnection().prepareStatement(sql);
            List<Parameter> parameters = toolBox.getParameters();
            // 注入参数
            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    preparedStatement.setObject(i + 1, parameters.get(i).getValue());
                }
            }
            // 查询总数
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    total = resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("获取分页前总数失败！" +
                            "\n>>>>>>>>>>>>>>>查询语句如下:{}" +
                            "\n>>>>>>>>>>>>>>>失败信息如下:{}"
                    , sql, e.getMessage());
        }
        return total;
    }
}
