/*
 *
 *  *
 *  *  * Copyright (c) 2024 Berk Delibalta
 *  *  *
 *  *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  * of this software and associated documentation files (the "Software"), to deal
 *  *  * in the Software without restriction, including without limitation the rights
 *  *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  * copies of the Software, and to permit persons to whom the Software is
 *  *  * furnished to do so, subject to the following conditions:
 *  *  *
 *  *  * The above copyright notice and this permission notice shall be included in
 *  *  * all copies or substantial portions of the Software.
 *  *  *
 *  *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  *  * THE SOFTWARE.
 *  *
 *
 */

package com.service.fabrickapi.mapper;


import com.service.fabrickapi.entity.TransactionEntity;
import com.service.fabrickapi.model.rest.TransactionRest;
import com.service.fabrickapi.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AccountTransactionSaveMapper implements Function<TransactionRest, TransactionRest> {
    private final Logger LOG = LoggerFactory.getLogger(AccountTransactionSaveMapper.class);
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountTransactionSaveMapper(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionRest apply(TransactionRest transactionRest) {
        if (transactionRepository.findByTransactionId(transactionRest.transactionId()).isEmpty()) {
            LOG.info("TRANSACTION REGISTRATION TO DB STARTED");

            var transactionEntity = new TransactionEntity();
            transactionEntity.setTransactionId(transactionRest.transactionId());
            transactionEntity.setOperationId(transactionRest.operationId());
            transactionEntity.setAccountingDate(transactionRest.accountingDate());
            transactionEntity.setValueDate(transactionRest.valueDate());
            transactionEntity.setType(transactionRest.type().toString());
            transactionEntity.setAmount(transactionRest.amount());
            transactionEntity.setCurrency(transactionRest.currency());
            transactionEntity.setDescription(transactionRest.description());
            transactionRepository.save(transactionEntity);

            LOG.info("TRANSACTION REGISTRATION ENDED");
        }
        return transactionRest;
    }
}
