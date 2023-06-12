package rbac.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rbac.common.Result;
import rbac.context.UserContextHolder;
import rbac.entity.Emp;
import rbac.entity.Role;
import rbac.service.IAuthService;
import rbac.token.UserToken;
import rbac.vo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author MiracloW
 */
@Api(value = "权限管理", tags = "权限管理")
@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @ApiOperation("根据角色ID获取分配权限接口")
    @GetMapping("/getMenuListByRoleId/{id}")
    public Result<List<Long>> getMenuListByRoleId(@PathVariable("id") Long id){
        return Result.success(authService.getMenuListByRoleId(id));
    }

    @ApiOperation("更新角色ID分配权限接口")
    @PostMapping("/saveRoleMenu")
    public Result<String> saveRoleMenu(@RequestBody RoleMenuVo roleMenuVo){
       boolean issaved = authService.saveRoleMenu(roleMenuVo);
       if(issaved)
           return Result.success("保存成功");
       else
           return Result.error("保存失败");
    }

    @ApiOperation("根据角色ID获取已分配用户接口")
    @PostMapping("/getempByRoleId")
    public Result<IPage<Emp>> getEmpListByRoleId(Long id, @RequestBody PageParams pageParams){
        return Result.success(authService.getEmpListByRoleId(id, pageParams));
    }

    @ApiOperation("根据角色ID获取未分配用户接口")
    @PostMapping("/getNotAddedEmpByRoleId")
    public Result<IPage<Emp>> getNotAddedEmpByRoleId(Long id, @RequestBody PageParams pageParams){
        return Result.success(authService.getNotAddedEmpByRoleId(id, pageParams));
    }

    @ApiOperation("保存角色分配的新用户接口")
    @PostMapping("/saveRoleEmp")
    public Result<String> saveRogetMenuListByRoleIdleEmp(@RequestBody RoleEmpVo roleEmpVo){
        boolean issaved = authService.saveRoleEmp(roleEmpVo);
        if(issaved)
            return Result.success("保存成功");
        else
            return Result.error("保存失败");
    }

    @ApiOperation("删除角色已分配的用户接口")
    @PostMapping("/deleteRoleEmp")
    public Result<String> deleteRoleEmp(@RequestBody RoleRemoveEmpVo roleRemoveEmpVo){
        boolean isdeleted = authService.deleteRoleEmp(roleRemoveEmpVo);
        if(isdeleted)
            return Result.success("删除成功");
        else
            return Result.error("删除失败");
    }

    @ApiOperation("删除用户已分配的角色接口")
    @PostMapping("/deleteEmpRole")
    public Result<String> deleteEmpRole(@RequestBody RoleRemoveEmpVo roleRemoveEmpVo){
        boolean isdeleted = authService.deleteRoleEmp(roleRemoveEmpVo);
        if(isdeleted)
            return Result.success("删除成功");
        else
            return Result.error("删除失败");
    }

    @ApiOperation("根据用户ID获取已分配角色接口")
    @PostMapping("/getRoleByEmpId")
    public Result<IPage<Role>> getRoleByEmpId(Long id, @RequestBody PageParams pageParams){
        return Result.success(authService.getRoleListByEmpId(id, pageParams));
    }

    @ApiOperation("根据用户ID获取未分配角色接口")
    @PostMapping("/getNotAddedRoleByEmpId")
    public Result<IPage<Role>> getNotAddedRoleByEmpId(Long id, @RequestBody PageParams pageParams){
        return Result.success(authService.getNotAddedRoleByEmpId(id, pageParams));
    }

    @ApiOperation("保存用户分配的新角色接口")
    @PostMapping("/saveEmpRole")
    public Result<String> saveEmpRole(@RequestBody EmpRoleVo empRoleVo){
        boolean issaved = authService.saveEmpRole(empRoleVo);
        if(issaved)
            return Result.success("保存成功");
        else
            return Result.error("保存失败");
    }

    @ApiOperation("获取用户的所有的权限接口")
    @GetMapping("/getAuth")
    public Result<List<String>> getAuth(){
        UserToken userToken = UserContextHolder.get();
        List<String> permissions = authService.getPermissionsByEmpId(userToken.getUserId());
        return Result.success(permissions);
    }
}
