package com.phone.api.web.rest;

import com.phone.api.repository.DistrictRepository;
import com.phone.api.service.DistrictService;
import com.phone.api.service.dto.DistrictDTO;
import com.phone.api.utilty.HeaderUtil;
import com.phone.api.utilty.PaginationUtil;
import com.phone.api.utilty.ResponseUtil;
import com.phone.api.web.rest.errors.BadRequestAlertException;
import com.phone.api.web.rest.errors.BadRequestException;
import com.phone.api.web.rest.errors.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

/**
 * swagger address : http://localhost:8080/swagger-ui/index.html
 * http://localhost:8080/v3/api-docs
 * springdoc.api-docs.path=/api-docs
 * http://localhost:8080/api-docs
 */

/**
 * REST controller for managing {@link com.phone.api.domain.District}.
 */
@RestController
@RequestMapping("/api/district")
public class DistrictResource {

    private final Logger log = LoggerFactory.getLogger(DistrictResource.class);
    private static final String ENTITY_NAME = "district";

    @Value("phoneListApp")
    private String applicationName;

    private final DistrictService districtService;
    private final DistrictRepository districtRepository;

    public DistrictResource(DistrictService districtService, DistrictRepository districtRepository) {
        this.districtService = districtService;
        this.districtRepository = districtRepository;
    }

    /**
     * {@code GET  /districts/:id} : get the "id" district.
     *
     * @param id the id of the districtDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the districtDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @Operation(
        description = "Get District Service",
        responses = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse", description = "Found the District", content = @Content(schema = @Schema(implementation = DistrictDTO.class))),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        },
        summary = "Get A District"
    )
    public ResponseEntity<DistrictDTO> getDistrict(@PathVariable("id") Long id) {
        log.debug("REST request to get District : {}", id);
        DistrictDTO districtDTO = districtService.findOne(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not Found District with id = " + id));
        return new ResponseEntity<>(districtDTO, HttpStatus.OK);
    }

    /**
     * {@code GET  /districts} : get all the districts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districts in body.
     */
    @GetMapping("")
    @Operation(
        description = "Get Districts Service",
        responses = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse", description = "Found the District/Districts", content = @Content(schema = @Schema(implementation = DistrictDTO.class))),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        },
        summary = "Get All Districts"
    )
    public ResponseEntity<List<DistrictDTO>> getAllDistricts(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Districts");

        Page<DistrictDTO> page = districtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code PUT  /districts/:id} : Updates an existing district.
     *
     * @param id the id of the districtDTO to save.
     * @param districtDTO the districtDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtDTO,
     * or with status {@code 400 (Bad Request)} if the districtDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the districtDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @Operation(
        description = "Update District Service",
        responses = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse", description = "Found the District", content = @Content(schema = @Schema(implementation = DistrictDTO.class))),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        },
        summary = "Update A District"
    )
    public ResponseEntity<DistrictDTO> updateDistrict(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody DistrictDTO districtDTO) throws URISyntaxException {
        log.debug("REST request to update District : {}, {}", id, districtDTO);
        if (districtDTO.getId() == null) {
            throw new ResourceNotFoundException("Invalid id = " + districtDTO.getId());
        }
        if (!Objects.equals(id, districtDTO.getId())) {
            throw new ResourceNotFoundException("Invalid id = " + districtDTO.getId());
        }
        if (!districtRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not Found District with id = " + id);
        }
        return new ResponseEntity<>(districtService.update(districtDTO), HttpStatus.OK);
    }

    /**
     * {@code POST  /districts} : Create a new district.
     *
     * @param districtDTO the districtDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new districtDTO, or with status {@code 400 (Bad Request)} if the district has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @Operation(
        description = "Create District Service",
        responses = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse", description = "Found the District", content = @Content(schema = @Schema(implementation = DistrictDTO.class))),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        },
        summary = "Create A District"
    )
    public ResponseEntity<DistrictDTO> createDistrict(@Valid @RequestBody DistrictDTO districtDTO) throws URISyntaxException {
        log.debug("REST request to save District : {}", districtDTO);
        if (districtDTO.getId() != null) {
            throw new BadRequestException("A new district cannot already have an ID " +  ENTITY_NAME + " id exists");
        }
        districtDTO = districtService.save(districtDTO);
        //return new ResponseEntity<>(districtDTO, HttpStatus.CREATED);
        //return ResponseEntity.created(new URI("/api/district/" + districtDTO.getId())).body(districtDTO);

        return ResponseEntity.created(new URI("/api/districts/" + districtDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, districtDTO.getId().toString()))
            .body(districtDTO);
    }

    /**
     * {@code DELETE  /districts/:id} : delete the "id" district.
     *
     * @param id the id of the districtDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @Operation(
            description = "Delete District Service",
        responses = {
            @ApiResponse(responseCode = "200", ref = "successfulResponse", description = "Found the District", content = @Content(schema = @Schema(implementation = DistrictDTO.class))),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        },
        summary = "Delete A District"
    )
    public ResponseEntity<Void> deleteDistrict(@PathVariable("id") Long id) {
        log.debug("REST request to delete District : {}", id);
        if (!districtRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not Found District with id = " + id);
        }
        districtService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
