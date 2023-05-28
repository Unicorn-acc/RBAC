package rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import rbac.entity.Emp;

import java.util.Map;

/**
 * @author MiracloW
 */
public interface EmpMapper extends BaseMapper<Emp> {

    IPage<Emp> empPage(@Param("page") Page<Emp> page,@Param("param") Map<String, Object> param);
}
