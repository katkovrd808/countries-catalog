package com.countries.catalog.service;

import com.countries.catalog.domain.CountryJson;
import com.countries.catalog.domain.graphql.CountryGql;
import com.countries.catalog.domain.graphql.CountryInputGql;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CountryService {

  List<CountryJson> allCountries();

  Slice<CountryGql> allCountriesGql(Pageable pageable);

  CountryJson findByIsoCode(String code);

  CountryGql findByIsoCodeGql(String code);

  CountryJson save(CountryJson country);

  CountryGql saveGql(CountryInputGql country);

  CountryJson update(CountryJson country);

  CountryGql updateGql(CountryInputGql country);
}
