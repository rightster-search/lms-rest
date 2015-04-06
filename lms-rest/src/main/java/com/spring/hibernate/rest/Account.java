package com.spring.hibernate.rest;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account {

	@OneToMany(mappedBy = "account",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Bookmark> bookmarks = new HashSet<Bookmark>();

	@Id
	@GeneratedValue
	private Long id;

	public Set<Bookmark> getBookmarks() {
		return bookmarks;
	}
	

	public void setBookmarks(Set<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	//@JsonIgnore
	public String password;
	public String username;

	public Account(String name, String password) {
		this.username = name;
		this.password = password;
	}

	Account() { // jpa only
	}

	@Override
	public String toString() {
		return "Account [bookmarks=" + bookmarks + ", id=" + id + ", password="
				+ password + ", username=" + username + "]";
	}

}
