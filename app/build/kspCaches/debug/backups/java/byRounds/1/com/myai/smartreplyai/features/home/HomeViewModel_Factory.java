package com.myai.smartreplyai.features.home;

import com.myai.smartreplyai.domain.repository.ConfigRepository;
import com.myai.smartreplyai.domain.repository.SettingsRepository;
import com.myai.smartreplyai.domain.usecase.ObserveConversationsUseCase;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<ObserveConversationsUseCase> observeConversationsProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<ConfigRepository> configRepositoryProvider;

  public HomeViewModel_Factory(Provider<ObserveConversationsUseCase> observeConversationsProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ConfigRepository> configRepositoryProvider) {
    this.observeConversationsProvider = observeConversationsProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.configRepositoryProvider = configRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(observeConversationsProvider.get(), settingsRepositoryProvider.get(), configRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<ObserveConversationsUseCase> observeConversationsProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ConfigRepository> configRepositoryProvider) {
    return new HomeViewModel_Factory(observeConversationsProvider, settingsRepositoryProvider, configRepositoryProvider);
  }

  public static HomeViewModel newInstance(ObserveConversationsUseCase observeConversations,
      SettingsRepository settingsRepository, ConfigRepository configRepository) {
    return new HomeViewModel(observeConversations, settingsRepository, configRepository);
  }
}
