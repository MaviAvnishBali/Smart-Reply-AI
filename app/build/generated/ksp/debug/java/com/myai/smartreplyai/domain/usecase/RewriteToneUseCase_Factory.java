package com.myai.smartreplyai.domain.usecase;

import com.myai.smartreplyai.domain.repository.SettingsRepository;
import com.myai.smartreplyai.features.ai.provider.GeminiProvider;
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
public final class RewriteToneUseCase_Factory implements Factory<RewriteToneUseCase> {
  private final Provider<GeminiProvider> geminiProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public RewriteToneUseCase_Factory(Provider<GeminiProvider> geminiProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.geminiProvider = geminiProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public RewriteToneUseCase get() {
    return newInstance(geminiProvider.get(), settingsRepositoryProvider.get());
  }

  public static RewriteToneUseCase_Factory create(Provider<GeminiProvider> geminiProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new RewriteToneUseCase_Factory(geminiProvider, settingsRepositoryProvider);
  }

  public static RewriteToneUseCase newInstance(GeminiProvider geminiProvider,
      SettingsRepository settingsRepository) {
    return new RewriteToneUseCase(geminiProvider, settingsRepository);
  }
}
