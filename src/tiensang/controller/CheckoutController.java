package tiensang.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tiensang.entity.Cart;
import tiensang.entity.Category;
import tiensang.entity.Mailer;
import tiensang.entity.Order;
import tiensang.entity.OrderDetail;
import tiensang.entity.Status;
import tiensang.entity.User;

@Transactional
@Controller
@RequestMapping("/user")
public class CheckoutController {
	@Autowired
	SessionFactory factory;
	@Autowired
	Mailer mailer;
	@ModelAttribute("listCategory")
	public List<Category> getListCate() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Category";
		Query query = session.createQuery(hql);
		List<Category> list = query.list();
		return list;
	}
	@RequestMapping("/checkoutCart")
	public String Checkout(ModelMap model, HttpSession session) {
		float price=0;

		User user = (User) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("user", new User());
			return "login";
		}

		HashMap<Integer, Cart> cartItems = (HashMap<Integer, Cart>) session.getAttribute("myCartItems");
		if (cartItems.isEmpty())//Nếu giỏ hàng đang rỗng
		{
			cartItems = new HashMap<Integer, Cart>();
			model.addAttribute("message", "Bạn chưa có món hàng nào trong giỏ hàng!");
			return "cart";
		}

		Status status = new Status();
		status.setId(1);
		Order order = new Order();
		order.setCreated(new Date());
		order.setStatus(status);
		order.setUser(user);
		boolean a = createOrder(order,model);
		if (a) {

			for (Map.Entry<Integer, Cart> entry : cartItems.entrySet()) {
				OrderDetail detail = new OrderDetail();
				detail.setOrder(order);
				detail.setProduct(entry.getValue().getProduct());
				detail.setUnitPrice(entry.getValue().getProduct().getPrice() * entry.getValue().getQuantity());
				detail.setQuantity(entry.getValue().getQuantity());
				price=price+detail.getUnitPrice();

				if (createOrDetail(detail,model)) {
					
					cartItems = new HashMap<Integer, Cart>();
					session.setAttribute("myCartItems", cartItems);
					session.setAttribute("myCartTotal", 0);
					session.setAttribute("myCartNum", 0);
					
				} else {
					model.addAttribute("message", "Đặt hàng thất bại2!");
					return "cart";
				}
			}

		} else {
			model.addAttribute("message", "Đặt hàng thất bại!1");
			return "cart";

		}
		listOrder(session, model);
		
		try {
				mailer.send("tiensang07@gmail.com", user.getEmail(), "Đặt hàng thành công",		
					"Cảm ơn bạn đã đặt hàng tại Website.Tổng số tiền bạn phải trả là: "+price+"VND .Món hàng của bạn đang được chuẩn bị. Chúng tôi sẽ liên hệ với bạn sớm nhất!. Chân thành cảm ơn quý khách.");
			//model.addAttribute("mgsMail", "Gửi mail thành công!");
		} catch (Exception e) {
			model.addAttribute("message", "Gửi mail thất bại!");
			// TODO: handle exception
		}
		model.addAttribute("message", " Đã đặt hàng thành công.");
		return "cart";
	}
	public boolean createOrDetail(OrderDetail detail, ModelMap model) {
		// TODO Auto-generated method stub
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		boolean a = true;
		try {

			session.save(detail);
			t.commit();
			// model.addAttribute("message", "Thêm mới thành công!");

		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			a = false;
			// model.addAttribute("message", "Thêm ODD mới thất bại!!"+e.getMessage());
		} finally {
			session.close();
		}

		return a;
	}
	public boolean createOrder(Order order, ModelMap model) {

		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		boolean a = true;
		try {

			session.save(order);
			t.commit();
			 model.addAttribute("message", "Thêm mới thành công!");

		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			a = false;
			 model.addAttribute("message", "Thêm đơn hàng mới thất bại!" + e.getMessage());
		} finally {
			session.close();
		}

		return a;
	}
	public void listOrder(HttpSession session,ModelMap model)
	{
		listOrOf(model, session);

		Session sessionn = factory.getCurrentSession();
		String hql = "SELECT d.order.id, " + " SUM(d.unitPrice)," + " SUM(d.quantity)" + " FROM OrderDetail d "
				+ " GROUP BY d.order.id";
		Query query = sessionn.createQuery(hql);
		List<Object[]> listOrDetail = query.list();
		model.addAttribute("listOrDetail", listOrDetail);
	}
	public void listOrOf(ModelMap model, HttpSession session) {
		User user = new User();
		user = (User) session.getAttribute("user");
		Session sessionn = factory.getCurrentSession();
		String hql = "FROM Order o WHERE o.user.id = :id AND o.status.id<>0";
		Query query = sessionn.createQuery(hql);
		query.setParameter("id", user.getId());
		List<Order> listOrOf = query.list();
		model.addAttribute("listOrOf", listOrOf);
	}
	@RequestMapping("/orderList")
	public String success(ModelMap model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			model.addAttribute("user", new User());
			return "login";
		}
		listOrder(session, model);
		return "orderlist";
	}
	@RequestMapping("order/remove/{orderID}")
	public String remove(ModelMap model,HttpServletRequest http,@PathVariable("orderID") int orderID,HttpSession a) 
	{
		Session session= factory.getCurrentSession();
		String hql="FROM Order o WHERE o.id=:orderID";
		Query query=session.createQuery(hql);
		query.setParameter("orderID", orderID);
		Order order= (Order) query.uniqueResult();
		Order tam=new Order();
		session=factory.openSession();
		Transaction t=session.beginTransaction();
		if(order!=null)
		{
			try {
				tam.setId(orderID);
				session.delete(tam);
				t.commit();
				model.addAttribute("message", "Xóa thành công!");
			}catch(Exception e)
			{
				t.rollback();
				model.addAttribute("message", "Xóa thất bại!"+e.getMessage()+tam.getId());
			}finally {
				session.close();
			}
		}
		listOrder(a,model);
		return "orderlist";
	}
}
