package cn.hll520.queryfilter.tools;


import org.junit.Test;

/**
 * 描述： 工具测试类
 *
 * @author lpc
 * @version 1.0 2021/4/5
 * @since 2021/4/5-下午11:25
 */
public class CheckSQLToolsTest {

    @Test
    public void checkSQLSort() {
    }

    @Test
    public void checkSQLInjection() {
        System.out.println(CheckSQLTools.checkSQLInjection("name"));
        System.out.println(CheckSQLTools.checkSQLInjection("select "));
        System.out.println(CheckSQLTools.checkSQLInjection(";drop "));
    }

    @Test
    public void checkSQLFilter() {
    }

    @Test
    public void checkSQLBranch() {
        System.out.println(CheckSQLTools.checkSQLBranch("name"));
        System.out.println(CheckSQLTools.checkSQLBranch("name';'drop all"));
    }
}