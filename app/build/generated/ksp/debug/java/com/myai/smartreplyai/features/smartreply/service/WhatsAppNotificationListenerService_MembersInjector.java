package com.myai.smartreplyai.features.smartreply.service;

import com.myai.smartreplyai.data.local.PreferencesDataStore;
import com.myai.smartreplyai.domain.repository.ConversationRepository;
import com.myai.smartreplyai.domain.usecase.GenerateSmartRepliesUseCase;
import com.myai.smartreplyai.features.smartreply.SuggestionStateHolder;
import com.myai.smartreplyai.features.smartreply.parser.WhatsAppNotificationParser;
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
public final class WhatsAppNotificationListenerService_MembersInjector implements MembersInjector<WhatsAppNotificationListenerService> {
  private final Provider<WhatsAppNotificationParser> parserProvider;

  private final Provider<ConversationRepository> conversationRepositoryProvider;

  private final Provider<GenerateSmartRepliesUseCase> generateSmartRepliesProvider;

  private final Provider<SuggestionStateHolder> suggestionStateHolderProvider;

  private final Provider<PreferencesDataStore> preferencesProvider;

  public WhatsAppNotificationListenerService_MembersInjector(
      Provider<WhatsAppNotificationParser> parserProvider,
      Provider<ConversationRepository> conversationRepositoryProvider,
      Provider<GenerateSmartRepliesUseCase> generateSmartRepliesProvider,
      Provider<SuggestionStateHolder> suggestionStateHolderProvider,
      Provider<PreferencesDataStore> preferencesProvider) {
    this.parserProvider = parserProvider;
    this.conversationRepositoryProvider = conversationRepositoryProvider;
    this.generateSmartRepliesProvider = generateSmartRepliesProvider;
    this.suggestionStateHolderProvider = suggestionStateHolderProvider;
    this.preferencesProvider = preferencesProvider;
  }

  public static MembersInjector<WhatsAppNotificationListenerService> create(
      Provider<WhatsAppNotificationParser> parserProvider,
      Provider<ConversationRepository> conversationRepositoryProvider,
      Provider<GenerateSmartRepliesUseCase> generateSmartRepliesProvider,
      Provider<SuggestionStateHolder> suggestionStateHolderProvider,
      Provider<PreferencesDataStore> preferencesProvider) {
    return new WhatsAppNotificationListenerService_MembersInjector(parserProvider, conversationRepositoryProvider, generateSmartRepliesProvider, suggestionStateHolderProvider, preferencesProvider);
  }

  @Override
  public void injectMembers(WhatsAppNotificationListenerService instance) {
    injectParser(instance, parserProvider.get());
    injectConversationRepository(instance, conversationRepositoryProvider.get());
    injectGenerateSmartReplies(instance, generateSmartRepliesProvider.get());
    injectSuggestionStateHolder(instance, suggestionStateHolderProvider.get());
    injectPreferences(instance, preferencesProvider.get());
  }

  @InjectedFieldSignature("com.myai.smartreplyai.features.smartreply.service.WhatsAppNotificationListenerService.parser")
  public static void injectParser(WhatsAppNotificationListenerService instance,
      WhatsAppNotificationParser parser) {
    instance.parser = parser;
  }

  @InjectedFieldSignature("com.myai.smartreplyai.features.smartreply.service.WhatsAppNotificationListenerService.conversationRepository")
  public static void injectConversationRepository(WhatsAppNotificationListenerService instance,
      ConversationRepository conversationRepository) {
    instance.conversationRepository = conversationRepository;
  }

  @InjectedFieldSignature("com.myai.smartreplyai.features.smartreply.service.WhatsAppNotificationListenerService.generateSmartReplies")
  public static void injectGenerateSmartReplies(WhatsAppNotificationListenerService instance,
      GenerateSmartRepliesUseCase generateSmartReplies) {
    instance.generateSmartReplies = generateSmartReplies;
  }

  @InjectedFieldSignature("com.myai.smartreplyai.features.smartreply.service.WhatsAppNotificationListenerService.suggestionStateHolder")
  public static void injectSuggestionStateHolder(WhatsAppNotificationListenerService instance,
      SuggestionStateHolder suggestionStateHolder) {
    instance.suggestionStateHolder = suggestionStateHolder;
  }

  @InjectedFieldSignature("com.myai.smartreplyai.features.smartreply.service.WhatsAppNotificationListenerService.preferences")
  public static void injectPreferences(WhatsAppNotificationListenerService instance,
      PreferencesDataStore preferences) {
    instance.preferences = preferences;
  }
}
