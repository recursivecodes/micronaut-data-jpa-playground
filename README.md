# micronaut-data-jpa-demo

## Validation

See the `savePerson()` method in `PersonController.groovy` and the `Person.groovy` class.

Try to save an invalid user:

```bash
curl -iX POST -H "Content-Type: application/json" -d '{"firstName": "T", "lastName": "Sharp", "age": 155}' http://localhost:8080/person/savePerson                      
HTTP/1.1 400 Bad Request
Content-Type: application/json
content-length: 238
connection: close
```

Returns multiple errors:

```json
{
  "message": "Bad Request",
  "_embedded": {
    "errors": [
      {
        "message": "person.age: must be less than or equal to 125"
      },
      {
        "message": "person.firstName: size must be between 3 and 10"
      }
    ]
  },
  "_links": {
    "self": {
      "href": "/person/savePerson",
      "templated": false
    }
  }
}
```

If there is only one invalid property as in this request:

```bash 
curl -X POST -H "Content-Type: application/json" -d '{"firstName": "T", "lastName": "Sharp", "age": 43}' http://localhost:8080/person/savePerson
```

Then the following structure is returned:

```json
{
  "message": "person.firstName: size must be between 3 and 10",
  "_links": {
    "self": {
      "href": "/person/savePerson",
      "templated": false
    }
  }
}
```

*Note*: if you don't like the fact that there is a different format returned for only a single error, talk to the people who wrote the spec. :-/

To save a valid user:

```bash
$curl -X POST -H "Content-Type: application/json" -d '{"firstName": "Todd", "lastName": "Sharp", "age": 43}' http://localhost:8080/person/savePerson                       | jq
{
  "id": 7,
  "firstName": "Todd",
  "lastName": "Sharp",
  "age": 43,
  "dateCreated": 1589222560917,
  "lastUpdated": 1589222560917
}
```