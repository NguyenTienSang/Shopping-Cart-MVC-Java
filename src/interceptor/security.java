package interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class security extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse reponse, 
			Object Handle) throws IOException
	{
		HttpSession session = request.getSession();
		if(session.getAttribute("user")==null)
		{
			System.out.println("Vui lòng dang nhap");
			reponse.sendRedirect(request.getContextPath()+"/login.htm");
			return false;
		}
		return true;
	}
}
