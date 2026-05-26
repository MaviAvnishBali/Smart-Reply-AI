package com.myai.smartreplyai.di;

import com.myai.smartreplyai.data.local.SmartReplyDatabase;
import com.myai.smartreplyai.data.local.dao.TemplateDao;
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
public final class DatabaseModule_ProvideTemplateDaoFactory implements Factory<TemplateDao> {
  private final Provider<SmartReplyDatabase> dbProvider;

  public DatabaseModule_ProvideTemplateDaoFactory(Provider<SmartReplyDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TemplateDao get() {
    return provideTemplateDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTemplateDaoFactory create(
      Provider<SmartReplyDatabase> dbProvider) {
    return new DatabaseModule_ProvideTemplateDaoFactory(dbProvider);
  }

  public static TemplateDao provideTemplateDao(SmartReplyDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTemplateDao(db));
  }
}
