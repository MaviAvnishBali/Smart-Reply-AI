package com.myai.smartreplyai.features.ai.lead;

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
public final class LeadDetector_Factory implements Factory<LeadDetector> {
  @Override
  public LeadDetector get() {
    return newInstance();
  }

  public static LeadDetector_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static LeadDetector newInstance() {
    return new LeadDetector();
  }

  private static final class InstanceHolder {
    private static final LeadDetector_Factory INSTANCE = new LeadDetector_Factory();
  }
}
