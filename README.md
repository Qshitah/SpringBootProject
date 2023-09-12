# Project with Spring Boot, Spring Security, and Spring Data JPA (Still in Progress)(For Now Just Authentification)
This project is a comprehensive authentication system built using Java Spring Boot, Spring Security, Spring Data JPA, and Hibernate. It includes features like user registration (signup), login, password reset, and password verification. Below are some key features and components of this project:

Spring Boot: The application is built on the Spring Boot framework, providing a solid foundation for rapid development and deployment.

Spring Security: User authentication and authorization are managed using Spring Security, ensuring secure access to protected resources.

Spring Data JPA and Hibernate: The project uses JPA annotations and Hibernate as the ORM (Object-Relational Mapping) tool to interact with the database.

User Registration (Signup): Users can register for an account by providing necessary details such as username, password, and email. Passwords are securely hashed and stored in the database.

Login: Registered users can log in using their credentials, and Spring Security handles authentication to ensure a secure login process.

Forgot Password: Users who forget their password can request a password reset. A secure reset link is sent via email, and a verification token is generated.

Password Verification: When users click on the password reset link, they are redirected to a page where they can create a new password after verifying their identity using the token.
Spring Data REST: The project employs Spring Data REST to expose RESTful APIs for managing user accounts and authentication processes.
