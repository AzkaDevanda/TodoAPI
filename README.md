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

