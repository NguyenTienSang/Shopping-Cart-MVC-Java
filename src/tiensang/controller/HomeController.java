package tiensang.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tiensang.entity.Product;
import tiensang.entity.User;
import tiensang.entity.Category;

@Transactional
@Controller

@RequestMapping("/")
public class HomeController {
	User user = new User();
	@Autowired
	SessionFactory factory;
	@RequestMapping("index")
	public String index(ModelMap model) {
		//San pham
		Session sessionsanpham = factory.getCurrentSession();
		String hqlsanpham = "FROM Product";
		Query querysanpham = sessionsanpham.createQuery(hqlsanpham);
		List<Product> listsanpham = querysanpham.list();
		model.addAttribute("listPro", listsanpham);
		return "index";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String viewlogin(ModelMap model, HttpSession session) {
		
		user = (User) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("user", new User());
			return "login";
		}
		return "redirect:/account.htm";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String viewlogin(ModelMap model, HttpSession session, @ModelAttribute("user") User user) {

		Session sessionn = factory.getCurrentSession();
		String hql = "FROM User";
		Query query = sessionn.createQuery(hql);
		List<User> listAcc = query.list();

		for (User ktra : listAcc) {
			if (user.getUsername().equals(ktra.getUsername()) && user.getPassword().equals(ktra.getPassword())) {
				this.user = ktra;
				if (this.user.getUserRole().equals("admin")) {

					session.setAttribute("user", this.user);
					return "admin/index";
				} else {

					session.setAttribute("user", this.user);
					return "redirect:/index.htm";

				}
			}

		}

		model.addAttribute("errorLogin", "Sai thông tin đăng nhập!");
		return "login";

	}
	


	@RequestMapping("logout")
	public String logout(HttpSession session, HttpServletRequest rq) {
		session = rq.getSession();
		User u = new User();
		u = (User) session.getAttribute("user");
		session.removeAttribute("user");
		return "redirect:/index.htm";

	}
	
	@RequestMapping("account")
	public String account(ModelMap model,HttpSession session) {
		Session sessionn = factory.getCurrentSession();
		String hql = "FROM User";
		Query query = sessionn.createQuery(hql);
		List<User> listAcc = query.list();
		model.addAttribute("list",listAcc);
		return "account";
	}
	
	@RequestMapping("client/products/{id}")
	public String product(@PathVariable("id") String id,ModelMap model)
	{
			Session session = factory.getCurrentSession();
			String hql = "FROM Product p WHERE p.category.id LIKE :id ";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			List<Product> listProCate = query.list();
			model.addAttribute("listPro", listProCate);
			String title=listProCate.get(0).getCategory().getName();
			model.addAttribute("title",title);
			return "index";
	}
	
	@ModelAttribute("listCategory")
	public List<Category> getListCate() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Category";
		Query query = session.createQuery(hql);
		List<Category> list = query.list();
		return list;
	}
	
	
	@RequestMapping("contact")
	public String contact() {
		return "contact";
	}
	
}