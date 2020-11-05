package com.beerhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InBeer {
    @NotEmpty
    private String name;
    @NotEmpty
    private String ingredients;
    @NotEmpty
    private String alcoholContent;
    @NotNull
    private Number price;
    @NotEmpty
    private String category;
}
