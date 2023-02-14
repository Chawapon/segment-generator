# segment-generator
Create simple project using SpringBoot to get random user from Random User API

## API
GET /v1/users/{seed}

### Schema
```schema
firstname   String 
lastname    String 
gender      String 
email       String 
```
### Example Response
```json
[ 
    {
        "firstname": "Ryder",
        "lastname": "lastname",
        "gender": "male",
        "email": "ryder.singh@example.com"
    }
]
```