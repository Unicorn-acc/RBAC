package rbac.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author MiracloW
 */
@Data
public class Emp {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long empNo;

    private String empName;

    private String password;

    private Integer deptNo;

    private String job;

    private String phone;

    @TableField(fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd")
    private LocalDateTime createTime;

    @TableLogic
    @TableField(select = false)
    private Integer isDeleted;

    @TableField(exist = false)
    private List<Role> roleList;
}
