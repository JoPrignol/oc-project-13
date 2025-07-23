package com.your_car_your_way.chat_poc.configurations;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

@Component
public class CookieFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        CookieCaptureWrapper responseWrapper = new CookieCaptureWrapper(httpResponse);
        chain.doFilter(request, responseWrapper);

        // Récupère les Set-Cookie et ajoute SameSite
        for (String header : responseWrapper.getHeaders("Set-Cookie")) {
            if (header.startsWith("JSESSIONID")) {
                String updated = header + "; SameSite=Lax";
                httpResponse.setHeader("Set-Cookie", updated);
                System.out.println("Cookie modifié envoyé : " + updated);
            } else {
                httpResponse.addHeader("Set-Cookie", header);
            }
        }
    }

    private static class CookieCaptureWrapper extends HttpServletResponseWrapper {
        public CookieCaptureWrapper(HttpServletResponse response) {
            super(response);
        }
    }
}
