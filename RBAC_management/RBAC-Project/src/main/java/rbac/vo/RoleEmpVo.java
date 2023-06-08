package rbac.vo;

import lombok.Data;

import java.util.List;

/**
 * @author MiracloW
 */
@Data
public class RoleEmpVo {

    private Long roleId;

    private List<Long> empIdList;

}
