package rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import rbac.entity.Emp;
import rbac.entity.Role;
import rbac.vo.PageParams;

import java.util.List;
import java.util.Map;

/**
 * @author MiracloW
 */
public interface IRoleService extends IService<Role> {

    IPage<Role> getRoleListWithPage(PageParams pageParams, Role role);

    IPage getRoleListByEmpId(Long empId, PageParams pageParams);

    IPage getNotAddedRoleByEmpId(Long empId, PageParams pageParams);

    Boolean removeRoleById(Long roleId);

    Boolean saveRole(Role role);

    Boolean updateRoleByRoleId(Role role);
}
