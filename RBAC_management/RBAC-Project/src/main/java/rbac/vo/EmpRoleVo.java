package rbac.vo;

import lombok.Data;

import java.util.List;

/**
 * @author MiracloW
 */
@Data
public class EmpRoleVo {

    private Long empId;

    private List<Long> roleIdList;
}
