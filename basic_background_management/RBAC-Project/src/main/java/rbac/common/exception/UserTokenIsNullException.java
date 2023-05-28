package rbac.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MiracloW
 */
@Data
public class UserTokenIsNullException extends CustomException{

    public UserTokenIsNullException(Integer code, String msg){
        super(code, msg);
    }
}
