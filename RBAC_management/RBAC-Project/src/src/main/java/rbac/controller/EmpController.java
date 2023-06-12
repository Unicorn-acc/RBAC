package rbac.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rbac.common.Result;
import rbac.entity.Emp;
import rbac.service.IEmpService;
import rbac.vo.PageParams;

/**
 * @author MiracloW
 */
@Slf4j
@RestController
@Api(value = "用户管理", tags = "用户管理")
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private IEmpService empService;

    @ApiOperation(value = "用户分页查询接口")
    @PostMapping("/page")
    public Result<IPage<Emp>> page(PageParams pageParams, @RequestBody Emp emp){ // page复用分页和条件查询
//        int a = 1 / emp.getDeptNo(); // 模拟系统异常
        return Result.success(empService.getEmpListWithPage(pageParams, emp));
    }

    @ApiOperation(value = "根据Id查询用户接口")
    @GetMapping("/info/{id}")
    public Result<Emp> getById(@PathVariable("id") Integer id){
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }

    @ApiOperation(value = "新增用户接口")
    @PostMapping("/save")
    public Result<String> save(@RequestBody Emp emp){
        boolean issaved = empService.saveEmp(emp);
        if(issaved)
            return Result.success("添加用户成功");
        else
            return Result.error("添加用户失败");
    }

    @ApiOperation(value = "更新用户接口")
    @PostMapping("/update")
    public Result<String> update(@RequestBody Emp emp){
        boolean isupdated = empService.updateEmpById(emp);
        if(isupdated)
            return Result.success("更新用户成功");
        else
            return Result.error("更新用户失败");
    }

    @ApiOperation(value = "删除用户接口")
    @DeleteMapping("/remove/{id}")
    public Result<String> remove(@PathVariable("id") Long id){
        boolean isdeleted = empService.removeEmpbyid(id);
        if(isdeleted)
            return Result.success("删除用户成功");
        else
            return Result.error("删除用户失败");
    }
}