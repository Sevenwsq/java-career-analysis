package cn.wsq.analysis.javacareer.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Silent
 * @date 2020/4/13 11:43:28
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultObj<T> {
    private Integer code = 200;
    private String msg = "";
    private T data;

    public ResultObj(T data) {
        this.data = data;
    }

    public ResultObj(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultObj(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultObj(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }
}
