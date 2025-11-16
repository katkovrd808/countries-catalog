package com.countries.catalog.data;

import com.countries.catalog.domain.CountryJson;
import com.countries.catalog.domain.graphql.CountryInputGql;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "countries")
public class CountryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
  private UUID id;

  @Column(name = "country_name", nullable = false)
  private String name;

  @Column(name = "iso_code", nullable = false)
  private String isoCode;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb", nullable = false)
  private List<List<List<List<Double>>>> coordinates;

  public static CountryEntity fromJson(CountryJson countryJson) {
    CountryEntity ce = new CountryEntity();
    ce.setName(countryJson.name());
    ce.setIsoCode(countryJson.isoCode());
    ce.setCoordinates(countryJson.coordinates());
    return ce;
  }

  public static CountryEntity fromGql(CountryInputGql countryGql) {
    CountryEntity ce = new CountryEntity();
    ce.setName(countryGql.name());
    ce.setIsoCode(countryGql.isoCode());
    ce.setCoordinates(countryGql.coordinates());
    return ce;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CountryEntity that = (CountryEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(isoCode, that.isoCode) && Objects.equals(coordinates, that.coordinates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, isoCode, coordinates);
  }
}
