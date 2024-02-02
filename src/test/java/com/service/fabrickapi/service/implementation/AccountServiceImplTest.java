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

package com.service.fabrickapi.service.implementation;

import com.service.fabrickapi.exception.AccountServiceException;
import com.service.fabrickapi.mapper.AccountBalancerRestMapper;
import com.service.fabrickapi.mapper.AccountTransactionSaveMapper;
import com.service.fabrickapi.mapper.TransactionRestMapper;
import com.service.fabrickapi.model.dto.AccountBalanceDTO;
import com.service.fabrickapi.model.dto.TransactionDTO;
import com.service.fabrickapi.model.rest.AccountBalanceRest;
import com.service.fabrickapi.model.rest.TransactionRest;
import com.service.fabrickapi.service.FabrickRestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountService;
    @Mock
    FabrickRestService fabrickRestService;
    @Mock
    AccountBalancerRestMapper accountBalancerRestMapper;
    @Mock
    TransactionRestMapper transactionRestMapper;
    @Mock
    AccountTransactionSaveMapper accountTransactionSaveMapper;
    AccountBalanceDTO accountBalanceDTO;
    AccountBalanceRest accountBalanceRest;
    Date activatedDate;
    TransactionDTO transactionDTO;
    TransactionRest transactionRest;
    Date accountingDate;
    Date valueDate;


    @BeforeEach
    void setUp() {
        activatedDate = new Date();

        accountBalanceDTO = new AccountBalanceDTO(
                "1234567890",
                "IT12345678901234567890",
                "12345",
                "67890",
                "IT",
                "ABC123",
                "123ABC",
                "987654321",
                "MyAlias",
                "Savings Account",
                "John Doe",
                "2022-01-29",
                "EUR"
        );

        accountBalanceRest = new AccountBalanceRest(
                1234567890L,
                "IT12345678901234567890",
                12345L,
                67890L,
                "IT",
                123,
                "ABC123",
                987654321L,
                "MyAlias",
                "Savings Account",
                "John Doe",
                activatedDate,
                "EUR"
        );

        activatedDate = new Date();
        valueDate = new Date();

        transactionDTO = new TransactionDTO(
                "123456789",
                "987654321",
                accountingDate, // Replace with the actual Date instance
                valueDate, // Replace with the actual Date instance
                new Object(), // Replace with the actual type instance
                BigDecimal.valueOf(500.50),
                "EUR",
                "Sample Transaction"
        );

        transactionRest = new TransactionRest(
                123456789L,
                987654321L,
                accountingDate,
                valueDate,
                new Object(),
                BigDecimal.valueOf(500.50),
                "EUR",
                "Sample Transaction"
        );


        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("get account balance - transfer service test 🏗️")
    void getAccountBalance() {
        when(fabrickRestService.getAccountBalance(anyLong())).thenReturn(Optional.ofNullable(accountBalanceDTO));
        when(accountBalancerRestMapper.apply(any(AccountBalanceDTO.class))).thenReturn(accountBalanceRest);

        AccountBalanceRest returnValue = accountService.getAccountBalance(1L);

        assertThat(returnValue).isNotNull();
        assertThat(returnValue.accountId()).isEqualTo(1234567890L);
        assertThat(returnValue.iban()).isEqualTo("IT12345678901234567890");
        assertThat(returnValue.abiCode()).isEqualTo(12345L);
        assertThat(returnValue.cabCode()).isEqualTo(67890L);
        assertThat(returnValue.countryCode()).isEqualTo("IT");
        assertThat(returnValue.internationalCin()).isEqualTo(123);
        assertThat(returnValue.nationalCin()).isEqualTo("ABC123");
        assertThat(returnValue.account()).isEqualTo(987654321L);
        assertThat(returnValue.alias()).isEqualTo("MyAlias");
        assertThat(returnValue.productName()).isEqualTo("Savings Account");
        assertThat(returnValue.holderName()).isEqualTo("John Doe");
        assertThat(returnValue.activatedDate()).isEqualTo(activatedDate);
        assertThat(returnValue.currency()).isEqualTo("EUR");

        verify(fabrickRestService, times(1)).getAccountBalance(anyLong());
        verify(accountBalancerRestMapper, times(1)).apply(any(AccountBalanceDTO.class));

    }

    @Test
    @DisplayName("get account balance for non-existing user and receive error - transfer service test 🏗️")
    void getAccountBalanceThrowsError() {
        when(fabrickRestService.getAccountBalance(anyLong())).thenReturn(Optional.empty());
        when(accountBalancerRestMapper.apply(any(AccountBalanceDTO.class))).thenReturn(null);

        assertThrows(AccountServiceException.class, () -> accountService.getAccountBalance(1L));

        verify(fabrickRestService, times(1)).getAccountBalance(anyLong());
    }

    @Test
    @DisplayName("get account transactions - transfer service test 🏗️")
    void getAccountTransactions() {
        when(fabrickRestService.getAccountTransactions(anyLong(), anyString(), anyString())).thenReturn(Optional.of(List.of(transactionDTO)));
        when(transactionRestMapper.apply(any(TransactionDTO.class))).thenReturn(transactionRest);
        when(accountTransactionSaveMapper.apply(any(TransactionRest.class))).thenReturn(transactionRest);

        List<TransactionRest> transactionRests = accountService.getAccountTransactions(1L, "2019-11-01", "2019-12-29");

        assertThat(transactionRests).isNotEmpty();
        assertThat(transactionRests.getFirst().transactionId()).isEqualTo(123456789L);
        assertThat(transactionRests.getFirst().operationId()).isEqualTo(987654321L);
        assertThat(transactionRests.getFirst().accountingDate()).isEqualTo(accountingDate);
        assertThat(transactionRests.getFirst().valueDate()).isEqualTo(valueDate);
        assertThat(transactionRests.getFirst().amount()).isEqualTo(BigDecimal.valueOf(500.50));
        assertThat(transactionRests.getFirst().currency()).isEqualTo("EUR");
        assertThat(transactionRests.getFirst().description()).isEqualTo("Sample Transaction");

        verify(fabrickRestService, times(1)).getAccountTransactions(anyLong(), anyString(), anyString());
        verify(transactionRestMapper, times(1)).apply(any(TransactionDTO.class));
        verify(accountTransactionSaveMapper, times(1)).apply(any(TransactionRest.class));
    }

    @Test
    @DisplayName("get non-existing account transactions - transfer service test 🏗️")
    void getAccountTransactionsThrowsException() {
        when(fabrickRestService.getAccountTransactions(anyLong(), anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(AccountServiceException.class, () -> accountService.getAccountBalance(1L));

        verify(fabrickRestService, times(1)).getAccountBalance(anyLong());

    }

}