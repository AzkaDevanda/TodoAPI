# TodoAPI

 # User API Spec

## Register User

- Endpoint : POST /api/users

Request Body:

```json
{
  "username": "Username1",
  "password": "rahasia",
  "name": "name1"
}
```

Response Body (Success):

```json
{
  "data": "Register Success"
}
```

Response Body (Failed):

```json
{
  "data": "Username must not blank...???"
}
```

## Login User

- Endpoint : POST /api/users

Request Body:

```json
{
  "username": "Username1",
  "pssword": "rahasia"
}
```

Response Body (Success):

```json
{
  "data": {
    "token": "TOKEN",
    "expiredAt": 2312412312312 // on milisecond
  }
}
```

Response Body (Failed):

```json
{
  "data": "Username or Password wrong"
}
```

## Get User

- Endpoint : GET /api/users

Request Header:

- X-API-TOKEN : Token (Mandatory)

Response Body (Success):

```json
{
  "data": {
    "username": "Username1",
    "name": "name1"
  }
}
```

Response Body (Failed, 401):

```json
{
  "data": "Unauthorized"
}
```

## Update User

- Endpoint : PATCH /api/users/current

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
  "name": "Bagus", // put if only want to update name
  "password": "new password" // put if only want to update password
}
```

Response Body (Success) :

```json
{
  "data": {
    "username": "Username1",
    "name": "name1"
  }
}
```

Response Body (Failed,401) :

```json
{
  "errors": "Unauthorized"
}
```

## Logout User

Endpoint : DELETE /api/auth/logout

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data": "OK"
}
```

# todo API Spec

## Create Todo

Request Header :

- X-API-TOKEN : Token (Mandatory)

Endpoint POST /api/todos

Request Body:

```json
{
  "task" : "contoh task",
  "description": "deskripsi",
  "completed" : "false",
  "deadline" : 12-02-2003
}
```

Response Body (success):

```json
{
  "data": {
    "task" : "contoh task",
    "description": "deskripsi",
    "completed" : "false",
    "deadline" : 12-02-2003
  }
}
```

Response Body (filed)

```json
{
  "errors": "All field must not blank"
}
```

## Update Todo

Request Header :

- X-API-TOKEN : Token (mandatory)

Endpoints : PUT api/todo/{taksId}

Request Body :

```json
{
  "task" : "contoh task",
  "description": "deskripsi",
  "completed" : "false",
  "deadline" : 12-02-2003
}
```

Response Body (success):

```json
{
  "data": {
    "task" : "task baru",
    "description": "deskripsi baru",
    "completed" : "true",
    "deadline" : 12-02-2003
  }
}
```

Response Body (filed) :

```json
{
  "errors": "Deskription invalid, task invalid,...."
}
```

## Get All Todo

Endpoint : GET /api/contacts

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data": {
    "task" : "contoh task",
    "description": "deskripsi",
    "completed" : "false",
    "deadline" : 12-02-2003
  },
  {
    "task" : "task baru",
    "description": "deskripsi baru",
    "completed" : "true",
    "deadline" : 12-02-2003
  }
}
```

Response Body (Failed, 404) :

```json
{
  "errors": "Todo is Empty"
}
```

## Remove Todo

Endpoint : DELETE /api/contacts/{idContact}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data": "OK"
}
```

Response Body (Failed) :

```json
{
  "errors": "Todo is not found"
}
```


