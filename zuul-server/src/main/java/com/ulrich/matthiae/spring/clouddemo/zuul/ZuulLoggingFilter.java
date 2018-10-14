package com.ulrich.matthiae.spring.clouddemo.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Log
@Component
public class ZuulLoggingFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        log.info("Request for --> " + request.getRequestURI());

        return null;
    }
}
