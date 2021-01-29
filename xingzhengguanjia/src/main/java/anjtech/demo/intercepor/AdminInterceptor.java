package anjtech.demo.intercepor;

import anjtech.demo.po.Role;
import anjtech.demo.po.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/signin");
            return false;
        }

        if (request.getSession().getAttribute("admin") != null) {
            return true;
        }

        response.sendRedirect("/task");
        return false;
    }
}
