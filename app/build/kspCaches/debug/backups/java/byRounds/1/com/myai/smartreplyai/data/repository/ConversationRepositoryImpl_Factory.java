package com.myai.smartreplyai.data.repository;

import com.myai.smartreplyai.data.local.dao.ConversationDao;
import com.myai.smartreplyai.data.local.dao.MessageDao;
import com.myai.smartreplyai.domain.repository.AnalyticsRepository;
import com.myai.smartreplyai.features.ai.lead.LeadDetector;
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
public final class ConversationRepositoryImpl_Factory implements Factory<ConversationRepositoryImpl> {
  private final Provider<ConversationDao> conversationDaoProvider;

  private final Provider<MessageDao> messageDaoProvider;

  private final Provider<LeadDetector> leadDetectorProvider;

  private final Provider<AnalyticsRepository> analyticsRepositoryProvider;

  public ConversationRepositoryImpl_Factory(Provider<ConversationDao> conversationDaoProvider,
      Provider<MessageDao> messageDaoProvider, Provider<LeadDetector> leadDetectorProvider,
      Provider<AnalyticsRepository> analyticsRepositoryProvider) {
    this.conversationDaoProvider = conversationDaoProvider;
    this.messageDaoProvider = messageDaoProvider;
    this.leadDetectorProvider = leadDetectorProvider;
    this.analyticsRepositoryProvider = analyticsRepositoryProvider;
  }

  @Override
  public ConversationRepositoryImpl get() {
    return newInstance(conversationDaoProvider.get(), messageDaoProvider.get(), leadDetectorProvider.get(), analyticsRepositoryProvider.get());
  }

  public static ConversationRepositoryImpl_Factory create(
      Provider<ConversationDao> conversationDaoProvider, Provider<MessageDao> messageDaoProvider,
      Provider<LeadDetector> leadDetectorProvider,
      Provider<AnalyticsRepository> analyticsRepositoryProvider) {
    return new ConversationRepositoryImpl_Factory(conversationDaoProvider, messageDaoProvider, leadDetectorProvider, analyticsRepositoryProvider);
  }

  public static ConversationRepositoryImpl newInstance(ConversationDao conversationDao,
      MessageDao messageDao, LeadDetector leadDetector, AnalyticsRepository analyticsRepository) {
    return new ConversationRepositoryImpl(conversationDao, messageDao, leadDetector, analyticsRepository);
  }
}
