/*
 *
 *  * Copyright (c) 2024 Berk Delibalta
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */

package com.service.fabrickapi.service.implementation;

import com.service.fabrickapi.exception.AccountServiceException;
import com.service.fabrickapi.mapper.AccountBalancerRestMapper;
import com.service.fabrickapi.mapper.TransactionRestMapper;
import com.service.fabrickapi.model.rest.AccountBalanceRest;
import com.service.fabrickapi.model.rest.TransactionRest;
import com.service.fabrickapi.service.AccountService;
import com.service.fabrickapi.service.FabrickRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.service.fabrickapi.model.error.ErrorMessages.RECORD_NOT_FOUND;

@Service
public class AccountServiceImpl implements AccountService {
    private final Logger LOG = LoggerFactory.getLogger(AccountService.class);
    private final FabrickRestService fabrickRestService;
    private final AccountBalancerRestMapper accountBalancerRestMapper;
    private final TransactionRestMapper transactionRestMapper;

    /**
     * Constructs a new instance of the AccountServiceImpl.
     *
     * @param fabrickRestService        The FabrickRestService instance to use for making REST API calls.
     * @param accountBalancerRestMapper The AccountBalancerRestMapper instance to use for mapping AccountBalanceDTO objects to AccountBalanceRest objects.
     * @param transactionRestMapper     The TransactionRestMapper instance to use for mapping TransactionDTO objects to TransactionRest objects.
     * @implNote The AccountServiceImpl constructor is annotated with @Autowired to inject the FabrickRestService,
     * AccountBalancerRestMapper, and TransactionRestMapper instances.
     * This allows the AccountServiceImpl to access the FabrickRestService and
     * map AccountBalanceDTO objects to AccountBalanceRest objects.
     */
    @Autowired
    public AccountServiceImpl(FabrickRestService fabrickRestService,
                              AccountBalancerRestMapper accountBalancerRestMapper,
                              TransactionRestMapper transactionRestMapper) {
        this.fabrickRestService = fabrickRestService;
        this.accountBalancerRestMapper = accountBalancerRestMapper;
        this.transactionRestMapper = transactionRestMapper;
    }

    /**
     * Retrieves the balance of the account owner.
     *
     * @param accountId The ID of the account owner.
     * @return AccountBalanceRest The account owner's balance in the requested currency, returned as a AccountBalanceRest class object.
     * @implNote If the account owner has no balance in the requested currency, the balance is returned as 0.00.
     * If the account owner has a balance in the requested currency, the balance is returned as the actual balance.
     */
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

    /**
     * Retrieves a list of transactions for the account owner within a specified date range.
     *
     * @param accountId          The ID of the account owner.
     * @param fromAccountingDate The start date (ISO 8601 format) for filtering transactions.
     * @param toAccountingDate   The end date (ISO 8601 format) for filtering transactions.
     * @return List<TransactionRest> A list of transactions for the account owner, ordered by date.
     * @implNote The list is limited to 100 transactions.
     * If the list is empty, the account owner has no transactions in the requested date range.
     * If the list is not empty, the account owner has transactions in the requested date range.
     * Each TransactionDTO object in the list includes details such as:
     * - transactionId: The ID of the transaction.
     * - accountingDate: The date of the transaction.
     * - valueDate: The date when the transaction is posted.
     * - amount: The amount of the transaction.
     * - currency: The currency of the transaction.
     * - description: The description of the transaction.
     * - transactionType: The type of the transaction.
     * - isUrgent: Whether the transaction is urgent.
     * - isBooked: Whether the transaction is booked.
     * - isPending: Whether the transaction is pending.
     * - isCashAdvance: Whether the transaction is a cash advance.
     */
    @Override
    @Cacheable(key = "accountId", value = "transactions")
    public List<TransactionRest> getAccountTransactions(Long accountId, String fromAccountingDate, String toAccountingDate) {
        try {
            return fabrickRestService
                    .getAccountTransactions(accountId, fromAccountingDate, toAccountingDate)
                    .orElseThrow(() -> {
                        LOG.error("TRANSACTION REST OBJECT LIST IS NULL");
                        return new AccountServiceException(RECORD_NOT_FOUND.getMessage());
                    })
                    .stream()
                    .map(transactionRestMapper)
                    .sorted(Comparator.comparing(TransactionRest::accountingDate).thenComparing(TransactionRest::valueDate).reversed())
                    .limit(30)
                    .toList();
        } catch (Exception e) {
            LOG.error("TRANSACTION OBJECT TRANSFORM OR SAVE FAILED");
            throw new AccountServiceException(e.getMessage());
        }
    }


}
