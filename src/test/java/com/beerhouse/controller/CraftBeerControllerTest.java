package com.beerhouse.controller;

import com.beerhouse.converter.ConverterInBeer;
import com.beerhouse.dto.InBeer;
import com.beerhouse.exception.ResourceNotFoundException;
import com.beerhouse.model.Beer;
import com.beerhouse.repository.CraftBeerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CraftBeerControllerTest {
    @InjectMocks
    CraftBeerController craftBeerController;
    @Mock
    CraftBeerRepository craftBeerRepository;
    @Mock
    private ConverterInBeer converterInBeer;

    @Before
    public void createValidator() {
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    }

    @Test
    public void create_beer() {
        Beer beer = Beer.builder()
                .id(1L)
                .name("SKOL")
                .alcoholContent("sim")
                .ingredients("cevada")
                .category("Pilsen")
                .price(8)
                .build();
        when(craftBeerRepository.save(beer)).thenReturn(beer);
        craftBeerController.create(beer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(beer.getId()).toUri();
        ResponseEntity<?> response = craftBeerController.create(beer);
        assertEquals(ResponseEntity.created(location).build(), response);
    }

    @Test
    public void get_all_beers() {
        Beer beer1 = Beer.builder()
                .id(1L)
                .name("SKOL")
                .alcoholContent("sim")
                .ingredients("cevada")
                .category("Pilsen")
                .price(8)
                .build();

        Beer beer2 = Beer.builder()
                .id(1L)
                .name("SKOL")
                .alcoholContent("sim")
                .ingredients("cevada")
                .category("Pilsen")
                .price(8)
                .build();

        List<Beer> beers = Arrays.asList(beer1, beer2);
        when(craftBeerRepository.findAll()).thenReturn(beers);

        ResponseEntity<List<Beer>> response = craftBeerController.getAll();
        assertEquals(beers, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void get_beer() {
        Beer beer = Beer.builder()
                .id(1L)
                .name("SKOL")
                .alcoholContent("sim")
                .ingredients("cevada")
                .category("Pilsen")
                .price(8)
                .build();
        when(craftBeerRepository.findById(1)).thenReturn(beer);
        ResponseEntity<Beer> response = craftBeerController.get(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(beer, response.getBody());
    }

    @Test
    public void update_beer() {
        InBeer inBeer = InBeer.builder()
                .name("SKOL")
                .alcoholContent("sim")
                .ingredients("cevada")
                .category("Pilsen")
                .price(8)
                .build();

        Beer beer = Beer.builder()
                .id(1L)
                .name("SKOL")
                .alcoholContent("sim")
                .ingredients("cevada")
                .category("Pilsen")
                .price(8)
                .build();

        when(converterInBeer.apply(inBeer, 1L)).thenReturn(beer);
        when(craftBeerRepository.save(beer)).thenReturn(beer);
        ResponseEntity<?> response = craftBeerController.update(1L, inBeer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void patch_beer() {
        InBeer inBeer = InBeer.builder()
                .price(8)
                .build();

        Beer beer = Beer.builder()
                .id(1L)
                .name("SKOL")
                .alcoholContent("sim")
                .ingredients("cevada")
                .category("Pilsen")
                .price(10)
                .build();

        Beer beerResponse = Beer.builder()
                .id(1L)
                .name("SKOL")
                .alcoholContent("sim")
                .ingredients("cevada")
                .category("Pilsen")
                .price(8)
                .build();
        when(craftBeerRepository.findById(1L)).thenReturn(beerResponse);
        when(converterInBeer.apply(inBeer, beer)).thenReturn(beerResponse);
        when(craftBeerRepository.save(beerResponse)).thenReturn(beerResponse);
        ResponseEntity<?> response = craftBeerController.patch(1L, inBeer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void not_exist_beer_to_update_partial() {
        InBeer inBeer = InBeer.builder()
                .price(8)
                .build();

        Beer beer = Beer.builder()
                .id(1L)
                .name("SKOL")
                .alcoholContent("sim")
                .ingredients("cevada")
                .category("Pilsen")
                .price(10)
                .build();

        Beer beerResponse = Beer.builder()
                .id(1L)
                .name("SKOL")
                .alcoholContent("sim")
                .ingredients("cevada")
                .category("Pilsen")
                .price(8)
                .build();
        when(converterInBeer.apply(inBeer, beer)).thenReturn(beerResponse);
        when(craftBeerRepository.save(beerResponse)).thenReturn(beerResponse);
        ResponseEntity<?> response = craftBeerController.patch(1L, inBeer);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void delete_beer() {
        ResponseEntity<?> response = craftBeerController.delete(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}