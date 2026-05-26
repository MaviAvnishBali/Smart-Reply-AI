package com.myai.smartreplyai.features.conversations;

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
public final class ConversationDetailViewModel_Factory implements Factory<ConversationDetailViewModel> {
  private final Provider<ConversationRepository> conversationRepositoryProvider;

  public ConversationDetailViewModel_Factory(
      Provider<ConversationRepository> conversationRepositoryProvider) {
    this.conversationRepositoryProvider = conversationRepositoryProvider;
  }

  @Override
  public ConversationDetailViewModel get() {
    return newInstance(conversationRepositoryProvider.get());
  }

  public static ConversationDetailViewModel_Factory create(
      Provider<ConversationRepository> conversationRepositoryProvider) {
    return new ConversationDetailViewModel_Factory(conversationRepositoryProvider);
  }

  public static ConversationDetailViewModel newInstance(
      ConversationRepository conversationRepository) {
    return new ConversationDetailViewModel(conversationRepository);
  }
}
