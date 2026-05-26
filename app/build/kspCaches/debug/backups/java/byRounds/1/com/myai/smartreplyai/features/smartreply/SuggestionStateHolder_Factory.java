package com.myai.smartreplyai.features.smartreply;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class SuggestionStateHolder_Factory implements Factory<SuggestionStateHolder> {
  @Override
  public SuggestionStateHolder get() {
    return newInstance();
  }

  public static SuggestionStateHolder_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SuggestionStateHolder newInstance() {
    return new SuggestionStateHolder();
  }

  private static final class InstanceHolder {
    private static final SuggestionStateHolder_Factory INSTANCE = new SuggestionStateHolder_Factory();
  }
}
