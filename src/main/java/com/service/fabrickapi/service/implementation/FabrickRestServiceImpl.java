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

import com.service.fabrickapi.configuration.Credentials;
import com.service.fabrickapi.exception.FabrickRestServiceException;
import com.service.fabrickapi.model.dto.AccountBalanceDTO;
import com.service.fabrickapi.model.dto.LoanTransferDTO;
import com.service.fabrickapi.model.dto.TransactionDTO;
import com.service.fabrickapi.model.request.LoanTransferRequest;
import com.service.fabrickapi.service.FabrickRestService;
import com.service.fabrickapi.shared.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;


@Service
public class FabrickRestServiceImpl implements FabrickRestService {
    private final Logger LOG = LoggerFactory.getLogger(FabrickRestServiceImpl.class);
    private final Credentials credentials;
    private final Utils utils;

    /**
     * Constructs a new instance of the FabrickRestServiceImpl.
     *
     * @param credentials The credentials for accessing the Fabrick API.
     * @param utils       The utility class for handling common operations.
     */
    @Autowired
    public FabrickRestServiceImpl(Credentials credentials, Utils utils) {
        this.credentials = credentials;
        this.utils = utils;
    }

    /**
     * Retrieves the account balance for the specified account owner.
     *
     * @param accountId The ID of the account owner.
     * @return Optional<AccountBalanceDTO> The account balance in the requested currency.
     * @throws FabrickRestServiceException If an error occurs during the account balance retrieval.
     */
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


    /**
     * Retrieves a list of transactions for the specified account owner within a date range.
     *
     * @param accountId          The ID of the account owner.
     * @param fromAccountingDate The start date for filtering transactions (ISO 8601 format).
     * @param toAccountingDate   The end date for filtering transactions (ISO 8601 format).
     * @return Optional<List < TransactionDTO> A list of transactions
     * @throws FabrickRestServiceException If an error occurs during the transaction retrieval.
     */
    @Override
    public Optional<List<TransactionDTO>> getAccountTransactions(Long accountId, String fromAccountingDate, String toAccountingDate) {
        try {
            HttpHeaders headers = utils.headers();
            HttpEntity<?> entity = new HttpEntity<>(headers);
            String uri = utils.buildUrlWithQueryParams(credentials.transactionsURL(), accountId, fromAccountingDate, toAccountingDate);

            LOG.info("FETCHING TRANSACTIONS FOR  {} - FROM {} UNTIL {}",
                    accountId,
                    fromAccountingDate,
                    toAccountingDate);

            ResponseEntity<String> response = utils.getStringResponseEntity(uri, entity, HttpMethod.GET);

            String responseBody = response.getBody();

            return Optional.ofNullable(utils.fromJson(responseBody, List.class, TransactionDTO.class));

        } catch (RestClientException e) {
            LOG.error("CANNOT FETCH TRANSACTIONS FOR {}", accountId);
            throw new FabrickRestServiceException(e.getMessage());
        }
    }


    /**
     * Executes a fund transfer based on the provided transfer request.
     *
     * @param accountId       The ID of the account initiating the transfer.
     * @param transferRequest The Transfer Request Object containing transfer details.
     * @return Optional<LoanTransferDTO> The result of the transfer operation returned as an Optional LoanTransferDTO class object.
     * @throws FabrickRestServiceException If an error occurs during the transfer execution.
     */
    @Override
    public Optional<LoanTransferDTO> executeTransfer(Long accountId, LoanTransferRequest transferRequest) {
        try {
            HttpHeaders headers = utils.headers();
            String jsonRequest = utils.toJson(transferRequest);

            LOG.info("JSON REQUEST OBJECT {} ", jsonRequest);

            HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);
            String uri = utils.buildUrl(credentials.transfersURL(), accountId);

            LOG.info("EXECUTING TRANSFER FROM REQUESTER {} FOR {} {}",
                    transferRequest.creditorDTO().name(),
                    transferRequest.amount(),
                    transferRequest.currency());

            ResponseEntity<String> response = utils.getStringResponseEntity(uri, entity, HttpMethod.POST);

            return Optional.ofNullable(utils.fromJson(response.getBody(), LoanTransferDTO.class));

        } catch (Exception e) {
            LOG.error("CANNOT EXECUTE TRANSFER - REQUESTER: {} - AMOUNT : {} {}", transferRequest.creditorDTO().name(),
                    transferRequest.amount(),
                    transferRequest.currency());
            throw new FabrickRestServiceException(e.getMessage());
        }


    }
}
