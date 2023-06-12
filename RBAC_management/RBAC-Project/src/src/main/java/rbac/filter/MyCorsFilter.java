package rbac.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.annotation.WebFilter;

/**
 * @author MiracloW
 */

@Component
public class MyCorsFilter {

    private CorsConfiguration corsConfig(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*"); // 设置访问源请求头，任意参数
        corsConfiguration.addAllowedMethod("*"); // 设置访问源请求方法， 任意请求方法
        corsConfiguration.addAllowedOriginPattern("*"); // 设置访问源地址，任意访问地址
        corsConfiguration.setMaxAge(3600L); // 设置跨域缓存时间
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig());
        return new CorsFilter(source);
    }
}
