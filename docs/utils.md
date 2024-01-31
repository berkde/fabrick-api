# SHARED PACKAGE CLASSES DOCUMENTATION

## Introduction
This Java library provides utility functions for interacting with the Fabrick API. It includes methods for building URLs, making API calls, and handling JSON data. The library is designed to be used in conjunction with the Fabrick API for accessing account and transaction information.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)
- [Author](#author)


## Usage
### Class: `Utils`

#### Constructor
```java
@Autowired
public Utils(Credentials credentials, RestTemplate restTemplate, ObjectMapper objectMapper){}
```

- `credentials`: The credentials object containing the API key and authentication schema.
- `restTemplate`: The RestTemplate used for making HTTP requests.
- `objectMapper`: The ObjectMapper used for parsing JSON responses.

#### Methods
1. `public HttpHeaders headers()`: Returns HTTP headers necessary to make API calls.

2. `public String buildUrl(String path, Long accountId)`: Builds a URL for a specific account.

3. `public String buildUrlWithQueryParams(String path, Long accountId, String fromAccountingDate, String toAccountingDate)`: Builds a URL for a specific account with query parameters.

4. `public ResponseEntity<String> getStringResponseEntity(String uri, HttpEntity<?> entity, HttpMethod httpMethod)`: Makes an API call and returns the response entity.

5. `public <K> String toJson(K object) throws AccountServiceException`: Converts an object to its JSON representation.

6. `public <K> K fromJson(String jsonString, Class<K> clazz) throws FabrickRestServiceException`: Converts a JSON string to an object of the specified class.

7. `public <K> K fromJson(String json, Class<? extends Collection> collectionClass, Class<?> elementClass) throws AccountServiceException`: Converts a JSON string to a collection of objects.

### Class: `ErrorMessages`
Enum containing error messages used in exception handling.

### Class: `Credentials`
Class representing the credentials object containing the API key and authentication schema.

## License
This library is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Author
Berk Delibalta
© 2024 Berk Delibalta. All rights reserved.