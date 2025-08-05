### Company Managment System
# UML 
```mermaid

 classDiagram
    direction TD

    subgraph "Controller Layer"
        class TeamController {
            +assignManagerToTeam(teamId, managerId): TeamDto
            +createTeam(teamDto): TeamDto
            +getTeamById(teamId): TeamDto
        }
        class UserController {
            +getUserById(id): UserResponseDto
        }
    end

    subgraph "Service Layer"
        class TeamService {
            +assignManagerToTeam(teamId, managerId): TeamDto
            +createTeam(teamDto): TeamDto
        }
        class UserService {
             +getUserById(id): UserResponseDto
        }
    end

    subgraph "Repository (DAO) Layer"
        class JpaRepository {
            <<interface>>
            +findById(ID id)
            +findAll()
            +save(S entity)
        }
        class TeamRepository {
            <<interface>>
            +findByManagerId(int managerId): List
        }
        class UserRepository {
            <<interface>>
            +findUserById(int id): Optional
        }
    end
    
    subgraph "Mapper Layer"
        class TeamMapper {
            <<interface>>
            +teamToTeamDto(Team team): TeamDto
        }
        class UserMapper {
            <<interface>>
            +toUserResponseDto(User user): UserResponseDto
        }
    end


    ' --- Relationships ---

    ' Controller depends on Service
    TeamController o-- TeamService
    UserController o-- UserService

    ' Service depends on Repository
    TeamService o-- TeamRepository
    TeamService o-- UserRepository
    UserService o-- UserRepository

    ' Service depends on Mapper
    TeamService o-- TeamMapper
    UserService o-- UserMapper

    ' Repository Inheritance from Spring Data JPA
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
