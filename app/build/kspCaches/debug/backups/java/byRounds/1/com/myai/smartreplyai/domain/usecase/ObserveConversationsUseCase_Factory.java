package com.myai.smartreplyai.domain.usecase;

import com.myai.smartreplyai.domain.repository.ConversationRepository;
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
public final class ObserveConversationsUseCase_Factory implements Factory<ObserveConversationsUseCase> {
  private final Provider<ConversationRepository> repositoryProvider;

  public ObserveConversationsUseCase_Factory(Provider<ConversationRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ObserveConversationsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ObserveConversationsUseCase_Factory create(
      Provider<ConversationRepository> repositoryProvider) {
    return new ObserveConversationsUseCase_Factory(repositoryProvider);
  }

  public static ObserveConversationsUseCase newInstance(ConversationRepository repository) {
    return new ObserveConversationsUseCase(repository);
  }
}
