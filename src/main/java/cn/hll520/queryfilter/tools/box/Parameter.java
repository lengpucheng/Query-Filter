package cn.hll520.queryfilter.tools.box;

/**
 * 描述：参数
 *
 * @author LPC_MI
 * @version 1.0 2021/6/21
 * @since 2021/6/21-17:08
 */
public class Parameter {
    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数值
     */
    private Object value;

    public Parameter() {
    }

    public Parameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
