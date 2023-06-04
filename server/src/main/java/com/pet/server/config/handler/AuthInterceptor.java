package com.pet.server.config.handler;

import com.pet.server.common.utils.DataStatic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpServletRequest req = (HttpServletRequest) request;
        String redirectUrl;
        String uId;

        if(!req.getRequestURI().contains("/api")){
            //Is browser (mvc)
            uId = (String) req.getSession().getAttribute(DataStatic.SESSION.KEY.LOGIN_ID);
            redirectUrl = "/oauth/login";
        }else{
            //Is mobile (api)
            uId = req.getHeader("uid");
            redirectUrl = "/api/error";
        }
        try{
            if(uId != null){
                return true;
            }else{
                response.sendRedirect(redirectUrl);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
