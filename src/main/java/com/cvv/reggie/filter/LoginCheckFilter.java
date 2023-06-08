package com.cvv.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.cvv.reggie.common.R;
import com.cvv.reggie.utils.ThreadLocalForCurrentUserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:这个过滤器是用来过滤没有登录的用户不能访问除登录以外的其他页面的，
 * 即只有登录的用户才能随意访问界面，未登录的用户只能访问登录界面，访问其他页面则会像前端发送NOTLOGIN信息
 */

@Slf4j
@WebFilter(filterName ="loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestUrl = request.getRequestURI();

        String[] strings = new String[]{"/backend/**","/front/**","/employee/login","/employee/logout","/common/**","/user/sendMsg","/user/login"};

        boolean check = check(strings, requestUrl);

        if (check){
            filterChain.doFilter(request,response);
            return;
        }

        Long currentEmployeeId = (Long) request.getSession().getAttribute("employee");
        if (currentEmployeeId != null){
            ThreadLocalForCurrentUserId.setCurrentId(currentEmployeeId);
            filterChain.doFilter(request,response);
            return;
        }

        Long currentUserId = (Long) request.getSession().getAttribute("user");
        if (currentUserId != null){
            ThreadLocalForCurrentUserId.setCurrentId(currentUserId);
            filterChain.doFilter(request,response);
            return;
        }


        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    private boolean check(String[] strings,String requestUrl){
        for (String url: strings) {
            boolean match = PATH_MATCHER.match(url, requestUrl);
            if (match){
                return true;
            }
        }
        return false;
    }
}
