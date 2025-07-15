# ecommerce-springboot-api

# Usage

1. Clone this repository

```bash
https://github.com/ROFL1ST/ecommerce-springboot-api.git
```

2. Import the project into your Java IDE (e.g., IntelliJ IDEA, Eclipse, VS Code)


3. Create application.properties file at src/main/resources/

```application.properties
spring.application.name=ecommerce

spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=password_db

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

spring.mail.username=example@gmail.com
spring.mail.password=smtp_password
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

server.port=8080

```

4. Run the application

```bash
./mvnw spring-boot:run
```
