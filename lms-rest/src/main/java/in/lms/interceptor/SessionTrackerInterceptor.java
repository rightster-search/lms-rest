package in.lms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class SessionTrackerInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// request.getSession().getServletContext();//Important
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("isloggedIn");
		if (obj == null) {
			if (!request.getRequestURI().endsWith("/login") && !request.getRequestURI().contains("loginfailed")) {

				System.out.println("In the interceptor --- context path -- "
						+ request.getContextPath() + " and the request URI -- "
						+ request.getRequestURI());
				request.getSession().setAttribute("current-path",
						request.getRequestURI());
				response.sendRedirect("login");
				return false;

			} else
				return true;
		} else {
			if (obj instanceof Boolean) {
				boolean isLoggedIn = (Boolean) obj;
				if (isLoggedIn)
					return true;
				else
					return false;
			} else
				return false;
		}
	}

}
