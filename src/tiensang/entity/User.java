package tiensang.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Users")
public class User {
	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Integer id;

	@Column(name = "Name")
	private String name;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Username")
	private String username;

	@Column(name = "Password")
	private String password;


	@Column(name = "Phone")
	private String phone;

	@Column(name = "Address")
	private String address;
	
	@Column(name = "UserRole")
	private String userRole;
	
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}