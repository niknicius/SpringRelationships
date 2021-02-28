package io.nanuvem.onetomany.combobox.demo.core.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomCorsFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  )
    throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "false");
    response.setHeader(
      "Access-Control-Allow-Methods",
      "POST, GET, OPTIONS, PUT, DELETE, PATCH"
    );
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Max-Age", "");
    response.setHeader(
      "Access-Control-Expose-Headers",
      "Access-Control-Allow-Origin, Access-Control-Request-Method, Accept, Content-Type"
    );
    response.setHeader(
      "Access-Control-Allow-Headers",
      "Origin, Content-Type, Accept, X-Requested-With, Authorization, lang"
    );
    response.setContentType("application/json");

    if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
      response.setStatus(HttpStatus.OK.value());
      return;
    }

    filterChain.doFilter(request, response);
  }
}
