package com.beerhouse.converter;

import com.beerhouse.dto.InBeer;
import com.beerhouse.model.Beer;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

import static java.util.Objects.nonNull;

@Component
public class ConverterInBeer implements BiFunction<InBeer,Long , Beer> {

    @Override
    public Beer apply(InBeer inBeer, Long id) {
        return Beer.builder()
                .id(id)
                .name(inBeer.getName())
                .ingredients(inBeer.getIngredients())
                .alcoholContent(inBeer.getAlcoholContent())
                .price(inBeer.getPrice())
                .category(inBeer.getCategory())
                .build();
    }

    public Beer apply(InBeer inBeer, Beer beer) {
        return Beer.builder()
                .id(beer.getId())
                .name(nonNull(inBeer.getName()) ? inBeer.getName() : beer.getName())
                .ingredients(nonNull(inBeer.getIngredients()) ? inBeer.getIngredients() : beer.getIngredients())
                .alcoholContent(nonNull(inBeer.getAlcoholContent()) ? inBeer.getAlcoholContent() : beer.getAlcoholContent())
                .price(nonNull(inBeer.getPrice()) ? inBeer.getPrice() : beer.getPrice())
                .category(nonNull(inBeer.getCategory()) ? inBeer.getCategory() : beer.getCategory())
                .build();
    }
}
