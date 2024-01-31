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

package com.service.fabrickapi.entity.json;

import com.service.fabrickapi.entity.TransactionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.math.BigDecimal;

import static java.lang.Math.toIntExact;
import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class TransactionEntityJSONTest {

    private final Long TRANSACTION_ID = 282831L;
    private final Long OPERATION_ID = 282831L;
    @Autowired
    private JacksonTester<TransactionEntity> transactionEntityJacksonTester;
    private TransactionEntity transactionEntity;

    @BeforeEach
    void setUp() {
        transactionEntity = new TransactionEntity();
        transactionEntity.setId(1L);
        transactionEntity.setTransactionId(TRANSACTION_ID);
        transactionEntity.setOperationId(OPERATION_ID);
        transactionEntity.setType(""" 
                {"enumeration":"GBS_TRANSACTION_TYPE","value":"GBS_ACCOUNT_TRANSACTION_TYPE_0050"}
                """);
        transactionEntity.setAmount(BigDecimal.valueOf(343.77));
        transactionEntity.setCurrency("EUR");
        transactionEntity.setDescription("PD VISA CORPORATE 10");
    }

    @Test
    @DisplayName("transaction entity serialize - json test ⚙️")
    public void testTransactionEntitySerialize() throws IOException {
        String jsonTransactionEntitySerialize = """
                 {
                   "id": 1,
                   "transactionId": 282831,
                   "operationId": 282831,
                   "type": "{\\"enumeration\\":\\"GBS_TRANSACTION_TYPE\\",\\"value\\":\\"GBS_ACCOUNT_TRANSACTION_TYPE_0050\\"}\\n",
                   "amount": "343.77",
                   "currency": "EUR",
                   "description": "PD VISA CORPORATE 10"
                 }
                """;


        assertThat(transactionEntityJacksonTester.write(transactionEntity)).isEqualToJson(jsonTransactionEntitySerialize);
        assertThat(transactionEntityJacksonTester.write(transactionEntity)).hasJsonPath("$.transactionId");
        assertThat(transactionEntityJacksonTester.write(transactionEntity)).hasJsonPath("$.operationId");
        assertThat(transactionEntityJacksonTester.write(transactionEntity)).hasJsonPath("$.type");
        assertThat(transactionEntityJacksonTester.write(transactionEntity)).hasJsonPath("$.amount");
        assertThat(transactionEntityJacksonTester.write(transactionEntity)).hasJsonPath("$.currency");
        assertThat(transactionEntityJacksonTester.write(transactionEntity)).hasJsonPath("$.description");

        assertThat(transactionEntityJacksonTester.write(transactionEntity)).extractingJsonPathNumberValue("$.transactionId").isEqualTo(toIntExact(TRANSACTION_ID));
        assertThat(transactionEntityJacksonTester.write(transactionEntity)).extractingJsonPathNumberValue("$.operationId").isEqualTo(toIntExact(OPERATION_ID));
        assertThat(transactionEntityJacksonTester.write(transactionEntity)).extractingJsonPathStringValue("$.type").isEqualTo("""
                {"enumeration":"GBS_TRANSACTION_TYPE","value":"GBS_ACCOUNT_TRANSACTION_TYPE_0050"}
                """);
        assertThat(transactionEntityJacksonTester.write(transactionEntity)).extractingJsonPathStringValue("$.amount").isEqualTo("343.77");
        assertThat(transactionEntityJacksonTester.write(transactionEntity)).extractingJsonPathStringValue("$.currency").isEqualTo("EUR");


    }

    @Test
    @DisplayName("transaction entity deserialize - json test ⚙️")
    public void testTransactionEntityDeSerialize() throws Exception {
        String jsonTransactionEntityDeserialize = "  {\n" +
                "                   \"id\": 1,\n" +
                "                   \"transactionId\": 282831,\n" +
                "                   \"operationId\": 282831,\n" +
                "                   \"accountingDate\": \"2019-11-28\",\n" +
                "                   \"valueDate\": \"2019-11-30\",\n" +
                "                   \"type\": \"{\\\"enumeration\\\":\\\"GBS_TRANSACTION_TYPE\\\",\\\"value\\\":\\\"GBS_ACCOUNT_TRANSACTION_TYPE_0050\\\"}\\n\",\n" +
                "                   \"amount\": \"343.77\",\n" +
                "                   \"currency\": \"EUR\",\n" +
                "                   \"description\": \"PD VISA CORPORATE 10\"\n" +
                "                 }";

        assertThat(transactionEntityJacksonTester.parse(jsonTransactionEntityDeserialize)).isEqualTo(transactionEntity);
        assertThat(transactionEntityJacksonTester.parseObject(jsonTransactionEntityDeserialize).getTransactionId()).isEqualTo(TRANSACTION_ID);
        assertThat(transactionEntityJacksonTester.parseObject(jsonTransactionEntityDeserialize).getOperationId()).isEqualTo(OPERATION_ID);
        assertThat(transactionEntityJacksonTester.parseObject(jsonTransactionEntityDeserialize).getType()).isEqualTo(
                """
                        {"enumeration":"GBS_TRANSACTION_TYPE","value":"GBS_ACCOUNT_TRANSACTION_TYPE_0050"}
                        """
        );
        assertThat(transactionEntityJacksonTester.parseObject(jsonTransactionEntityDeserialize).getAmount()).isEqualTo(BigDecimal.valueOf(343.77));
        assertThat(transactionEntityJacksonTester.parseObject(jsonTransactionEntityDeserialize).getCurrency()).isEqualTo("EUR");
        assertThat(transactionEntityJacksonTester.parseObject(jsonTransactionEntityDeserialize).getDescription()).isEqualTo("PD VISA CORPORATE 10");
    }


}
