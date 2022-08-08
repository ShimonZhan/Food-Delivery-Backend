package uk.ac.soton.food_delivery.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uk.ac.soton.food_delivery.entity.DO.LoginUser;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.utils.JwtUtil;
import uk.ac.soton.food_delivery.utils.R;
import uk.ac.soton.food_delivery.utils.RedisCache;
import uk.ac.soton.food_delivery.utils.WebUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Shimon Zhan
 * @Date: 2022-03-12 13:22:02
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisCache redisCache;

    @Value("${jwt.tokenHeader}")
    private String header;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get token
        String token = request.getHeader(header);
        if (!StringUtils.hasText(token)) {
            // pass
            filterChain.doFilter(request, response);
            return;
        }
//        log.error("***"+token+"***");
        token = token.substring(tokenHead.length() + 1);
//        log.error("***"+token+"***");

        // parse token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (ExpiredJwtException expiredJwtException) {
            WebUtils.writeJSON(response, R.resultCode(ResultCode.TOKEN_VALIDATE_EXPIRED));
            return;
        } catch (Exception e) {
//            filterChain.doFilter(request, response);
            WebUtils.writeJSON(response, R.resultCode(ResultCode.TOKEN_VALIDATE_FAILED));
            return;
        }
        // get user info from redis
        String redisKey = "login:" + userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (loginUser == null) {
            WebUtils.writeJSON(response, R.resultCode(ResultCode.USER_NOT_LOGIN));
            return;
        }
        // save to SecurityContextHolder
        // get authorities to Authentication
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // pass
        filterChain.doFilter(request, response);
    }
}
