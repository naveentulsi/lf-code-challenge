package com.labforward.api.hello.controller;

import com.labforward.api.core.creation.EntityCreatedResponse;
import com.labforward.api.core.exception.ResourceNotFoundException;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Api("Create, update and fetch greetings")
public class HelloController {

	public static final String GREETING_NOT_FOUND = "Greeting Not Found";

	private HelloWorldService helloWorldService;

	@Autowired
	public void setHelloWorldService(HelloWorldService helloWorldService) {
		this.helloWorldService = helloWorldService;
	}

	@ApiOperation(value = "Get default greeting", response = Greeting.class)
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "", response = Greeting.class)
	})
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public Greeting helloWorld() {
		return getHello(HelloWorldService.DEFAULT_ID);
	}

	@ApiOperation(value = "Get greeting entity by Id", response = Greeting.class)
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "", response = Greeting.class),
			@ApiResponse(code = 400, message = GREETING_NOT_FOUND)
	})
	@RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Greeting getHello(@PathVariable String id) {
		return helloWorldService.getGreeting(id)
		                        .orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
	}


	@ApiOperation(value = "Create greeting entity", response = Greeting.class)
	@ApiResponses( value = {
			@ApiResponse(code = 201, message = "Created", response = Greeting.class)
	})
	@RequestMapping(value = "/hello", method = RequestMethod.POST)
	public EntityCreatedResponse createGreeting(@RequestBody Greeting request) throws URISyntaxException {
		Greeting greeting = helloWorldService.createGreeting(request);
		return new EntityCreatedResponse<>(greeting, new URI("/hello/".concat(greeting.getId())));
	}

	@ApiOperation(value = "Patch an existing greeting by Id", response = Greeting.class)
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "", response = Greeting.class),
			@ApiResponse(code = 400, message = GREETING_NOT_FOUND)
	})
	@RequestMapping(value = "/hello/{id}", method = RequestMethod.PATCH)
	@ResponseBody
	public Greeting updateGreeting(@PathVariable String id, @RequestBody Greeting request) {
		return helloWorldService.updateGreeting(id, request)
				.orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
	}
}
