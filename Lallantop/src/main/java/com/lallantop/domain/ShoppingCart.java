package com.lallantop.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private BigDecimal GrandToptal;
	
	
	@OneToMany(mappedBy="shoppingCart" , cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CartItem> cartitemList;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getGrandToptal() {
		return GrandToptal;
	}

	public void setGrandToptal(BigDecimal grandToptal) {
		GrandToptal = grandToptal;
	}

	public List<CartItem> getCartitemList() {
		return cartitemList;
	}

	public void setCartitemList(List<CartItem> cartitemList) {
		this.cartitemList = cartitemList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
