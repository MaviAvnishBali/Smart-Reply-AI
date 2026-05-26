package com.myai.smartreplyai.data.repository;

import com.myai.smartreplyai.data.local.dao.AnalyticsDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AnalyticsRepositoryImpl_Factory implements Factory<AnalyticsRepositoryImpl> {
  private final Provider<AnalyticsDao> analyticsDaoProvider;

  public AnalyticsRepositoryImpl_Factory(Provider<AnalyticsDao> analyticsDaoProvider) {
    this.analyticsDaoProvider = analyticsDaoProvider;
  }

  @Override
  public AnalyticsRepositoryImpl get() {
    return newInstance(analyticsDaoProvider.get());
  }

  public static AnalyticsRepositoryImpl_Factory create(
      Provider<AnalyticsDao> analyticsDaoProvider) {
    return new AnalyticsRepositoryImpl_Factory(analyticsDaoProvider);
  }

  public static AnalyticsRepositoryImpl newInstance(AnalyticsDao analyticsDao) {
    return new AnalyticsRepositoryImpl(analyticsDao);
  }
}
