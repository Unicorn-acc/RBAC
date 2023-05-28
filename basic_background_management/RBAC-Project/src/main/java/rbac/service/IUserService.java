package rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import rbac.entity.User;

/**
 * @author MiracloW
 */
public interface IUserService extends IService<User> {
    public User getUserByUsername(String username);
}
