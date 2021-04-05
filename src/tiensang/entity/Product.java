package tiensang.entity;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Products")
public class Product {
	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Integer id;

	@Column(name = "Name")
	private String name;

	@Column(name = "Price")
	private Float price;
	@Column(name = "Photo")
	private String photo;
	
	@Column(name = "Quantity")
	private Integer quantity;
	
	@Column(name = "Sold")
	private Integer sold;

	@ManyToOne
	@JoinColumn(name = "CateId")
	private Category category;
	
	@OneToMany(mappedBy="product",fetch=FetchType.EAGER)
	private Collection<OrderDetail> orderDetails;
	
	public Collection<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Collection<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getSold() {
		return sold;
	}

	public void setSold(Integer sold) {
		this.sold = sold;
	}

}