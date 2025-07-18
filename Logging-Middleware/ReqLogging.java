
package com.example.backend.middleware;

import jakarta.servlet.*;
        import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReqLogging implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        System.out.println("🔹 [LOG] " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());

        chain.doFilter(request, response);
    }
}
