package cm.elsha.cardreport.web.rest;

import com.codahale.metrics.annotation.Timed;
import cm.elsha.cardreport.domain.Classe;

import cm.elsha.cardreport.repository.ClasseRepository;
import cm.elsha.cardreport.web.rest.util.HeaderUtil;
import cm.elsha.cardreport.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Classe.
 */
@RestController
@RequestMapping("/api")
public class ClasseResource {

    private final Logger log = LoggerFactory.getLogger(ClasseResource.class);
        
    @Inject
    private ClasseRepository classeRepository;

    /**
     * POST  /classes : Create a new classe.
     *
     * @param classe the classe to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classe, or with status 400 (Bad Request) if the classe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/classes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Classe> createClasse(@Valid @RequestBody Classe classe) throws URISyntaxException {
        log.debug("REST request to save Classe : {}", classe);
        if (classe.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("classe", "idexists", "A new classe cannot already have an ID")).body(null);
        }
        Classe result = classeRepository.save(classe);
        return ResponseEntity.created(new URI("/api/classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("classe", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /classes : Updates an existing classe.
     *
     * @param classe the classe to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classe,
     * or with status 400 (Bad Request) if the classe is not valid,
     * or with status 500 (Internal Server Error) if the classe couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/classes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Classe> updateClasse(@Valid @RequestBody Classe classe) throws URISyntaxException {
        log.debug("REST request to update Classe : {}", classe);
        if (classe.getId() == null) {
            return createClasse(classe);
        }
        Classe result = classeRepository.save(classe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("classe", classe.getId().toString()))
            .body(result);
    }

    /**
     * GET  /classes : get all the classes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of classes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/classes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Classe>> getAllClasses(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Classes");
        Page<Classe> page = classeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /classes/:id : get the "id" classe.
     *
     * @param id the id of the classe to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classe, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/classes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Classe> getClasse(@PathVariable Long id) {
        log.debug("REST request to get Classe : {}", id);
        Classe classe = classeRepository.findOne(id);
        return Optional.ofNullable(classe)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /classes/:id : delete the "id" classe.
     *
     * @param id the id of the classe to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/classes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        log.debug("REST request to delete Classe : {}", id);
        classeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("classe", id.toString())).build();
    }

}
