package com.nishanth.springmongodb.api;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class Users{
	
	@Id
	private String id;
	private String name;
	private String city_of_birth;
	private String profession;

	public final String getId() {
		return id;
	}
	public final void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity_of_birth() {
		return city_of_birth;
	}
	public void setCity_of_birth(String city_of_birth) {
		this.city_of_birth = city_of_birth;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	
}
