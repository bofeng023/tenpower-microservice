package entity;

import java.util.List;

/**
 * 分页结果
 */
public class PageResult<T> {
    private long total; //总记录数
    private List<T> rows; //每页显示条数

    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
