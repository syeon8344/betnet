package web.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import web.model.dto.MemberDto;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    /*
    *
    세션에 저장된 사용자 정보를 쉽게 가져오기 위한 @Login 어노테이션과 HandlerMethodArgumentResolver 인터페이스를 구현하는 LoginUserArgumentResolver를 구현해줍니다.
    LoginUserArgumentResolver 클래스는 컨트롤러 메서드에서 특정 조건에 맞는 파라미터가 있을 경우 원하는 값을 바인딩해주는 역할을 합니다.
    HandlerMethodArgumentResolver 인터페이스를 구현한 LoginUserArgumentResolver 클래스의 메소드들은 Spring MVC 프레임워크 내부에서 사용됩니다.
    * */
    private final HttpSession httpSession;

    //파라미터에 @Login 어노테이션이 붙어 있고, 파라미터 클래스 타입이 MemberDto.class인 경우 true를 반환한다.
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = MemberDto.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }


    //파라미터에 전달할 객체를 생성한다.
    //여기선 세션에서 객체를 가져온다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // 이미 세션이 있다면 그 세션을 돌려주고, 세션이 없으면 null을 돌려준다.
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }
        return session.getAttribute("user");
    }
}