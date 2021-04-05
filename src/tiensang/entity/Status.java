package tiensang.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Status")
public class Status {

	@Id
	@Column(name = "Id")
	private Integer id;
	@Column(name = "Name")
	private String name;
	@OneToMany(mappedBy="status",fetch=FetchType.EAGER)
	private Collection<Order> orders;
	public Collection<Order> getOrders() {
		return orders;
	}
	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
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
	
}