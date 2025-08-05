###                  Company Managment System
## User-API
# UML
erDiagram
    USER {
        int id PK
        string name
        string email
    }
    TEAM {
        int id PK
        string name
        int manager_id FK
    }
    USER ||--o{ TEAM : "manages"
