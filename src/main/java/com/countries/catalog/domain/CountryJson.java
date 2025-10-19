package com.countries.catalog.domain;

import com.countries.catalog.data.CountryEntity;
import jakarta.annotation.Nonnull;

import java.util.List;

public record CountryJson(
  String name,
  String isoCode,
  List<List<List<List<Double>>>> coordinates
) {

  @Nonnull
  public static CountryJson fromEntity(CountryEntity countryEntity) {
    return new CountryJson(
      countryEntity.getName(),
      countryEntity.getIsoCode(),
      countryEntity.getCoordinates()
    );
  }
}
