package com.lance.view.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityFilter implements Filter {
    private FilterConfig _filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;
    }

    public void destroy() {
        _filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                                     ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        System.out.println(req.getRequestURI());
//        if ("/lance/".equals(req.getRequestURI())) {
//            //            req.getRequestDispatcher("/faces/welcome.jspx").forward(request, response);
//            ((HttpServletResponse) response).sendRedirect("/lance/");
//        }
        chain.doFilter(request, response);
    }
}
