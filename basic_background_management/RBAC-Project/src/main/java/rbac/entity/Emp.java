package rbac.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author MiracloW
 */
@Data
public class Emp {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long empNo;

    private String empName;

    private Integer deptNo;

    private String job;

    private String phone;

    @TableField(fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd")
    private LocalDateTime createTime;


}
