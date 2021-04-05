package admin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tiensang.entity.User;


@Transactional
@Controller
@RequestMapping("/admin/user")
public class userController {
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;
	@RequestMapping(method = RequestMethod.GET)
	public String userList(ModelMap model) {
		model.addAttribute("listUser", getListUser());
		return "admin/listUser";
	}
	public List<User> getListUser() {
		Session session = factory.getCurrentSession();
		String hql = "FROM User";
		Query query = session.createQuery(hql);
		List<User> list = query.list();
		return list;
	}
	@ModelAttribute("totalUser")
	public int totalItem() {
		// TODO Auto-generated method stub
		Session sessionn = factory.getCurrentSession();
		String hql = "SELECT COUNT(p) FROM User p";
		Query query = sessionn.createQuery(hql);
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

	// hàm xóa User
	@RequestMapping("remove/{userId}")
	public String removeUser(ModelMap model, @PathVariable("userId") int userId) {
		User pp = new User();
		User p = findUserById(userId);
		if (p != null) {

			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				pp.setId(userId);
				session.delete(pp);
				model.addAttribute("successRemoveUser", "Đã xóa thành công!");
				t.commit();

			} catch (Exception e) {
				// TODO: handle exception
				model.addAttribute("failRemoveeUser", "Xóa thất bại!");
				t.rollback();

			} finally {
				session.close();
			}

		} else {
			model.addAttribute("failRemoveUser", "Người dùng này không tồn tại! :(");
			model.addAttribute("listUser", getListUser());
			return "admin/listUser";
		}
		model.addAttribute("listUser", getListUser());
		return "admin/listUser";
	}
	public User findUserById(int userId) {
		Session session = factory.getCurrentSession();
		String hql = "FROM User p WHERE p.id LIKE :id ";
		Query query = session.createQuery(hql);
		query.setParameter("id", userId);
		User obj = (User) query.uniqueResult();
		return obj;
	}
	//hàm thêm user
		@RequestMapping(value = "add", method = RequestMethod.GET)
		public String addUser(ModelMap model) {
			model.addAttribute("userr", new User());
			return "admin/user_formAdd";
		}

		@RequestMapping(value = "add", method = RequestMethod.POST)
		public String addUser(ModelMap model,@ModelAttribute("userr") User userr) {
		
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {
				session.save(userr);
				model.addAttribute("message", "Tạo thành công.");
				t.commit();

			} catch (Exception e) {
				// TODO: handle exception
				model.addAttribute("message", "Tạo thất bại. Vui lòng kiểm tra lại thông tin!");
				t.rollback();
			} finally {
				session.close();
			}
			model.addAttribute("userr", new User());
			return "admin/user_formAdd";
		}
}
