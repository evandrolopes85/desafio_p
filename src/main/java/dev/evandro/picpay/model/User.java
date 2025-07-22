package dev.evandro.picpay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "full_name", nullable = false, length=255)
	private String fullName;
	
	@Column(name = "document", nullable = false, length=14, unique = true)
	private String document;
	
	@Column(name = "email", nullable = false, length=255, unique = true)
	private String email;
	
	@Column(name = "password", nullable = false, length = 8)
	private String password;
	
	@Column(name = "type_user", nullable = false)
	private TypeUser typeUser;
	
	@OneToOne(mappedBy = "user")
	@JsonIgnoreProperties("user")
	private Wallet wallet;
	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TypeUser getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(TypeUser typeUser) { 
		this.typeUser = typeUser;
	}

	
	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", fullName=" + fullName + ", document=" + document + ", email=" + email
				+ ", password=" + password + ", typeUser=" + typeUser + ", wallet=" + wallet + "]";
	}
	
	
}
