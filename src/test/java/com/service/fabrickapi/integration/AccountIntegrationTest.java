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

package com.service.fabrickapi.integration;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.service.fabrickapi.controller.AccountController;
import com.service.fabrickapi.model.rest.AccountBalanceRest;
import com.service.fabrickapi.model.rest.TransactionRest;
import com.service.fabrickapi.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AccountController.class)
public class AccountIntegrationTest {

    private static final XmlMapper xmlMapper = new XmlMapper();
    @InjectMocks
    final AccountController accountController;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AccountService accountService;
    AccountBalanceRest accountBalanceRest;
    Date activatedDate;
    TransactionRest transactionRest;
    Date accountingDate;
    Date valueDate;

    @Autowired
    public AccountIntegrationTest(AccountController accountController) {
        this.accountController = accountController;
    }

    public static <T> List<T> deserializeXmlList(String xml, Class<T> valueType) throws IOException {
        var xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xml, xmlMapper.getTypeFactory().constructCollectionType(List.class, valueType));
    }

    @BeforeEach
    void setUp() {
        activatedDate = new Date();
        accountingDate = new Date();
        valueDate = new Date();

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

    }

    @Test
    @DisplayName("get account balance - account integration test 📡")
    void getAccountBalance() throws Exception {
        when(accountService.getAccountBalance(anyLong())).thenReturn(accountBalanceRest);

        var result = mockMvc.perform(
                        get("/api/v1/account/{accountId}/balance", 1234L)
                                .content(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isOk()).andReturn();


        String xmlResponse = result.getResponse().getContentAsString();
        var balanceRest = xmlMapper.readValue(xmlResponse, AccountBalanceRest.class);

        assertThat(balanceRest).isNotNull();

        assertThat(balanceRest.accountId()).isEqualTo(accountBalanceRest.accountId());
        assertThat(balanceRest.account()).isEqualTo(accountBalanceRest.account());
        assertThat(balanceRest.iban()).isEqualTo(accountBalanceRest.iban());
        assertThat(balanceRest.abiCode()).isEqualTo(accountBalanceRest.abiCode());
        assertThat(balanceRest.cabCode()).isEqualTo(accountBalanceRest.cabCode());
        assertThat(balanceRest.internationalCin()).isEqualTo(accountBalanceRest.internationalCin());
        assertThat(balanceRest.nationalCin()).isEqualTo(accountBalanceRest.nationalCin());
        assertThat(balanceRest.alias()).isEqualTo(accountBalanceRest.alias());
        assertThat(balanceRest.productName()).isEqualTo(accountBalanceRest.productName());
        assertThat(balanceRest.holderName()).isEqualTo(accountBalanceRest.holderName());
        assertThat(balanceRest.activatedDate()).isEqualTo(accountBalanceRest.activatedDate());
        assertThat(balanceRest.currency()).isEqualTo(accountBalanceRest.currency());
    }

    @Test
    @DisplayName("get account transactions - account integration test 📡")
    void getAccountTransactions() throws Exception {
        when(accountService.getAccountTransactions(anyLong(), anyString(), anyString())).thenReturn(List.of(transactionRest));

        var result = mockMvc.perform(
                        get("/api/v1/account/{accountId}/transactions", 1234L)
                                .param("fromAccountingDate", "2019-11-01")
                                .param("toAccountingDate", "2019-12-01")
                                .content(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String xmlResponse = result.getResponse().getContentAsString();
        var transactionRests = deserializeXmlList(xmlResponse, TransactionRest.class);

        assertThat(transactionRests).isNotNull();
        assertThat(transactionRests.size()).isEqualTo(1);
        assertThat(transactionRests.getFirst().transactionId()).isEqualTo(transactionRest.transactionId());
        assertThat(transactionRests.getFirst().operationId()).isEqualTo(transactionRest.operationId());
        assertThat(transactionRests.getFirst().amount()).isEqualTo(transactionRest.amount());
        assertThat(transactionRests.getFirst().currency()).isEqualTo(transactionRest.currency());
        assertThat(transactionRests.getFirst().description()).isEqualTo(transactionRest.description());
    }


}
