package com.myai.smartreplyai.features.permissions;

import com.myai.smartreplyai.domain.repository.SettingsRepository;
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
public final class PermissionsViewModel_Factory implements Factory<PermissionsViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public PermissionsViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public PermissionsViewModel get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static PermissionsViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new PermissionsViewModel_Factory(settingsRepositoryProvider);
  }

  public static PermissionsViewModel newInstance(SettingsRepository settingsRepository) {
    return new PermissionsViewModel(settingsRepository);
  }
}
