package com.myai.smartreplyai.features.ai.engine;

import com.myai.smartreplyai.data.local.dao.MessageDao;
import com.myai.smartreplyai.data.repository.AnalyticsRepositoryImpl;
import com.myai.smartreplyai.domain.repository.SettingsRepository;
import com.myai.smartreplyai.domain.repository.TemplateRepository;
import com.myai.smartreplyai.features.ai.provider.GeminiProvider;
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
public final class SmartReplyEngine_Factory implements Factory<SmartReplyEngine> {
  private final Provider<TemplateRepository> templateRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<MessageDao> messageDaoProvider;

  private final Provider<GeminiProvider> geminiProvider;

  private final Provider<AnalyticsRepositoryImpl> analyticsRepositoryProvider;

  public SmartReplyEngine_Factory(Provider<TemplateRepository> templateRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<MessageDao> messageDaoProvider, Provider<GeminiProvider> geminiProvider,
      Provider<AnalyticsRepositoryImpl> analyticsRepositoryProvider) {
    this.templateRepositoryProvider = templateRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.messageDaoProvider = messageDaoProvider;
    this.geminiProvider = geminiProvider;
    this.analyticsRepositoryProvider = analyticsRepositoryProvider;
  }

  @Override
  public SmartReplyEngine get() {
    return newInstance(templateRepositoryProvider.get(), settingsRepositoryProvider.get(), messageDaoProvider.get(), geminiProvider.get(), analyticsRepositoryProvider.get());
  }

  public static SmartReplyEngine_Factory create(
      Provider<TemplateRepository> templateRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<MessageDao> messageDaoProvider, Provider<GeminiProvider> geminiProvider,
      Provider<AnalyticsRepositoryImpl> analyticsRepositoryProvider) {
    return new SmartReplyEngine_Factory(templateRepositoryProvider, settingsRepositoryProvider, messageDaoProvider, geminiProvider, analyticsRepositoryProvider);
  }

  public static SmartReplyEngine newInstance(TemplateRepository templateRepository,
      SettingsRepository settingsRepository, MessageDao messageDao, GeminiProvider geminiProvider,
      AnalyticsRepositoryImpl analyticsRepository) {
    return new SmartReplyEngine(templateRepository, settingsRepository, messageDao, geminiProvider, analyticsRepository);
  }
}
