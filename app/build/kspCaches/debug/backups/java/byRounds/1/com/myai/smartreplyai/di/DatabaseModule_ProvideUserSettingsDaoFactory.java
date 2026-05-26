package com.myai.smartreplyai.di;

import com.myai.smartreplyai.data.local.SmartReplyDatabase;
import com.myai.smartreplyai.data.local.dao.UserSettingsDao;
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
public final class DatabaseModule_ProvideUserSettingsDaoFactory implements Factory<UserSettingsDao> {
  private final Provider<SmartReplyDatabase> dbProvider;

  public DatabaseModule_ProvideUserSettingsDaoFactory(Provider<SmartReplyDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public UserSettingsDao get() {
    return provideUserSettingsDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideUserSettingsDaoFactory create(
      Provider<SmartReplyDatabase> dbProvider) {
    return new DatabaseModule_ProvideUserSettingsDaoFactory(dbProvider);
  }

  public static UserSettingsDao provideUserSettingsDao(SmartReplyDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideUserSettingsDao(db));
  }
}
