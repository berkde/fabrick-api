# ENTITY PACKAGE CLASSES DOCUMENTATION

### Table of Contents

1. [TransactionEntity Documentation](#transactionentity-documentation)

---

## TransactionEntity Documentation

### Overview

The `TransactionEntity` class is a Java entity representing transaction information in a database context. It is annotated with JPA (Java Persistence API) annotations to define its mapping to a database table. This entity encapsulates details related to a transaction, including transaction ID, operation ID, accounting date, value date, type, amount, currency, and description.

### Class Structure

- **Package**: `com.service.fabrickapi.entity`
- **Author**: Berk Delibalta

### Fields

| Field             | Description                                |
|-------------------|--------------------------------------------|
| id                | Unique identifier for the transaction entity (auto-generated). |
| transactionId     | Transaction ID.                            |
| operationId       | Operation ID.                              |
| accountingDate    | Accounting date of the transaction.        |
| valueDate         | Value date of the transaction.             |
| type              | Type of the transaction.                   |
| amount            | Amount of the transaction.                 |
| currency          | Currency of the transaction.               |
| description       | Description of the transaction.           |

### Database Mapping

- **Table Name**: transactions
- **Primary Key**: id
- **Indexes**: Unique indexes on transaction_id and operation_id columns.

### Constructors

1. **Default Constructor**: Initializes an empty `TransactionEntity` instance.
2. **Parameterized Constructor**: Initializes the `TransactionEntity` with values for each field.

### Methods

1. **Getters and Setters**: Accessor and mutator methods for each field.
2. **Equals and HashCode**: Overridden methods to ensure proper comparison and hashing based on the id, transactionId, and operationId fields.

### Table Definition

```sql
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    transaction_id BIGINT UNIQUE NOT NULL,
    operation_id BIGINT UNIQUE NOT NULL,
    accounting_date DATE NOT NULL,
    value_date DATE NOT NULL,
    type VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);
```

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