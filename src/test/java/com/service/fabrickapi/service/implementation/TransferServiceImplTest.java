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


import com.service.fabrickapi.exception.TransferServiceException;
import com.service.fabrickapi.mapper.LoanTransferRestMapper;
import com.service.fabrickapi.model.dto.*;
import com.service.fabrickapi.model.request.LoanTransferRequest;
import com.service.fabrickapi.model.rest.LoanTransferRest;
import com.service.fabrickapi.service.FabrickRestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TransferServiceImplTest {
    @InjectMocks
    TransferServiceImpl transferService;
    @Mock
    FabrickRestService fabrickRestService;
    @Mock
    LoanTransferRestMapper loanTransferRestMapper;
    @Mock
    LoanTransferRequest loanTransferRequest;
    LoanTransferRest loanTransferRest;
    LoanTransferDTO loanTransferDTO;
    AddressDTO addressDTO;
    AccountDTO accountDTO;
    CreditorDTO creditorDTO;
    DebtorDTO debtorDTO;
    FeeDTO feeDTO;
    Date creditorCurrencyDate;
    Date debtorValueDate;
    Date creditorValueDate;
    Long ACCOUNT_ID = 3489753984739L;

    AmountDTO amountDTO = new AmountDTO(
            BigInteger.valueOf(1000),
            "USD",
            BigInteger.valueOf(950),
            "EUR",
            new Date(),
            2
    );


    @BeforeEach
    void setUp() {
        creditorCurrencyDate = new Date();
        debtorValueDate = new Date();
        creditorValueDate = new Date();

        addressDTO = new AddressDTO("Via Rouille 20", "Aosta", "Italy");
        accountDTO = new AccountDTO("123456789");
        creditorDTO = new CreditorDTO("Creditor Name", accountDTO, addressDTO);
        debtorDTO = new DebtorDTO("Debtor Name", accountDTO);
        feeDTO = new FeeDTO("fee001", "Transaction Fee", BigInteger.TEN, "EUR");
        amountDTO = new AmountDTO(BigInteger.valueOf(1000), "EUR", BigInteger.valueOf(950), "EUR", creditorCurrencyDate, 2);

        loanTransferDTO = new LoanTransferDTO(
                "12345", "Pending", "Outgoing",
                creditorDTO, addressDTO, debtorDTO,
                "456", "https://example.com", "TRN987654",
                "Loan Transfer Description", "2024-01-29",
                "2024-01-30", debtorValueDate, creditorValueDate,
                amountDTO, true, false, "Fixed", "123456789",
                Collections.singletonList(feeDTO), true
        );

        loanTransferRest = new LoanTransferRest(
                12345L, "Pending", "Outgoing",
                creditorDTO, addressDTO, debtorDTO,
                456L, "https://example.com", "TRN987654",
                "Loan Transfer Description", "2024-01-29",
                "2024-01-30", debtorValueDate, creditorValueDate,
                amountDTO, true, false, "Fixed", 123456789L,
                Collections.singletonList(feeDTO), true
        );
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("transfer loan - transfer service test 🏗️")
    void transferLoan() {
        when(fabrickRestService.executeTransfer(anyLong(), any(LoanTransferRequest.class))).thenReturn(Optional.ofNullable(loanTransferDTO));
        when(loanTransferRestMapper.apply(any(LoanTransferDTO.class))).thenReturn(loanTransferRest);

        LoanTransferRest returnValue = transferService.transferLoan(ACCOUNT_ID, loanTransferRequest);

        assertThat(returnValue).isNotNull();
        assertThat(returnValue.moneyTransferId()).isEqualTo(12345L);
        assertThat(returnValue.status()).isEqualTo("Pending");
        assertThat(returnValue.direction()).isEqualTo("Outgoing");
        assertThat(returnValue.creditor()).isEqualTo(creditorDTO);
        assertThat(returnValue.addressDTO()).isEqualTo(addressDTO);
        assertThat(returnValue.debtorDTO()).isEqualTo(debtorDTO);
        assertThat(returnValue.uri()).isEqualTo("https://example.com");
        assertThat(returnValue.feeAccountId()).isEqualTo(123456789L);
        assertThat(returnValue.feeType()).isEqualTo("Fixed");
        assertThat(returnValue.feeDTOs()).isEqualTo(Collections.singletonList(feeDTO));
        assertThat(returnValue.isUrgent()).isEqualTo(true);
        assertThat(returnValue.isInstant()).isEqualTo(false);

        verify(fabrickRestService, times(1)).executeTransfer(anyLong(), any(LoanTransferRequest.class));
        verify(loanTransferRestMapper, times(1)).apply(any(LoanTransferDTO.class));

    }

    @Test
    @DisplayName("make a loan transfer for non-existing user and receive error - transfer service test 🏗️")
    void transferLoanThrowsException() {
        when(fabrickRestService.executeTransfer(anyLong(), any(LoanTransferRequest.class))).thenReturn(Optional.empty());
        when(loanTransferRestMapper.apply(any(LoanTransferDTO.class))).thenReturn(null);

        assertThrows(TransferServiceException.class, () -> transferService.transferLoan(ACCOUNT_ID + 1, loanTransferRequest));

        verify(fabrickRestService, times(1)).executeTransfer(anyLong(), any(LoanTransferRequest.class));
    }

}