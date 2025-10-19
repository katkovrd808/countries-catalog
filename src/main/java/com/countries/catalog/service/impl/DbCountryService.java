package com.countries.catalog.service.impl;

import com.countries.catalog.data.CountryEntity;
import com.countries.catalog.data.repository.CountriesRepository;
import com.countries.catalog.domain.CountryJson;
import com.countries.catalog.exception.CountryAlreadyExistsException;
import com.countries.catalog.exception.CountryNotFoundException;
import com.countries.catalog.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class DbCountryService implements CountryService {

  private final CountriesRepository countriesRepository;

  @Autowired
  public DbCountryService(CountriesRepository countriesRepository) {
    this.countriesRepository = countriesRepository;
  }

  @Transactional(readOnly = true)
  @Override
  public List<CountryJson> allCountries() {
    return countriesRepository.findAll()
      .stream()
      .map(CountryJson::fromEntity)
      .toList();
  }

  @Transactional(readOnly = true)
  @Override
  public CountryJson findByIsoCode(String code) {
    return countriesRepository.findByIsoCode(code)
      .stream()
      .map(CountryJson::fromEntity)
      .findFirst()
      .orElseThrow(
        () -> new CountryNotFoundException("Country with ISO code " + code + " was not found! Try to create it first.")
      );
  }

  @Transactional(readOnly = true)
  public Optional<CountryJson> findOptionalByIsoCode(String code) {
    return countriesRepository.findByIsoCode(code)
      .stream()
      .map(CountryJson::fromEntity)
      .findFirst();
  }

  @Transactional
  @Override
  public CountryJson save(@RequestBody CountryJson country) {
    if (country == null) {
      throw new IllegalArgumentException("Country to save can not be null!");
    }

    final String isoCode = country.isoCode();

    if (findOptionalByIsoCode(isoCode).isPresent()) {
      throw new CountryAlreadyExistsException("Country with ISO code " + isoCode + " already exists!");
    }

    CountryEntity ce = CountryEntity.fromJson(country);
    return CountryJson.fromEntity(countriesRepository.save(ce));
  }

  @Transactional
  @Override
  public CountryJson update(@RequestBody CountryJson country) {
    CountryEntity ce = countriesRepository.findByIsoCode(country.isoCode()).orElseThrow(
      () -> new CountryNotFoundException("Country with ISO code " + country.isoCode() + " was not found! Try to create it first.")
    );

    final List<List<List<List<Double>>>> coordinates = country.coordinates();

    if (coordinates == null || coordinates.isEmpty()) {
      throw new IllegalArgumentException("Coordinates to update can not be null or empty!");
    }

    ce.setCoordinates(coordinates);

    return CountryJson.fromEntity(countriesRepository.save(ce));
  }
}
