package admin.controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Transactional
@Controller
@RequestMapping("/admin")
public class orderController {
	@Autowired
	SessionFactory factory;
	@RequestMapping(value="order/list",method=RequestMethod.GET)
	public String orderlist(ModelMap model)
	{
		List<Object[]> listOrDetail = null;
		listOrDetail=listOrder();
		model.addAttribute("listOrDetail",getListNav(0,10));
		model.addAttribute("totalItem",listOrDetail.size()/10);
		return "admin/orderList";
	}
	@RequestMapping("order/list/{page}")
	public String page(ModelMap model, @RequestParam("page") int page)
	{
		List<Object[]> listOrDetail = null;
		listOrDetail=listOrder();
		model.addAttribute("listOrDetail",getListNav((page-1)*10,10));
		model.addAttribute("totalItem",listOrDetail.size()/10);
		return "admin/orderList";
	}
	public List<Object[]> listOrder()
	{
		String hql = "SELECT b.order.id,b.order.user.name,b.order.user.email,b.order.created,SUM(b.unitPrice),b.order.status.name"
				+ " FROM OrderDetail as b"
				+ " WHERE b.order.status.id=1"
				+" GROUP BY b.order.id,b.order.user.name,b.order.user.email,b.order.created,b.order.status.name"
				+ " ORDER BY b.order.created ";
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<Object[]> listOrDetail =  query.list();
		return listOrDetail;
	}
	public List<Object[]> getListNav(int i, int j) 
	{
		String hql = "SELECT b.order.id,b.order.user.name,b.order.user.email,b.order.created,SUM(b.unitPrice),b.order.status.name"
				+ " FROM OrderDetail as b"
				+" GROUP BY b.order.id,b.order.user.name,b.order.user.email,b.order.created,b.order.status.name"
				+ " ORDER BY b.order.created";
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(i);
		query.setMaxResults(j);
		List<Object[]> listOr = query.list();
		return listOr;
	}
	@RequestMapping(value="order/firm/{orderId}", method=RequestMethod.GET)
	public String firmOrder(ModelMap model,@PathVariable("orderId") int orderId)
	{
		String hql = "UPDATE Order as b" + " SET b.status = 2" + "WHERE b.id= :id";
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("id", orderId);
		query.executeUpdate();
		List<Object[]> listOrDetail = null;
		listOrDetail=listOrder();
		model.addAttribute("listOrDetail",getListNav(0,10));
		model.addAttribute("totalItem",listOrDetail.size()/10);
		model.addAttribute("message","Đã Xác Nhận Giao Hàng !!!");
		return "admin/orderList";
	}
	@RequestMapping(value="order/refuse/{orderId}", method=RequestMethod.GET)
	public String refuseOrder(ModelMap model,@PathVariable("orderId") int orderId)
	{
		String hql = "UPDATE Order as b" + " SET b.status = 3" + "WHERE b.id= :id";
		Session session = factory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("id", orderId);
		query.executeUpdate();
		List<Object[]> listOrDetail = null;
		listOrDetail=listOrder();
		model.addAttribute("listOrDetail",getListNav(0,10));
		model.addAttribute("totalItem",listOrDetail.size()/10);
		model.addAttribute("message","Đã từ chối giao hàng !!!");
		return "admin/orderList";
	}
}
