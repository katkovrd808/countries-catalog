package com.countries.catalog.domain.graphql;

import com.countries.catalog.data.CountryEntity;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.UUID;

public record CountryGql(
  UUID id,
  String name,
  String isoCode,
  List<List<List<List<Double>>>> coordinates
) {

  @Nonnull
  public static CountryGql fromEntity(CountryEntity countryEntity) {
    return new CountryGql(
      countryEntity.getId(),
      countryEntity.getName(),
      countryEntity.getIsoCode(),
      countryEntity.getCoordinates()
    );
  }
}
