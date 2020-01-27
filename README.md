# Labforward Code Challenge for Backend Engineer Candidate

This is a simple Hello World API for recruiting purposes. You, as a candidate, should work on the challenge on your own account. Please clone the repo to your account and create a PR with your solution. 

## Task	
You need to add a new endpoint to the API to allow users to *update the greetings they created*.

## Solution
I have listed all the changes and enhancements below. I spent 10-12 hours analyzing and implementing the solution.
#### API Changes 
 - Added a new endpoint `Patch /hello/{id}` to patch the existing greeting in the system.
   - It will update the entity, only if it is present in the system.
   - If there is no entity in system for the provided Id, then system shall send a resource not found response.
- Updated `Post /hello` - endpoint.
  - This endpoint was returning the newly created entity in response.
  - Updated code shall return the newly created entity as well as the location of entity in response header.
  - It is achieved by using `EntityCreatedResponse.class`, which has location field.

#### DI Change
- Dependency injection strategy changed. Removed constructor injection of `HelloService.class` in `HelloController.class` and used setter injection instead.

#### API Documentation
- Added Swagger dependency in project. It will enable the audience to visualize all the available API operations.
- Added `GlobalConfiguration.class` - This is used for adding project configuration, included swagger configuration bean for API documentation.<br/>
Documentation path: http://localhost:8080/swagger-ui.html#/

#### Test
- Fixed `returnsBadRequestWhenUnexpectedAttributeProvided()` and `createOKWhenRequiredGreetingProvided()` test in `HelloControllerTest.class`.
- Added integration test case for `Patch /hello{id}` endpoint.
- Added unit test case for update greeting in `HelloWorldServiceTest.class`

## Note
I found logger and datasource missing in the application. I assume it was avoided, for convenience sake. <br/> 
Thank you for sending this coding challenge. I have tried my best to provide a feasible solution.
 


