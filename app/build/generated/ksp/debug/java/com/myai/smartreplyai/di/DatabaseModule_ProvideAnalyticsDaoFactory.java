package com.myai.smartreplyai.di;

import com.myai.smartreplyai.data.local.SmartReplyDatabase;
import com.myai.smartreplyai.data.local.dao.AnalyticsDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideAnalyticsDaoFactory implements Factory<AnalyticsDao> {
  private final Provider<SmartReplyDatabase> dbProvider;

  public DatabaseModule_ProvideAnalyticsDaoFactory(Provider<SmartReplyDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AnalyticsDao get() {
    return provideAnalyticsDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAnalyticsDaoFactory create(
      Provider<SmartReplyDatabase> dbProvider) {
    return new DatabaseModule_ProvideAnalyticsDaoFactory(dbProvider);
  }

  public static AnalyticsDao provideAnalyticsDao(SmartReplyDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAnalyticsDao(db));
  }
}
