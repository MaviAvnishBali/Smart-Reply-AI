package com.myai.smartreplyai.features.templates;

import com.myai.smartreplyai.domain.repository.TemplateRepository;
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
public final class TemplatesViewModel_Factory implements Factory<TemplatesViewModel> {
  private final Provider<TemplateRepository> templateRepositoryProvider;

  public TemplatesViewModel_Factory(Provider<TemplateRepository> templateRepositoryProvider) {
    this.templateRepositoryProvider = templateRepositoryProvider;
  }

  @Override
  public TemplatesViewModel get() {
    return newInstance(templateRepositoryProvider.get());
  }

  public static TemplatesViewModel_Factory create(
      Provider<TemplateRepository> templateRepositoryProvider) {
    return new TemplatesViewModel_Factory(templateRepositoryProvider);
  }

  public static TemplatesViewModel newInstance(TemplateRepository templateRepository) {
    return new TemplatesViewModel(templateRepository);
  }
}
