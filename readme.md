
# Devices manager

This project was built with Java 21, Spring boot and H2DB.

The project goals are:
* Add new Device
* List device by All, brand and ID
* Update the Device
* Update partial Device
* Delete device by ID

## How to run the project

1. go to the project root.
1. execute ```docker-compose up app```

The application will be up and running listing the port 8080

## Swagger:
http://localhost:8080/swagger-ui/index.html

## Examples:

### GET

All the get operation are in paged response.

You can define the size and page usign the params:

size=1 and page=0

#### Get all
```curl --location 'http://localhost:8080/devices'```

#### Get by id
```curl --location 'http://localhost:8080/devices/1'```

#### Get by brand
```curl --location 'http://localhost:8080/devices?brand=text'```


### New Device

```
curl --location 'localhost:8080/devices' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Text",
    "brand": "Text"
}'
````

### Update Device

```
curl --location --request PUT 'localhost:8080/devices/1' \
--header 'Content-Type: application/json' \
--data '{
    "name": "new text",
    "brand": "new text"
}'
```

### Update partial information

```
curl --location --request PATCH 'localhost:8080/devices/1' \
--header 'Content-Type: application/json-patch+json' \
--data '[
    {
        "op": "replace",
        "path": "/brand",
        "value": "test2"
    },
    {
        "op": "replace",
        "path": "/name",
        "value": "xpto"
    }
]'
```

### Delete device

```
curl --location --request DELETE 'localhost:8080/devices/1'
```