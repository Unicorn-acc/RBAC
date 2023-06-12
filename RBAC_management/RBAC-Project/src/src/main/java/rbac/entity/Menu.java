package rbac.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MiracloW
 */
@Data
public class Menu {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String url;

    private String urlName;

    private Integer status;

    private Integer type;

    private Long pid;

    private Integer orderNum;

    @TableField(fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private List<Menu> children;

    @TableField(exist = false)
    private String pidName;
}
