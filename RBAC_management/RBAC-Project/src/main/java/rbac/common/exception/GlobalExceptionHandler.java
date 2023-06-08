package rbac.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rbac.common.Result;

/**
 * @author MiracloW
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 系统异常
    @ExceptionHandler(Exception.class)
    public Result<String> systemExceptionHandler(Exception e){
        log.error("系统异常：",e);
        return Result.error("系统繁忙，请稍后再试");
    }

    // 业务异常
    @ExceptionHandler(CustomException.class)
    public Result<String> CustomExceptionHandler(CustomException e){
        log.error("业务异常，异常代码：" + e.getCode() + "， 信息：" + e.getMsg());
        return Result.write(e.getCode(), e.getMsg());
    }
}
