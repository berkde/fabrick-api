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

package com.service.fabrickapi.controller;

import com.service.fabrickapi.model.error.ErrorMessage;
import com.service.fabrickapi.model.rest.AccountBalanceRest;
import com.service.fabrickapi.model.rest.TransactionRest;
import com.service.fabrickapi.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final Logger LOG = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * Controller endpoint for retrieving the balance of a specified account.
     * This endpoint allows clients to query and retrieve the balance of a specific account.
     * The response can be formatted in either XML or JSON.
     *
     * @param accountId The unique identifier of the account for which the balance is requested.
     * @return ResponseEntity<String> A response entity containing the balance of the account in the requested currency.
     * The balance is returned as a JSON or XML object, depending on the requested media type.
     * @implNote This endpoint is accessible via the HTTP GET method.
     * The produced media types include both XML and JSON formats.
     * The method logs relevant information about the request, including account ID
     * and the requester's thread.
     */

    @Operation(
            summary = "GET BALANCE",
            description = "Endpoint to fetch current account balance of the creditor."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance fetched successfully",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = AccountBalanceRest.class)
                            )
                    }),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    })
    })
    @GetMapping(path = "/{accountId}/balance",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AccountBalanceRest> getAccountBalance(@PathVariable Long accountId) {
        LOG.info("GET API ENDPOINT REQUEST | Account Balance - Account ID: {} - Requested By: {}",
                accountId,
                Thread.currentThread().getName());
        AccountBalanceRest returnValue = accountService.getAccountBalance(accountId);
        return new ResponseEntity<>(returnValue, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * Controller endpoint for retrieving a list of transactions for a specified account within a given date range.
     * This endpoint allows clients to query and retrieve a list of transactions for a specific account
     * within the specified date range. The response can be formatted in either XML or JSON.
     *
     * @param accountId          The unique identifier of the account for which transactions are requested.
     * @param fromAccountingDate The start date (ISO 8601 format) for filtering transactions.
     * @param toAccountingDate   The end date (ISO 8601 format) for filtering transactions.
     * @return ResponseEntity<List < TransactionDTO>> A response entity containing the list of transactions.
     * The transactions are ordered by date and may include details such as transaction ID, amount,
     * currency, description, and transaction type.
     * @implNote This endpoint is accessible via the HTTP GET method.
     * The produced media types include both XML and JSON formats.
     * The transactions list is limited to 100 transactions.
     * The method logs relevant information about the request, including account ID,
     * date range, and the requester's thread.
     */

    @Operation(
            summary = "GET TRANSACTIONS",
            description = "Endpoint to fetch account transactions of the creditor."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions fetched successfully",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class)
                            )
                    }),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    })
    })
    @GetMapping(path = "/{accountId}/transactions",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TransactionRest>> getAccountTransactions(
            @PathVariable Long accountId,
            @RequestParam String fromAccountingDate,
            @RequestParam String toAccountingDate) {
        LOG.info("GET API ENDPOINT REQUEST | Account Transactions - Account ID: {} - With Query Params: fromAccountingDate: {} toAccountingDate: {} - Requested By: {}",
                accountId,
                fromAccountingDate,
                toAccountingDate,
                Thread.currentThread().getName());
        List<TransactionRest> transactions = accountService.getAccountTransactions(accountId, fromAccountingDate, toAccountingDate);
        return ResponseEntity.ok(transactions);
    }
}
