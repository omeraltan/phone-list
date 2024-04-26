package com.phone.api.web.rest;

import com.phone.api.repository.DistrictRepository;
import com.phone.api.service.DistrictService;
import com.phone.api.service.dto.DistrictDTO;
import com.phone.api.utilty.HeaderUtil;
import com.phone.api.utilty.PaginationUtil;
import com.phone.api.utilty.ResponseUtil;
import com.phone.api.web.rest.errors.BadRequestAlertException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * swagger address : http://localhost:8080/swagger-ui/index.html
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
    public ResponseEntity<DistrictDTO> getDistrict(@PathVariable("id") Long id) {
        log.debug("REST request to get District : {}", id);
        Optional<DistrictDTO> districtDTO = districtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(districtDTO);
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
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError"),
            @ApiResponse(responseCode = "200", ref = "successfulResponse")
        }
    )
    public ResponseEntity<List<DistrictDTO>> getAllDistricts(Pageable pageable) {
        log.debug("REST request to get a page of Districts");
        Page<DistrictDTO> page = districtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code POST  /districts} : Create a new district.
     *
     * @param districtDTO the districtDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new districtDTO, or with status {@code 400 (Bad Request)} if the district has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DistrictDTO> createDistrict(@Valid @RequestBody DistrictDTO districtDTO) throws URISyntaxException {
        log.debug("REST request to save District : {}", districtDTO);
        if (districtDTO.getId() != null) {
            throw new BadRequestAlertException("A new district cannot already have an ID", ENTITY_NAME, "id exists");
        }
        districtDTO = districtService.save(districtDTO);
        return ResponseEntity.created(new URI("/api/districts/" + districtDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, districtDTO.getId().toString()))
            .body(districtDTO);
    }




}
