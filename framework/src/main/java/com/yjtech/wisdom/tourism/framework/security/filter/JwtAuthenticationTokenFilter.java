package com.yjtech.wisdom.tourism.framework.security.filter;

import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 *
 * @author liuhong
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String servletPath = request.getServletPath();
        if (servletPath.contains("/screen")||(servletPath.contains("/system/dict/data"))) {
            return true;
        }
        return false;
    }
}
