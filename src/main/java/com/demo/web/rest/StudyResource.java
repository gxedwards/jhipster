package com.demo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.demo.domain.Study;
import com.demo.repository.StudyRepository;
import com.demo.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Study.
 */
@RestController
@RequestMapping("/api")
public class StudyResource {

    private final Logger log = LoggerFactory.getLogger(StudyResource.class);

    @Inject
    private StudyRepository studyRepository;

    /**
     * POST  /studys -> Create a new study.
     */
    @RequestMapping(value = "/studys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Study study) throws URISyntaxException {
        log.debug("REST request to save Study : {}", study);
        if (study.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new study cannot already have an ID").build();
        }
        studyRepository.save(study);
        return ResponseEntity.created(new URI("/api/studys/" + study.getId())).build();
    }

    /**
     * PUT  /studys -> Updates an existing study.
     */
    @RequestMapping(value = "/studys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Study study) throws URISyntaxException {
        log.debug("REST request to update Study : {}", study);
        if (study.getId() == null) {
            return create(study);
        }
        studyRepository.save(study);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /studys -> get all the studys.
     */
    @RequestMapping(value = "/studys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Study>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Study> page = studyRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/studys", offset, limit);
        return new ResponseEntity<List<Study>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /studys/:id -> get the "id" study.
     */
    @RequestMapping(value = "/studys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Study> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Study : {}", id);
        Study study = studyRepository.findOne(id);
        if (study == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(study, HttpStatus.OK);
    }

    /**
     * DELETE  /studys/:id -> delete the "id" study.
     */
    @RequestMapping(value = "/studys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Study : {}", id);
        studyRepository.delete(id);
    }
}
