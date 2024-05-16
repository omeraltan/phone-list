package com.phone.api.web.rest;

import com.phone.api.exception.BadRequestException;
import com.phone.api.exception.ResourceNotFoundException;
import com.phone.api.service.PhoneService;
import com.phone.api.service.dto.CustomerDTO;
import com.phone.api.service.dto.PhoneDTO;
import com.phone.api.utilty.HeaderUtil;
import com.phone.api.utilty.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phone")
@CrossOrigin(maxAge = 3600)
@Tag(name = "Phone", description = "Operations related to phones")
public class PhoneResource {

    private final Logger log = LoggerFactory.getLogger(PhoneResource.class);
    private static final String ENTITY_NAME = "phone";
    private final PhoneService phoneService;

    @Value("phoneListApp")
    private String applicationName;

    public PhoneResource(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
     */
    @GetMapping("/customers")
    @Operation(
        description = "Retrieves the list of all customers for use in phone dropdown menus.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        },
        summary = "Get All Customers For Phones (Dropdown)"
    )
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        log.debug("REST request to get all Customers for phone page");
        Optional<List<CustomerDTO>> customerDTO = phoneService.findAll();
        if (!customerDTO.isPresent()) {
            throw new ResourceNotFoundException("Not Found Customer / Customers");
        }
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO.get());
    }

    /**
     * {@code GET  /phones} : get all the phones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phones in body.
     */
    @GetMapping("/phones")
    @Operation(
        description = "Get All Phones Service",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PhoneDTO.class))),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        },
        summary = "Get All Phones"
    )
    public ResponseEntity<List<PhoneDTO>> getAllPhones(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Phones");

        Page<PhoneDTO> page = phoneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code POST  /phone} : Create a new phone.
     *
     * @param phoneDTO the phoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phoneDTO, or with status {@code 400 (Bad Request)} if the phone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @Operation(
        description = "Create Phone Service",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PhoneDTO.class))),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        },
        summary = "Create A Phone"
    )
    public ResponseEntity<PhoneDTO> createPhone(@Valid @RequestBody PhoneDTO phoneDTO) throws URISyntaxException {
        log.debug("REST request to save phone : {}", phoneDTO);
        if (phoneDTO.getId() != null) {
            throw new BadRequestException("A new phone cannot already have an ID" + ENTITY_NAME + "id exists");
        }
        phoneDTO = phoneService.save(phoneDTO);
        return ResponseEntity.created(new URI("/api/phone/" + phoneDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, phoneDTO.getId().toString()))
            .body(phoneDTO);
    }
}
