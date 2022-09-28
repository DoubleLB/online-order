package org.lbw.story.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.lbw.story.common.exception.NotAuthenticationException;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
public class TokenUtils {

    public static final String SECRET = "lbw123456";//密钥

    public static long expire_time = 100;//过期时间，单位：秒

    /**
     * 生成一个登录令牌
     *
     * @param tokenUser
     * @return
     */
    public static String loginSign(TokenUser tokenUser) {

        //创建一个加密算法
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        //创建一个令牌的构建者对象
        JWTCreator.Builder builder = JWT.create()
                .withClaim(TokenUser.CLAIM_NAME_TYPE, tokenUser.getType())
                .withClaim(TokenUser.CLAIM_NAME_USERID, tokenUser.getUserId())
                .withClaim(TokenUser.CLAIM_NAME_USERNAME, tokenUser.getUserName());

        if (tokenUser.isAdmin()) {
            if (tokenUser.getShopId() != null && StringUtils.hasText(tokenUser.getShopName())) {
                builder.withClaim(TokenUser.CLAIM_NAME_SHOPID, tokenUser.getShopId())
                        .withClaim(TokenUser.CLAIM_NAME_SHOPNAME, tokenUser.getShopName());
            }
        }

        //设置过期时间
        builder.withExpiresAt(new Date(System.currentTimeMillis()+expire_time*1000));

        //通过加密算法构建一个令牌
        String token = builder.sign(algorithm);

        return token;
    }

    /**
     * 从客户端令牌中获取用户信息
     *
     * @param clientToken
     * @return
     */
    public static TokenUser getTokenUser(String clientToken) {

        if (!StringUtils.hasText(clientToken)) {
            throw new NotAuthenticationException("令牌为空！请登录");
        }

        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWT.decode(clientToken);
        } catch (JWTDecodeException e) {
            throw new NotAuthenticationException("令牌错误！请登录");
        }

        //解码
        Integer type = decodedJWT.getClaim(TokenUser.CLAIM_NAME_TYPE).asInt();
        if (type == null) {
            throw new NotAuthenticationException("令牌无用户类型！请登录");
        }

        String userId = decodedJWT.getClaim(TokenUser.CLAIM_NAME_USERID).asString();
        String userName = decodedJWT.getClaim(TokenUser.CLAIM_NAME_USERNAME).asString();
        if (!StringUtils.hasText(userId) || !StringUtils.hasText(userName)) {
            throw new NotAuthenticationException("令牌无用户信息！请登录");
        }

        if (TokenUser.TYPE_ADMIN.equals(type)) {
            Integer shopId = decodedJWT.getClaim(TokenUser.CLAIM_NAME_SHOPID).asInt();
            String shopName = decodedJWT.getClaim(TokenUser.CLAIM_NAME_SHOPNAME).asString();

            return TokenUser.creatAdmin(userId, userName, shopId, shopName);
        }


        return TokenUser.creatUser(userId, userName);

    }

    /**
     * 校验客户端令牌。如果验证不通过，抛出自定义异常NotAuthenticationException
     *
     * @param clientToken
     * @return
     */
    public static TokenUser verify(String clientToken) {

        TokenUser tokenUser = getTokenUser(clientToken);

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();

        try {
            verifier.verify(clientToken);
            return tokenUser;
        } catch (TokenExpiredException e) {
            throw new NotAuthenticationException("令牌失效！请登录");
        } catch (JWTVerificationException e) {
            throw new NotAuthenticationException("令牌非法！请登录");
        }

    }
}
