package com.myai.smartreplyai.data.repository;

import com.myai.smartreplyai.data.local.PreferencesDataStore;
import com.myai.smartreplyai.data.local.dao.UserSettingsDao;
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
public final class SettingsRepositoryImpl_Factory implements Factory<SettingsRepositoryImpl> {
  private final Provider<PreferencesDataStore> preferencesProvider;

  private final Provider<UserSettingsDao> userSettingsDaoProvider;

  public SettingsRepositoryImpl_Factory(Provider<PreferencesDataStore> preferencesProvider,
      Provider<UserSettingsDao> userSettingsDaoProvider) {
    this.preferencesProvider = preferencesProvider;
    this.userSettingsDaoProvider = userSettingsDaoProvider;
  }

  @Override
  public SettingsRepositoryImpl get() {
    return newInstance(preferencesProvider.get(), userSettingsDaoProvider.get());
  }

  public static SettingsRepositoryImpl_Factory create(
      Provider<PreferencesDataStore> preferencesProvider,
      Provider<UserSettingsDao> userSettingsDaoProvider) {
    return new SettingsRepositoryImpl_Factory(preferencesProvider, userSettingsDaoProvider);
  }

  public static SettingsRepositoryImpl newInstance(PreferencesDataStore preferences,
      UserSettingsDao userSettingsDao) {
    return new SettingsRepositoryImpl(preferences, userSettingsDao);
  }
}
