# REPOSITORY PACKAGE CLASSES DOCUMENTATION

## TransactionRepository Documentation

| **Overview** | The `TransactionRepository` interface is a Spring `@Repository` responsible for CRUD operations on `TransactionEntity` instances. It extends `CrudRepository`. |
| --- | --- |
| **Class Structure** | - **Package**: `com.service.fabrickapi.repository`<br>- **Author**: Berk Delibalta |
| **Copyright** | Copyright (c) 2024 Berk Delibalta |
| **License** | MIT License |
| **Repository Interface** | `CrudRepository<TransactionEntity, Long>` |
| **Methods** | 1. `Optional<TransactionEntity> findByTransactionId(long transactionId)`<br>   - *Description*: Finds a `TransactionEntity` by its `transactionId`.<br>   - *Parameters*: `transactionId` - The unique identifier of the transaction.<br>   - *Returns*: An `Optional` containing the found `TransactionEntity`.<br>2. `Integer deleteByTransactionId(long transactionId)`<br>   - *Description*: Deletes a `TransactionEntity` by its `transactionId`.<br>   - *Parameters*: `transactionId` - The unique identifier of the transaction.<br>   - *Returns*: The number of affected rows (0 or 1). |
| **Additional Notes** | 1. The repository uses native SQL for the delete operation, specifying the target table (`transactions`) and condition based on `transaction_id`. |
| **SQL Query** | `DELETE FROM transactions t WHERE t.transaction_id = :transactionId` |

---

This documentation provides details about the `TransactionRepository` interface, including its class structure, methods, and additional notes. Let me know if there are any adjustments needed!