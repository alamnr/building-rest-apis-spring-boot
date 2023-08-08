package dev.denvega.streamsschdule.controller;

import dev.denvega.streamsschdule.exception.LiveStreamException;
import dev.denvega.streamsschdule.model.LiveStream;
import dev.denvega.streamsschdule.repository.LiveStreamRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/streams")
public class LiveStreamController {

    private final LiveStreamRepository repository;

    public LiveStreamController(LiveStreamRepository repository){
        this.repository = repository;
    }

    // Get  http://localhost:8080/streams
    @GetMapping
    /*public List<LiveStream> findAll(){
        return repository.findAll();
    }*/
    public ResponseEntity<List<LiveStream>>  findAll(){
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.add("my-custom-header","custom-header-value");
        return new ResponseEntity<>(repository.findAll(),responseHeader,200);
        // return ResponseEntity.ok(repository.findAll());
    }

    // Get http://localhost:8080/streams/1bbcd6d0-92f0-4e0c-b67d-dac73e84599a
    @GetMapping("/{id}")
    /*
    public  LiveStream findById(@PathVariable String id) throws LiveStreamException {
        return repository.findById(id);
    }   */

    public ResponseEntity<LiveStream> findById(@PathVariable String id) throws LiveStreamException {
        LiveStream stream = repository.findById(id);
        if(stream == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(stream, HttpStatus.OK);
        }
    }

    // Post http://localhost:8080/streams
    /* @ResponseStatus(HttpStatus.CREATED)
    @PostMapping

    public LiveStream create(@Valid @RequestBody LiveStream stream){
        return repository.create(stream);
    } */

    @PostMapping
    public ResponseEntity<LiveStream>  create(@Valid @RequestBody LiveStream stream){
        return new ResponseEntity<>(repository.create(stream),HttpStatus.CREATED);
    }


    // Put http://localhost:8080/streams/9c9c4ab1-8457-456f-aaf4-45fa3c29b41b

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update (@RequestBody LiveStream stream, @PathVariable String id){
         repository.update(stream,id);
    }

    // Delete http://localhost:8080/streams/9c9c4ab1-8457-456f-aaf4-45fa3c29b41b
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        repository.delete(id);
    }
}
