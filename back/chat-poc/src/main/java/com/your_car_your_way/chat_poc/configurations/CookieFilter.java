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


// Ce filtre intercepte les réponses HTTP afin de modifier les en-têtes "Set-Cookie".
// Le but est d'ajouter l'attribut SameSite=Lax aux cookies JSESSIONID
// pour éviter les problèmes de cross-site.

@Component
public class CookieFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        CookieCaptureWrapper responseWrapper = new CookieCaptureWrapper(httpResponse);
        chain.doFilter(request, responseWrapper);

        for (String header : responseWrapper.getHeaders("Set-Cookie")) {
            if (header.startsWith("JSESSIONID")) {
                String updated = header + "; SameSite=Lax";
                httpResponse.setHeader("Set-Cookie", updated);
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
