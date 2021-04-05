package admin.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import tiensang.entity.Category;
import tiensang.entity.Product;


@Transactional
@Controller
@RequestMapping("/admin/product")
public class proController {
	@Autowired
	SessionFactory factory;
	@Autowired
	ServletContext context;
	@RequestMapping("list")
	public String category(HttpSession session, ModelMap model)
	{
		Session sessionn = factory.getCurrentSession();
		String hql = "FROM Product";
		Query query = (Query) sessionn.createQuery(hql);
		List<Product> listPro = query.list();
		model.addAttribute("listPro",listPro);
		return "admin/listProduct";
	}
	@RequestMapping(value="add", method=RequestMethod.GET)
	public String addProduct(ModelMap model)
	{
		Product pro =new Product();
		model.addAttribute("pro",pro);
		model.addAttribute("listCategory", listCate());
		return "admin/product_formAdd";
	}
	@RequestMapping(value="add", method=RequestMethod.POST)
	public String addProduct(ModelMap model,@ModelAttribute("pro") Product pro,BindingResult errors, @RequestParam("photo") MultipartFile photo ) 
	{
		if(pro.getName().trim().length()==0)
		{
			errors.rejectValue("name","pro","Vui lòng nhập Tên Loại");
		}
		if(checkSo(String.valueOf(pro.getPrice()).trim())==false)
		{
			errors.rejectValue("price","pro","Vui lòng nhập lại giá");
		}
		if(checkSo(String.valueOf(pro.getQuantity()))==false)
		{
			errors.rejectValue("quantity","pro","Vui lòng nhập lại số lượng");
		}
		if(errors.hasErrors()) 
		{
			model.addAttribute("message", "");
		}
		if(photo.isEmpty()==true)
		{
			model.addAttribute("uploadPhoto","Vui lòng Upload file ảnh");
		}
		else
		{
			try
			{
				String photoPath=context.getRealPath("images/home/"+photo.getOriginalFilename());
								photo.transferTo(new File(photoPath));	
								System.out.println(photoPath);
								model.addAttribute("uploadPhoto","Upload file thành công!");
								
			}
			catch(Exception e)
			{
				model.addAttribute("uploadPhoto","Lỗi upload file!");
			}
			Session session = factory.openSession();
			Transaction t= session.beginTransaction();
			String hql = "FROM Product";
			Query query = session.createQuery(hql);
			List<Product> listPro = query.list();
			 for (Product i:listPro)
			 {
				 if(pro.getName().equals(i.getName()))
				 {
					 model.addAttribute("message","Tên sản phẩm đã được sử dụng!!!");
					 return "admin/product_formAdd";	
				 }
			 }
			try
			{
				pro.setSold(0);
					pro.setPhoto(photo.getOriginalFilename());
					session.save(pro);
					t.commit();
					model.addAttribute("message", "Thêm Thành Công");
					pro =new Product();
					model.addAttribute("pro",pro);
					model.addAttribute("listCategory", listCate());
					return "admin/product_formAdd";	
			}
			catch(Exception ex) 
			 {
					t.rollback();
					model.addAttribute("message", "Thêm thất bại!!"+ ex.getMessage());
					model.addAttribute("listCategory", listCate());
					return "admin/product_formAdd";	
			}
			finally 
			{
					session.close();
			}
		}
		model.addAttribute("listCategory", listCate());
		return "admin/product_formAdd";
	}
	public boolean checkSo(String str) 
	{
		for(int i = 0; i < str.length(); i++) 
		{
			if((str.charAt(i) <= 46 && str.charAt(i)!=46)) 
			{
				return false;
			}
			else if(str.charAt(i) >= 57)
			{
				return false;
			}
		}
		return true;
	}
	public List<Category> listCate() {

		Session sessionn = factory.getCurrentSession();
		String hql = "FROM Category";
		Query query = sessionn.createQuery(hql);
		List<Category> listCates = query.list();
		return listCates;
	}
	
	@RequestMapping(value = "edit/{proId}", method = RequestMethod.GET)
	public String editPro(ModelMap model, @PathVariable("proId") int proId) 
	{
		Product product = new Product();
		product.setId(proId);
		model.addAttribute("product", product);
		model.addAttribute("listCategory", listCate());
		return "admin/product_formEdit";
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String editPro(ModelMap model, @ModelAttribute("product") Product pro,@RequestParam("img") MultipartFile img)
	{
		if(img.isEmpty()==true)
		{
			model.addAttribute("uploadPhoto","Vui lòng Upload file ảnh");
		}
		else
		{
			try
			{
				String photoPath=context.getRealPath("/images/products/small/"+img.getOriginalFilename());
								img.transferTo(new File(photoPath));	
								model.addAttribute("uploadPhoto","Upload file thành công!");
			}
			catch(Exception e)
			{
				model.addAttribute("uploadPhoto","Lỗi upload file!");
			}
			 Session sessionn = factory.openSession();
			 Transaction t = sessionn.beginTransaction();
			try
			{
					pro.setPhoto(img.getOriginalFilename());
					sessionn.update(pro);
					t.commit();
					model.addAttribute("message", "Update Thành Công");
			}
			catch(Exception ex) 
			 {
					t.rollback();
					model.addAttribute("message", "Update thất bại!!"+ ex.getMessage());	
			}
			finally 
			{
					sessionn.close();
			}
		}
		model.addAttribute("listCategory", listCate());	
			return "admin/product_formEdit";
	}
	public List<Product> sanpham(List<Product> p)
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM Product";
		Query query = session.createQuery(hql);
		p = query.list();
		return p;
	}
	// remove
		@RequestMapping("remove/{proId}")
		public String deleteProduct(ModelMap model,@PathVariable("proId") int proId)
		{
			Product p = new Product();
			Session session = factory.openSession();
			Transaction t = session.beginTransaction();
			try
			{
				p.setId(proId);
				session.delete(p);
				t.commit();
				model.addAttribute("message", "Xóa thành công!!");	
			}
			catch(Exception e)
			{
				t.rollback();
				model.addAttribute("message", "Xóa thất bại!!");	
			}
			finally {
				session.close();
			}
			Session sessionn = factory.getCurrentSession();
			String hql = "FROM Product";
			Query query = (Query) sessionn.createQuery(hql);
			List<Product> listPro = query.list();
			model.addAttribute("listPro",listPro);
			return "admin/listProduct";
		}
	
}
