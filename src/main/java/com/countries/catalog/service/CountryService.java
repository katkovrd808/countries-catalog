package com.countries.catalog.service;

import com.countries.catalog.domain.CountryJson;

import java.util.List;
import java.util.Optional;

public interface CountryService {

  List<CountryJson> allCountries();

  CountryJson findByIsoCode(String code);

  CountryJson save(CountryJson country);

  CountryJson update(CountryJson country);

}
