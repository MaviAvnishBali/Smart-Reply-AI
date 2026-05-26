package com.myai.smartreplyai.domain.usecase;

import com.myai.smartreplyai.features.ai.engine.SmartReplyEngine;
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
public final class GenerateSmartRepliesUseCase_Factory implements Factory<GenerateSmartRepliesUseCase> {
  private final Provider<SmartReplyEngine> engineProvider;

  public GenerateSmartRepliesUseCase_Factory(Provider<SmartReplyEngine> engineProvider) {
    this.engineProvider = engineProvider;
  }

  @Override
  public GenerateSmartRepliesUseCase get() {
    return newInstance(engineProvider.get());
  }

  public static GenerateSmartRepliesUseCase_Factory create(
      Provider<SmartReplyEngine> engineProvider) {
    return new GenerateSmartRepliesUseCase_Factory(engineProvider);
  }

  public static GenerateSmartRepliesUseCase newInstance(SmartReplyEngine engine) {
    return new GenerateSmartRepliesUseCase(engine);
  }
}
