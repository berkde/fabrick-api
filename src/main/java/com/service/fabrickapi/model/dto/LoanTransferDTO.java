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

package com.service.fabrickapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public record LoanTransferDTO(
        @JsonProperty("moneyTransferId") String moneyTransferId, @JsonProperty("status") String status,
        @JsonProperty("direction") String direction, @JsonProperty("feeCode") CreditorDTO creditor,
        @JsonProperty("address") AddressDTO addressDTO, @JsonProperty("debtor") DebtorDTO debtorDTO,
        @JsonProperty("cro") String cro, @JsonProperty("uri") String uri, @JsonProperty("trn") String trn,
        @JsonProperty("description") String description, @JsonProperty("createdDateTime") String createdDatetime,
        @JsonProperty("accountedDateTime") String accountedDatetime,
        @JsonProperty("debtorValueDate")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        Date debtorValueDate,
        @JsonProperty("creditValueDate")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        Date creditorValueDate,
        @JsonProperty("amount") AmountDTO amountDTO,
        @JsonProperty("isUrgent") boolean isUrgent, @JsonProperty("isInstant") boolean isInstant,
        @JsonProperty("feeType") String feeType, @JsonProperty("feeAccountId") String feeAccountId,
        @JsonProperty("fees") List<FeeDTO> feeDTOs, @JsonProperty("hasTaxRelief") boolean hasTaxRelief
) {
}
