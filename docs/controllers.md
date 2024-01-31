# CONTROLLER PACKAGE CLASSES DOCUMENTATION

### Table of Content

---

1.  [Account Controller](#account-controller-documentation)
2. [Transfer Controller](#transfer-controller-documentation)

---

## [Account Controller Documentation](#accountcontroller)

## Overview

The `AccountController` class handles interactions with the Fabrick API for retrieving account-related information. It provides endpoints for fetching account balance and transactions.

## Class Structure

- **Package**: `com.service.fabrickapi.controller`
- **Author**: Berk Delibalta
- **Copyright**: © 2024 Berk Delibalta

## Methods

### 1. Get Account Balance

**Endpoint:** `GET /api/v1/account/{accountId}/balance`

**Description:** Retrieves the balance of a specified account.

**Request:**
- Method: `GET`
- Produces: JSON or XML
- Path Variable: `{accountId}` - The unique identifier of the account

**Response:**
- Status Code: `200 OK` if successful, `403 Forbidden` if access is forbidden, `500 Internal Server Error` otherwise
- Body: Account balance details in the form of `AccountBalanceRest` object

  | Parameter   | Data Type            | Description                           |
    |-------------|----------------------|---------------------------------------|
  | accountId   | Long                 | Path variable representing the account ID to fetch the balance |

  | Response                | Data Type                     | HTTP Status Codes | Description                           |
    |-------------------------|-------------------------------|-------------------|---------------------------------------|
  | ResponseEntity<AccountBalanceRest> | AccountBalanceRest          | 200 OK            | HTTP response containing the AccountBalanceRest object |
  | AccountBalanceRest      | -                             |                   | Response object representing the account balance details |
  |                        |                               | 403 Forbidden     | If access is forbidden |
  |                        |                               | 500 Internal Server Error | If there is an internal server error |

---

### 2. Get Account Transactions

**Endpoint:** `GET /api/v1/account/{accountId}/transactions`

**Description:** Retrieves a list of transactions for a specified account within a given date range.

**Request:**
- Method: `GET`
- Produces: JSON or XML
- Path Variable: `{accountId}` - The unique identifier of the account
- Query Parameters: `fromAccountingDate`, `toAccountingDate` - Date range for filtering transactions

**Response:**
- Status Code: `200 OK` if successful, `403 Forbidden` if access is forbidden, `500 Internal Server Error` otherwise
- Body: List of transactions in the form of `List<TransactionRest>` object

  | Parameter           | Data Type                  | Description                           |
    |---------------------|----------------------------|---------------------------------------|
  | accountId           | Long                       | Path variable representing the account ID to fetch transactions |
  | fromAccountingDate   | String                     | Query parameter representing the start date for filtering transactions (ISO 8601 format) |
  | toAccountingDate     | String                     | Query parameter representing the end date for filtering transactions (ISO 8601 format) |

  | Response                | Data Type                     | HTTP Status Codes | Description                           |
    |-------------------------|-------------------------------|-------------------|---------------------------------------|
  | ResponseEntity<List<TransactionRest>> | List<TransactionRest>          | 200 OK            | HTTP response containing a list of TransactionRest objects |
  | List<TransactionRest>   | -                             |                   | Response object representing a list of transactions |
  |                        |                               | 403 Forbidden     | If access is forbidden |
  |                        |                               | 500 Internal Server Error | If there is an internal server error |

## [Transfer Controller Documentation](#transfercontroller)

## Overview

The `TransferController` class handles loan transfer operations from one account to another. It provides an endpoint for initiating loan transfers.

## Class Structure

- **Package**: `com.service.fabrickapi.controller`
- **Author**: Berk Delibalta
- **Copyright**: © 2024 Berk Delibalta

## Methods

### 1. Transfer Loan

**Endpoint:** `POST /api/v1/transfer/{accountId}`

**Description:** Initiates a loan transfer from one account to another.

**Request:**
- Method: `POST`
- Consumes: JSON or XML
- Produces: JSON or XML
- Path Variable: `{accountId}` - The unique identifier of the account initiating the loan transfer
- Body: Loan transfer details in the form of `LoanTransferRequest` object

**Response:**
- Status Code: `200 OK` if successful, `403 Forbidden` if access is forbidden, `500 Internal Server Error` otherwise
- Body: Loan transfer details in the form of `LoanTransferRest` object

  | Parameter       | Data Type                   | Description                           |
    |-----------------|-----------------------------|---------------------------------------|
  | accountId       | Long                        | Path variable representing the account ID initiating the loan transfer |
  | transferRequest | LoanTransferRequest         | Request body containing details for the loan transfer |

  | Response                | Data Type                | HTTP Status Codes | Description                           |
    |-------------------------|--------------------------|-------------------|---------------------------------------|
  | ResponseEntity<LoanTransferRest> | LoanTransferRest         | 200 OK            | HTTP response containing the LoanTransferRest object |
  | LoanTransferRest        | -                        |                   | Response object representing the details of the loan transfer |
  |                        |                          | 403 Forbidden     | If access is forbidden |
  |                        |                          | 500 Internal Server Error | If there is an internal server error |


## Swagger Documentation

API documentation is available using Swagger. You can access it by navigating to `/swagger-ui.html` after starting the application.

## License

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