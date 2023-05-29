# Spring Security with JWT

## Objective
Implementing simple authentication and authorization using Spring Security and JWT.

## Contents
- Implementing a simple authentication and authorization mechanism using Spring Security and JSON Web Tokens (JWT).
- There are two types of permissions available: ADMIN and USER.
- Utilizing Docker containers with the help of docker-compose.
- Tailoring an Exception Handler to fit the project requirements.
- Using key rolling technology to create access tokens with multiple keys.
- Allows the selection of desired Role at the time of login:
- The [joinUsernameAndRole] function is utilized to include the desired ROLE within the [username] parameter that is passed to the [loadUserByUsername] method of the [Userdetails] class.
- A user can possess multiple Profiles, with each profile having the ability to hold a single permission.

## HOW TO RUN
``docker-compose build --no-cache & docker-compose up``

## Running in Debug Mode
- Run the database using docker-compose and launch the application separately.
