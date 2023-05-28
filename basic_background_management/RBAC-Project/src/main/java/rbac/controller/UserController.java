package rbac.controller;

import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import rbac.common.R;
import rbac.common.exception.CustomException;
import rbac.common.utils.JWTUtils;
import rbac.context.UserContextHolder;
import rbac.entity.User;
import rbac.entity.UserToken;
import rbac.service.IUserService;

/**
 * @author MiracloW
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user")
    public R user(){
        UserToken userToken = UserContextHolder.get();
        R r = R.success();
        r.put("userToken", userToken);
        return r;
    }

    @PostMapping("/login")
    public R login(String username, String password){

        if(ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)){
            return R.error("用户名或者密码为空");
        }

        User user = userService.getUserByUsername(username);
        if(user == null)
            return R.error("用户名不存在");
        if(!user.getPassword().equals(password))
            return R.error("用户名或密码错误");

        // 生成Token
        UserToken userToken = new UserToken();
        userToken.setUserId(user.getId());
        userToken.setUserName(user.getUserName());
        String token = JWTUtils.createToken(userToken);

        R r = R.success();
        r.put("token", token);
        return r;
    }
}
