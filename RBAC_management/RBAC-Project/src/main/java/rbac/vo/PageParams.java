package rbac.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MiracloW
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {

    private int pageNum;

    private int pageSize;
}
