package com.iox.users.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table
public class User {

	@Id
	private Long id;
	@Column(unique = true)
	private String email;
	private String name;
	private String userName;
	private String phone;

	public User() {

	}

	public User(String email, String name, String userName, String phone) {
		super();
		this.email = email;
		this.name = name;
		this.userName = userName;
		this.phone = phone;
	}

	public User(Long id, String email, String name, String userName, String phone) {
		this(email, name, userName, phone);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", userName=" + userName + ", phone=" + phone
				+ "]";
	}

}
