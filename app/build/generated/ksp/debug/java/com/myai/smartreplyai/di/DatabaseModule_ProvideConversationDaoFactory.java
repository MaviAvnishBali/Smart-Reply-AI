package com.myai.smartreplyai.di;

import com.myai.smartreplyai.data.local.SmartReplyDatabase;
import com.myai.smartreplyai.data.local.dao.ConversationDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideConversationDaoFactory implements Factory<ConversationDao> {
  private final Provider<SmartReplyDatabase> dbProvider;

  public DatabaseModule_ProvideConversationDaoFactory(Provider<SmartReplyDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ConversationDao get() {
    return provideConversationDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideConversationDaoFactory create(
      Provider<SmartReplyDatabase> dbProvider) {
    return new DatabaseModule_ProvideConversationDaoFactory(dbProvider);
  }

  public static ConversationDao provideConversationDao(SmartReplyDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideConversationDao(db));
  }
}
