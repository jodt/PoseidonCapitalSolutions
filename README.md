# POSEIDON

## Prerequisites:

1. Spring Boot 3.1.0
2. Java 18
3. Thymeleaf
4. Mysql 8.1.0


## Using Poseidon

To use Poseidon, follow these steps:

* Clone the project

### Database (Two methods):

* :warning: You must install mysql and set your username and password. :warning:

### First method : 
* Open a terminal and use the command :

```
mysql -u *username* -p *password*
```

* Now you can create the poseidon database using data.sql in the "doc" folder. Copy file path and use commands :

```
CREATE DATABASE IF NOT EXISTS `poseidon`;
```
```
USE poseidon;
```
```
SOURCE /path_of_the_file
```

* Two users will be created to be able to log in:

* * Login : admin / Password : Admin123456789+
* * Login : user / Password : Admin123456789+

### Second method :
* you can also just run the project and the database will be created at that time after the next configuration step.

* Open a terminal and use the command
```
mysql -u *username -p *password*
```

```
USE poseidon;
```
* Create users using insertData.sql in the "doc" folder. Copy file path and use commands :

```
SOURCE /path_of_the_file
```

* Two users will be created to be able to log in:

* * Login : admin / Password : Admin123456789+
* * Login : user / Password : Admin123456789+


### Project setup :

* Open the project in your IDE. Open the application.properties file and edit the following lines:

```
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/poseidon?createDatabaseIfNotExist=true
(change the mysql port if it's not 3306)
spring.datasource.username="put your username here" 
spring.datasource.password="put your password here"
```

* If you want to use github to authenticate, enter the following properties
```
spring.security.oauth2.client.registration.github.client-id="YOUR CLIENT ID"
spring.security.oauth2.client.registration.github.client-secret="YOUR CLIENT SECRET"
```
### Run the project and have fun
