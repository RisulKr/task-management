# Taks-Management Application

*Task Management* is implemented using REST API

## Instruction
### Prerequisites
- Pre-installation of Maven and jdk17 is required
- Clone the Git repository: `https://github.com/RisulKr/task-management.git` (main branch)
- Run the command `mvn clean package` in the root folder

## Parameters
- **Port** - 8080
- **URL** - http://localhost:8080
- **Swagger** - http://localhost:8080/swagger-ui/index.html

## DB console connection parameters
- **Driver Class** - org.postgresql.Driver
- **JDBC URL** - jdbc:postgresql://localhost:5432/task_management
- **User Name** - postgres
- **Password** - hL1Uk8u7X1C1WcV

## Endpoints
### TASK's 
- **Create task** - POST request http://localhost:8080/task
    - `RequestBody: TaskDto`
    - `ResponseBody: String`
- **Get task** - GET request http://localhost:8080/task/{id}
    - `PathVariable: task id`
    - `ResponseBody: TaskSelectDto`
- **Get all tasks** - GET request http://localhost:8080/task
    - `ResponseBody: List of TaskSelectDto`
- **Update task** - PUT request http://localhost:8080/task/{id}
    - `PathVariable: task id`
    - `RequestBody: TaskDto`
    - `ResponseBody: String`
- **Delete task** - DELETE request http://localhost:8080/task/{id}
    - `PathVariable: task id`
    - `ResponseBody: String` (Task was deleted successfully)
- **Get all Task for Board - GET request http://localhost:8080/task/status
     - `Response Body: Page of TaskSelectDTO`
     - `Request Parameters: status, pageNumber, sort-strategy,sort-direction`
- **Get all favourite tasks** - GET request http://localhost:8080/task/favourite
     - `Response body: Page of TaskSelectDTO ` 
     - `Request Parameters: pageNumber , pageSize,sort-strategy,sort-direction`
- **Notification** - GET request http://localhost:8080/task/notification
     - `Response Body: Integer`


## USER's
- **Get all Users** - GET request http://localhost:8080/users/getUsers/All
    - `Response body: List of UserDTO`
- **Get user** - GET request http://localhost:8080/users/getUser/{id}
    - `Path variable: id`
    - `Response body: UserDTO`
- **Delete user** - DELETE request http://localhost:8080/users/deleteUser/{id}
    - `Path Variable:id`
    - `Response body: String`
- **Assign admin** - PATCH request http://localhost:8080/users/assignAdmin/{username},{id}
    - `Path Variables: username, id`
    - `Response body: String`

## REGISTRATION's
- **Register a user** - get request http://localhost:8080/register/registerUser
  - `Request Body: RegisterDTO`
  - `Response Body: String`