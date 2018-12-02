package cn.net.zhipeng.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Accessors(chain = true)
@Setter
public class Msg  implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean success;
    private String message;
    private Map<String, Object> extend = new HashMap<>();

    public static Msg success() {
        Msg result = new Msg();
        result.setSuccess(true);
        result.setMessage("处理成功！");
        return result;
    }

    public static Msg success(String msg) {
        Msg result = new Msg();
        result.setSuccess(true);
        result.setMessage(msg);
        return result;
    }

    public static Msg fail() {
        Msg result = new Msg();
        result.setSuccess(false);
        result.setMessage("处理失败！");
        return result;
    }

    public static Msg fail(String msg) {
        Msg result = new Msg();
        result.setSuccess(false);
        result.setMessage(msg);
        return result;
    }

    public  Msg add(String name, Object value) {
        this.getExtend().put(name, value);
        return this;
    }
}
