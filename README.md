### Company Managment System
# UML 
```mermaid

class Company {
  -id: int
  -name: String
  -description: String
  -location: String
}

class Department {
  -id: int
  -name: String
}

class User {
  -id: int
  -name: String
  -email: String
  -password: String
  -phone: String
  -title: String
  -salaryGross: Float
  -level: Level
  -role: Role
}

enum Level {
  FRESH
  JUNIOR
  LEAD
  SENIOR
}

enum Role {
  COMPANY_MANAGER
  EMPLOYEE
  MANAGER
}

class Team {
  -id: int
  -name: String
}

class Token {
  -id: int
  -token: String
  -tokenType: TokenType
  -expired: boolean
  -revoked: boolean
}

enum TokenType {
  BEARER
  REFRESH
}


' Define Relationships
' ====================

' Company <--> Department
Company "1" o-- "*" Department : "has"

' Department <--> User
Department "1" o-- "*" User : "contains"

' User <--> User (Self-referencing for manager)
User "1" o-- "*" User : "manages >"

' User <--> Team (Manager of Team)
User "1" o-- "*" Team : "manages"

' User <--> Token
User "1" o-- "*" Token : "has"

' Team <--> User (Many-to-Many for members)
Team "1" *-- "*" User : "< has members"


' Connect Enums to User class
User .> Level
User .> Role
Token .> TokenType

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
