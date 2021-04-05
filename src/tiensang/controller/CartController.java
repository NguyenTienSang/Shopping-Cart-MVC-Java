package tiensang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tiensang.entity.Cart;
import tiensang.entity.Product;

@Transactional
@Controller
@RequestMapping("user/cart")
public class CartController {
	@Autowired
	SessionFactory factory;
	@RequestMapping()
	public String cart(ModelMap model, HttpSession session, HttpServletRequest rq) {
		HashMap<Integer, Cart> cartItems = new HashMap<Integer, Cart>();
		cartItems = (HashMap<Integer, Cart>) session.getAttribute("myCartItems");
		if (cartItems == null) {
			model.addAttribute("msgCart", "Giỏ hàng đang rỗng!");
			cartItems = new HashMap<Integer, Cart>();
		}

		session.setAttribute("myCartItems", cartItems);
		session.setAttribute("myCartTotal", totalPrice(cartItems));
		session.setAttribute("myCartNum", cartItems.size());
		return "cart";
	}
	@ModelAttribute("listCategory")
	public List<Category> getListCate() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Category";
		Query query = session.createQuery(hql);
		List<Category> list = query.list();
		return list;
	}
	
	public float totalPrice(HashMap<Integer, Cart> cartItems) {
		float count = 0;
		for (Map.Entry<Integer, Cart> list : cartItems.entrySet()) {
			count += list.getValue().getProduct().getPrice() * list.getValue().getQuantity();
		}
		return count;
	}
	
	@RequestMapping(value = "/add/{productId}", method = RequestMethod.GET)
	public String viewAdd(ModelMap model, HttpSession session, @PathVariable("productId") int productId) {
		HashMap<Integer, Cart> cartItems = new HashMap<Integer, Cart>();
		cartItems = (HashMap<Integer, Cart>) session.getAttribute("myCartItems");
		if (cartItems == null) {
			cartItems = new HashMap<Integer, Cart>();
		}
		Product product = findById(productId);
		if (product != null) {
			if (cartItems.containsKey(productId)) {
				Cart item = cartItems.get(productId);
				item.setProduct(product);
				item.setQuantity(item.getQuantity() + 1);
				cartItems.put(productId, item);
			} else {
				Cart item = new Cart();
				item.setProduct(product);
				item.setQuantity(1);
				cartItems.put(productId, item);
			}
		}

		session.setAttribute("myCartItems", cartItems);
		session.setAttribute("myCartTotal", totalPrice(cartItems));
		session.setAttribute("myCartNum", cartItems.size());
		
		Session sessionn = factory.getCurrentSession();
		String hql = "FROM Product";
		Query query = sessionn.createQuery(hql);
		List<Product> listPro = query.list();
		model.addAttribute("listPro", listPro);
		return "index";
	}
	public Product findById(int productId) {
		Session session = factory.getCurrentSession();
		String hql = "FROM Product p WHERE p.id LIKE :id ";
		Query query = session.createQuery(hql);
		query.setParameter("id", productId);
		Product obj = (Product) query.uniqueResult();
		return obj;
	}
	@RequestMapping(value = "/remove/{productId}", method = RequestMethod.GET)
	public String viewRemove(ModelMap model, HttpSession session, @PathVariable("productId") int productId)
	{
		HashMap<Integer, Cart> cartItems = new HashMap<Integer, Cart>();
		cartItems = (HashMap<Integer, Cart>) session.getAttribute("myCartItems");
		if (cartItems == null) 
		{
			cartItems = new HashMap<Integer, Cart>();
		}
		if (cartItems.containsKey(productId)) 
		{
			cartItems.remove(productId);
			model.addAttribute("msgCart","Xóa thành công");
			CongsoluongPro(productId);
		}
			session.setAttribute("myCartItems", cartItems);
			session.setAttribute("myCartTotal", totalPrice(cartItems));
			session.setAttribute("myCartNum", cartItems.size());
			return "cart";
	}
	
	public void CongsoluongPro(int proId)
	{
		Session session = factory.getCurrentSession();
		String hql = "UPDATE Product as b" + " SET b.quantity = b.quantity+1 " + "WHERE b.id= :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", proId);
		query.executeUpdate();
	}
}
