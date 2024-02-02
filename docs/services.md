# SERVICE PACKAGE CLASSES DOCUMENTATION

### Table of Contents
1. [Account Service Implementation](#account-service-implementation)
2. [Fabrick REST Service Implementation](#fabrick-rest-service-implementation)
3. [Transfer Service Implementation](#transfer-service-implementation)

---

# [Account Service Implementation](#account-service-implementation)

## Overview

The `AccountServiceImpl` class is an implementation of the `AccountService` interface, providing functionality related to retrieving account-related information. It interfaces with the Fabrick REST API to retrieve account balances and transactions, and the results are transformed using mappers before being stored in the database.

## Class Structure

| Package | Author          | Copyright                             |
|---------|-----------------|---------------------------------------|
| `com.service.fabrickapi.service.implementation` | Berk Delibalta | © 2024 Berk Delibalta |

## Dependencies

| Dependency                                          | Description                                          |
|-----------------------------------------------------|------------------------------------------------------|
| `com.service.fabrickapi.configuration.Credentials` | Configuration class for Fabrick API credentials.      |
| `com.service.fabrickapi.entity.TransactionEntity`    | Entity class representing transactions.              |
| `com.service.fabrickapi.exception.AccountServiceException` | Exception related to account-related services.    |
| `com.service.fabrickapi.exception.FabrickRestServiceException` | Exception related to Fabrick REST API services. |
| `com.service.fabrickapi.exception.TransferServiceException` | Exception related to fund transfer services.   |
| `com.service.fabrickapi.mapper.AccountBalanceRestMapper` | Mapper class for transforming account balance responses. |
| `com.service.fabrickapi.mapper.TransactionRestMapper` | Mapper class for transforming transaction responses. |
| `com.service.fabrickapi.model.dto.AccountBalanceDTO` | DTO class representing account balance information. |
| `com.service.fabrickapi.model.dto.TransactionDTO` | DTO class representing transaction information.   |
| `com.service.fabrickapi.model.error.ErrorMessages` | Class containing error messages for account-related services. |
| `com.service.fabrickapi.model.request.LoanTransferRequest` | Request model for initiating loan transfers.     |
| `com.service.fabrickapi.model.rest.AccountBalanceRest` | Model class representing RESTful account balance information. |
| `com.service.fabrickapi.model.rest.LoanTransferRest` | Model class representing RESTful loan transfer information. |
| `com.service.fabrickapi.model.rest.TransactionRest` | Model class representing RESTful transaction information. |
| `com.service.fabrickapi.shared.Utils`               | Shared utility methods for various functionalities. |
| `org.springframework.cache.annotation.CacheEvict`    | Annotation for evicting entries from a cache.        |
| `org.springframework.cache.annotation.Cacheable`     | Annotation for caching the result of a method.       |
| `org.springframework.stereotype.Service`             | Annotation indicating that a class is a service.     |

## Methods

| Method                                      | Description                                                                                                                                                             |
|---------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `retrieveAccountBalanceRequest(String accountId)` | Retrieves the account balance by its ID. <br> - Annotations: <br>   - `@Cacheable(value = "accountBalances", key = "#accountId")` <br>   - `@Transactional` <br>   - `@SendTo("account-balance")` |
| `retrieveAccountBalance(String accountId)` | Retrieves the account balance by its ID. <br> - Parameters: <br>   - `accountId` - The ID of the account. <br> - Returns: <br>   - An `AccountBalanceRest` object representing the account balance. |
| `retrieveTransactionsRequest(String accountId, String fromDate, String toDate)` | Retrieves transactions for an account within a specified date range. <br> - Annotations: <br>   - `@Cacheable(value = "transactions", key = "#accountId + #fromDate + #toDate")` <br>   - `@Transactional` <br>   - `@SendTo("transactions")` |
| `retrieveTransactions(String accountId, String fromDate, String toDate)` | Retrieves transactions for an account within a specified date range. <br> - Parameters: <br>   - `accountId` - The ID of the account. <br>   - `fromDate` - The start date of the date range. <br>   - `toDate` - The end date of the date range. <br> - Returns: <br>   - A list of `TransactionRest` objects representing the transactions. |
| `initiateLoanTransferRequest(LoanTransferRequest request)` | Initiates a loan transfer. <br> - Annotations: <br>   - `@Transactional` <br>   - `@SendTo("loan-transfer")` |
| `initiateLoanTransfer(LoanTransferRequest request)` | Initiates a loan transfer. <br> - Parameters: <br>   - `request` - The loan transfer request. <br> - Returns: <br>   - An `LoanTransferRest` object representing the loan transfer. |

## Code Example

```java
@Service
public class AccountServiceImpl implements AccountService {

    @Override
    @Cacheable(key = "accountId", value = "account")
    public AccountBalanceRest getAccountBalance(Long accountId) {
        return fabrickRestService
                .getAccountBalance(accountId)
                .map(accountBalancerRestMapper)
                .orElseThrow(() -> {
                    LOG.error("ACCOUNT BALANCE DTO OBJECT IS NULL");
                    return new AccountServiceException(RECORD_NOT_FOUND.getMessage());
                });
    }


    @Override
    @Cacheable(key = "accountId", value = "transactions")
    public List<TransactionRest> getAccountTransactions(Long accountId, String fromAccountingDate, String toAccountingDate) {
        return fabrickRestService
                .getAccountTransactions(accountId, fromAccountingDate, toAccountingDate)
                .orElseThrow(() -> new AccountServiceException(RECORD_NOT_FOUND.getMessage()))
                .parallelStream()
                .peek(transactionDTO -> LOG.info("MAPPING TRANSACTION DTO OBJECT {}", transactionDTO))
                .map(transactionRestMapper)
                .peek(transactionRest -> LOG.info("RETURNING TRANSACTION REST OBJECT {}", transactionRest))
                .map(accountTransactionSaveMapper)
                .sorted(Comparator.comparing(TransactionRest::accountingDate).thenComparing(TransactionRest::valueDate).reversed())
                .limit(30)
                .toList();
    }

    // Other methods and implementations are omitted for brevity
}
```

---

# [Fabrick REST Service Implementation](#fabrick-rest-service-implementation)

## Overview

The `FabrickRestServiceImpl` class is responsible for interacting with the Fabrick REST API. It provides implementations for retrieving account balances, transactions, and executing fund transfers.

## Class Structure

| Package | Author          | Copyright                             |
|---------|-----------------|---------------------------------------|
| `com.service.fabrickapi.service.implementation` | Berk Delibalta | © 2024 Berk Delibalta |

## Dependencies

| Dependency                                          | Description                                          |
|-----------------------------------------------------|------------------------------------------------------|
| `com.service.fabrickapi.configuration.Credentials` | Configuration class for Fabrick API credentials.      |
| `com.service.fabrickapi.exception.FabrickRestServiceException` | Exception related to Fabrick REST API services. |
| `com.service.fabrickapi.mapper.AccountBalanceRestMapper` | Mapper class for transforming account balance responses. |
| `com.service.fabrickapi.mapper.TransactionRestMapper` | Mapper class for transforming transaction responses. |
| `com.service.fabrickapi.model.dto.AccountBalanceDTO` | DTO class representing account balance information. |
| `com.service.fabrickapi.model.dto.TransactionDTO` | DTO class representing transaction information.   |
| `com.service.fabrickapi.model.error.ErrorMessages` | Class containing error messages for Fabrick REST API services. |
| `com.service.fabrickapi.model.request.LoanTransferRequest` | Request model for initiating loan transfers.     |
|`com.service.fabrickapi.model.rest.AccountBalanceRest` | Model class representing RESTful account balance information. |
| `com.service.fabrickapi.model.rest.LoanTransferRest` | Model class representing RESTful loan transfer information. |
| `com.service.fabrickapi.model.rest.TransactionRest` | Model class representing RESTful transaction information. |
| `com.service.fabrickapi.shared.Utils`               | Shared utility methods for various functionalities. |
| `org.springframework.beans.factory.annotation.Autowired` | Annotation for automatic dependency injection.     |
| `org.springframework.cache.annotation.Cacheable`     | Annotation for caching the result of a method.       |
| `org.springframework.context.annotation.Primary`    | Annotation indicating that a bean should be given preference when multiple candidates are qualified to autowire a single-valued dependency. |
| `org.springframework.http.ResponseEntity`             | Represents an HTTP response.                         |
| `org.springframework.stereotype.Service`             | Annotation indicating that a class is a service.     |
| `org.springframework.web.client.RestTemplate`       | Represents a template for making RESTful HTTP requests. |

## Methods

| Method                                      | Description                                                                                                                                                             |
|---------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `retrieveAccountBalance(String accountId)` | Retrieves the account balance from the Fabrick REST API. <br> - Parameters: <br>   - `accountId` - The ID of the account. <br> - Returns: <br>   - An `AccountBalanceDTO` object representing the account balance. |
| `retrieveTransactions(String accountId, String fromDate, String toDate)` | Retrieves transactions for an account within a specified date range from the Fabrick REST API. <br> - Parameters: <br>   - `accountId` - The ID of the account. <br>   - `fromDate` - The start date of the date range. <br>   - `toDate` - The end date of the date range. <br> - Returns: <br>   - A list of `TransactionDTO` objects representing the transactions. |
| `initiateLoanTransfer(LoanTransferRequest request)` | Initiates a loan transfer through the Fabrick REST API. <br> - Parameters: <br>   - `request` - The loan transfer request. <br> - Returns: <br>   - A `LoanTransferDTO` object representing the initiated loan transfer. |

## Code Example

```java
@Service
public class FabrickRestServiceImpl implements FabrickRestService {

    // Other dependencies and constructor are omitted for brevity

    @Override
    public Optional<AccountBalanceDTO> getAccountBalance(Long accountId) {
        try {
            HttpHeaders headers = utils.headers();
            HttpEntity<?> entity = new HttpEntity<>(headers);
            String uri = utils.buildUrl(credentials.balanceURL(), accountId);

            LOG.info("FETCHING ACCOUNT BALANCE FOR {}", accountId);

            ResponseEntity<String> response = utils.getStringResponseEntity(uri, entity, HttpMethod.GET);

            return Optional.ofNullable(utils.fromJson(response.getBody(), AccountBalanceDTO.class));
        } catch (Exception e) {
            LOG.error("CANNOT FETCH ACCOUNT BALANCE FOR {}", accountId);
            throw new FabrickRestServiceException(e.getMessage());
        }

    }

    // Other methods and implementations are omitted for brevity
}
```

---

# [Transfer Service Implementation](#transfer-service-implementation)

## Overview

The `TransferServiceImpl` class is responsible for transferring loans to specified creditor accounts. It interfaces with the Fabrick REST API to execute loan transfers.

## Class Structure

| Package | Author          | Copyright                             |
|---------|-----------------|---------------------------------------|
| `com.service.fabrickapi.service.implementation` | Berk Delibalta | © 2024 Berk Delibalta |

## Dependencies

| Dependency                                          | Description                                          |
|-----------------------------------------------------|------------------------------------------------------|
| `com.service.fabrickapi.configuration.Credentials` | Configuration class for Fabrick API credentials.      |
| `com.service.fabrickapi.exception.FabrickRestServiceException` | Exception related to Fabrick REST API services. |
| `com.service.fabrickapi.exception.TransferServiceException` | Exception related to fund transfer services.   |
| `com.service.fabrickapi.mapper.LoanTransferRestMapper` | Mapper class for transforming loan transfer responses. |
| `com.service.fabrickapi.model.dto.LoanTransferDTO` | DTO class representing loan transfer information.  |
| `com.service.fabrickapi.model.error.ErrorMessages` | Class containing error messages for fund transfer services. |
| `com.service.fabrickapi.model.request.LoanTransferRequest` | Request model for initiating loan transfers.     |
| `com.service.fabrickapi.model.rest.LoanTransferRest` | Model class representing RESTful loan transfer information. |
| `com.service.fabrickapi.shared.Utils`               | Shared utility methods for various functionalities. |
| `org.springframework.beans.factory.annotation.Autowired` | Annotation for automatic dependency injection.     |
| `org.springframework.cache.annotation.Cacheable`     | Annotation for caching the result of a method.       |
| `org.springframework.stereotype.Service`             | Annotation indicating that a class is a service.     |

## Methods

| Method                                      | Description                                                                                                                                                             |
|---------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `initiateLoanTransferRequest(LoanTransferRequest request)` | Initiates a loan transfer. <br> - Annotations: <br>   - `@Transactional` <br>   - `@CacheEvict(value = "loanTransfers", allEntries = true)` <br>   - `@SendTo("loan-transfer")` |
| `initiateLoanTransfer(LoanTransferRequest request)` | Initiates a loan transfer. <br> - Parameters: <br>   - `request` - The loan transfer request. <br> - Returns: <br>   - A `LoanTransferRest` object representing the initiated loan transfer. |

## Code Example

```java
@Service
public class TransferServiceImpl implements TransferService {

    @Override
    @CachePut(key = "#accountId", value = "transactions")
    public LoanTransferRest transferLoan(Long accountId, LoanTransferRequest transferRequest) {
        return fabrickRestService
                .executeTransfer(accountId, transferRequest)
                .map(loanTransferRestMapper)
                .orElseThrow(() -> {
                    LOG.error("LOAN TRANSFER REST OBJECT IS NULL");
                    return new TransferServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getMessage());
                });
    }
    // Other methods and implementations are omitted for brevity
}

    
```

---

This documentation provides a comprehensive overview of the service classes in the Fabrick API Service, including their methods, dependencies, and code examples. Each section is structured similarly to the provided example, offering clarity and ease of navigation.