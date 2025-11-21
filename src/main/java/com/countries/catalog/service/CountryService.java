package com.countries.catalog.service;

import com.countries.catalog.domain.CountryJson;
import com.countries.catalog.domain.graphql.CountryGql;
import com.countries.catalog.domain.graphql.CountryInputGql;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import javax.annotation.Nullable;
import java.util.List;

public interface CountryService {

  List<CountryJson> allCountries();

  Slice<CountryGql> allCountriesGql(@Nullable Pageable pageable);

  List<CountryGql> findAll();

  CountryJson findByIsoCode(String code);

  CountryGql findByIsoCodeGql(String code);

  CountryJson save(CountryJson country);

  CountryGql saveGql(CountryInputGql country);

  List<CountryGql> saveAllGql(List<CountryInputGql> countries);

  CountryJson update(CountryJson country);

  CountryGql updateGql(CountryInputGql country);
}
