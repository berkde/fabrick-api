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

package com.service.fabrickapi.exception;

import com.service.fabrickapi.model.error.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @param ex The exception object
     * @return ResponseEntity<Object>
     * @implNote These methods are global exception handlers for all exceptions in the application.
     * they handle the exceptions by creating an ErrorMessage object and returning it as a ResponseEntity.
     * The ErrorMessage object contains the error message and the current date.
     * The ResponseEntity is returned with a status code.
     * This status code indicates that the server encountered
     */

    @ExceptionHandler(AccountServiceException.class)
    public ResponseEntity<?> handleAccountServiceException(AccountServiceException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getLocalizedMessage(), new Date());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), getStatusCode(ex));
    }

    @ExceptionHandler(TransferServiceException.class)
    public ResponseEntity<?> handleTransferServiceException(TransferServiceException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getLocalizedMessage(), new Date());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), getStatusCode(ex));
    }

    @ExceptionHandler(FabrickRestServiceException.class)
    public ResponseEntity<?> handleFabrickRestServiceException(FabrickRestServiceException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getLocalizedMessage(), new Date());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), getStatusCode(ex));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherExceptions(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getLocalizedMessage(), new Date());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpStatus getStatusCode(Exception ex) {
        if (ex.getMessage().contains("401 Unauthorized")) {
            return HttpStatus.UNAUTHORIZED;
        } else if (ex.getMessage().contains("403 Forbidden")) {
            return HttpStatus.FORBIDDEN;
        } else if (ex.getMessage().contains("400 Bad Request")) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("404 Not Found")) {
            return HttpStatus.NOT_FOUND;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
