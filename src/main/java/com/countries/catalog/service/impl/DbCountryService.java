package com.countries.catalog.service.impl;

import com.countries.catalog.data.CountryEntity;
import com.countries.catalog.data.repository.CountriesRepository;
import com.countries.catalog.domain.CountryJson;
import com.countries.catalog.domain.graphql.CountryGql;
import com.countries.catalog.domain.graphql.CountryInputGql;
import com.countries.catalog.exception.CountryAlreadyExistsException;
import com.countries.catalog.exception.CountryNotFoundException;
import com.countries.catalog.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Nonnull;
import java.util.ArrayList;
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
  public List<CountryJson> allCountries() {
    return countriesRepository.findAll()
      .stream()
      .map(CountryJson::fromEntity)
      .toList();
  }

  @Nonnull
  @Transactional(readOnly = true)
  @Override
  public CountryJson findByIsoCode(String code) {
    return CountryJson.fromGql(findByIsoCodeGql(code));
  }

  @Nonnull
  @Transactional
  @Override
  public CountryJson save(@RequestBody CountryJson country) {
    if (country == null) {
      throw new IllegalArgumentException("Country to save can not be null!");
    } else if (country.isoCode().isEmpty()) {
      throw new CountryAlreadyExistsException("Country with ISO code " + country.isoCode() + " already exists!");
    }

    CountryEntity ce = CountryEntity.fromJson(country);
    return CountryJson.fromEntity(countriesRepository.save(ce));
  }

  @Nonnull
  @Transactional
  @Override
  public CountryJson update(@RequestBody CountryJson country) {
    CountryEntity ce = countriesRepository
      .findByIsoCode(country.isoCode())
      .orElseThrow(() -> new CountryNotFoundException("Country with ISO code " + country.isoCode() + " was not found! Try to create it first."));

    var coordinates = country.coordinates();

    if (coordinates == null || coordinates.isEmpty()) {
      throw new IllegalArgumentException("Coordinates to update can not be null or empty!");
    }

    ce.setCoordinates(coordinates);

    return CountryJson.fromEntity(countriesRepository.save(ce));
  }

  @Nonnull
  @Transactional(readOnly = true)
  @Override
  public Page<CountryGql> allCountriesGql(Pageable pageable) {
    return countriesRepository.findAll(pageable)
      .map(CountryGql::fromEntity);
  }

  @Nonnull
  @Override
  public List<CountryGql> findAll() {
    return countriesRepository.findAll().stream()
      .map(CountryGql::fromEntity)
      .toList();
  }

  @Nonnull
  @Transactional(readOnly = true)
  @Override
  public CountryGql findByIsoCodeGql(String code) {
    return countriesRepository.findByIsoCode(code)
      .stream()
      .map(CountryGql::fromEntity)
      .findFirst()
      .orElseThrow(() -> new CountryNotFoundException("Country with ISO code " + code + " was not found! Try to create it first."));
  }

  @Nonnull
  @Transactional
  @Override
  public CountryGql saveGql(@RequestBody CountryInputGql country) {
    if (country == null) {
      throw new IllegalArgumentException("Country to save can not be null!");
    } else if (findOptionalByIsoCode(country.isoCode()).isPresent()) {
      throw new CountryAlreadyExistsException("Country with ISO code " + country.isoCode() + " already exists!");
    }

    CountryEntity ce = CountryEntity.fromGql(country);
    return CountryGql.fromEntity(countriesRepository.save(ce));
  }

  @Nonnull
  @Override
  public List<CountryGql> saveAllGql(List<CountryInputGql> countries) {
    if (countries.isEmpty()) {
      throw new IllegalArgumentException("List of countries can't be null!");
    }
    List<CountryGql> created = new ArrayList<>();
    for (CountryInputGql country : countries) {
      created.add(saveGql(country));
    }
    return created;
  }

  @Nonnull
  @Transactional
  @Override
  public CountryGql updateGql(@RequestBody CountryInputGql country) {
    CountryEntity ce = countriesRepository
      .findByIsoCode(country.isoCode())
      .orElseThrow(() -> new CountryNotFoundException("Country with ISO code " + country.isoCode() + " was not found! Try to create it first."));

    var coordinates = country.coordinates();

    if (coordinates == null || coordinates.isEmpty()) {
      throw new IllegalArgumentException("Coordinates to update can not be null or empty!");
    }

    ce.setCoordinates(coordinates);

    return CountryGql.fromEntity(countriesRepository.save(ce));
  }

  @Nonnull
  @Transactional(readOnly = true)
  private Optional<CountryJson> findOptionalByIsoCode(String code) {
    return countriesRepository.findByIsoCode(code)
      .stream()
      .map(CountryJson::fromEntity)
      .findFirst();
  }
}
