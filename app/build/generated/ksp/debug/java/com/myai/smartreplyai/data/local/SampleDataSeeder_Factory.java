package com.myai.smartreplyai.data.local;

import com.myai.smartreplyai.data.local.dao.ConversationDao;
import com.myai.smartreplyai.data.local.dao.TemplateDao;
import com.myai.smartreplyai.data.local.dao.UserSettingsDao;
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
public final class SampleDataSeeder_Factory implements Factory<SampleDataSeeder> {
  private final Provider<ConversationDao> conversationDaoProvider;

  private final Provider<TemplateDao> templateDaoProvider;

  private final Provider<UserSettingsDao> userSettingsDaoProvider;

  public SampleDataSeeder_Factory(Provider<ConversationDao> conversationDaoProvider,
      Provider<TemplateDao> templateDaoProvider,
      Provider<UserSettingsDao> userSettingsDaoProvider) {
    this.conversationDaoProvider = conversationDaoProvider;
    this.templateDaoProvider = templateDaoProvider;
    this.userSettingsDaoProvider = userSettingsDaoProvider;
  }

  @Override
  public SampleDataSeeder get() {
    return newInstance(conversationDaoProvider.get(), templateDaoProvider.get(), userSettingsDaoProvider.get());
  }

  public static SampleDataSeeder_Factory create(Provider<ConversationDao> conversationDaoProvider,
      Provider<TemplateDao> templateDaoProvider,
      Provider<UserSettingsDao> userSettingsDaoProvider) {
    return new SampleDataSeeder_Factory(conversationDaoProvider, templateDaoProvider, userSettingsDaoProvider);
  }

  public static SampleDataSeeder newInstance(ConversationDao conversationDao,
      TemplateDao templateDao, UserSettingsDao userSettingsDao) {
    return new SampleDataSeeder(conversationDao, templateDao, userSettingsDao);
  }
}
