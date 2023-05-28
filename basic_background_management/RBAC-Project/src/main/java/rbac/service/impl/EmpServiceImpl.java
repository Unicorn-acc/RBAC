package rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import rbac.entity.Emp;
import rbac.mapper.EmpMapper;
import rbac.service.IEmpService;

import java.util.Map;

/**
 * @author MiracloW
 */
@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements IEmpService {
    @Override
    public IPage getEmpListByParamWithPage(Map<String, Object> param) {
        // 从map中取出分页参数和查询字段参数
        Integer pagenum = (Integer)param.get("current");
        Integer pagesize = (Integer)param.get("size");
        Page<Emp> page = new Page<>(pagenum, pagesize);

        return baseMapper.empPage(page, param);
    }
}
