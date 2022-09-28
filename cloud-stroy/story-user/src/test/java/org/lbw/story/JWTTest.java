package org.lbw.story;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.lbw.story.common.TokenUser;

import java.util.Date;


@Slf4j
public class JWTTest {

    @Test
    public void test01(){

        TokenUser tokenUser  =  TokenUser.creatUser("test","测试用户");

        //创建一个加密算法
        Algorithm algorithm = Algorithm.HMAC256("123456");


        //创建一个令牌的构建者对象
        JWTCreator.Builder builder = JWT.create();

        //通过构建者设置令牌需要携带的信息和过期时间等
        builder
                .withClaim(TokenUser.CLAIM_NAME_USERID,tokenUser.getUserId())
                .withClaim(TokenUser.CLAIM_NAME_USERNAME,tokenUser.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis()+60*1000));

        //通过加密算法构建一个令牌
        String token = builder.sign(algorithm);
        log.debug("Token:{}",token);
    }

    @Test
    public void test02(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDTEFJTV9OQU1FX1VTRVJJRCI6InRlc3QiLCJDTEFJTV9OQU1FX1VTRVJOQU1FIjoi5rWL6K-V55So5oi3IiwiZXhwIjoxNjUzMzc5NTM1fQ.sPj3xYMHGXVx4X9oTRwgc4BQUX8zgtSL5lg6mRXuQ_U";

        //获取解码后的令牌
        DecodedJWT decodedJWT = JWT.decode(token);

        String userId = decodedJWT.getClaim(TokenUser.CLAIM_NAME_USERID).asString();
        String userName = decodedJWT.getClaim(TokenUser.CLAIM_NAME_USERNAME).asString();
        log.debug("userId:{},userName:{}",userId,userName);
    }

    @Test
    public void test03(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDTEFJTV9OQU1FX1VTRVJJRCI6InRlc3QiLCJDTEFJTV9OQU1FX1VTRVJOQU1FIjoi5rWL6K-V55So5oi3IiwiZXhwIjoxNjUzMzc5NTM1fQ.sPj3xYMHGXVx4X9oTRwgc4BQUX8zgtSL5lg6mRXuQ_U";

        //通过生成令牌使用加密算法和密钥获取令牌构建器的构建者
        Verification verification = JWT.require(Algorithm.HMAC256("123456"));

        //通过构建者设置对哪些信息需要校验
        verification
                .withClaim(TokenUser.CLAIM_NAME_USERID,"test")
                .withClaim(TokenUser.CLAIM_NAME_USERNAME,"测试用户");

        //通过构建器创建者创建一个验证器
        JWTVerifier verifier = verification.build();

        //校验令牌
        try {
            DecodedJWT decodedJWT =  verifier.verify(token);
            log.debug("userId:{},userName:{}",decodedJWT.getClaim(TokenUser.CLAIM_NAME_USERID),decodedJWT.getClaim(TokenUser.CLAIM_NAME_USERNAME));

        } catch (TokenExpiredException e) {
            log.debug("令牌过期!");
        }catch (JWTVerificationException e){
            e.printStackTrace();
        }
    }
}
