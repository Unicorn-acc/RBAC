package rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import rbac.entity.Emp;
import rbac.entity.Menu;
import rbac.entity.Role;
import rbac.vo.*;

import java.util.List;
import java.util.Set;

/**
 * @author MiracloW
 */
public interface IAuthService {

    List<Long> getMenuListByRoleId(Long roleId);

    Boolean saveRoleMenu(RoleMenuVo roleMenuVo);

    IPage<Emp> getEmpListByRoleId(Long roleId, PageParams pageParams);

    IPage<Emp> getNotAddedEmpByRoleId(Long roleId, PageParams pageParams);

    Boolean saveRoleEmp(RoleEmpVo roleEmpVo);

    Boolean deleteRoleEmp(RoleRemoveEmpVo roleRemoveEmpVo);

    IPage<Role> getRoleListByEmpId(Long empId, PageParams pageParams);

    IPage<Role> getNotAddedRoleByEmpId(Long empId, PageParams pageParams);

    Boolean saveEmpRole(EmpRoleVo empRoleVo);

    Set<String> getPermissionsByEmpId(Long emp_id);

}
