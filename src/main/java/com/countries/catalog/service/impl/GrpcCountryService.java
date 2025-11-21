package com.countries.catalog.service.impl;

import com.countries.catalog.domain.graphql.CountryGql;
import com.countries.catalog.domain.graphql.CountryInputGql;
import com.countries.catalog.mapper.CountryMapper;
import com.countries.catalog.service.CountryService;
import com.countries.grpc.countriescatalog.*;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Service
public class GrpcCountryService extends CountriescatalogServiceGrpc.CountriescatalogServiceImplBase {

  private final CountryService countryService;
  private final CountryMapper countryMapper;

  @Autowired
  public GrpcCountryService(CountryService countryService, CountryMapper countryMapper) {
    this.countryService = countryService;
    this.countryMapper = countryMapper;
  }

  @Override
  public void country(IsoRequest request, StreamObserver<CountryResponse> responseObserver) {
    final CountryGql country = countryService.findByIsoCodeGql(request.getIsoCode());
    responseObserver.onNext(countryMapper.toProto(country));
    responseObserver.onCompleted();
  }

  @Override
  public void countries(Empty request, StreamObserver<CountryListResponse> responseObserver) {
    final List<CountryGql> countries = countryService.findAll();
    responseObserver.onNext(countryMapper.toList(countries));
    responseObserver.onCompleted();
  }

  @Override
  public void addCountry(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
    final CountryGql country = countryService.saveGql(countryMapper.toInputGql(request));
    responseObserver.onNext(countryMapper.toProto(country));
    responseObserver.onCompleted();
  }

  @Nonnull
  @Override
  public StreamObserver<CountryRequest> clientStreamingAddCountries(StreamObserver<Count> responseObserver) {
    return new StreamObserver<CountryRequest>() {
      private int count = 0;
      private final List<CountryInputGql> countries = new ArrayList<>();

      @Override
      public void onNext(CountryRequest countryRequest) {
        countries.add(countryMapper.toInputGql(countryRequest));
        count++;
      }

      @Override
      public void onError(Throwable throwable) {
        responseObserver.onError(Status.INTERNAL
          .withDescription("Failed to process country: " + throwable.getMessage())
          .asRuntimeException());
      }

      @Override
      public void onCompleted() {
        countryService.saveAllGql(countries);
        responseObserver.onNext(Count.newBuilder()
          .setCount(count)
          .build());
        responseObserver.onCompleted();
      }
    };
  }

  @Override
  public void updateCountry(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
    final CountryGql country = countryService.updateGql(countryMapper.toInputGql(request));
    responseObserver.onNext(countryMapper.toProto(country));
    responseObserver.onCompleted();
  }
}
