package com.zslin.bus.poi.model;

import com.zslin.bus.poi.util.ExcelResources;

public class User {
	
	private int id;
	private String username;
	private String nickname;
	private int age;
	private String password;
	//不导出
	private double money;
	
	
	
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	@ExcelResources(title="用户密码",order=5)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@ExcelResources(title="用户标识",order=1)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ExcelResources(title="用户名",order=2)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@ExcelResources(title="用户昵称",order=3)
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@ExcelResources(title="用户年龄",order=4)
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public User(int id, String username, String nickname, int age) {
		super();
		this.id = id;
		this.username = username;
		this.nickname = nickname;
		this.age = age;
	}
	
	
	public User(int id, String username, String nickname, int age,
			String password, double money) {
		super();
		this.id = id;
		this.username = username;
		this.nickname = nickname;
		this.age = age;
		this.password = password;
		this.money = money;
	}
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", nickname="
				+ nickname + ", age=" + age + "]";
	}
	
	
}
