### Company Managment System
# UML 
```mermaid
classDiagram
    class Company {
        -int id
        -String name
        -String description
        -String location
    }

    class Department {
        -int id
        -String name
    }

    class User {
        -int id
        -String name
        -String email
        -String password
        -String title
        -Level level
        -Role role
    }

    class Team {
        -int id
        -String name
    }

    class Token {
        -int id
        -String token
        -TokenType tokenType
        -boolean expired
        -boolean revoked
    }

    class Level {
        <<enumeration>>
        FRESH
        JUNIOR
        LEAD
        SENIOR
    }

    class Role {
        <<enumeration>>
        COMPANY_MANAGER
        EMPLOYEE
        MANAGER
    }
    
    class TokenType {
        <<enumeration>>
        BEARER
        REFRESH
    }

    ' --- Relationships ---
    Company "1" -- "0..*" Department : has
    Department "1" -- "0..*" User : contains
    User "1" o-- "0..*" User : manages
    User "1" -- "0..*" Token : has
    Team "0..*" -- "1" User : managed by
    Team "*" -- "*" User : has members
    
    ' --- Enum Usage ---
    User ..> Level
    User ..> Role
    Token ..> TokenType
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
