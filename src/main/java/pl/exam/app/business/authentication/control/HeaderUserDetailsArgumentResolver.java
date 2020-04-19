package pl.exam.app.business.authentication.control;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class HeaderUserDetailsArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == UserDetails.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {

        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        String token = request.getHeader("Authorization");
        DecodedJWT decoded = JWT.decode(token);
        return new UserDetails(decoded.getSubject(), decoded.getClaim("Authorities").asList(String.class));
    }
}
