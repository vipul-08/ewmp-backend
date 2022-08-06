package com.hashedin.interceptor;

import com.hashedin.config.JwtVerifier;
import com.hashedin.constants.Constants;
import com.hashedin.exceptions.TokenException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class SellerInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtVerifier jwtverifier;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        boolean access = false;
        String token = request.getHeader(Constants.AUTHORIZATION);
        System.out.println(request.getMethod());
        if (request.getMethod().equalsIgnoreCase("OPTIONS"))
        {
            response.setStatus(HttpServletResponse.SC_OK);
            access = true;
        }
        else
        {
            if(!StringUtils.isBlank(token))
            {
                Long id = jwtverifier.checkTokenValidity(token, Constants.SELLER);
                request.setAttribute(Constants.ID, id);
                MDC.clear();
                MDC.put(Constants.CLIENT_ID, String.valueOf(id));
                MDC.put(Constants.TRACE_ID, UUID.randomUUID().toString());
                access=true;
            }
            else
            {
                throw new TokenException(Constants.AUTHORIZATION_HEADER_MISSING);
            }
        }
        return access;

    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception ex) throws Exception
    {
        MDC.remove(Constants.TRACE_ID);
        MDC.remove(Constants.CLIENT_ID);
    }

}
