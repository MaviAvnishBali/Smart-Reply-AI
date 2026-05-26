package com.myai.smartreplyai.features.smartreply;

import com.myai.smartreplyai.domain.repository.ConversationRepository;
import com.myai.smartreplyai.domain.usecase.GenerateSmartRepliesUseCase;
import com.myai.smartreplyai.domain.usecase.RewriteToneUseCase;
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
public final class SmartReplyViewModel_Factory implements Factory<SmartReplyViewModel> {
  private final Provider<ConversationRepository> conversationRepositoryProvider;

  private final Provider<GenerateSmartRepliesUseCase> generateSmartRepliesProvider;

  private final Provider<RewriteToneUseCase> rewriteToneProvider;

  public SmartReplyViewModel_Factory(
      Provider<ConversationRepository> conversationRepositoryProvider,
      Provider<GenerateSmartRepliesUseCase> generateSmartRepliesProvider,
      Provider<RewriteToneUseCase> rewriteToneProvider) {
    this.conversationRepositoryProvider = conversationRepositoryProvider;
    this.generateSmartRepliesProvider = generateSmartRepliesProvider;
    this.rewriteToneProvider = rewriteToneProvider;
  }

  @Override
  public SmartReplyViewModel get() {
    return newInstance(conversationRepositoryProvider.get(), generateSmartRepliesProvider.get(), rewriteToneProvider.get());
  }

  public static SmartReplyViewModel_Factory create(
      Provider<ConversationRepository> conversationRepositoryProvider,
      Provider<GenerateSmartRepliesUseCase> generateSmartRepliesProvider,
      Provider<RewriteToneUseCase> rewriteToneProvider) {
    return new SmartReplyViewModel_Factory(conversationRepositoryProvider, generateSmartRepliesProvider, rewriteToneProvider);
  }

  public static SmartReplyViewModel newInstance(ConversationRepository conversationRepository,
      GenerateSmartRepliesUseCase generateSmartReplies, RewriteToneUseCase rewriteTone) {
    return new SmartReplyViewModel(conversationRepository, generateSmartReplies, rewriteTone);
  }
}
