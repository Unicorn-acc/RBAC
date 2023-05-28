package rbac.entity;

import lombok.Data;

/**
 * 用户Token
 * @author MiracloW
 */
@Data
public class UserToken {

    private Long userId;

    private String userName;
}
