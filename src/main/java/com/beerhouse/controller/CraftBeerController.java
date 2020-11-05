package com.beerhouse.controller;

import com.beerhouse.converter.ConverterInBeer;
import com.beerhouse.dto.InBeer;
import com.beerhouse.exception.ResourceNotFoundException;
import com.beerhouse.model.Beer;
import com.beerhouse.repository.CraftBeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/beers")
public class CraftBeerController {

    private final CraftBeerRepository craftBeerRepository;
    private final ConverterInBeer converterInBeer;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Beer beer) {
        craftBeerRepository.save(beer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(beer.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Beer>> getAll() {
        return new ResponseEntity<>(craftBeerRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Beer> get(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(craftBeerRepository.findById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") long id, @RequestBody @Valid InBeer inBeer) {
        Beer beer = converterInBeer.apply(inBeer, id);
        craftBeerRepository.save(beer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> patch(@PathVariable(value = "id") long id, @RequestBody InBeer inBeer) {
        Beer beer = craftBeerRepository.findById(id);
        if (isNull(beer)) {
            throw new ResourceNotFoundException("Beer Not Found for ID:" + id);
        }
        beer = converterInBeer.apply(inBeer, beer);
        craftBeerRepository.save(beer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") long id) {
        craftBeerRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
