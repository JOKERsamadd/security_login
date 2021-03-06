package cn.net.zhipeng.entity;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {

    private static final long serialVersionUID = 1L;
    private long total;  //总记录数
    private List rows;   //每页显示的记录

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public PageResult(long total, List rows) {
        this.total = total;
        this.rows = rows;
    }
}
