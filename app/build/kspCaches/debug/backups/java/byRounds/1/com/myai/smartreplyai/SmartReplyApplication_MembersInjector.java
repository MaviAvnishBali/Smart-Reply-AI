package com.myai.smartreplyai;

import com.myai.smartreplyai.data.local.SampleDataSeeder;
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
public final class SmartReplyApplication_MembersInjector implements MembersInjector<SmartReplyApplication> {
  private final Provider<SampleDataSeeder> sampleDataSeederProvider;

  public SmartReplyApplication_MembersInjector(
      Provider<SampleDataSeeder> sampleDataSeederProvider) {
    this.sampleDataSeederProvider = sampleDataSeederProvider;
  }

  public static MembersInjector<SmartReplyApplication> create(
      Provider<SampleDataSeeder> sampleDataSeederProvider) {
    return new SmartReplyApplication_MembersInjector(sampleDataSeederProvider);
  }

  @Override
  public void injectMembers(SmartReplyApplication instance) {
    injectSampleDataSeeder(instance, sampleDataSeederProvider.get());
  }

  @InjectedFieldSignature("com.myai.smartreplyai.SmartReplyApplication.sampleDataSeeder")
  public static void injectSampleDataSeeder(SmartReplyApplication instance,
      SampleDataSeeder sampleDataSeeder) {
    instance.sampleDataSeeder = sampleDataSeeder;
  }
}
