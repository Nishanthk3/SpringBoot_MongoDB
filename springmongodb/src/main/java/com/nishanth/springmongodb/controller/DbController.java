package com.nishanth.springmongodb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nishanth.springmongodb.api.Users;

@RestController
@RequestMapping("/")
public class DbController {

	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping("/")
	public String initialPage() {
		return "Welcome To Spring Boot MongoDB";
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public Users getObject(@PathVariable String id, HttpServletRequest httpReq,HttpServletResponse httpResp)
	{
		/* for testing use 54036e88f69266236b64c748 */
		Query query = new Query(Criteria.where("id").is(id));
		Users users = mongoTemplate.findOne(query, Users.class, "users");
		return users;
	}
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(@RequestBody Users users, HttpServletRequest httpReq,HttpServletResponse httpResp)
	{
		mongoTemplate.insert(users, "users");
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable String id, @RequestBody Users users, HttpServletRequest httpReq,HttpServletResponse httpResp)
	{
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		if(users.getName() != null)
			update.set("name", users.getName());
		if(users.getCity_of_birth() != null)
			update.set("city_of_birth", users.getCity_of_birth());
		if(users.getProfession() != null)
			update.set("profession", users.getProfession());
		mongoTemplate.updateFirst(query, update, Users.class, "users");
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public void remove(@RequestBody Users users, HttpServletRequest httpReq,HttpServletResponse httpResp)
	{
		Query query = new Query(Criteria.where("id").is(users.getId()));
		mongoTemplate.remove(query, Users.class, "users");
	}
	
//	@RequestMapping(value = "/removeField", method = RequestMethod.DELETE)
//	public void removeField(@RequestBody Users users, HttpServletRequest httpReq,HttpServletResponse httpResp)
//	{
//		Query query = new Query(Criteria.where("id").is(users.getId()));
//		Update update = new Update();
//		update.unset("class");
//		mongoTemplate.updateFirst(query, update, Users.class, "users");
//	}
}
