package rbac.mapper;

import rbac.entity.Emp;
import rbac.entity.Menu;
import rbac.vo.EmpRoleVo;
import rbac.vo.RoleEmpVo;
import rbac.vo.RoleMenuVo;
import rbac.vo.RoleRemoveEmpVo;

import java.util.List;

/**
 * @author MiracloW
 */
public interface AuthMapper {

    List<Long> getMenuListByRoleId(Long roleId);

    Integer deleteRoleMenuByRoleId(Long roleId);

    // 批量插入
    Integer saveRoleMenuList(RoleMenuVo roleMenuVo);

    Integer saveRoleEmpList(RoleEmpVo roleEmpVo);

    Integer deleteRoleEmp(RoleRemoveEmpVo roleRemoveEmpVo);

    Integer saveEmpRoleList(EmpRoleVo empRoleVo);

    List<String> getPermissionsByEmpId(Long emp_Id);
}
