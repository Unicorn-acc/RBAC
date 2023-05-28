package rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import rbac.entity.Emp;

import java.util.Map;

/**
 * @author MiracloW
 */
public interface IEmpService extends IService<Emp> {
    IPage getEmpListByParamWithPage(Map<String, Object> param);
}
