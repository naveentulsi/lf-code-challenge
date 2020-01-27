package com.labforward.api.hello;

import com.labforward.api.core.exception.EntityValidationException;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldServiceTest {

	@Autowired
	private HelloWorldService helloService;

	public HelloWorldServiceTest() {
	}

	@Test
	public void getDefaultGreetingIsOK() {
		Optional<Greeting> greeting = helloService.getDefaultGreeting();
		Assert.assertTrue(greeting.isPresent());
		Assert.assertEquals(HelloWorldService.DEFAULT_ID, greeting.get().getId());
		Assert.assertEquals(HelloWorldService.DEFAULT_MESSAGE, greeting.get().getMessage());
	}

	@Test(expected = EntityValidationException.class)
	public void createGreetingWithEmptyMessageThrowsException() {
		final String EMPTY_MESSAGE = "";
		helloService.createGreeting(new Greeting(EMPTY_MESSAGE));
	}

	@Test(expected = EntityValidationException.class)
	public void createGreetingWithNullMessageThrowsException() {
		helloService.createGreeting(new Greeting(null));
	}

	@Test
	public void createGreetingOKWhenValidRequest() {
		final String HELLO_LUKE = "Hello Luke";
		Greeting request = new Greeting(HELLO_LUKE);

		Greeting created = helloService.createGreeting(request);
		Assert.assertEquals(HELLO_LUKE, created.getMessage());
	}

	@Test
	public void updateGreetingWithNullReturnEmptyOptional(){
		final Optional<Greeting> updated = helloService.updateGreeting(null, null);
		Assert.assertFalse(updated.isPresent());
	}

	@Test
	public void updateGreetingOkReturnWithValidGreeting(){
		final String HELLO_LUKE = "Hello Luke";
		final String HELLO_JOHN = "Hello John";
		Greeting greeting = new Greeting(HELLO_LUKE);

		final Greeting createGreeting = helloService.createGreeting(greeting);
		greeting.setMessage(HELLO_JOHN);
		final Optional<Greeting> optionalGreeting = helloService.updateGreeting(greeting.getId(), greeting);

		Assert.assertTrue(optionalGreeting.isPresent());
		Assert.assertEquals(greeting.getMessage(), optionalGreeting.get().getMessage());
		Assert.assertEquals(createGreeting.getId(), optionalGreeting.get().getId());
	}
}
