package com.countries.catalog.domain.graphql;

import com.countries.catalog.data.CountryEntity;
import jakarta.annotation.Nonnull;

import java.util.List;

public record CountryInputGql(
  String name,
  String isoCode,
  List<List<List<List<Double>>>> coordinates
) {

  @Nonnull
  public static CountryInputGql fromEntity(CountryEntity countryEntity) {
    return new CountryInputGql(
      countryEntity.getName(),
      countryEntity.getIsoCode(),
      countryEntity.getCoordinates()
    );
  }
}
