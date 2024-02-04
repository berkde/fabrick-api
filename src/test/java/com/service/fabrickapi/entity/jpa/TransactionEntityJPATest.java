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

package com.service.fabrickapi.entity.jpa;

import com.service.fabrickapi.entity.TransactionEntity;
import com.service.fabrickapi.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class TransactionEntityJPATest {

    private final Long TRANSACTION_ID = 8345823529878925L;
    private final Long OPERATION_ID = 3294875238947892L;
    private final Date ACCOUNTING_DATE = new Date(2019 - 11 - 29);
    private final Date VALUE_DATE = new Date(2019 - 11 - 29);
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        var transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(TRANSACTION_ID);
        transactionEntity.setOperationId(OPERATION_ID);
        transactionEntity.setAccountingDate(ACCOUNTING_DATE);
        transactionEntity.setValueDate(VALUE_DATE);
        transactionEntity.setType(""" 
                {"enumeration":"GBS_TRANSACTION_TYPE","value":"GBS_ACCOUNT_TRANSACTION_TYPE_0050"}
                """);
        transactionEntity.setAmount(BigDecimal.valueOf(343.77));
        transactionEntity.setCurrency("EUR");
        transactionEntity.setDescription("PD VISA CORPORATE 10");

        testEntityManager.persist(transactionEntity);

    }

    @AfterEach
    void tearDown() {
        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    @DisplayName("save transaction entity and retrieve by transactionId - entity jpa test 🛠️")
    public void saveUserEntityAndRetrieveByUsername() {
        var transaction = this.transactionRepository.findByTransactionId(TRANSACTION_ID);

        assertThat(transaction).isNotNull();
        assertThat(transaction).isPresent();
        assertThat(transaction.get().getOperationId()).isEqualTo(OPERATION_ID);
        assertThat(transaction.get().getAccountingDate()).isEqualTo(ACCOUNTING_DATE);
        assertThat(transaction.get().getValueDate()).isEqualTo(VALUE_DATE);
        assertThat(transaction.get().getType()).isEqualTo("""
                {"enumeration":"GBS_TRANSACTION_TYPE","value":"GBS_ACCOUNT_TRANSACTION_TYPE_0050"}
                """);
        assertThat(transaction.get().getAmount()).isEqualTo(BigDecimal.valueOf(343.77));
        assertThat(transaction.get().getCurrency()).isEqualTo("EUR");
        assertThat(transaction.get().getDescription()).isEqualTo("PD VISA CORPORATE 10");
        assertThat(transaction.get().getTransactionId()).isEqualTo(TRANSACTION_ID);
    }

    @Test
    @DisplayName("save transaction entity and attempt retrieve non existing transaction - entity jpa test 🛠️")
    public void saveUserEntityAndRetrieveNonExistingEntity() {
        assertThat(this.transactionRepository.findByTransactionId(TRANSACTION_ID + 1).isPresent()).isFalse();
    }

    @Test
    @DisplayName("save transaction entity and delete - entity jpa test 🛠️")
    public void saveUserEntityAndDelete() {
        int isDeleted = this.transactionRepository.deleteByTransactionId(TRANSACTION_ID);
        assertThat(isDeleted).isEqualTo(1);
    }
}