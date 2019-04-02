package com.zslin.basic.interceptor;

import com.zslin.basic.dao.IAppConfigDao;
import com.zslin.basic.model.AppConfig;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class SystemInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private IAppConfigDao appConfigDao;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //将系统配置文件存入Session中
        AppConfig appConfig = (AppConfig) session.getAttribute("appConfig");
        if(appConfigDao==null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            appConfigDao = (IAppConfigDao) factory.getBean("appConfigDao");
        }
        if(appConfig==null) {
            appConfig = appConfigDao.loadOne();
            session.setAttribute("appConfig", appConfig);
        }

        return super.preHandle(request, response, handler);
    }
}