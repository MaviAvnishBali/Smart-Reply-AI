package com.myai.smartreplyai.features.analytics;

import com.myai.smartreplyai.domain.usecase.GetAnalyticsSummaryUseCase;
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
public final class AnalyticsViewModel_Factory implements Factory<AnalyticsViewModel> {
  private final Provider<GetAnalyticsSummaryUseCase> getAnalyticsSummaryProvider;

  public AnalyticsViewModel_Factory(
      Provider<GetAnalyticsSummaryUseCase> getAnalyticsSummaryProvider) {
    this.getAnalyticsSummaryProvider = getAnalyticsSummaryProvider;
  }

  @Override
  public AnalyticsViewModel get() {
    return newInstance(getAnalyticsSummaryProvider.get());
  }

  public static AnalyticsViewModel_Factory create(
      Provider<GetAnalyticsSummaryUseCase> getAnalyticsSummaryProvider) {
    return new AnalyticsViewModel_Factory(getAnalyticsSummaryProvider);
  }

  public static AnalyticsViewModel newInstance(GetAnalyticsSummaryUseCase getAnalyticsSummary) {
    return new AnalyticsViewModel(getAnalyticsSummary);
  }
}
