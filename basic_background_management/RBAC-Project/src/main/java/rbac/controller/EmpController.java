package rbac.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rbac.common.R;
import rbac.entity.Emp;
import rbac.service.IEmpService;

import java.util.List;
import java.util.Map;

/**
 * @author MiracloW
 */
@Slf4j
@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private IEmpService empService;

    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> param){ // page复用分页和条件查询
//        int x = 10 / (Integer)param.get("deptNo"); 测试异常
        return R.success(empService.getEmpListByParamWithPage(param));
    }

    @GetMapping("/list")
    public R list(){
        return R.success(empService.list());
    }

    @GetMapping("/info/{id}")
    public R getById(@PathVariable("id") Integer id){
        Emp emp = empService.getById(id);
        return R.success(emp);
    }

    @PostMapping("/save")
    public R save(@RequestBody Emp emp){
//        log.info(emp.toString());
        boolean issaved = empService.save(emp);
        return R.write(issaved);
    }

    @PostMapping("/update")
    public R update(@RequestBody Emp emp){
        boolean isupdated = empService.updateById(emp);
        return R.write(isupdated);
    }

    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable("id") Integer id){
        boolean isdeleted = empService.removeById(id);
        return R.write(isdeleted);
    }

    @PostMapping("/remove")
    public R removeList(@RequestBody List<Long> ids){
        boolean isdeleted = empService.removeBatchByIds(ids);
        return R.write(isdeleted);
    }


}