package rbac.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rbac.common.Result;
import rbac.entity.Menu;
import rbac.service.IMenuService;

import java.util.List;


/**
 * @author MiracloW
 */
@RestController
@Slf4j
@Api(value = "菜单管理", tags = "菜单管理")
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;


    @ApiOperation(value = "获取菜单树接口")
    @GetMapping("/tree")
    public Result<List<Menu>> tree(){
        return Result.success(menuService.getTreelist());
    }


    @ApiOperation(value = "添加菜单接口")
    @PostMapping("/save")
    public Result<String> save(@RequestBody Menu menu){
        boolean issaved = menuService.save(menu);
        if(issaved)
            return Result.success("更新菜单成功");
        else
            return Result.error("更新菜单失败");
    }


    @ApiOperation(value = "更新菜单接口")
    @PostMapping("/update")
    public Result<String> update(@RequestBody Menu menu){
        boolean isupdated = menuService.updateById(menu);
        if(isupdated)
            return Result.success("更新菜单成功");
        else
            return Result.error("更新菜单失败");
    }

    @ApiOperation(value = "删除菜单接口")
    @DeleteMapping("/remove/{id}")
    public Result<String> remove(@PathVariable("id") Long id){
        boolean isdeleted = menuService.removeById(id);
        if(isdeleted)
            return Result.success("更新菜单成功");
        else
            return Result.error("更新菜单失败");
    }

    @ApiOperation(value = "查询菜单接口")
    @GetMapping("/info/{id}")
    public Result<Menu> info(@PathVariable("id") Long id){
        return Result.success(menuService.getInfoByidWithpidName(id));
    }


}
