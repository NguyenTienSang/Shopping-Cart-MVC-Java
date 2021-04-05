package admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@Controller
public class trangchuAdmin {
	@RequestMapping("trangchuAdmin")
	public String manage(HttpSession session)
	{
		return"admin/index";
	}
	
}