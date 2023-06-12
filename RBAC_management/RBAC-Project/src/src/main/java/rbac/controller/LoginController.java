package rbac.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rbac.common.Result;
import rbac.common.utils.JWTUtils;
import rbac.context.UserContextHolder;
import rbac.entity.Emp;
import rbac.service.IAuthService;
import rbac.token.UserToken;
import rbac.service.IEmpService;
import rbac.vo.EmpLoginVo;
import rbac.vo.UserPasswordVo;

/**
 * @author MiracloW
 */
@Slf4j
@Api(value = "用户登录管理", tags = "用户登录管理")
@RestController
public class LoginController {

    @Autowired
    private IEmpService empService;

    @Autowired
    private IAuthService authService;

    private long key_timeout = 60 * 30 * 1000; // key超时时间30分钟

    @ApiOperation(value = "用户登录接口")
    @PostMapping("/login")
    public Result<String> login(@RequestBody EmpLoginVo empLoginVo){
        Emp emp = empService.login(empLoginVo);

        // 登录成功，将用户拥有的权限保存到Redis中
        Long id = emp.getEmpNo();
//        authService.getPermissionsByEmpId(id);


        // 使用JWT加密用户名和token，然后返回
        // 生成返回给前端的token
        UserToken userToken = new UserToken();
        userToken.setUserId(emp.getEmpNo());
        userToken.setUserName(emp.getEmpName());

        String JWTtoken = JWTUtils.createToken(userToken);

        return Result.success(JWTtoken);
    }

    @ApiOperation(value = "查询用户信息接口")
    @GetMapping("/userinfo")
    public Result<UserToken> userinfo(){
        UserToken userToken = UserContextHolder.get();
        return Result.success(userToken);
    }

    @ApiOperation(value = "修改用户密码接口")
    @PostMapping("/updatepassword")
    public Result<String> updatepassword(@RequestBody UserPasswordVo userPasswordVo){
        log.info(userPasswordVo.toString());
        Boolean isupdated = empService.updatepassword(userPasswordVo);
        if(isupdated)
            return Result.success("更新成功");
        else
            return Result.error("更新失败");
    }
}
