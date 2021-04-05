package admin.controller;

import java.util.List;

import javax.servlet.ServletContext;

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
import org.springframework.web.bind.annotation.RequestMethod;

import tiensang.entity.Category;

@Controller
@Transactional
@RequestMapping("/admin/category")
public class categoryController {
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String viewProductList(ModelMap model) {
		// model.addAttribute("listProduct", getListCate(0, 10));
		return "admin/listCate";
	}
	@ModelAttribute("listCategory")
	public List<Category> getListCate() {
		Session session = factory.getCurrentSession();
		String hql = "FROM Category";
		Query query = session.createQuery(hql);
		List<Category> list = query.list();
		return list;
	}
	//Thêm loại hàng
		@RequestMapping(value = "add", method = RequestMethod.GET)
		public String addProduct(ModelMap model) {
			model.addAttribute("cate", new Category());
			return "admin/category_formAdd";
		}

		@RequestMapping(value = "add", method = RequestMethod.POST)
		public String addProduct(ModelMap model, @ModelAttribute("cate") Category cate) {

			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {

				session.save(cate);

				t.commit();
				model.addAttribute("message", "Tạo thành công.");
			} catch (Exception e) {
				// TODO: handle exception
				model.addAttribute("message", "Tạo thất bại!");
				t.rollback();
			} finally {
				session.close();
			}

			model.addAttribute("cate", new Category());
			return "admin/category_formAdd";
		}
		
		
		@RequestMapping("remove/{cateId}")
		public String removePro(ModelMap model, @PathVariable("cateId") String cateId) {
			Category pp = new Category();
			Category p = findCateById(cateId);
			if (p != null) {

				Session session = factory.openSession();
				Transaction t = session.beginTransaction();
				try {
					pp.setId(cateId);
					session.delete(pp);
					model.addAttribute("message", "Đã xóa thành công!");
					t.commit();

				} catch (Exception e) {
					// TODO: handle exception
					model.addAttribute("message", "Xóa thất bại!");
					t.rollback();

				} finally {
					session.close();
				}

			} else {
				model.addAttribute("message", "Loại hàng này không tồn tại! :(");
				return "admin/productList";
			}
			listCate(model);
			return "admin/listCate";
		}
		
		public Category findCateById(String cateId) {
			Session session = factory.getCurrentSession();
			String hql = "FROM Category p WHERE p.id LIKE :id ";
			Query query = session.createQuery(hql);
			query.setParameter("id", cateId);
			Category obj = (Category) query.uniqueResult();
			return obj;
		}
		public void listCate(ModelMap model) {
			Session session = factory.getCurrentSession();
			String hql = "FROM Category";
			Query query = session.createQuery(hql);
			List<Category> list = query.list();
			model.addAttribute("listCategory", list);
		}
		
		@RequestMapping(value = "edit/{cateId}", method = RequestMethod.GET)
		public String editPro(ModelMap model, @PathVariable("cateId") String cateId) {

			model.addAttribute("cateId", cateId);
			model.addAttribute("cate", new Category());
			return "admin/category_formEdit";
		}

		@RequestMapping(value = "edit", method = RequestMethod.POST)
		public String editPro(ModelMap model, @ModelAttribute("cate") Category cate) {

			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try {

				session.update(cate);
				model.addAttribute("successEditCate", "Chỉnh sửa thành công!");
				t.commit();

			} catch (Exception e) {
				t.rollback();
				model.addAttribute("failEditCate", "Chỉnh sửa thất bại! Vui lòng kiểm tra lại thông tin!");
			} finally {
				session.close();
			}
			listCate(model);
			return "admin/listCate";
		}
}
