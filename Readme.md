# Exercise instructions

1. Create an implementation of the repository interface which has to work with the database implemented
    1. We provide H2 implementation, if you want you can use other solution.
2. Complete repository implementation to get a customer.
3. Create Api REST methods to create and update a customer
4. Document Api Rest with the necessary details and handle API response errors
5. Add field validations for the Customer object.
    1. firstName Not Null and size < 50 && > 10
    2. lastName Not Null and size < 100 && > 10
    3. company Not Null
6. Include an explanation of your proposed solution.

## Instructions to build project
- The project works with JDK version 11 
- Build: mvn clean install
- Deploy: mvn spring-boot:run
- H2 console: http://localhost:8080/h2-console/
    - JDCB URL: jdbc:h2:mem:brujula
    - User: brujula
    - Password: brujula
    
## Rest Api
- Url: http://localhost:8080/swagger-ui/index.html

## Delivery instructions
- Don´t commit to the current repository, complete the exercise and compress it, including a document with the explanations that you consider. 