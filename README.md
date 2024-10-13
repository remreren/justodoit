# justodoit
## just todo it, schedule for later on

# technologies

- java 21
- spring boot 3.4.4
- couchbase 7.6.3
- prometheus v2.44.0 (for observability)
- grafana v9.5.2 (for observability)

# architecture

## folder structure
the project structured for modularity. there is 3 base folders; domain, exception and security

security module contains security configurations and filters. it is responsible for authentication and authorization mainly.

exception module contains custom exceptions and exception handlers.

domain contains 3 submodules; todo, user and auth.
- auth module is responsible for authentication and authorization.
- user module is an internal module, not exposed to the outside world. it is used mainly for authentication and authorization by auth module.
- todo module is responsible for todo operations. it is the main module of the project.

## project structure (sequence diagram)

```mermaid
sequenceDiagram
    participant User
    participant SpringSecurity
    participant AuthModule
    participant JwtService
    participant UserModule
    participant TodoModule
    participant Database

    Note over User,Database: Registration Flow
    User->>+AuthModule: Register(username, password)
    AuthModule->>+UserModule: createUser(user)
    UserModule->>Database: Save user details
    Database-->>UserModule: Acknowledgement
    UserModule-->>-AuthModule: User created
    AuthModule->>+JwtService: createToken(user)
    JwtService-->>-AuthModule: Token created
    AuthModule-->>-User: Registration successful

    Note over User,Database: Login Flow
    User->>+AuthModule: Login(username, password)
    AuthModule->>+UserModule: getUser(username)
    UserModule->>Database: Fetch user details
    Database-->>UserModule: User data
    UserModule-->>-AuthModule: User data
    AuthModule->>AuthModule: Validate User
    alt Valid credentials
        AuthModule->>+JwtService: createToken(user)
        JwtService-->>-AuthModule: Token Created
        AuthModule-->>User: Login successful (JWT token)
    else Invalid credentials
        AuthModule-->>-User: Login failed
    end

    Note over User,Database: Create Todo Flow
    User->>SpringSecurity: Authenticate User
    alt Token valid
        SpringSecurity->>+JwtService: Validate Token (JWT token)
        JwtService->>-SpringSecurity: JWT Valid
        SpringSecurity->>SpringSecurity: Set Auditor (User Email)
        SpringSecurity->>+TodoModule: Proceed with request
        TodoModule->>Database: Save todo with auditor
        Database-->>TodoModule: Todo saved
        TodoModule-->>-User: Todo created
    else Token invalid
        SpringSecurity->>+JwtService: Validate Token (JWT token)
        JwtService-->>-User: Unauthorized access
    end

    Note over User,Database: List Todos Flow
    User->>SpringSecurity: Authenticate User
    alt Token valid
        SpringSecurity->>+JwtService: Validate Token (JWT token)
        JwtService-->>-SpringSecurity: JWT Valid
        SpringSecurity->>+TodoModule: Proceed with request
        TodoModule->>Database: Fetch todos by auditor
        Database-->>TodoModule: List of todos
        TodoModule-->>-User: Display todos
    else Token invalid
        SpringSecurity->>+JwtService: Validate Token (JWT token)
        JwtService-->>-User: Unauthorized access
    end
```

# how to run

## for development

### prerequisites

- docker
- maven
- java 21

### steps

- first run the docker compose file contains couchbase, prometheus and grafana
- then run the spring boot application

```shell
cd docker
docker compose up -d # run the docker compose file
```

```shell
./mvnw spring-boot:run # run the spring boot application
```

## for production

you can use the docker compose file in the docker folder to run the application in production mode.

!!! CAUTION !!! do not forget to change values in the docker-compose file before running it in production.
