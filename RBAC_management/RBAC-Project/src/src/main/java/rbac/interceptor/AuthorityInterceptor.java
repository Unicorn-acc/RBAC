package rbac.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import rbac.common.enums.ExceptionEnum;
import rbac.common.exception.CustomException;
import rbac.context.UserContextHolder;
import rbac.service.IAuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * @author MiracloW
 */
@Component
public class AuthorityInterceptor implements HandlerInterceptor {

    @Autowired
    private IAuthService authService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 放行超级管理员
        String userName = UserContextHolder.get().getUserName();
        if(userName.equals("admin"))
            return true;

        // 获取该用户拥有的所有权限，一一对比
        List<String> perSet = authService.getPermissionsByEmpId(UserContextHolder.get().getUserId());
        String url = request.getRequestURL().toString();

        boolean flag = false;
        for(String per : perSet){
            if(antPathMatcher.match(per, url)){
                flag = true;
                break;
            }
        }
        if(!flag)
            throw new CustomException(ExceptionEnum.AUTHORITY_NOT_ACCESS.getCode(), ExceptionEnum.AUTHORITY_NOT_ACCESS.getMsg());

        return true;
    }
}
