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

