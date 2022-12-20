#Drone Application

### Technologies

- Java
- Maven
- mysql Database
- Spring Boot
- Spring Data JPA
- flyway

### Requirements

You need the following to build and run the application:
- JDK 11
- Maven 3.6.3

## How to run Application

### step 1 - clone project with Terminal

### step 2 - create database on mysql driver db

### step 3 - check database credentials
```
default mysql credentials:
- drive is jdbc:mysql://localhost:3306/db?useSSL=false
- username = root
- password = root
if those are diffret you can change it from application.yml
at the application
```


### step 4 - Open the application Folder in an IDE, As a maven Project.
 
```
mvn spring-boot:run
```
### step 5 - open swagger page on any browser to check all available apis
### with it's all request samples

```
localhost:1010/swagger-ui.html
```

### to send and register medication /v1/medication/register
### as not supported to add content type at openAPI
```
curl --location --request POST 'localhost:1010/v1/medication/register' \
--header 'Content-Type: multipart/form-data' \
--form 'medicationFile=@"/{{ur image directory}}/file_name"' \
--form 'medication="{
  \"code\": \"CODE-123\",
  \"name\": \"MED-Name\",
  \"weight\": 400
}";type=application/json'
```