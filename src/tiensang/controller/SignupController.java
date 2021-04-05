package tiensang.controller;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import javax.servlet.http.HttpServletResponse;



import tiensang.entity.User;

@Transactional
@Controller
public class SignupController {
	@Autowired
	SessionFactory factory;
	@RequestMapping(value = "signup", method = RequestMethod.GET)
	public String insert(ModelMap model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String insert(ModelMap model, @ModelAttribute("user") User user, BindingResult errors,@RequestParam("password") String password) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		if (user.getName().trim().length() == 0) {
			errors.rejectValue("name", "user", "Vui lòng nhập họ tên");
		}
		if (user.getEmail().trim().length() == 0) {
			errors.rejectValue("email", "user", "Vui lòng nhập email");
		}
		if (user.getUsername().trim().length() == 0) {
			errors.rejectValue("username", "user", "Vui lòng nhập username");
		}
		if (user.getPassword().trim().length() == 0) {
			errors.rejectValue("password", "user", "Vui lòng nhập mật khẩu");
		}
		if (user.getPhone().trim().length() == 0) {
			errors.rejectValue("phone", "user", "Vui lòng nhập số điện thoại");
		}
		if (user.getAddress().trim().length() == 0) {
			errors.rejectValue("address", "user", "Vui lòng nhập địa chỉ");
		}
			if(!user.getPassword().equals(password))
			{
				model.addAttribute("message", "Mật khẩu không trùng khớp!");
				return "signup";
			}
			try
			{
				user.setUserRole("client");
				session.save(user);
				model.addAttribute("message","Đăng Ký Thành Công");
				t.commit();
			
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Đăng Ký Thất Bại");
		} finally {
			session.close();
		}
		return "signup";
	}
}
