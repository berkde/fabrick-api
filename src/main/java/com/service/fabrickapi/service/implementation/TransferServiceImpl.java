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

import com.service.fabrickapi.exception.TransferServiceException;
import com.service.fabrickapi.mapper.LoanTransferRestMapper;
import com.service.fabrickapi.model.dto.LoanTransferDTO;
import com.service.fabrickapi.model.error.ErrorMessages;
import com.service.fabrickapi.model.request.LoanTransferRequest;
import com.service.fabrickapi.model.rest.LoanTransferRest;
import com.service.fabrickapi.service.FabrickRestService;
import com.service.fabrickapi.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {
    private final Logger LOG = LoggerFactory.getLogger(TransferService.class);
    private final FabrickRestService fabrickRestService;
    private final LoanTransferRestMapper loanTransferRestMapper;

    /**
     * Constructor for the Transfer Service Implementation.
     *
     * @param fabrickAPIService      The Fabrick REST Service object.
     * @param loanTransferRestMapper The LoanTransfer REST Mapper object.
     * @implNote This constructor injects the Fabrick REST Service and
     * Transaction REST Mapper objects into the Transfer Service Implementation.
     */
    @Autowired
    public TransferServiceImpl(FabrickRestService fabrickAPIService,
                               LoanTransferRestMapper loanTransferRestMapper) {
        this.fabrickRestService = fabrickAPIService;
        this.loanTransferRestMapper = loanTransferRestMapper;
    }

    /**
     * Transfers a loan to the specified creditor account.
     *
     * @param accountId       The ID of the creditor account.
     * @param transferRequest The Transfer Request Object containing details for the loan transfer.
     * @return LoanTransferRest   The result of the loan transfer operation returned as a LoanTransferRest class object.
     * @implNote This method delegates the loan transfer operation to the Fabrick REST service.
     */
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
}
