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

import com.service.fabrickapi.model.dto.LoanTransferDTO;
import com.service.fabrickapi.model.rest.LoanTransferRest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class LoanTransferRestMapper implements Function<LoanTransferDTO, LoanTransferRest> {
    @Override
    public LoanTransferRest apply(LoanTransferDTO loanTransferDTO) {
        return new LoanTransferRest(
                Long.valueOf(loanTransferDTO.moneyTransferId()),
                loanTransferDTO.status(),
                loanTransferDTO.direction(),
                loanTransferDTO.creditor(),
                loanTransferDTO.addressDTO(),
                loanTransferDTO.debtorDTO(),
                Long.valueOf(loanTransferDTO.cro()),
                loanTransferDTO.uri(),
                loanTransferDTO.trn(),
                loanTransferDTO.description(),
                loanTransferDTO.createdDatetime(),
                loanTransferDTO.accountedDatetime(),
                loanTransferDTO.debtorValueDate(),
                loanTransferDTO.creditorValueDate(),
                loanTransferDTO.amountDTO(),
                loanTransferDTO.isUrgent(),
                loanTransferDTO.isInstant(),
                loanTransferDTO.feeType(),
                Long.valueOf(loanTransferDTO.feeAccountId()),
                loanTransferDTO.feeDTOs(),
                loanTransferDTO.hasTaxRelief()
        );
    }
}
