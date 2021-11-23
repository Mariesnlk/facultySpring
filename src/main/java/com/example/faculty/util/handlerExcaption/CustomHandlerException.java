package com.example.faculty.util.handlerExcaption;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CustomHandlerException implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler instanceof HandlerMethod) {

            HandlerMethod hm = (HandlerMethod) handler;
            ErrorView errorView = hm.getMethodAnnotation(ErrorView.class);
            if (errorView != null) {
                String viewName = errorView.value();
                ModelAndView model = new ModelAndView(viewName);
                model.addObject("requestUri", request.getRequestURI());
                model.addObject("exception", ex);
                HttpStatus status = errorView.status();
                // model.addObject("statusValue", status.value());
                response.setStatus(status.value());
                return model;
            }
        }
        //returning null for default processing

        return null;
    }
}