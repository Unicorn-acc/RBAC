package rbac.common.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import rbac.token.UserToken;

import java.util.Date;

/**
 * JWT生成用户Token
 * @author MiracloW
 */
public class JWTUtils {

    private static final String USERTOKEN = "token";

    private static final long TOKEN_TIMEOUT = 60 * 30 * 1000; // 30分钟

    private static final Algorithm SIGN = Algorithm.HMAC256("SECURITY");

    /**
     * 创建一个新的Token字符串
     * @param userToken 用户信息
     * @return
     */
    public static String createToken(UserToken userToken) {

        String userjson = JSON.toJSONString(userToken);

        String token = JWT.create()
                .withClaim(USERTOKEN, userjson)
                .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_TIMEOUT)) // 设置超时时间三十分钟
                .sign(SIGN);
        return token;
    }

    public static DecodedJWT checkToken(String token) throws JWTVerificationException {
        return JWT.require(SIGN).build().verify(token);
    }

    public static UserToken getUserToken(String token) {
        DecodedJWT decodedJWT = checkToken(token);
        String tokenjson = decodedJWT.getClaim(USERTOKEN).asString();
        return JSON.parseObject(tokenjson, UserToken.class);
    }
}
