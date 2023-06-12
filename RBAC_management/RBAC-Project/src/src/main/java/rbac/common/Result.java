package rbac.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.io.ResolverUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MiracloW
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private Integer code; //编码

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> Result<T> success() {
        Result<T> r = new Result<T>();
        r.code = 200;
        return r;
    }

    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<T>();
        r.data = object;
        r.code = 200;
        return r;
    }

    public static <T> Result<T> error() {
        Result r = new Result();
        r.code = 500;
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result r = new Result();
        r.code = 500;
        r.msg = msg;
        return r;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public static <T> Result<T> write(Integer code, String msg) {
        Result r = new Result();
        r.code = code;
        r.msg = msg;
        return r;
    }
}
