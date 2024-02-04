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
import com.service.fabrickapi.controller.TransferController;
import com.service.fabrickapi.model.dto.*;
import com.service.fabrickapi.model.request.LoanTransferRequest;
import com.service.fabrickapi.model.rest.LoanTransferRest;
import com.service.fabrickapi.service.TransferService;
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

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TransferController.class)
public class TransferIntegrationTest {
    private static final XmlMapper xmlMapper = new XmlMapper();
    @InjectMocks
    final TransferController transferController;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TransferService transferService;
    LoanTransferRest loanTransferRest;
    AmountDTO amountDTO;
    AccountDTO debtorAccountDTO;
    AccountDTO creditorAccountDTO;
    DebtorDTO debtorDTO;
    AddressDTO addressDTO;
    CreditorDTO creditorDTO;
    FeeDTO feeDTO1;
    FeeDTO feeDTO2;
    Date creditorCurrencyDate;
    Date debtorValueDate;
    Date creditorValueDate;

    @Autowired
    public TransferIntegrationTest(TransferController transferController) {
        this.transferController = transferController;
    }

    @BeforeEach
    void setUp() {
        creditorCurrencyDate = new Date();
        debtorValueDate = new Date();
        creditorCurrencyDate = new Date();

        amountDTO = new AmountDTO(
                BigInteger.valueOf(1000),
                "EUR",
                BigInteger.valueOf(900),
                "EUR",
                creditorCurrencyDate,
                2
        );

        debtorAccountDTO = new AccountDTO("DebtorAccountCode");
        creditorAccountDTO = new AccountDTO("CreditorAccountCode");
        debtorDTO = new DebtorDTO("Debtor Name", debtorAccountDTO);
        addressDTO = new AddressDTO("123 Main St", "Cityville", "US");
        creditorDTO = new CreditorDTO("Creditor Name", creditorAccountDTO, addressDTO);
        feeDTO1 = new FeeDTO("FeeCode1", "Fee Description 1", BigInteger.valueOf(50), "USD");
        feeDTO2 = new FeeDTO("FeeCode2", "Fee Description 2", BigInteger.valueOf(30), "EUR");

        loanTransferRest = new LoanTransferRest(
                123L,
                "SUCCESS",
                "OUTGOING",
                creditorDTO,
                addressDTO,
                debtorDTO,
                456L,
                "https://example.com",
                "TRN123", // trn
                "Loan Transfer Description",
                "2019-01-29",
                "2019-01-29",
                debtorValueDate,
                creditorValueDate,
                amountDTO,
                true,
                false,
                "FIXED",
                789L,
                List.of(feeDTO1, feeDTO2),
                true
        );
    }


    @Test
    @DisplayName("transfer loan - transfer integration test 📡")
    void transferLoan() throws Exception {
        when(transferService.transferLoan(anyLong(), any(LoanTransferRequest.class))).thenReturn(loanTransferRest);

        var result = mockMvc.perform(
                        post("/api/v1/transfer/{accountId}", 1234L)
                                .content("""
                                        {
                                            "creditor": {
                                                "name": "John Doe",
                                                "account": {
                                                    "accountCode": "IT40L0326822311052923800661"
                                                }
                                            },
                                            "executionDate": "2019-04-01",
                                            "description": "Payment invoice 75/2017",
                                            "amount": 800,
                                            "currency": "EUR"
                                        }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andReturn();


        String xmlResponse = result.getResponse().getContentAsString();
        var returnValue = xmlMapper.readValue(xmlResponse, LoanTransferRest.class);

        assertThat(returnValue).isNotNull();

        assertThat(returnValue.moneyTransferId()).isEqualTo(123L);
        assertThat(returnValue.status()).isEqualTo("SUCCESS");
        assertThat(returnValue.direction()).isEqualTo("OUTGOING");

        assertThat(returnValue.creditor()).isNotNull();
        assertThat(returnValue.creditor().name()).isEqualTo("Creditor Name");
        assertThat(returnValue.creditor().accountDTO()).isNotNull();
        assertThat(returnValue.creditor().accountDTO().accountCode()).isEqualTo("CreditorAccountCode");

        assertThat(returnValue.debtorDTO()).isNotNull();
        assertThat(returnValue.debtorDTO().name()).isEqualTo("Debtor Name");
        assertThat(returnValue.debtorDTO().accountDTO()).isNotNull();
        assertThat(returnValue.debtorDTO().accountDTO().accountCode()).isEqualTo("DebtorAccountCode");

        assertThat(returnValue.amountDTO()).isNotNull();

        assertThat(returnValue.feeDTOs()).isNotNull();
        assertThat(returnValue.feeDTOs().size()).isEqualTo(2);
        assertThat(returnValue.feeDTOs().getFirst().feeCode()).isEqualTo("FeeCode1");
        assertThat(returnValue.feeDTOs().getLast().feeCode()).isEqualTo("FeeCode2");

        assertThat(returnValue.addressDTO()).isNotNull();
        assertThat(returnValue.addressDTO().city()).isEqualTo("Cityville");
        assertThat(returnValue.addressDTO().countryCode()).isEqualTo("US");
        assertThat(returnValue.addressDTO().address()).isEqualTo("123 Main St");

    }


}
