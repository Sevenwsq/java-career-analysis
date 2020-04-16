package cn.wsq.analysis.javacareer.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Silent
 * @date 2020/4/13 19:31:19
 * @description
 */
@Data
public class BaseVo<T> {

    /**
     * 当前页数
     */
    private Long current;

    /**
     * 每页个数
     */
    private Long size;

    /**
     * 一共多少页数
     */
    private Long pages;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 排序字段
     */
    private String sortColumn;

    /**
     * 排序方法 ASC 或 DESC
     */
    private String sortMethod;

    /**
     * 返回数据
     */
    private List<T> list;
}
