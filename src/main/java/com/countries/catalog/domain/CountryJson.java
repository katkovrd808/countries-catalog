package com.countries.catalog.domain;

import com.countries.catalog.data.CountryEntity;
import com.countries.catalog.domain.graphql.CountryGql;
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

  @Nonnull
  public static CountryJson fromGql(CountryGql CountryGql) {
    return new CountryJson(
      CountryGql.name(),
      CountryGql.isoCode(),
      CountryGql.coordinates()
    );
  }
}
