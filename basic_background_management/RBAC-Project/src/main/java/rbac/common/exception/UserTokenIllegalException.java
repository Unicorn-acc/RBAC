package rbac.common.exception;

import lombok.Data;

/**
 * @author MiracloW
 */
@Data
public class UserTokenIllegalException extends CustomException{
    public UserTokenIllegalException(Integer code, String msg){
        super(code, msg);
    }
}
