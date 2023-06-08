package rbac;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author MiracloW
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "rbac.mapper")
public class RBACApplication {

    public static void main(String[] args){
        SpringApplication.run(RBACApplication.class, args);
        log.info("项目启动成功！");
    }
}
