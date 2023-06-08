package rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import rbac.common.enums.ExceptionEnum;
import rbac.common.exception.CustomException;
import rbac.entity.Emp;
import rbac.mapper.EmpMapper;
import rbac.service.IEmpService;
import rbac.vo.EmpLoginVo;
import rbac.vo.PageParams;
import rbac.vo.UserPasswordVo;

import java.util.stream.Collectors;

/**
 * @author MiracloW
 */
@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements IEmpService {

    @Override
    public Emp login(EmpLoginVo empLoginVo) {
        String username = empLoginVo.getName();
        String password = empLoginVo.getPassword();
        if(ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)){
            throw new CustomException(ExceptionEnum.USER_DATA_EMPTY.getCode(), ExceptionEnum.USER_DATA_EMPTY.getMsg());
        }

        Emp emp = baseMapper.getUserByUsername(username);
        if(emp == null)
            throw new CustomException(ExceptionEnum.USER_NOT_EXIST.getCode(), ExceptionEnum.USER_NOT_EXIST.getMsg());

        if(!emp.getPassword().equals(password))
            throw new CustomException(ExceptionEnum.USER_NAME_OR_PASSWORD_WRONG.getCode(), ExceptionEnum.USER_NAME_OR_PASSWORD_WRONG.getMsg());

        emp.setPassword(null);

        return emp;
    }

    @Override
    public Boolean saveEmp(Emp emp) {
        Emp tmp = baseMapper.getUserByUsername(emp.getEmpName());
        if(tmp != null)
            throw new CustomException(ExceptionEnum.USER_EXIST.getCode(), ExceptionEnum.USER_EXIST.getMsg());
        return this.save(emp);
    }

    @Override
    public Boolean updateEmpById(Emp emp) {
        Emp user = this.getById(emp.getEmpNo());
        if(!user.getEmpName().equals(emp.getEmpName())){
            user = baseMapper.getUserByUsername(emp.getEmpName());
            if(user != null)
                throw new CustomException(ExceptionEnum.USER_EXIST.getCode(), ExceptionEnum.USER_EXIST.getMsg());
        }
        return this.updateById(emp);
    }

    @Override
    public IPage<Emp> getEmpListWithPage(PageParams pageParams, Emp emp) {
        Page<Emp> page = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());

        IPage<Emp> empIPage = baseMapper.empPage(page, emp);

        empIPage.setRecords(empIPage.getRecords().stream().map((item) ->{
            item.setPassword(null);
            return item;
        }).collect(Collectors.toList()));

        return empIPage;
    }

    @Override
    public IPage getEmpByRoleId(Long roleId, PageParams pageParams) {
        Page<Emp> page = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());

        IPage<Emp> empIPage = baseMapper.getEmpByRoleId(page, roleId);

        empIPage.setRecords(empIPage.getRecords().stream().map((item) ->{
            item.setPassword(null);
            return item;
        }).collect(Collectors.toList()));

        return empIPage;
    }

    @Override
    public IPage getNotAddedEmpByRoleId(Long roleId, PageParams pageParams) {
        Page<Emp> page = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());

        IPage<Emp> empIPage = baseMapper.getNotAddedEmpByRoleId(page, roleId);

        empIPage.setRecords(empIPage.getRecords().stream().map((item) ->{
            item.setPassword(null);
            return item;
        }).collect(Collectors.toList()));

        return empIPage;
    }

    @Transactional
    @Override
    public Boolean removeEmpbyid(Long empid) {
        this.removeById(empid);
        baseMapper.removeRelationshipByEmpid(empid);
        return true;
    }

    @Override
    public Boolean updatepassword(UserPasswordVo userPasswordVo) {
        if(userPasswordVo.getNewpassword().equals(userPasswordVo.getOripassword()))
            throw new CustomException(ExceptionEnum.USER_PASSWORD_SAME.getCode(), ExceptionEnum.USER_PASSWORD_SAME.getMsg());

        Emp emp = this.getById(userPasswordVo.getEmp_id());
        if(emp == null)
            throw new CustomException(ExceptionEnum.USER_NOT_EXIST.getCode(), ExceptionEnum.USER_NOT_EXIST.getMsg());
        if(!emp.getPassword().equals(userPasswordVo.getOripassword()))
            throw new CustomException(ExceptionEnum.USER_PASSWORD_WRONG.getCode(), ExceptionEnum.USER_PASSWORD_WRONG.getMsg());

        emp.setPassword(userPasswordVo.getNewpassword());
        this.updateById(emp);

        return true;
    }
}
