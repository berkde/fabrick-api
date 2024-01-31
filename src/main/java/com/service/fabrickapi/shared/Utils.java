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

package com.service.fabrickapi.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.fabrickapi.configuration.Credentials;
import com.service.fabrickapi.exception.AccountServiceException;
import com.service.fabrickapi.exception.FabrickRestServiceException;
import com.service.fabrickapi.model.error.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Collections;

import static com.service.fabrickapi.model.error.ErrorMessages.INVALID_JSON_ERROR;

@Component
public class Utils {
    private final Logger LOG = LoggerFactory.getLogger(Utils.class);
    private final Credentials credentials;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    /**
     * Constructor for Utils class.
     *
     * @param restTemplate The RestTemplate used for making HTTP requests.
     * @param credentials  The credentials object containing the API key and authentication schema.
     * @param objectMapper The ObjectMapper used for parsing JSON responses.
     */

    @Autowired
    public Utils(Credentials credentials, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.credentials = credentials;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    /**
     * @return Http Headers necessary to make API calls
     */
    public HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Auth-Schema", credentials.authSchema());
        headers.set("Api-Key", credentials.apiKey());
        headers.set("X-Time-Zone", "Europe/Rome");
        return headers;
    }

    /**
     * Builds a URL for a specific account.
     *
     * @param path      The path to the resource.
     * @param accountId The ID of the account.
     * @return The URL for the resource.
     */
    public String buildUrl(String path, Long accountId) {
        return UriComponentsBuilder.fromHttpUrl(credentials.baseURL() + path)
                .buildAndExpand(accountId)
                .toUriString();
    }

    /**
     * Builds a URL for a specific account with query parameters.
     *
     * @param path               The path to the resource.
     * @param accountId          The ID of the account.
     * @param fromAccountingDate The start date for the query.
     * @param toAccountingDate   The end date for the query.
     * @return The URL for the resource.
     */
    public String buildUrlWithQueryParams(String path, Long accountId, String fromAccountingDate, String toAccountingDate) {
        return UriComponentsBuilder.fromHttpUrl(credentials.baseURL() + path)
                .queryParam("fromAccountingDate", fromAccountingDate)
                .queryParam("toAccountingDate", toAccountingDate)
                .buildAndExpand(accountId)
                .toUriString();
    }

    /**
     * Builds a URL for a specific account with query parameters.
     *
     * @param uri    The path to the API
     * @param entity The Header and Body of the request
     * @return ResponseEntity<String> The response entity containing the response body.
     * @throws RestClientException If an error occurs during the request.
     */
    public ResponseEntity<String> getStringResponseEntity(String uri, HttpEntity<?> entity, HttpMethod httpMethod) {
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(
                    uri,
                    httpMethod,
                    entity,
                    String.class
            );

            LOG.info(exchange.getBody());
            return exchange;

        } catch (RestClientException e) {
            LOG.error("FAILED TO MAKE THE REST API CALL");
            throw new RestClientException(e.getMessage());
        }
    }


    /**
     * Converts an object to its JSON representation.
     *
     * @param object The object to be converted.
     * @param <K>    The type of the object.
     * @return String The JSON representation of the object.
     * @throws AccountServiceException If an error occurs during JSON conversion.
     */
    public <K> String toJson(K object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOG.error("FAILED TO CONVERT THE CLASS OBJECT TO JSON OBJECT");
            throw new FabrickRestServiceException(INVALID_JSON_ERROR.getMessage());
        }
    }

    /**
     * Converts a JSON string to an object of the specified class.
     *
     * @param jsonString The JSON string to be converted.
     * @param clazz      The class of the target object.
     * @param <K>        The type of the target object.
     * @return K The object converted from JSON.
     * @throws FabrickRestServiceException If an error occurs during JSON conversion.
     */
    public <K> K fromJson(String jsonString, Class<K> clazz) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode payloadNode = jsonNode.path("payload");

            if (payloadNode.isMissingNode())
                throw new AccountServiceException(INVALID_JSON_ERROR.getMessage());

            return objectMapper.readValue(payloadNode.toString(), clazz);
        } catch (JsonProcessingException e) {
            LOG.error("FAILED TO CONVERT THE JSON OBJECT TO CLASS OBJECT");
            throw new FabrickRestServiceException(ErrorMessages.INVALID_JSON_ERROR.getMessage());
        }
    }


    /**
     * Converts a JSON string to a collection of objects.
     *
     * @param json            The JSON string to be converted.
     * @param collectionClass The class of the target collection.
     * @param elementClass    The class of the elements in the collection.
     * @param <K>             The type of the elements in the collection.
     * @return K The collection of objects converted from JSON.
     * @throws AccountServiceException If an error occurs during JSON conversion.
     */
    public <K> K fromJson(String json, Class<? extends Collection> collectionClass, Class<?> elementClass) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);

            JsonNode payloadNode = jsonNode.path("payload").path("list");
            if (payloadNode.isMissingNode())
                throw new AccountServiceException(INVALID_JSON_ERROR.getMessage());

            JavaType type = new ObjectMapper().getTypeFactory().constructCollectionType(collectionClass, elementClass);
            return new ObjectMapper().readValue(payloadNode.toString(), type);

        } catch (JsonProcessingException e) {
            LOG.error("FAILED TO CONVERT THE JSON OBJECT TO LIST CLASS OBJECT");
            throw new FabrickRestServiceException(ErrorMessages.INVALID_JSON_ERROR.getMessage());
        }
    }

}
