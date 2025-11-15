package com.countries.catalog.controller.graphql;

import com.countries.catalog.domain.graphql.CountryGql;
import com.countries.catalog.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class CountriesQueryController {

  private final CountryService countryService;

  @Autowired
  public CountriesQueryController(CountryService countryService) {
    this.countryService = countryService;
  }

  @QueryMapping
  @ResponseStatus(HttpStatus.OK)
  Slice<CountryGql> countries(@Argument int page, @Argument int size) {
    return countryService.allCountriesGql(PageRequest.of(
      page, size
    ));
  }

  @QueryMapping
  @ResponseStatus(HttpStatus.OK)
  CountryGql country(@Argument String isoCode) {
    return countryService.findByIsoCodeGql(isoCode);
  }
}
