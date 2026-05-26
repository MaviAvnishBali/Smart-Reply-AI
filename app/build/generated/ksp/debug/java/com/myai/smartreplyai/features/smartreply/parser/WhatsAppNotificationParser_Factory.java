package com.myai.smartreplyai.features.smartreply.parser;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class WhatsAppNotificationParser_Factory implements Factory<WhatsAppNotificationParser> {
  @Override
  public WhatsAppNotificationParser get() {
    return newInstance();
  }

  public static WhatsAppNotificationParser_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WhatsAppNotificationParser newInstance() {
    return new WhatsAppNotificationParser();
  }

  private static final class InstanceHolder {
    private static final WhatsAppNotificationParser_Factory INSTANCE = new WhatsAppNotificationParser_Factory();
  }
}
