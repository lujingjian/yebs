package com.example.server.config.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*jwt工具类*/
@Component
public class jwtUtil {
    //配置常量
    public static final String yonghuming_KEY="sub";
    //jwt创建时间
    public static final String shijian="created";
    //获得的秘钥
   @Value("${jwt.secret}")
    private String secret;
        //超时时间
    @Value("${jwt.expiration}")
    private long expiration;

    //根据用户信息生成token
    public String getToke(UserDetails userDetails){
        //准备荷载
        Map<String,Object> cla= new HashMap<>();
        cla.put(yonghuming_KEY,userDetails.getUsername());
        cla.put(shijian,new Date());
        return generratetoken(cla);
    }

    /*根据荷载生成JWT  */
    private String generratetoken(Map<String,Object> cla) {
        return Jwts.builder()
                .setClaims(cla)
                //失效时间是Long类型需要转换
                .setExpiration(genneratrExpiration())
                //签名
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

            /*失效时间*/
    private Date genneratrExpiration() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }
    /*01从Token中获取用户名*/
    public String getUsernameFromToken(String token){
        String username;
        //根据Token拿荷载
        try {
            Claims claims=getclaFromToken(token);
            username=claims.getSubject();
        } catch (Exception e) {
            username=null;
        }
        return username;
    }
    /* 02*从token中获取荷载*/
    private Claims getclaFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {

        }
        return claims;

    }
    /*判断是否有效;*/
    public  boolean dataToken(String token,UserDetails userDetails){
        String username=getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())&&!isTokenExpired(token);
    }


    /*判断token是否失效*/
    private boolean isTokenExpired(String token) {
        Date ExpireDate=ExpirationdataFromToken(token);
        //判断它是否在当前时间前面
        return ExpireDate.before(new Date());
    }

        /*从token中获取过期时间*/
    private Date ExpirationdataFromToken(String token) {
        //获取荷载
        Claims claims=getclaFromToken(token);
        //在从荷载中获取过期时间直接返回
        return claims.getExpiration();


    }
    /*判断token是否被刷新*/
    public boolean tokenIs(String token){
        //判断它是否刷新 失效取反 失效则刷新
        return !isTokenExpired(token);
    }

    /*刷新*/
    public String refreshToken(String token){
        Claims claims=getclaFromToken(token);
        claims.put(shijian,new Date());

        return generratetoken(claims);
    }

}
