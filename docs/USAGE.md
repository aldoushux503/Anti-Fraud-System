# API endpoints usage

This document contains examples for all endpoints that can be used to interact with the system. Below you can find
a list of all processes that can be performed using the API.

### Processes

- [Signup new user](#signup)
- [Login as existing user](#login)
- [Delete existing user](#delete-user)
- [Get a user list](#get-user-list)
- [Update a users role](#update-user-role)
- [Update a users access level](#update-user-access)
- [Post a new transaction](#post-transaction)
- [Save a suspicious ip](#save-suspicious-ip)
- [Delete a suspicious ip](#delete-suspicious-ip)
- [Save a stolen card number](#save-stolen-card-number)
- [Delete a stolen card number](#delete-stolen-card-number)
- [Add transaction feedback](#add-transaction-feedback)
- [Get transaction history for a given card number](#get-transaction-history)

The full list of endpoints is available in the table of the main [README](../README.md) file.

## Signup

```
POST /api/auth/user
{
   "name": "<String value, not empty>",
   "username": "<String value, not empty>",
   "password": "<String value, not empty>"
}
```

Response:

```
{
    "id": 1,
    "name": "John Doe",
    "username": "JohnDoe",
    "role": "ADMINISTRATOR"
}
```

## Login

```
POST /api/auth/login
{
   "username": "<String value, not empty>",
   "password": "<String value, not empty>"
}
```

Response:

```
{
    "id": 1,
    "name": "John Doe",
    "username": "JohnDoe",
    "role": "ADMINISTRATOR"
}
```

_Note: This endpoint should be used to authenticate a user with http basic authentication. The response will be stored
in the browser's local storage._

## Delete user

```
DELETE /api/auth/user/{username}
```

Response:

```
{
   "username": "JohnDoe",
   "status": "Deleted successfully!"
}
```

## Get user list

```
GET /api/auth/list
```

Response:

```
[
    {
        "id": <user1 id>,
        "name": "<user1 name>",
        "username": "<user1 username>",
        "role": "<user1 role>"
    },
     ...
    {
        "id": <userN id>,
        "name": "<userN name>",
        "username": "<userN username>",
        "role": "<userN role>"
    }
]
```

## Update user role

```
PUT /api/auth/role
{
   "username": "<String value, not empty>",
   "role": "<String value, not empty>"
}
```

Response:

```
{
   "id": <Long value, not empty>,
   "name": "<String value, not empty>",
   "username": "<String value, not empty>",
   "role": "<String value, not empty>"
}
```

## Update user access

```
PUT /api/auth/access
{
   "username": "<String value, not empty>",
   "operation": "<[LOCK, UNLOCK]>"  // determines whether the user will be activated or deactivated
}
```

Response:

```
{
    "status": "User <username> <[locked, unlocked]>!"
}
```

## Post transaction

```
POST /api/antifraud/transaction
{
  "amount": <Long>,
  "ip": "<String value, not empty>",
  "number": "<String value, not empty>",
  "region": "<String value, not empty>",
  "date": "yyyy-MM-ddTHH:mm:ss"
}
```

Response:

```
{
   "result": "ALLOWED",
   "info": "none"
}
```

## Save suspicious IP

```
POST /api/antifraud/suspicious-ip
{
  "ip": "<String value, not empty>"
}
```

Response:

```
{
   "id": "<Long value, not empty>",
   "ip": "<String value, not empty>"
}
```

## Delete suspicious IP

```
DELETE /api/antifraud/suspicious-ip/{ip}
```

Response:

```
{
   "status": "IP <ip address> successfully removed!"
}
```

## Save stolen card number

```
POST /api/antifraud/stolencard
{
  "number": "<String value, not empty>"
}
```

Response:

```
{
   "id": "<Long value, not empty>",
   "number": "<String value, not empty>"
}
```

## Delete stolen card number

```
DELETE /api/antifraud/stolencard/{number}
```

Response:

```
{
   "status": "Card <number> successfully removed!"
}
```

## Add transaction feedback

```
PUT /api/antifraud/transaction
{
   "transactionId": <Long>,
   "feedback": "<String>"
}
```

_Feedback can be 'ALLOWED', 'MANUAL_PROCESSING' or 'PROHIBITED'_.

Response:

```
{
  "transactionId": <Long>,
  "amount": <Long>,
  "ip": "<String value, not empty>",
  "number": "<String value, not empty>",
  "region": "<String value, not empty>",
  "date": "yyyy-MM-ddTHH:mm:ss",
  "result": "<String>",
  "feedback": "<String>"
}
```

## Get transaction history

```
GET /api/antifraud/history/{number}
```

Response:

```
[
    {
      "transactionId": <Long>,
      "amount": <Long>,
      "ip": "<String value, not empty>",
      "number": number,
      "region": "<String value, not empty>",
      "date": "yyyy-MM-ddTHH:mm:ss",
      "result": "<String>",
      "feedback": "<String>"
    },
     ...
    {
      "transactionId": <Long>,
      "amount": <Long>,
      "ip": "<String value, not empty>",
      "number": number,
      "region": "<String value, not empty>",
      "date": "yyyy-MM-ddTHH:mm:ss",
      "result": "<String>",
      "feedback": "<String>"
    }
]
```
