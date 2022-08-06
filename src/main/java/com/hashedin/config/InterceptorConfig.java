package com.hashedin.config;

import com.hashedin.interceptor.BuyerInterceptor;
import com.hashedin.interceptor.SellerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private BuyerInterceptor buyerInterceptor;

    @Autowired
    private SellerInterceptor sellerInterceptor;

    private static final List<String> LIST_OF_API_NOT_PASSING_THROUGH_INTERCEPTOR = Arrays.asList("/login", "/Authorize", "/forgotPassword", "/forgotPasswordChecker", "/forgotPasswordUpdate", "/changepassword", "/signup","/api/file/upload", "/seller-verification/verified/{userId}", "/seller-verification/not-verified/{userId}", "/buyer-verification/verified/{serialNo}/{cartId}","/buyer-verification/not-verified/{serialNo}/{cartId}", "/buyer/filter", "/buyer/getProduct/{productId}","/buyer/searchFilter/{keyword}");

    /**
     * Interceptor Paths.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(buyerInterceptor)
                .addPathPatterns("/buyer/**")
                .excludePathPatterns(LIST_OF_API_NOT_PASSING_THROUGH_INTERCEPTOR);
        registry.addInterceptor(sellerInterceptor)
                .addPathPatterns("/seller/**")
                .excludePathPatterns(LIST_OF_API_NOT_PASSING_THROUGH_INTERCEPTOR);
    }
}
