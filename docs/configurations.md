# CONFIGURATION PACKAGE CLASSES DOCUMENTATION

## Beans Configuration Documentation

### Overview

The `Beans` class is a Spring configuration class that defines beans for `RestTemplate` and `ObjectMapper`. It sets custom configurations for the `RestTemplate`, such as connect timeout and read timeout.

### Class Structure

- **Package**: `com.service.fabrickapi.configuration`
- **Author**: Berk Delibalta

### Beans

1. **restTemplate**: Bean for the `RestTemplate` configured with custom timeouts.
    - **Method**: `restTemplate`
    - **Configuration**: Connect timeout and read timeout are both set to 10 seconds.

2. **objectMapper**: Bean for the `ObjectMapper`.
    - **Method**: `objectMapper`

---

## Credentials Configuration Documentation

### Overview

The `Credentials` class is a configuration properties class that holds properties related to Fabrick API. It is annotated with `@ConfigurationProperties(prefix = "fabrick")` to bind the properties from the configuration file.

### Class Structure

- **Package**: `com.service.fabrickapi.configuration`
- **Author**: Berk Delibalta

### Properties

1. **baseURL**: Base URL for Fabrick API.
2. **authSchema**: Authentication schema for Fabrick API.
3. **apiKey**: API key for Fabrick API.
4. **balanceURL**: URL for retrieving account balance from Fabrick API.
5. **transactionsURL**: URL for retrieving transactions from Fabrick API.
6. **transfersURL**: URL for performing transfers via Fabrick API.

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