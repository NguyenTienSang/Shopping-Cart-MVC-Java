package tiensang.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Orders")
public class Order {
	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "Created")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date created;
	
	@ManyToOne
	@JoinColumn(name = "StatusId")
	private Status status;
	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;
	
	@OneToMany(mappedBy="order",fetch=FetchType.EAGER)
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
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}