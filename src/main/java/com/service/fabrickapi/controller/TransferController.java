/*
 *
 *  * Copyright (c) 2024 Berk Delibalta
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */

package com.service.fabrickapi.controller;

import com.service.fabrickapi.model.error.ErrorMessage;
import com.service.fabrickapi.model.request.LoanTransferRequest;
import com.service.fabrickapi.model.rest.LoanTransferRest;
import com.service.fabrickapi.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/transfer")
public class TransferController {
    private final Logger LOG = LoggerFactory.getLogger(TransferController.class);
    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }


    /**
     * Controller endpoint for initiating a loan transfer from one account to another.
     * This endpoint allows clients to initiate a loan transfer from the specified account to another account.
     * The request body should contain the necessary details for the transfer, such as the creditor information,
     * amount, currency, and additional details.
     *
     * @param transferRequest The Transfer Request Object containing the details for the loan transfer.
     * @param accountId       The unique identifier of the account initiating the loan transfer.
     * @return ResponseEntity<String> A response entity containing the result of the loan transfer operation
     * returned as a JSON or XML object, depending on the requested media type.
     * @implNote This endpoint is accessible via the HTTP POST method.
     * The request body should be formatted in either JSON or XML.
     * The produced media types include both XML and JSON formats for the response.
     * The method logs relevant information about the request, including account ID,
     * transfer details, and the requester's thread.
     */

    @Operation(
            summary = "TRANSFER CREDIT",
            description = "Endpoint to make a loan."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan credit transferred successfully",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = String.class)
                            )
                    }),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ErrorMessage.class)
                            )
                    })
    })
    @PostMapping(path = "/{accountId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LoanTransferRest> transferLoan(@RequestBody LoanTransferRequest transferRequest,
                                                         @PathVariable("accountId") Long accountId) {
        LOG.info("POST API ENDPOINT REQUEST | Transfer Loan - Account ID: {}  - Request: {} - Requested By: {}",
                accountId,
                transferRequest.toString(),
                Thread.currentThread().getName());
        var returnValue = transferService.transferLoan(accountId, transferRequest);
        return new ResponseEntity<>(returnValue, new HttpHeaders(), HttpStatus.OK);
    }

}
