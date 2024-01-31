
---
## MODEL PACKAGE CLASSES DOCUMENTATION

### Table of Contents

1. [AccountBalanceDTO ](#accountbalancedto-documentation)
2. [AccountDTO ](#accountdto-documentation)
3. [AddressDTO ](#addressdto-documentation)
4. [AmountDTO ](#amountdto-documentation)
5. [CreditorDTO ](#creditordto-documentation)
6. [DebtorDTO ](#debtordto-documentation)
7. [FeeDTO ](#feedto-documentation)
8. [LegalPersonBeneficiaryDTO ](#legalpersonbeneficiarydto-documentation)
9. [LoanTransferDTO ](#loantransferdto-documentation)
10. [NaturalPersonBeneficiaryDTO ](#naturalpersonbeneficiarydto-documentation)
11. [TaxReliefDTO ](#taxreliefdto-documentation)
12. [TransactionDTO ](#transactiondto-documentation)
13. [ErrorMessage Documentation](#errormessage-documentation)
14. [ErrorMessages Enum Documentation](#errormessages-enum-documentation)
15. [LoanTransferRequest Documentation](#loantransferrequest-documentation)
16. [AccountBalanceRest Documentation](#accountbalancerest-documentation)
17. [LoanTransferRest Documentation](#loantransferrest-documentation)
18. [TransactionRest Documentation](#transactionrest-documentation)

---

## [AccountBalanceDTO Documentation](#accountbalancedto-documentation)

## Overview

The `AccountBalanceDTO` class is a Java record representing the data transfer object (DTO) for account balance information. It encapsulates details related to an account's balance.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta
- **Copyright**: © 2024 Berk Delibalta

## Fields

1. **accountId**: Unique identifier for the account.
2. **iban**: International Bank Account Number.
3. **abiCode**: ABI Code.
4. **cabCode**: CAB Code.
5. **countryCode**: Country Code.
6. **internationalCin**: International CIN (Customer Identification Number).
7. **nationalCin**: National CIN (Customer Identification Number).
8. **account**: Account number.
9. **alias**: Alias for the account.
10. **productName**: Product name associated with the account.
11. **holderName**: Name of the account holder.
12. **activatedDate**: Date when the account was activated.
13. **currency**: Currency of the account.

## Constructor

- **Record Constructor**: Initializes the `AccountBalanceDTO` with values for each field.

## Methods

1. **Getters**: Automatically generated accessor methods for each field.

### AccountBalanceDTO Table

| Field              | Type   | Description                                  |
|--------------------|--------|----------------------------------------------|
| accountId          | String | Unique identifier for the account.            |
| iban               | String | International Bank Account Number.           |
| abiCode            | String | ABI Code.                                    |
| cabCode            | String | CAB Code.                                    |
| countryCode        | String | Country Code.                                |
| internationalCin   | String | International CIN (Customer Identification Number).|
| nationalCin        | String | National CIN (Customer Identification Number).    |
| account            | String | Account number.                             |
| alias              | String | Alias for the account.                       |
| productName        | String | Product name associated with the account.   |
| holderName         | String | Name of the account holder.                 |
| activatedDate      | String | Date when the account was activated.        |
| currency           | String | Currency of the account.                    |


---

## [AccountDTO Documentation](#accountdto-documentation)

## Overview

The `AccountDTO` class is a Java record representing the data transfer object (DTO) for account information. It encapsulates details related to an account, including the account code.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta

## Fields

1. **accountCode**: Account code.

## Constructor

- **Record Constructor**: Initializes the `AccountDTO` with the value for the `accountCode` field.

## Methods

1. **Getters**: Automatically generated accessor method for the `accountCode` field.

### AccountDTO Table

| Field       | Type   | Description        |
|-------------|--------|--------------------|
| accountCode | String | Account code.      |

---

## [AddressDTO Documentation](#addressdto-documentation)

## Overview

The `AddressDTO` class is a Java record representing the data transfer object (DTO) for address information. It encapsulates details related to an address, including the address, city, and country code.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta

## Fields

1. **address**: Address.
2. **city**: City.
3. **countryCode**: Country code.

## Constructor

- **Record Constructor**: Initializes the `AddressDTO` with values for the `address`, `city`, and `countryCode` fields.

## Methods

1. **Getters**: Automatically generated accessor methods for the `address`, `city`, and `countryCode` fields.

### AddressDTO Table

| Field       | Type   | Description       |
|-------------|--------|-------------------|
| address     | String | Address.          |
| city        | String | City.             |
| countryCode | String | Country code.     |


---

## [AmountDTO Documentation](#addressdto-documentation)

## Overview

The `AmountDTO` class is a Java record representing the data transfer object (DTO) for amount information. It encapsulates details related to amounts, including debtor and creditor amounts, currencies, creditor currency date, and exchange rate.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta

## Fields

1. **debtorAmount**: Debtor amount.
2. **debtorCurrency**: Debtor currency.
3. **creditorAmount**: Creditor amount.
4. **creditorCurrency**: Creditor currency.
5. **creditorCurrencyDate**: Creditor currency date.
6. **exchangeRate**: Exchange rate.

## Constructor

- **Record Constructor**: Initializes the `AmountDTO` with values for each field.

## Methods

1. **Getters**: Automatically generated accessor methods for each field.

### AmountDTO Table

| Field                  | Type          | Description                                   |
|------------------------|---------------|-----------------------------------------------|
| debtorAmount           | BigInteger    | Debtor amount.                                |
| debtorCurrency         | String        | Debtor currency.                              |
| creditorAmount         | BigInteger    | Creditor amount.                              |
| creditorCurrency      | String        | Creditor currency.                            |
| creditorCurrencyDate  | Date          | Creditor currency date.                       |
| exchangeRate           | int           | Exchange rate.                                |

---

## [CreditorDTO Documentation](#creditor-documentation)

## Overview

The `CreditorDTO` class is a Java record representing the data transfer object (DTO) for creditor information. It encapsulates details related to a creditor, including the name, account information, and address information.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta

## Fields

1. **name**: Name

of the creditor.
2. **accountDTO**: AccountDTO associated with the creditor.
3. **addressDTO**: AddressDTO associated with the creditor.

## Constructor

- **Record Constructor**: Initializes the `CreditorDTO` with values for each field.

## Methods

1. **Getters**: Automatically generated accessor methods for each field.

### CreditorDTO Table

| Field       | Type         | Description                                      |
|-------------|--------------|--------------------------------------------------|
| name        | String       | Name of the creditor.                            |
| accountDTO  | AccountDTO    | AccountDTO associated with the creditor.         |
| addressDTO  | AddressDTO    | AddressDTO associated with the creditor.         |

---

## [DebtorDTO Documentation](#debtor-documentation)

## Overview

The `DebtorDTO` class is a Java record representing the data transfer object (DTO) for debtor information. It encapsulates details related to a debtor, including the name and account information.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta

## Fields

1. **name**: Name of the debtor.
2. **accountDTO**: AccountDTO associated with the debtor.

## Constructor

- **Record Constructor**: Initializes the `DebtorDTO` with values for each field.

## Methods

1. **Getters**: Automatically generated accessor methods for each field.

### DebtorDTO Table

| Field       | Type         | Description                           |
|-------------|--------------|---------------------------------------|
| name        | String       | Name of the debtor.                    |
| accountDTO  | AccountDTO    | AccountDTO associated with the debtor.|


---

## [FeeDTO Documentation](#feedto-documentation)

## Overview

The `FeeDTO` class is a Java record representing the data transfer object (DTO) for fee information. It encapsulates details related to a fee, including the fee code, description, amount, and currency.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta

## Fields

1. **feeCode**: Fee code.
2. **description**: Description of the fee.
3. **amount**: Fee amount.
4. **currency**: Currency of the fee.

## Constructor

- **Record Constructor**: Initializes the `FeeDTO` with values for each field.

## Methods

1. **Getters**: Automatically generated accessor methods for each field.

### FeeDTO Table

| Field       | Type          | Description          |
|-------------|---------------|----------------------|
| feeCode     | String        | Fee code.            |
| description | String        | Description of fee.  |
| amount      | BigInteger    | Fee amount.          |
| currency    | String        | Currency of fee.     |

---

## [LegalPersonBeneficiaryDTO Documentation](#legalpersonbeneficiartdto-documentation)

## Overview

The `LegalPersonBeneficiaryDTO` class is a Java record representing the data transfer object (DTO) for legal person beneficiary information. It encapsulates details related to a legal person beneficiary, including fiscal codes.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta

## Fields

1. **fiscalCode**: Fiscal code of the legal person beneficiary.
2. **legalRepresentativeFiscalCode**: Fiscal code of the legal representative.

## Constructor

- **Record Constructor**: Initializes the `LegalPersonBeneficiaryDTO` with values for each field.

## Methods

1. **Getters**: Automatically generated accessor methods for each field.

### LegalPersonBeneficiaryDTO Table

| Field                       | Type   | Description                                      |
|-----------------------------|--------|--------------------------------------------------|
| fiscalCode                  | String | Fiscal code of the legal person beneficiary.     |
| legalRepresentativeFiscalCode | String | Fiscal code of the legal representative.       |

---

## [LoanTransferDTO Documentation](#loantransafer-documentation)

## Overview

The `LoanTransferDTO` class is a Java record representing the data transfer object (DTO) for loan transfer information. It encapsulates details related to a loan transfer, including transfer ID, status, direction, creditor information, address, debtor information, CRO, URI, TRN, description, creation date, accounting date, debtor value date, creditor value date, amount, urgency, instant status, fee type, fee account ID, fees, and tax relief status.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta

## Fields

1. **moneyTransferId**: Money transfer ID.
2. **status**: Status of the loan transfer.
3. **direction**: Transfer direction.
4. **creditor**: Creditor information.
5. **addressDTO**: Address information associated with the loan transfer.
6. **debtorDTO**: Debtor information associated with the loan transfer.
7. **cro**: CRO (Codice Riferimento Operazione).
8. **uri**: Uniform Resource Identifier.
9. **trn**: Transaction Reference Number.
10. **description**: Description of the loan transfer.
11. **createdDatetime**: Creation date and time.
12. **accountedDatetime**: Accounting date and time.
13. **debtorValueDate**: Debtor value date.
14. **creditorValueDate**: Creditor value date.
15. **amountDTO**: Amount information associated with the loan transfer.
16. **isUrgent**: Urgency status.
17. **isInstant**: Instant status.
18. **feeType**: Fee type.
19. **feeAccountId**: Fee account ID.
20. **feeDTOs**: List of fee information associated with the loan transfer.
21. **hasTaxRelief**: Tax relief status.

## Constructor

- **Record Constructor**: Initializes the `LoanTransferDTO` with values for each field.

## Methods

1. **Getters**: Automatically generated accessor methods for each field.

### LoanTransferDTO Table

| Field                 | Type          | Description                                       |
|-----------------------|---------------|---------------------------------------------------|
| moneyTransferId       | String        | Money transfer ID.                                |
| status                | String        | Status of the loan transfer.                      |
| direction             | String        | Transfer direction.                               |
| creditor              | CreditorDTO    | Creditor information.                             |
| addressDTO            | AddressDTO     | Address information associated with the loan transfer.|
| debtorDTO             | DebtorDTO      | Debtor information associated with the loan transfer.|
| cro                   | String        | CRO (Codice Riferimento Operazione).               |
| uri                   | String        | Uniform Resource
| trn                   | String        | Transaction Reference Number.                     |
| description           | String        | Description of the loan transfer.                 |
| createdDatetime       | String        | Creation date and time.                           |
| accountedDatetime     | String        | Accounting date and time.                         |
| debtorValueDate       | Date          | Debtor value date.                                |
| creditorValueDate     | Date          | Creditor value date.                              |
| amountDTO             | AmountDTO      | Amount information associated with the loan transfer.|
| isUrgent              | boolean       | Urgency status.                                   |
| isInstant             | boolean       | Instant status.                                  |
| feeType               | String        | Fee type.                                         |
| feeAccountId          | String        | Fee account ID.                                   |
| feeDTOs               | List<FeeDTO>  | List of fee information associated with the loan transfer.|
| hasTaxRelief          | boolean       | Tax relief status.                                |

---

## [NaturalPersonBeneficiaryDTO Documentation](#naturalpersonbeneficiarydto-documentation)

## Overview

The `NaturalPersonBeneficiaryDTO` class is a Java record representing the data transfer object (DTO) for natural person beneficiary information. It encapsulates details related to a natural person beneficiary, including multiple fiscal codes.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta

## Fields

1. **fiscalCode1**: First fiscal code of the natural person beneficiary.
2. **fiscalCode2**: Second fiscal code of the natural person beneficiary.
3. **fiscalCode3**: Third fiscal code of the natural person beneficiary.
4. **fiscalCode4**: Fourth fiscal code of the natural person beneficiary.
5. **fiscalCode5**: Fifth fiscal code of the natural person beneficiary.

## Constructor

- **Record Constructor**: Initializes the `NaturalPersonBeneficiaryDTO` with values for each field.

## Methods

1. **Getters**: Automatically generated accessor methods for each field.

### NaturalPersonBeneficiaryDTO Table

| Field       | Type   | Description                                          |
|-------------|--------|------------------------------------------------------|
| fiscalCode1 | String | First fiscal code of the natural person beneficiary. |
| fiscalCode2 | String | Second fiscal code of the natural person beneficiary.|
| fiscalCode3 | String | Third fiscal code of the natural person beneficiary. |
| fiscalCode4 | String | Fourth fiscal code of the natural person beneficiary.|
| fiscalCode5 | String | Fifth fiscal code of the natural person beneficiary. |

---

## [TaxReliefDTO Documentation](#taxreliefdto-documentation)

## Overview

The `TaxReliefDTO` class is a Java record representing the data transfer object (DTO) for tax relief information. It encapsulates details related to tax relief, including tax relief ID, condo upgrade status, creditor fiscal code, beneficiary type, natural person beneficiary information, and legal person beneficiary information.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta

## Fields

1. **taxReliefId**: Tax relief ID.
2. **isCondoUpgrade**: Condo upgrade status.
3. **creditorFiscalCode**: Creditor fiscal code.
4. **beneficiaryType**: Beneficiary type.
5. **naturalPersonBeneficiaryDTO**: NaturalPersonBeneficiaryDTO associated with the tax relief.
6. **legalPersonBeneficiaryDTO**: LegalPersonBeneficiaryDTO associated with the tax relief.

## Constructor

- **Record Constructor**: Initializes the `TaxReliefDTO` with values for each field.

## Methods

1. **Getters**: Automatically generated accessor methods for each field.

### TaxReliefDTO Table

| Field                         | Type                       | Description                                               |
|-------------------------------|----------------------------|-----------------------------------------------------------|
| taxReliefId                   | String                     | Tax relief ID.                                            |
| isCondoUpgrade                | Boolean                    | Condo upgrade status.                                     |
| creditorFiscalCode            | String                     | Creditor fiscal code.                                     |
| beneficiaryType               | String                     | Beneficiary type.                                         |
| naturalPersonBeneficiaryDTO    | NaturalPersonBeneficiaryDTO | Natural person beneficiary information associated with the tax relief.|
| legalPersonBeneficiaryDTO      | LegalPersonBeneficiaryDTO   | Legal person beneficiary information associated with the tax relief.  |

---

## [TransactionDTO Documentation](#transactiondto-documentation)

## Overview

The `TransactionDTO` class is a Java record representing the data transfer object (DTO) for transaction information. It encapsulates details related to a transaction, including transaction ID, operation ID, accounting date, value date, type, amount, currency, and description.

## Class Structure

- **Package**: `com.service.fabrickapi.model.dto`
- **Author**: Berk Delibalta

## Fields

1. **transactionId**: Transaction ID.
2. **operationId**: Operation ID.
3. **accountingDate**: Accounting date.
4. **valueDate**: Value date.
5. **type**: Type of the transaction.
6. **amount**: Transaction amount.
7. **currency**: Currency of the transaction.
8. **description**: Description of the transaction.

## Constructor

- **Record Constructor**: Initializes the `TransactionDTO` with values for each field.

## Methods

1. **Getters**: Automatically generated accessor methods for each field.

### TransactionDTO Table

| Field         | Type          | Description                 |
|---------------|---------------|-----------------------------|
| transactionId | String        | Transaction ID.             |
| operationId   | String        | Operation ID.               |
| accountingDate| Date          | Accounting date.            |
| valueDate      | Date          | Value date.                 |
| type          | Object         | Type of the transaction.    |
| amount        | BigDecimal    | Transaction amount.         |
| currency      | String        | Currency of the transaction.|
| description   | String        | Description of the transaction.|

---


## [ErrorMessage Documentation](#errormessage-documentation)

### Overview

The `ErrorMessage` class is a Java record representing an error message along with the timestamp. It is used to encapsulate information about an error, including the error message and the timestamp when the error occurred.

### Class Structure

- **Package**: `com.service.fabrickapi.model.error`
- **Author**: Berk Delibalta

### Fields

1. **message**: Error message.
2. **date**: Timestamp indicating when the error occurred.

### Constructor

- **Record Constructor**: Initializes the `ErrorMessage` with values for each field.

### Methods

1. **Getters**: Automatically generated accessor methods for each field.

### ErrorMessage Table

| Field   | Type   | Description                 |
|---------|--------|-----------------------------|
| message | String | Error message.              |
| date    | Date   | Timestamp of the error.     |


---

## [ErrorMessages Enum Documentation](#errormessages-enum-documentation)

### Overview

The `ErrorMessages` enum provides predefined error messages as constants. It is used to represent standard error messages for different HTTP status codes.

### Class Structure

- **Package**: `com.service.fabrickapi.model.error`
- **Author**: Berk Delibalta

### Enum Constants

1. **INTERNAL_SERVER_ERROR**: Represents the 500 Internal Server Error.
2. **RECORD_NOT_FOUND**: Represents the 404 Record Not Found.
3. **INVALID_JSON_ERROR**: Represents the 500 Invalid JSON Error.
4. **UNAUTHORIZED_ERROR**: Represents the 401 Unauthorized Error.
5. **BAD_REQUEST_ERROR**: Represents the 400 Bad Request.

### Methods

1. **getMessage()**: Returns the error message associated with each enum constant.
2. **setMessage()**: Sets the error message for each enum constant.

### ErrorMessages Enum Table

| Enum Constant          | Message                       |
|------------------------|-------------------------------|
| INTERNAL_SERVER_ERROR   | 500 Internal Server Error     |
| RECORD_NOT_FOUND        | 404 Record Not Found          |
| INVALID_JSON_ERROR      | 500 Invalid JSON Error        |
| UNAUTHORIZED_ERROR      | 401 Unauthorized Error        |
| BAD_REQUEST_ERROR       | 400 Bad Request               |


---

## [LoanTransferRequest Documentation](#loantransferrequest-documentation)

### Overview

The `LoanTransferRequest` class is a Java record representing a request for a loan transfer. It encapsulates details about the creditor, execution date, description, amount, and currency for the loan transfer.

### Class Structure

- **Package**: `com.service.fabrickapi.model.request`
- **Author**: Berk Delibalta

### Fields

1. **creditorDTO**: Creditor details.
2. **executionDate**: Execution date for the loan transfer.
3. **description**: Description of the loan transfer.
4. **amount**: Amount of the loan transfer.
5. **currency**: Currency of the loan transfer.

### Constructor

- **Record Constructor**: Initializes the `LoanTransferRequest` with values for each field.

### Methods

1. **Getters**: Automatically generated accessor methods for each field.

### LoanTransferRequest Table

| Field           | Type           | Description                       |
|-----------------|----------------|-----------------------------------|
| creditorDTO     | CreditorDTO    | Creditor details.                 |
| executionDate   | Date           | Execution date for the loan transfer. |
| description     | String         | Description of the loan transfer.  |
| amount          | BigInteger     | Amount of the loan transfer.       |
| currency        | String         | Currency of the loan transfer.     |


## [AccountBalanceRest Documentation](#accountbalancerest-documentation)


### Overview

The `AccountBalanceRest` class is a Java record representing the data transfer object (DTO) for account balance information in a REST context. It encapsulates details related to an account's balance, including account ID, IBAN, ABI code, CAB code, country code, international CIN, national CIN, account number, alias, product name, holder name, activation date, and currency.

### Class Structure

- **Package**: `com.service.fabrickapi.model.rest`
- **Author**: Berk Delibalta

### Fields

1. **accountId**: Account ID.
2. **iban**: International Bank Account Number (IBAN).
3. **abiCode**: ABI (ABI Code).
4. **cabCode**: CAB (CAB Code).
5. **countryCode**: Country code.
6. **internationalCin**: International CIN.
7. **nationalCin**: National CIN.
8. **account**: Account number.
9. **alias**: Alias for the account.
10. **productName**: Product name associated with the account.
11. **holderName**: Holder name.
12. **activatedDate**: Activation date of the account.
13. **currency**: Currency of the account.

### Constructor

- **Record Constructor**: Initializes the `AccountBalanceRest` with values for each field.

### Methods

1. **Getters**: Automatically generated accessor methods for each field.

### AccountBalanceRest Table

| Field           | Type   | Description                       |
|-----------------|--------|-----------------------------------|
| accountId       | Long   | Account ID.                       |
| iban            | String | International Bank Account Number (IBAN). |
| abiCode         | Long   | ABI (ABI Code).                   |
| cabCode         | Long   | CAB (CAB Code).                   |
| countryCode     | String | Country code.                     |
| internationalCin| int    | International CIN.                |
| nationalCin     | String | National CIN.                     |
| account         | Long   | Account number.                   |
| alias           | String | Alias for the account.            |
| productName     | String | Product name associated with the account. |
| holderName      | String | Holder name.                      |
| activatedDate   | Date   | Activation date of the account.   |
| currency        | String | Currency of the account.          |


---

## [LoanTransferRest Documentation](#loantransferrest-documentation)

### Overview

The `LoanTransferRest` class is a Java record representing the data transfer object (DTO) for loan transfer information in a REST context. It encapsulates details related to a loan transfer, including money transfer ID, status, direction, creditor details, address details, debtor details, CRO, URI, TRN, description, created date and time, accounted date and time, debtor value date, creditor value date, amount details, urgency, instant status, fee type, fee account ID, list of fee details, and tax relief status.

### Class Structure

- **Package**: `com.service.fabrickapi.model.rest`
- **Author**: Berk Delibalta

### Fields

1. **moneyTransferId**: Money transfer ID.
2. **status**: Status of the loan transfer.
3. **direction**: Direction of the loan transfer.
4. **creditor**: Creditor details.
5. **addressDTO**: Address details.
6. **debtorDTO**: Debtor details.
7. **cro**: CRO (Central Registration Office) number.
8. **uri**: URI (Uniform Resource Identifier) associated with the loan transfer.
9. **trn**: TRN (Transaction Reference Number) associated with the loan transfer.
10. **description**: Description of the loan transfer.
11. **createdDatetime**: Date and time when the loan transfer was created.
12. **accountedDatetime**: Date and time when the loan transfer was accounted.
13. **debtorValueDate**: Value date for the debtor.
14. **creditorValueDate**: Value date for the creditor.
15. **amountDTO**: Amount details.
16. **isUrgent**: Urgency status of the loan transfer.
17. **isInstant**: Instant status of the loan transfer.
18. **feeType**: Fee type associated with the loan transfer.
19. **feeAccountId**: Fee account ID.
20. **feeDTOs**: List of fee details.
21. **hasTaxRelief**: Tax relief status.

### Constructor

- **Record Constructor**: Initializes the `LoanTransferRest` with values for each field.

### Methods

1. **Getters**: Automatically generated accessor methods for each field.

### LoanTransferRest Table

| Field                  | Type         | Description                           |
|------------------------|--------------|---------------------------------------|
| moneyTransferId        | Long         | Money transfer ID.                    |
| status                 | String       | Status of the loan transfer.          |
| direction              | String       | Direction of the loan transfer.       |
| creditor               | CreditorDTO  | Creditor details.                     |
| addressDTO             | AddressDTO   | Address details.                      |
| debtorDTO              | DebtorDTO    | Debtor details.                       |
| cro                    | Long         | CRO (Central Registration Office) number. |
| uri                    | String       | URI associated with the loan transfer. |
| trn                    | String       | TRN associated with the loan transfer. |
| description            | String       | Description of the loan transfer.     |
| createdDatetime        | String       | Date and time when the loan transfer was created. |
| accountedDatetime      | String       | Date and time when the loan transfer was accounted. |
| debtorValueDate        | Date         | Value date for the debtor.             |
| creditorValueDate      | Date         | Value date for the creditor.           |
| amountDTO              | AmountDTO    | Amount details.                       |
| isUrgent               | boolean      | Urgency status of the loan transfer.  |
| isInstant              | boolean      | Instant status of the loan transfer. |
| feeType                | String       | Fee type associated with the loan transfer. |
| feeAccountId           | Long         | Fee account ID.                       |
| feeDTOs                | List<FeeDTO> | List of fee details.                  |
| hasTaxRelief           | boolean      | Tax relief status.                    |


---

## [TransactionRest Documentation](#transactionrest-documentation)

### Overview

The `TransactionRest` class is a Java record representing the data transfer object (DTO) for transaction information in a REST context. It encapsulates details related to a transaction, including transaction ID, operation ID, accounting date, value date, type, amount, currency, and description.

### Class Structure

- **Package**: `com.service.fabrickapi.model.rest`
- **Author**: Berk Delibalta

### Fields

1. **transactionId**: Transaction ID.
2. **operationId**: Operation ID.
3. **accountingDate**: Accounting date of the transaction.
4. **valueDate**: Value date of the transaction.
5. **type**: Type of the transaction.
6. **amount**: Amount of the transaction.
7. **currency**: Currency of the transaction.
8. **description**: Description of the transaction.

### Constructor

- **Record Constructor**: Initializes the `TransactionRest` with values for each field.

### Methods

1. **Getters**: Automatically generated accessor methods for each field.

### TransactionRest Table

| Field           | Type           | Description                       |
|-----------------|----------------|-----------------------------------|
| transactionId   | Long           | Transaction ID.                   |
| operationId     | Long           | Operation ID.                     |
| accountingDate  | Date           | Accounting date of the transaction. |
| valueDate       | Date           | Value date of the transaction.    |
| type            | Object         | Type of the transaction.          |
| amount          | BigDecimal     | Amount of the transaction.        |
| currency        | String         | Currency of the transaction.      |
| description     | String         | Description of the transaction.   |


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

**Note:** Please refer to the actual implementation and configuration files for additional details, as the provided documentation is a high-level overview.