package com.wanhuhealth.rules.utils;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebConfig implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String origin = (String) servletRequest.getRemoteHost()+":"+servletRequest.getRemotePort();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "86400");
        if(request.getHeader("Access-Control-Request-Method")!=null){
        	  response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
        } 
        if(request.getHeader("Access-Control-Request-Headers")!=null){
        	response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        } 
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}