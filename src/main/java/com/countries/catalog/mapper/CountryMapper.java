package com.countries.catalog.mapper;

import com.countries.catalog.domain.graphql.CountryGql;
import com.countries.catalog.domain.graphql.CountryInputGql;
import com.countries.grpc.countriescatalog.*;
import org.mapstruct.Mapper;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CountryMapper {

  CountryResponse toProto(CountryGql country);

  @Nonnull
  default CountryInputGql toInputGql(CountryRequest countryRequest) {
    if (countryRequest != null) {
      return new CountryInputGql(
        countryRequest.getName(),
        countryRequest.getIsoCode(),
        toCoordinates(countryRequest.getCoordinates())
      );
    }
    return CountryInputGql.empty();
  }

  @Nonnull
  default CountryListResponse toList(List<CountryGql> countries) {
    return countries == null || countries.isEmpty() ?
      CountryListResponse.getDefaultInstance() :
      CountryListResponse.newBuilder()
        .addAllCountries(countries.stream()
          .map(this::toProto).collect(Collectors.toList()))
        .build();
  }

  @Nonnull
  default GeometryData toGeometry(List<List<List<List<Double>>>> coordinates) {
    return coordinates == null || coordinates.isEmpty() ?
      GeometryData.getDefaultInstance() :
      GeometryData.newBuilder()
        .addAllPolygons(coordinates.stream()
          .map(polygonData -> PolygonShape.newBuilder()
            .addAllRings(polygonData.stream()
              .map(ringData -> RingPath.newBuilder()
                .addAllPoints(ringData.stream()
                  .map(pointData -> Coordinate.newBuilder()
                    .setLongitude(pointData.get(0))
                    .setLatitude(pointData.get(1))
                    .build())
                  .collect(Collectors.toList()))
                .build())
              .collect(Collectors.toList()))
            .build())
          .collect(Collectors.toList()))
        .build();
  }

  @Nonnull
  default List<List<List<List<Double>>>> toCoordinates(GeometryData geometry) {
    return geometry == null ? Collections.emptyList() :
      geometry.getPolygonsList().stream()
        .map(polygon -> polygon.getRingsList().stream()
          .map(ring -> ring.getPointsList().stream()
            .map(point -> Arrays.asList(point.getLongitude(), point.getLatitude()))
            .collect(Collectors.toList()))
          .collect(Collectors.toList()))
        .collect(Collectors.toList());
  }
}
