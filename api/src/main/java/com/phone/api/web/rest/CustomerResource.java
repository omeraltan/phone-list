package com.phone.api.web.rest;

import com.phone.api.domain.Customer;
import com.phone.api.exception.BadRequestException;
import com.phone.api.exception.ResourceNotFoundException;
import com.phone.api.repository.CustomerRepository;
import com.phone.api.service.CustomerService;
import com.phone.api.service.dto.CustomerDTO;
import com.phone.api.utilty.HeaderUtil;
import com.phone.api.utilty.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * REST controller for managing {@link Customer}.
 */
@RestController
@RequestMapping("/api/customer")
@CrossOrigin(maxAge = 3600)
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);
    private static final String ENTITY_NAME = "Customer";

    @Value("phoneListApp")
    private String applicationName;

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerResource(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    /**
     * {@code GET  /customer/:id} : get the "id" customer.
     *
     * @param id the id of the customerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @Operation(
        description = "Get Customer Service",
        responses = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse", description = "Found the Customer", content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        },
        summary = "Get A Customer"
    )
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id") Long id) {
        log.debug("REST request to get Customer : {}", id);
        CustomerDTO customerDTO = customerService.findOne(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not Found Customer with id = " + id));
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
     */
    @GetMapping("")
    @Operation(
        description = "Get Customers Service",
        responses = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse", description = "Found the Customer/Customers", content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        },
        summary = "Get All Customers"
    )
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Customers");

        Page<CustomerDTO> page = customerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code POST  /customers} : Create a new customer.
     *
     * @param customerDTO the customerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerDTO, or with status {@code 400 (Bad Request)} if the customer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @Operation(
        description = "Create Customer Service",
        responses = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse", description = "Found the Customer", content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        },
        summary = "Create A Customer"
    )
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws URISyntaxException {
        log.debug("REST request to save Customer : {}", customerDTO);
        if (customerDTO.getId() != null) {
            throw new BadRequestException("A new customer cannot already have an ID " +  ENTITY_NAME + " id exists");
        }
        customerDTO = customerService.save(customerDTO);
        return ResponseEntity.created(new URI("/api/customers/" + customerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, customerDTO.getId().toString()))
            .body(customerDTO);
    }

}
