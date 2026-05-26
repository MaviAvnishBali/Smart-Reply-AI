package com.myai.smartreplyai.features.premium;

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
public final class PremiumViewModel_Factory implements Factory<PremiumViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public PremiumViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public PremiumViewModel get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static PremiumViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new PremiumViewModel_Factory(settingsRepositoryProvider);
  }

  public static PremiumViewModel newInstance(SettingsRepository settingsRepository) {
    return new PremiumViewModel(settingsRepository);
  }
}
