package rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbac.entity.Emp;
import rbac.entity.Menu;
import rbac.entity.Role;
import rbac.mapper.AuthMapper;
import rbac.service.IAuthService;
import rbac.service.IEmpService;
import rbac.service.IRoleService;
import rbac.vo.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author MiracloW
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private IEmpService empService;

    @Autowired
    private IRoleService roleService;

    @Override
    public List<Long> getMenuListByRoleId(Long roleId) {
        return authMapper.getMenuListByRoleId(roleId);
    }

    @Transactional
    @Override
    public Boolean saveRoleMenu(RoleMenuVo roleMenuVo) {

        authMapper.deleteRoleMenuByRoleId(roleMenuVo.getRoleId());
        if(roleMenuVo.getMenuIdList().size() != 0)
            authMapper.saveRoleMenuList(roleMenuVo);
        return true;
    }

    @Override
    public IPage<Emp> getEmpListByRoleId(Long roleId, PageParams pageParams) {
        return empService.getEmpByRoleId(roleId, pageParams);
    }

    @Override
    public IPage<Emp> getNotAddedEmpByRoleId(Long roleId, PageParams pageParams) {
        return empService.getNotAddedEmpByRoleId(roleId, pageParams);
    }

    @Override
    public Boolean saveRoleEmp(RoleEmpVo roleEmpVo) {
        if(roleEmpVo.getEmpIdList().size() != 0)
            authMapper.saveRoleEmpList(roleEmpVo);
        return true;
    }

    @Override
    public Boolean deleteRoleEmp(RoleRemoveEmpVo roleRemoveEmpVo) {
        authMapper.deleteRoleEmp(roleRemoveEmpVo);
        return true;
    }

    @Override
    public IPage<Role> getRoleListByEmpId(Long empId, PageParams pageParams) {
        return roleService.getRoleListByEmpId(empId, pageParams);
    }

    @Override
    public IPage<Role> getNotAddedRoleByEmpId(Long empId, PageParams pageParams) {
        return roleService.getNotAddedRoleByEmpId(empId, pageParams);
    }

    @Override
    public Boolean saveEmpRole(EmpRoleVo empRoleVo) {
        if(empRoleVo.getRoleIdList().size() != 0)
            authMapper.saveEmpRoleList(empRoleVo);
        return true;
    }

    @Override
    public List<String> getPermissionsByEmpId(Long emp_id) {
        List<String> permissionList = authMapper.getPermissionsByEmpId(emp_id);
        Set<String> permissionSet = new HashSet<>();
        for(String p : permissionList){
            if(ObjectUtils.isNotEmpty(p))
                permissionSet.add(p);
        }
        List<String> premissionlist = new ArrayList<>(permissionSet);
        return premissionlist;

    }
}
