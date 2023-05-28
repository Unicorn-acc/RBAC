package rbac.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rbac.common.R;

/**
 * @author MiracloW
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 系统异常
    @ExceptionHandler(Exception.class)
    public R systemExceptionHandler(Exception e){
        log.error("系统异常：",e);
        return R.error("系统繁忙，请稍后再试");
    }

    // 业务异常
    @ExceptionHandler(CustomException.class)
    public R CustomExceptionHandler(CustomException e){
        log.error("业务异常：",e);
         return R.error(e.getMsg());
    }
}
