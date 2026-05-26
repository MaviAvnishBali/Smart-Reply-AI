package com.myai.smartreplyai;

import com.myai.smartreplyai.domain.repository.ConfigRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<ConfigRepository> configRepositoryProvider;

  public MainActivity_MembersInjector(Provider<ConfigRepository> configRepositoryProvider) {
    this.configRepositoryProvider = configRepositoryProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<ConfigRepository> configRepositoryProvider) {
    return new MainActivity_MembersInjector(configRepositoryProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectConfigRepository(instance, configRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.myai.smartreplyai.MainActivity.configRepository")
  public static void injectConfigRepository(MainActivity instance,
      ConfigRepository configRepository) {
    instance.configRepository = configRepository;
  }
}
