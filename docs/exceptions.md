# EXCEPTION PACKAGE CLASSES DOCUMENTATION

### Table of Contents

1. [Global Exception Handler ](#globalexceptionhandler-documentation)
2. [Account Service Exception](#accountserviceexception-documentation)
3. [Transfer Service Exception](#transferserviceexception-documentation)
4. [Fabrick Rest Service Exception](#fabrickrestserviceexception-documentation)

---

## GlobalExceptionHandler Documentation

### Overview

The `GlobalExceptionHandler` class is a Spring `ControllerAdvice` responsible for handling exceptions globally across the application. It contains methods annotated with `@ExceptionHandler` to handle specific exceptions and generate appropriate error responses.

### Class Structure

- **Package**: `com.service.fabrickapi.exception`
- **Author**: Berk Delibalta

### Fields

None

### Constructors

None

### Methods

1. **handleAccountServiceException**
    - **Parameters**: `AccountServiceException ex`
    - **Return Type**: `ResponseEntity<?>`
    - **Description**: Handles exceptions of type `AccountServiceException` by creating an `ErrorMessage` object and returning it as a `ResponseEntity`. The `ErrorMessage` object contains the error message and the current date. The `ResponseEntity` is returned with the appropriate HTTP status code.

2. **handleTransferServiceException**
    - **Parameters**: `TransferServiceException ex`
    - **Return Type**: `ResponseEntity<?>`
    - **Description**: Handles exceptions of type `TransferServiceException` by creating an `ErrorMessage` object and returning it as a `ResponseEntity`. The `ErrorMessage` object contains the error message and the current date. The `ResponseEntity` is returned with the appropriate HTTP status code.

3. **handleFabrickRestServiceException**
    - **Parameters**: `FabrickRestServiceException ex`
    - **Return Type**: `ResponseEntity<?>`
    - **Description**: Handles exceptions of type `FabrickRestServiceException` by creating an `ErrorMessage` object and returning it as a `ResponseEntity`. The `ErrorMessage` object contains the error message and the current date. The `ResponseEntity` is returned with the appropriate HTTP status code.

4. **handleOtherExceptions**
    - **Parameters**: `Exception ex`
    - **Return Type**: `ResponseEntity<?>`
    - **Description**: Handles general exceptions (instances of `Exception` class) by creating an `ErrorMessage` object and returning it as a `ResponseEntity`. The `ErrorMessage` object contains the error message and the current date. The `ResponseEntity` is returned with the HTTP status code set to INTERNAL_SERVER_ERROR.

5. **getStatusCode**
    - **Parameters**: `Exception ex`
    - **Return Type**: `HttpStatus`
    - **Description**: Determines the appropriate HTTP status code based on the exception's message. If the message contains specific phrases such as "401 Unauthorized," "403 Forbidden," "400 Bad Request," or "404 Not Found," it returns the corresponding HTTP status code. Otherwise, it defaults to INTERNAL_SERVER_ERROR.

---

## AccountServiceException Documentation

### Overview

The `AccountServiceException` class is a custom runtime exception used to represent exceptions related to the account service. It extends the base `RuntimeException` class and provides a constructor to set the exception message.

### Class Structure

- **Package**: `com.service.fabrickapi.exception`
- **Author**: Berk Delibalta

### Constructor

- **Constructor**: Initializes the `AccountServiceException` with a custom error message.


---

## TransferServiceException Documentation

### Overview

The `TransferServiceException` class is a custom runtime exception used to represent exceptions related to the transfer service. It extends the base `RuntimeException` class and provides a constructor to set the exception message.

### Class Structure

- **Package**: `com.service.fabrickapi.exception`
- **Author**: Berk Delibalta

### Constructor

- **Constructor**: Initializes the `TransferServiceException` with a custom error message.

---

## FabrickRestServiceException Documentation

### Overview

The `FabrickRestServiceException` class is a custom runtime exception used to represent exceptions related to Fabrick REST services. It extends the base `RuntimeException` class and provides a constructor to set the exception message.

### Class Structure

- **Package**: `com.service.fabrickapi.exception`
- **Author**: Berk Delibalta

### Constructor

- **Constructor**: Initializes the `FabrickRestServiceException` with a custom error message.


### License

MIT License

Copyright (c) 2024 Berk Delibalta

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.