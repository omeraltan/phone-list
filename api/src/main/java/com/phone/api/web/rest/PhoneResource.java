package com.phone.api.web.rest;

import com.phone.api.domain.Phone;
import com.phone.api.exception.BadRequestException;
import com.phone.api.service.PhoneService;
import com.phone.api.service.dto.CustomerDTO;
import com.phone.api.service.dto.PhoneDTO;
import com.phone.api.utilty.HeaderUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/phone")
@CrossOrigin(maxAge = 3600)
@Tag(name = "Phone", description = "Operations related to phones")
public class PhoneResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);
    private static final String ENTITY_NAME = "phone";
    private final PhoneService phoneService;

    @Value("phoneListApp")
    private String applicationName;

    public PhoneResource(PhoneService phoneService) {
        this.phoneService = phoneService;
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
            @ApiResponse(responseCode = "200", ref = "successfulResponse", description = "Found the Phone", content = @Content(schema = @Schema(implementation = PhoneDTO.class))),
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
