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

import com.service.fabrickapi.model.dto.AccountBalanceDTO;
import com.service.fabrickapi.model.rest.AccountBalanceRest;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.function.Function;

@Component
public class AccountBalancerRestMapper implements Function<AccountBalanceDTO, AccountBalanceRest> {
    @Override
    public AccountBalanceRest apply(AccountBalanceDTO accountBalanceDTO) {
        return new AccountBalanceRest(
                Long.valueOf(accountBalanceDTO.accountId()),
                accountBalanceDTO.iban(),
                Long.valueOf(accountBalanceDTO.abiCode()),
                Long.valueOf(accountBalanceDTO.cabCode()),
                accountBalanceDTO.countryCode(),
                Integer.parseInt(accountBalanceDTO.internationalCin()),
                accountBalanceDTO.nationalCin(),
                Long.valueOf(accountBalanceDTO.account()),
                accountBalanceDTO.alias(),
                accountBalanceDTO.productName(),
                accountBalanceDTO.holderName(),
                Date.valueOf(accountBalanceDTO.activatedDate()),
                accountBalanceDTO.currency()
        );
    }
}
