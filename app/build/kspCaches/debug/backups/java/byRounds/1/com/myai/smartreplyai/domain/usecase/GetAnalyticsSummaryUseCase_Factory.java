package com.myai.smartreplyai.domain.usecase;

import com.myai.smartreplyai.domain.repository.AnalyticsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class GetAnalyticsSummaryUseCase_Factory implements Factory<GetAnalyticsSummaryUseCase> {
  private final Provider<AnalyticsRepository> repositoryProvider;

  public GetAnalyticsSummaryUseCase_Factory(Provider<AnalyticsRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetAnalyticsSummaryUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetAnalyticsSummaryUseCase_Factory create(
      Provider<AnalyticsRepository> repositoryProvider) {
    return new GetAnalyticsSummaryUseCase_Factory(repositoryProvider);
  }

  public static GetAnalyticsSummaryUseCase newInstance(AnalyticsRepository repository) {
    return new GetAnalyticsSummaryUseCase(repository);
  }
}
