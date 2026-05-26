package com.myai.smartreplyai.features.conversations;

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
public final class ConversationsViewModel_Factory implements Factory<ConversationsViewModel> {
  private final Provider<ObserveConversationsUseCase> observeConversationsProvider;

  public ConversationsViewModel_Factory(
      Provider<ObserveConversationsUseCase> observeConversationsProvider) {
    this.observeConversationsProvider = observeConversationsProvider;
  }

  @Override
  public ConversationsViewModel get() {
    return newInstance(observeConversationsProvider.get());
  }

  public static ConversationsViewModel_Factory create(
      Provider<ObserveConversationsUseCase> observeConversationsProvider) {
    return new ConversationsViewModel_Factory(observeConversationsProvider);
  }

  public static ConversationsViewModel newInstance(
      ObserveConversationsUseCase observeConversations) {
    return new ConversationsViewModel(observeConversations);
  }
}
