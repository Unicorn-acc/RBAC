package rbac.common.exception;

import lombok.Data;

/**
 * @author MiracloW
 */
@Data
public class UserTokenExpiredException extends CustomException{
    public UserTokenExpiredException(Integer code, String msg){
        super(code, msg);
    }
}
