package rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rbac.common.enums.ExceptionEnum;
import rbac.common.exception.CustomException;
import rbac.entity.Emp;
import rbac.entity.Role;
import rbac.mapper.RoleMapper;
import rbac.service.IRoleService;
import rbac.vo.PageParams;

import java.util.List;
import java.util.Map;

/**
 * @author MiracloW
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public IPage<Role> getRoleListWithPage(PageParams pageParams, Role role) {
        Page<Role> pageinfo = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());
        return baseMapper.rolePage(pageinfo, role);
    }

    @Override
    public IPage getRoleListByEmpId(Long empId, PageParams pageParams) {
        Page<Role> pageinfo = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());

        return baseMapper.getRoleListByEmpId(pageinfo, empId);
    }

    @Override
    public IPage getNotAddedRoleByEmpId(Long empId, PageParams pageParams) {
        Page<Role> pageinfo = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());

        return baseMapper.getNotAddedRoleByEmpId(pageinfo, empId);
    }

    @Transactional
    @Override
    public Boolean removeRoleById(Long roleId) {
        this.removeById(roleId);
        baseMapper.removeEmpRoleRelationshipByRoleId(roleId);
        baseMapper.removeRoleMenuRelationshipByRoleId(roleId);
        return true;
    }

    @Override
    public Boolean saveRole(Role role) {
        Role currole = baseMapper.getRoleByRoleName(role.getRoleName());
        if(currole != null)
            throw new CustomException(ExceptionEnum.ROLE_EXIST.getCode(), ExceptionEnum.ROLE_EXIST.getMsg());
        return this.save(role);
    }

    @Override
    public Boolean updateRoleByRoleId(Role role) {
        Role currole = this.getById(role.getId());
        if(!currole.getRoleName().equals(role.getRoleName())){
            currole = baseMapper.getRoleByRoleName(role.getRoleName());
            if(currole != null)
                throw new CustomException(ExceptionEnum.ROLE_EXIST.getCode(), ExceptionEnum.ROLE_EXIST.getMsg());
        }
        return this.updateById(role);
    }


}
