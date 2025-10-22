package com.countries.catalog.controller;

import com.countries.catalog.domain.CountryJson;
import com.countries.catalog.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/countries")
public class CountriesController {

  private final CountryService countryService;

  @Autowired
  public CountriesController(CountryService countryService) {
    this.countryService = countryService;
  }

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  List<CountryJson> all() {
    return countryService.allCountries();
  }

  @GetMapping("/{isoCode}")
  @ResponseStatus(HttpStatus.OK)
  CountryJson getByIso(@PathVariable String isoCode) {
    return countryService.findByIsoCode(isoCode);
  }

  @PostMapping("/add")
  @ResponseStatus(HttpStatus.CREATED)
  CountryJson save(@RequestBody CountryJson country) {
    return countryService.save(country);
  }

  @PutMapping("/update/{code}")
  @ResponseStatus(HttpStatus.OK)
  CountryJson update(@PathVariable String code, @RequestBody CountryJson country) {
    return countryService.update(country);
  }
}
