package com.myai.smartreplyai.features.ai.provider;

import com.myai.smartreplyai.features.ai.data.GeminiApiService;
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
public final class GeminiProvider_Factory implements Factory<GeminiProvider> {
  private final Provider<GeminiApiService> apiProvider;

  public GeminiProvider_Factory(Provider<GeminiApiService> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public GeminiProvider get() {
    return newInstance(apiProvider.get());
  }

  public static GeminiProvider_Factory create(Provider<GeminiApiService> apiProvider) {
    return new GeminiProvider_Factory(apiProvider);
  }

  public static GeminiProvider newInstance(GeminiApiService api) {
    return new GeminiProvider(api);
  }
}
