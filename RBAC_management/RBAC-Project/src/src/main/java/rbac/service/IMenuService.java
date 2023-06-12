package rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import rbac.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 * @author MiracloW
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getTreelist();

    Menu getInfoByidWithpidName(Long id);
}
