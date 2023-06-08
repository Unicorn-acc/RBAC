package rbac.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rbac.common.Result;
import rbac.entity.Role;
import rbac.service.IRoleService;
import rbac.vo.PageParams;

/**
 * @author MiracloW
 */
@RestController
@Slf4j
@Api(value = "角色管理", tags = "角色管理")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "角色分页查询接口")
    @PostMapping("/page")
    public Result<IPage<Role>> page(PageParams pageParams, @RequestBody Role role){
        return Result.success(roleService.getRoleListWithPage(pageParams, role));
    }


    @ApiOperation(value = "根据id查询角色接口")
    @GetMapping("/info/{id}")
    public Result<Role> getByid(@PathVariable("id") Long id){
        Role role = roleService.getById(id);
        return Result.success(role);
    }

    @ApiOperation(value = "新增角色接口")
    @PostMapping("/save")
    public Result<String> save(@RequestBody Role role){
        log.info(role.toString());
        boolean issaved = roleService.saveRole(role);
        if(issaved)
            return Result.success("添加角色成功");
        else
            return Result.error("添加角色失败");
    }

    @ApiOperation(value = "更新角色接口")
    @PostMapping("/update")
    public Result<String> update(@RequestBody Role role){
        boolean isupdated = roleService.updateRoleByRoleId(role);
        if(isupdated)
            return Result.success("更新角色成功");
        else
            return Result.error("更新角色失败");
    }

    @ApiOperation(value = "删除角色接口")
    @DeleteMapping("/remove/{id}")
    public Result<String> delete(@PathVariable("id") Long id){
        boolean isdeleted = roleService.removeRoleById(id);
        if(isdeleted)
            return Result.success("删除角色成功");
        else
            return Result.error("删除角色失败");
    }
}
