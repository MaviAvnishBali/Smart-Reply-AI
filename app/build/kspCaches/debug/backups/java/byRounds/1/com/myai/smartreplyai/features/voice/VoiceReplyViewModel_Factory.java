package com.myai.smartreplyai.features.voice;

import com.myai.smartreplyai.domain.repository.ConversationRepository;
import com.myai.smartreplyai.domain.usecase.GenerateSmartRepliesUseCase;
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
public final class VoiceReplyViewModel_Factory implements Factory<VoiceReplyViewModel> {
  private final Provider<ConversationRepository> conversationRepositoryProvider;

  private final Provider<GenerateSmartRepliesUseCase> generateSmartRepliesProvider;

  public VoiceReplyViewModel_Factory(
      Provider<ConversationRepository> conversationRepositoryProvider,
      Provider<GenerateSmartRepliesUseCase> generateSmartRepliesProvider) {
    this.conversationRepositoryProvider = conversationRepositoryProvider;
    this.generateSmartRepliesProvider = generateSmartRepliesProvider;
  }

  @Override
  public VoiceReplyViewModel get() {
    return newInstance(conversationRepositoryProvider.get(), generateSmartRepliesProvider.get());
  }

  public static VoiceReplyViewModel_Factory create(
      Provider<ConversationRepository> conversationRepositoryProvider,
      Provider<GenerateSmartRepliesUseCase> generateSmartRepliesProvider) {
    return new VoiceReplyViewModel_Factory(conversationRepositoryProvider, generateSmartRepliesProvider);
  }

  public static VoiceReplyViewModel newInstance(ConversationRepository conversationRepository,
      GenerateSmartRepliesUseCase generateSmartReplies) {
    return new VoiceReplyViewModel(conversationRepository, generateSmartReplies);
  }
}
