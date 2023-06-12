package rbac.vo;

import lombok.Data;

/**
 * @author MiracloW
 */
@Data
public class UserPasswordVo {

    private long emp_id;

    private String oripassword;

    private String newpassword;
}
