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

## project architecture (sequence diagram)

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

    Note over User,Database: Todo Services Flow
    User->>SpringSecurity: User Action
    SpringSecurity->>SpringSecurity: Authenticate User
    SpringSecurity->>+JwtService: Validate Token (JWT token)
    JwtService-->>-SpringSecurity: JWT Validation
    SpringSecurity->>SpringSecurity: Set Auditor (User Email)
    alt Token Valid
        Note over User,Database: Create Todo Flow
        SpringSecurity->>+TodoModule: Create Todo
        TodoModule->>+Database: Create Todo with Auditor
        Database-->>-TodoModule: Todo created
        TodoModule-->>-User: Todo created

        Note over User,Database: Update/Patch Todo Flow
        SpringSecurity->>+TodoModule: Update Todo
        TodoModule->>+Database: Update Todo with Auditor
        Database-->>-TodoModule: Todo updated
        TodoModule-->>-User: Todo updated

        Note over User,Database: List Todos Flow
        SpringSecurity->>+TodoModule: List Todos
        TodoModule->>+Database: Get Todo list for Auditor
        Database-->>-TodoModule: List of todos
        TodoModule-->>-User: List of todos
    else Token Invalid
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
