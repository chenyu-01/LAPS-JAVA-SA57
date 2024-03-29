# LAPS-JAVA-SA57

We are creating a Leave Application Processing System using JAVA Spring Boot Framework. Boilerplate files Generated By Intellij Idea. Use Maven as Dependency manager


## Authors

[@Preethi Venkiitu](https://www.github.com/preethivenkat5)
[@Chen Yu](https://www.github.com/chenyu-01)
[@Liu Bao](https://www.github.com/MozLau)
[@Yu Zeijing](https://www.github.com/CsCesium)
[@PriyadharshiniMuthamilselvan](https://www.github.com/PriyadharshiniMuthamilselvan)


## LOGIN
Admin :

Username : admin@gmail.com

password : password123

Employee :

Username : employee2@gmail.com

Password : password123

Manager :

Username : manager@gmail.com

Password : password123


## Features

-Login:
Single entry point for all users.
Password-protected access.
User authentication with user ids and passwords stored in the database.

-Supported Leave Types
Annual, Medical, and Compensation leave.

-Employees can submit, update, or delete leave applications.

-Validation of leave details.

-Different statuses: Applied, Updated, Deleted, Approved, Cancelled.

-Conditions checked for leave application.

-View Personal Leave History and View Leave Application:
Employees can view their leave history and individual leave details.

-View Leave Application for Approval and Subordinate Leave History:
Managers can view and act on leave applications submitted by subordinates.
Managers can view complete leave history of subordinates.

-Administration:

Admins can create, modify, and delete employees.
Role assignment and modification for employees.
Maintain leave types, public holidays, and employee annual leave entitlements.

-REST Controller and REST Repository:
Implement RESTful services for data exchange

-ReactJS Implementation

_ThymeLeaf Implementation






REST Controller

Admin Controller
1. /api/admin
2. /api/admin/users
3. /api/admin/{id}
4. /api/users/Manager/{id}
5. /api/admin/users

Leave Application Controller
1. /api/applications
2. /api/applications/applied
3. /api/applications/applied_list/{id}
4. /api/applications/approve/{id}
5. /api/applications/reject/{id}
6. /api/applications/comment/{id}
7. /api/applications/get/{id}
8. /api/applications/findemployee/{id}
9. /api/applications/update
10. /api/applications/cancel/{id}
11. /api/applications/delete/{id}
12. /api/applications/submit/{id}
13. /api/applications/query/{id}

User Controller

1. /api/users
2. /api/users/login
3. /api/users/check-auth
4. /api/users/logout
5. /api/users/register


LeaveEntitlement Controller

1./api/leave-entitlement
2.api/leave-entitlement/get/{userId}

Controller
LeaveType Controller

1./leavetype/list
2./leavetype/editDetails/{id}

Public Holiday Controller

1./publicholidays/list
2./publicholidays/create
3./publicholidays/edit/{id}
4./publicholidays/delete/{id}

Role Controller

1. /admin/role/list




## Run Locally

# Running the Java Application

This guide provides step-by-step instructions on how to run the Java application on your system. Follow these steps to ensure a smooth setup and execution.

## Prerequisites

Before proceeding, ensure you have the following installed:
- Java (JDK)
- MySQL Server

## Step 1: Modify `application.properties`

First, you need to configure the database connection in the `application.properties` file. Follow these steps:

1. Open the `application.properties` file located in the `src/main/resources` directory.
2. Locate the following properties:
spring.datasource.username=
spring.datasource.password=
3. Replace the above lines with your MySQL username and password. For example:
spring.datasource.username=root
spring.datasource.password=myPassword
4. Ensure the `spring.datasource.url` property matches your local MySQL setup:
spring.datasource.url=jdbc:mysql://localhost:3306/SA
spring.datasource.url=jdbc:mysql://localhost:3306/SA
./mvnw spring-boot:run
Or, if you are using Windows:
mvnw spring-boot:run

The application should now start and be accessible. Ensure that the database server is running and accessible for the application to connect to.

## Troubleshooting

If you encounter any issues, check the following:

- Ensure that MySQL server is running.
- Verify that the database username and password in `application.properties` are correct.
- Confirm that the `spring.datasource.url` in `application.properties` correctly points to your MySQL database.

