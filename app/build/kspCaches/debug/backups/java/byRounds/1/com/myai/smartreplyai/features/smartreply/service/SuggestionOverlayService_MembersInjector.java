package com.myai.smartreplyai.features.smartreply.service;

import com.myai.smartreplyai.features.smartreply.SuggestionStateHolder;
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
public final class SuggestionOverlayService_MembersInjector implements MembersInjector<SuggestionOverlayService> {
  private final Provider<SuggestionStateHolder> suggestionStateHolderProvider;

  public SuggestionOverlayService_MembersInjector(
      Provider<SuggestionStateHolder> suggestionStateHolderProvider) {
    this.suggestionStateHolderProvider = suggestionStateHolderProvider;
  }

  public static MembersInjector<SuggestionOverlayService> create(
      Provider<SuggestionStateHolder> suggestionStateHolderProvider) {
    return new SuggestionOverlayService_MembersInjector(suggestionStateHolderProvider);
  }

  @Override
  public void injectMembers(SuggestionOverlayService instance) {
    injectSuggestionStateHolder(instance, suggestionStateHolderProvider.get());
  }

  @InjectedFieldSignature("com.myai.smartreplyai.features.smartreply.service.SuggestionOverlayService.suggestionStateHolder")
  public static void injectSuggestionStateHolder(SuggestionOverlayService instance,
      SuggestionStateHolder suggestionStateHolder) {
    instance.suggestionStateHolder = suggestionStateHolder;
  }
}
