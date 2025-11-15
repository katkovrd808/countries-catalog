package com.countries.catalog.controller.graphql;

import com.countries.catalog.domain.graphql.CountryGql;
import com.countries.catalog.domain.graphql.CountryInputGql;
import com.countries.catalog.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class CountriesMutationController {

  private final CountryService countryService;

  @Autowired
  public CountriesMutationController(CountryService countryService) {
    this.countryService = countryService;
  }

  @MutationMapping
  @ResponseStatus(HttpStatus.CREATED)
  CountryGql addCountry(@Argument CountryInputGql input) {
    return countryService.saveGql(input);
  }

  @MutationMapping
  @ResponseStatus(HttpStatus.OK)
  CountryGql updateCountry(@Argument CountryInputGql input) {
    return countryService.updateGql(input);
  }
}
