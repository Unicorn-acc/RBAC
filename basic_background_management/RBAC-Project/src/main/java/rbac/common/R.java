package rbac.common;

import lombok.Data;

import java.util.HashMap;

/**
 * @author MiracloW
 */
@Data
public class R extends HashMap { //ResultVo

//    private Integer code; // 请求状态码
//
//    private String msg; // 状态信息
//
//    private Object data; // 相应数据

    public static R success(){
        R r = new R();
        r.put("code", 200);
        return r;
    }

    public static R success(Object data){
        R success = success();
        success.put("data", data);
        return success;
    }

    public static R error(){
        R r = new R();
        r.put("code", 500);
        return r;
    }

    public static R error(String msg){
        R error = error();
        error.put("msg", msg);
        return error;
    }

    public static R write(Boolean flag){
        if(flag)
            return success();
        else
            return error();
    }
}
