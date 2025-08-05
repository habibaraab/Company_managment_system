### Company Managment System
# UML 
```mermaid

classDiagram
    class TeamController {
        +assignManagerToTeam(): TeamDto
        +createTeam(): TeamDto
        +getTeamById(): TeamDto
    }
    class UserController {
        +getUserById(): UserResponseDto
    }

    class TeamService {
        +assignManagerToTeam(): TeamDto
        +createTeam(): TeamDto
    }
    class UserService {
         +getUserById(): UserResponseDto
    }

    class JpaRepository {
        <<interface>>
        +findById()
        +findAll()
        +save()
    }
    class TeamRepository {
        <<interface>>
        +findByManagerId(): List
    }
    class UserRepository {
        <<interface>>
        +findUserById(): Optional
    }
    
    class TeamMapper {
        <<interface>>
        +teamToTeamDto(): TeamDto
    }
    class UserMapper {
        <<interface>>
        +toUserResponseDto(): UserResponseDto
    }

    TeamController o-- TeamService
    UserController o-- UserService
    
    TeamService o-- TeamRepository
    TeamService o-- UserRepository
    UserService o-- UserRepository

    TeamService o-- TeamMapper
    UserService o-- UserMapper

    JpaRepository <|-- TeamRepository
    JpaRepository <|-- UserRepository
```

# ERD
```mermaid
erDiagram
    COMPANY {
        int id PK
        varchar(255) name
        varchar(255) description
        varchar(255) location
    }

    DEPARTMENT {
        int id PK
        varchar(255) name
        int company_id FK
    }

    USER {
        int id PK
        varchar(255) name
        varchar(255) email UK
        varchar(255) title
        enum role
        enum level
        int department_id FK
        int manager_id FK
    }

    TEAM {
        int id PK
        varchar(255) name
        int manager_id FK
    }

    TOKEN {
        int id PK
        varchar(255) token UK
        enum token_type
        bit expired
        bit revoked
        int user_id FK
    }

    TEAM_MEMBERS {
        int team_id PK, FK
        int user_id PK, FK
    }

    COMPANY ||--o{ DEPARTMENT : "has"
    DEPARTMENT ||--o{ USER : "contains"
    USER }o--|| USER : "manages"
    USER ||--o{ TEAM : "manages"
    USER ||--o{ TOKEN : "has"
    
    TEAM }o--o{ TEAM_MEMBERS : "is part of"
    USER }o--o{ TEAM_MEMBERS : "is member of"

```
