package com.myai.smartreplyai;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.myai.smartreplyai.data.local.PreferencesDataStore;
import com.myai.smartreplyai.data.local.SampleDataSeeder;
import com.myai.smartreplyai.data.local.SmartReplyDatabase;
import com.myai.smartreplyai.data.local.dao.AnalyticsDao;
import com.myai.smartreplyai.data.local.dao.ConversationDao;
import com.myai.smartreplyai.data.local.dao.MessageDao;
import com.myai.smartreplyai.data.local.dao.TemplateDao;
import com.myai.smartreplyai.data.local.dao.UserSettingsDao;
import com.myai.smartreplyai.data.repository.AnalyticsRepositoryImpl;
import com.myai.smartreplyai.data.repository.ConfigRepositoryImpl;
import com.myai.smartreplyai.data.repository.ConversationRepositoryImpl;
import com.myai.smartreplyai.data.repository.SettingsRepositoryImpl;
import com.myai.smartreplyai.data.repository.TemplateRepositoryImpl;
import com.myai.smartreplyai.di.DatabaseModule_ProvideAnalyticsDaoFactory;
import com.myai.smartreplyai.di.DatabaseModule_ProvideConversationDaoFactory;
import com.myai.smartreplyai.di.DatabaseModule_ProvideDatabaseFactory;
import com.myai.smartreplyai.di.DatabaseModule_ProvideMessageDaoFactory;
import com.myai.smartreplyai.di.DatabaseModule_ProvideTemplateDaoFactory;
import com.myai.smartreplyai.di.DatabaseModule_ProvideUserSettingsDaoFactory;
import com.myai.smartreplyai.di.NetworkModule_ProvideGeminiApiFactory;
import com.myai.smartreplyai.di.NetworkModule_ProvideOkHttpClientFactory;
import com.myai.smartreplyai.di.NetworkModule_ProvideRetrofitFactory;
import com.myai.smartreplyai.domain.usecase.GenerateSmartRepliesUseCase;
import com.myai.smartreplyai.domain.usecase.GetAnalyticsSummaryUseCase;
import com.myai.smartreplyai.domain.usecase.ObserveConversationsUseCase;
import com.myai.smartreplyai.domain.usecase.RewriteToneUseCase;
import com.myai.smartreplyai.features.ai.AiSettingsViewModel;
import com.myai.smartreplyai.features.ai.AiSettingsViewModel_HiltModules;
import com.myai.smartreplyai.features.ai.AiSettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.ai.AiSettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.ai.data.GeminiApiService;
import com.myai.smartreplyai.features.ai.engine.SmartReplyEngine;
import com.myai.smartreplyai.features.ai.lead.LeadDetector;
import com.myai.smartreplyai.features.ai.provider.GeminiProvider;
import com.myai.smartreplyai.features.analytics.AnalyticsViewModel;
import com.myai.smartreplyai.features.analytics.AnalyticsViewModel_HiltModules;
import com.myai.smartreplyai.features.analytics.AnalyticsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.analytics.AnalyticsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.conversations.ConversationDetailViewModel;
import com.myai.smartreplyai.features.conversations.ConversationDetailViewModel_HiltModules;
import com.myai.smartreplyai.features.conversations.ConversationDetailViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.conversations.ConversationDetailViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.conversations.ConversationsViewModel;
import com.myai.smartreplyai.features.conversations.ConversationsViewModel_HiltModules;
import com.myai.smartreplyai.features.conversations.ConversationsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.conversations.ConversationsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.home.HomeViewModel;
import com.myai.smartreplyai.features.home.HomeViewModel_HiltModules;
import com.myai.smartreplyai.features.home.HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.home.HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.onboarding.OnboardingViewModel;
import com.myai.smartreplyai.features.onboarding.OnboardingViewModel_HiltModules;
import com.myai.smartreplyai.features.onboarding.OnboardingViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.onboarding.OnboardingViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.permissions.PermissionsViewModel;
import com.myai.smartreplyai.features.permissions.PermissionsViewModel_HiltModules;
import com.myai.smartreplyai.features.permissions.PermissionsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.permissions.PermissionsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.premium.PremiumViewModel;
import com.myai.smartreplyai.features.premium.PremiumViewModel_HiltModules;
import com.myai.smartreplyai.features.premium.PremiumViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.premium.PremiumViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.settings.SettingsViewModel;
import com.myai.smartreplyai.features.settings.SettingsViewModel_HiltModules;
import com.myai.smartreplyai.features.settings.SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.settings.SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.smartreply.SmartReplyViewModel;
import com.myai.smartreplyai.features.smartreply.SmartReplyViewModel_HiltModules;
import com.myai.smartreplyai.features.smartreply.SmartReplyViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.smartreply.SmartReplyViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.smartreply.SuggestionStateHolder;
import com.myai.smartreplyai.features.smartreply.parser.WhatsAppNotificationParser;
import com.myai.smartreplyai.features.smartreply.service.SuggestionOverlayService;
import com.myai.smartreplyai.features.smartreply.service.SuggestionOverlayService_MembersInjector;
import com.myai.smartreplyai.features.smartreply.service.WhatsAppNotificationListenerService;
import com.myai.smartreplyai.features.smartreply.service.WhatsAppNotificationListenerService_MembersInjector;
import com.myai.smartreplyai.features.splash.SplashViewModel;
import com.myai.smartreplyai.features.splash.SplashViewModel_HiltModules;
import com.myai.smartreplyai.features.splash.SplashViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.splash.SplashViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.templates.TemplatesViewModel;
import com.myai.smartreplyai.features.templates.TemplatesViewModel_HiltModules;
import com.myai.smartreplyai.features.templates.TemplatesViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.templates.TemplatesViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.myai.smartreplyai.features.voice.VoiceReplyViewModel;
import com.myai.smartreplyai.features.voice.VoiceReplyViewModel_HiltModules;
import com.myai.smartreplyai.features.voice.VoiceReplyViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.myai.smartreplyai.features.voice.VoiceReplyViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
public final class DaggerSmartReplyApplication_HiltComponents_SingletonC {
  private DaggerSmartReplyApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public SmartReplyApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements SmartReplyApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public SmartReplyApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements SmartReplyApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public SmartReplyApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements SmartReplyApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public SmartReplyApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements SmartReplyApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SmartReplyApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements SmartReplyApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public SmartReplyApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements SmartReplyApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public SmartReplyApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements SmartReplyApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public SmartReplyApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends SmartReplyApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends SmartReplyApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends SmartReplyApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends SmartReplyApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>builderWithExpectedSize(14).put(AiSettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AiSettingsViewModel_HiltModules.KeyModule.provide()).put(AnalyticsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AnalyticsViewModel_HiltModules.KeyModule.provide()).put(ConversationDetailViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ConversationDetailViewModel_HiltModules.KeyModule.provide()).put(ConversationsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ConversationsViewModel_HiltModules.KeyModule.provide()).put(HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, HomeViewModel_HiltModules.KeyModule.provide()).put(MainViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, MainViewModel_HiltModules.KeyModule.provide()).put(OnboardingViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, OnboardingViewModel_HiltModules.KeyModule.provide()).put(PermissionsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, PermissionsViewModel_HiltModules.KeyModule.provide()).put(PremiumViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, PremiumViewModel_HiltModules.KeyModule.provide()).put(SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SettingsViewModel_HiltModules.KeyModule.provide()).put(SmartReplyViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SmartReplyViewModel_HiltModules.KeyModule.provide()).put(SplashViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SplashViewModel_HiltModules.KeyModule.provide()).put(TemplatesViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, TemplatesViewModel_HiltModules.KeyModule.provide()).put(VoiceReplyViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, VoiceReplyViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectConfigRepository(instance, singletonCImpl.configRepositoryImplProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends SmartReplyApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AiSettingsViewModel> aiSettingsViewModelProvider;

    private Provider<AnalyticsViewModel> analyticsViewModelProvider;

    private Provider<ConversationDetailViewModel> conversationDetailViewModelProvider;

    private Provider<ConversationsViewModel> conversationsViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<MainViewModel> mainViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private Provider<PermissionsViewModel> permissionsViewModelProvider;

    private Provider<PremiumViewModel> premiumViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<SmartReplyViewModel> smartReplyViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private Provider<TemplatesViewModel> templatesViewModelProvider;

    private Provider<VoiceReplyViewModel> voiceReplyViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private GetAnalyticsSummaryUseCase getAnalyticsSummaryUseCase() {
      return new GetAnalyticsSummaryUseCase(singletonCImpl.analyticsRepositoryImplProvider.get());
    }

    private ObserveConversationsUseCase observeConversationsUseCase() {
      return new ObserveConversationsUseCase(singletonCImpl.conversationRepositoryImplProvider.get());
    }

    private GenerateSmartRepliesUseCase generateSmartRepliesUseCase() {
      return new GenerateSmartRepliesUseCase(singletonCImpl.smartReplyEngineProvider.get());
    }

    private RewriteToneUseCase rewriteToneUseCase() {
      return new RewriteToneUseCase(singletonCImpl.geminiProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.aiSettingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.analyticsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.conversationDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.conversationsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.mainViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.permissionsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.premiumViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.smartReplyViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.templatesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
      this.voiceReplyViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 13);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(14).put(AiSettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) aiSettingsViewModelProvider)).put(AnalyticsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) analyticsViewModelProvider)).put(ConversationDetailViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) conversationDetailViewModelProvider)).put(ConversationsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) conversationsViewModelProvider)).put(HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) homeViewModelProvider)).put(MainViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) mainViewModelProvider)).put(OnboardingViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) onboardingViewModelProvider)).put(PermissionsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) permissionsViewModelProvider)).put(PremiumViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) premiumViewModelProvider)).put(SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) settingsViewModelProvider)).put(SmartReplyViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) smartReplyViewModelProvider)).put(SplashViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) splashViewModelProvider)).put(TemplatesViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) templatesViewModelProvider)).put(VoiceReplyViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) voiceReplyViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.myai.smartreplyai.features.ai.AiSettingsViewModel 
          return (T) new AiSettingsViewModel(singletonCImpl.settingsRepositoryImplProvider.get());

          case 1: // com.myai.smartreplyai.features.analytics.AnalyticsViewModel 
          return (T) new AnalyticsViewModel(viewModelCImpl.getAnalyticsSummaryUseCase());

          case 2: // com.myai.smartreplyai.features.conversations.ConversationDetailViewModel 
          return (T) new ConversationDetailViewModel(singletonCImpl.conversationRepositoryImplProvider.get());

          case 3: // com.myai.smartreplyai.features.conversations.ConversationsViewModel 
          return (T) new ConversationsViewModel(viewModelCImpl.observeConversationsUseCase());

          case 4: // com.myai.smartreplyai.features.home.HomeViewModel 
          return (T) new HomeViewModel(viewModelCImpl.observeConversationsUseCase(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.configRepositoryImplProvider.get());

          case 5: // com.myai.smartreplyai.MainViewModel 
          return (T) new MainViewModel(singletonCImpl.settingsRepositoryImplProvider.get());

          case 6: // com.myai.smartreplyai.features.onboarding.OnboardingViewModel 
          return (T) new OnboardingViewModel(singletonCImpl.settingsRepositoryImplProvider.get());

          case 7: // com.myai.smartreplyai.features.permissions.PermissionsViewModel 
          return (T) new PermissionsViewModel(singletonCImpl.settingsRepositoryImplProvider.get());

          case 8: // com.myai.smartreplyai.features.premium.PremiumViewModel 
          return (T) new PremiumViewModel(singletonCImpl.settingsRepositoryImplProvider.get());

          case 9: // com.myai.smartreplyai.features.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.settingsRepositoryImplProvider.get());

          case 10: // com.myai.smartreplyai.features.smartreply.SmartReplyViewModel 
          return (T) new SmartReplyViewModel(singletonCImpl.conversationRepositoryImplProvider.get(), viewModelCImpl.generateSmartRepliesUseCase(), viewModelCImpl.rewriteToneUseCase());

          case 11: // com.myai.smartreplyai.features.splash.SplashViewModel 
          return (T) new SplashViewModel(singletonCImpl.settingsRepositoryImplProvider.get());

          case 12: // com.myai.smartreplyai.features.templates.TemplatesViewModel 
          return (T) new TemplatesViewModel(singletonCImpl.templateRepositoryImplProvider.get());

          case 13: // com.myai.smartreplyai.features.voice.VoiceReplyViewModel 
          return (T) new VoiceReplyViewModel(singletonCImpl.conversationRepositoryImplProvider.get(), viewModelCImpl.generateSmartRepliesUseCase());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends SmartReplyApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends SmartReplyApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    private GenerateSmartRepliesUseCase generateSmartRepliesUseCase() {
      return new GenerateSmartRepliesUseCase(singletonCImpl.smartReplyEngineProvider.get());
    }

    @Override
    public void injectSuggestionOverlayService(SuggestionOverlayService suggestionOverlayService) {
      injectSuggestionOverlayService2(suggestionOverlayService);
    }

    @Override
    public void injectWhatsAppNotificationListenerService(
        WhatsAppNotificationListenerService whatsAppNotificationListenerService) {
      injectWhatsAppNotificationListenerService2(whatsAppNotificationListenerService);
    }

    @CanIgnoreReturnValue
    private SuggestionOverlayService injectSuggestionOverlayService2(
        SuggestionOverlayService instance) {
      SuggestionOverlayService_MembersInjector.injectSuggestionStateHolder(instance, singletonCImpl.suggestionStateHolderProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private WhatsAppNotificationListenerService injectWhatsAppNotificationListenerService2(
        WhatsAppNotificationListenerService instance2) {
      WhatsAppNotificationListenerService_MembersInjector.injectParser(instance2, singletonCImpl.whatsAppNotificationParserProvider.get());
      WhatsAppNotificationListenerService_MembersInjector.injectConversationRepository(instance2, singletonCImpl.conversationRepositoryImplProvider.get());
      WhatsAppNotificationListenerService_MembersInjector.injectGenerateSmartReplies(instance2, generateSmartRepliesUseCase());
      WhatsAppNotificationListenerService_MembersInjector.injectSuggestionStateHolder(instance2, singletonCImpl.suggestionStateHolderProvider.get());
      WhatsAppNotificationListenerService_MembersInjector.injectPreferences(instance2, singletonCImpl.preferencesDataStoreProvider.get());
      return instance2;
    }
  }

  private static final class SingletonCImpl extends SmartReplyApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<SmartReplyDatabase> provideDatabaseProvider;

    private Provider<SampleDataSeeder> sampleDataSeederProvider;

    private Provider<ConfigRepositoryImpl> configRepositoryImplProvider;

    private Provider<PreferencesDataStore> preferencesDataStoreProvider;

    private Provider<SettingsRepositoryImpl> settingsRepositoryImplProvider;

    private Provider<AnalyticsRepositoryImpl> analyticsRepositoryImplProvider;

    private Provider<LeadDetector> leadDetectorProvider;

    private Provider<ConversationRepositoryImpl> conversationRepositoryImplProvider;

    private Provider<TemplateRepositoryImpl> templateRepositoryImplProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Retrofit> provideRetrofitProvider;

    private Provider<GeminiApiService> provideGeminiApiProvider;

    private Provider<GeminiProvider> geminiProvider;

    private Provider<SmartReplyEngine> smartReplyEngineProvider;

    private Provider<SuggestionStateHolder> suggestionStateHolderProvider;

    private Provider<WhatsAppNotificationParser> whatsAppNotificationParserProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private ConversationDao conversationDao() {
      return DatabaseModule_ProvideConversationDaoFactory.provideConversationDao(provideDatabaseProvider.get());
    }

    private TemplateDao templateDao() {
      return DatabaseModule_ProvideTemplateDaoFactory.provideTemplateDao(provideDatabaseProvider.get());
    }

    private UserSettingsDao userSettingsDao() {
      return DatabaseModule_ProvideUserSettingsDaoFactory.provideUserSettingsDao(provideDatabaseProvider.get());
    }

    private AnalyticsDao analyticsDao() {
      return DatabaseModule_ProvideAnalyticsDaoFactory.provideAnalyticsDao(provideDatabaseProvider.get());
    }

    private MessageDao messageDao() {
      return DatabaseModule_ProvideMessageDaoFactory.provideMessageDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<SmartReplyDatabase>(singletonCImpl, 1));
      this.sampleDataSeederProvider = DoubleCheck.provider(new SwitchingProvider<SampleDataSeeder>(singletonCImpl, 0));
      this.configRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ConfigRepositoryImpl>(singletonCImpl, 2));
      this.preferencesDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<PreferencesDataStore>(singletonCImpl, 4));
      this.settingsRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<SettingsRepositoryImpl>(singletonCImpl, 3));
      this.analyticsRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AnalyticsRepositoryImpl>(singletonCImpl, 5));
      this.leadDetectorProvider = DoubleCheck.provider(new SwitchingProvider<LeadDetector>(singletonCImpl, 7));
      this.conversationRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ConversationRepositoryImpl>(singletonCImpl, 6));
      this.templateRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TemplateRepositoryImpl>(singletonCImpl, 9));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 13));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 12));
      this.provideGeminiApiProvider = DoubleCheck.provider(new SwitchingProvider<GeminiApiService>(singletonCImpl, 11));
      this.geminiProvider = DoubleCheck.provider(new SwitchingProvider<GeminiProvider>(singletonCImpl, 10));
      this.smartReplyEngineProvider = DoubleCheck.provider(new SwitchingProvider<SmartReplyEngine>(singletonCImpl, 8));
      this.suggestionStateHolderProvider = DoubleCheck.provider(new SwitchingProvider<SuggestionStateHolder>(singletonCImpl, 14));
      this.whatsAppNotificationParserProvider = DoubleCheck.provider(new SwitchingProvider<WhatsAppNotificationParser>(singletonCImpl, 15));
    }

    @Override
    public void injectSmartReplyApplication(SmartReplyApplication smartReplyApplication) {
      injectSmartReplyApplication2(smartReplyApplication);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @CanIgnoreReturnValue
    private SmartReplyApplication injectSmartReplyApplication2(SmartReplyApplication instance) {
      SmartReplyApplication_MembersInjector.injectSampleDataSeeder(instance, sampleDataSeederProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.myai.smartreplyai.data.local.SampleDataSeeder 
          return (T) new SampleDataSeeder(singletonCImpl.conversationDao(), singletonCImpl.templateDao(), singletonCImpl.userSettingsDao());

          case 1: // com.myai.smartreplyai.data.local.SmartReplyDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.myai.smartreplyai.data.repository.ConfigRepositoryImpl 
          return (T) new ConfigRepositoryImpl();

          case 3: // com.myai.smartreplyai.data.repository.SettingsRepositoryImpl 
          return (T) new SettingsRepositoryImpl(singletonCImpl.preferencesDataStoreProvider.get(), singletonCImpl.userSettingsDao());

          case 4: // com.myai.smartreplyai.data.local.PreferencesDataStore 
          return (T) new PreferencesDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.myai.smartreplyai.data.repository.AnalyticsRepositoryImpl 
          return (T) new AnalyticsRepositoryImpl(singletonCImpl.analyticsDao());

          case 6: // com.myai.smartreplyai.data.repository.ConversationRepositoryImpl 
          return (T) new ConversationRepositoryImpl(singletonCImpl.conversationDao(), singletonCImpl.messageDao(), singletonCImpl.leadDetectorProvider.get(), singletonCImpl.analyticsRepositoryImplProvider.get());

          case 7: // com.myai.smartreplyai.features.ai.lead.LeadDetector 
          return (T) new LeadDetector();

          case 8: // com.myai.smartreplyai.features.ai.engine.SmartReplyEngine 
          return (T) new SmartReplyEngine(singletonCImpl.templateRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.messageDao(), singletonCImpl.geminiProvider.get(), singletonCImpl.analyticsRepositoryImplProvider.get());

          case 9: // com.myai.smartreplyai.data.repository.TemplateRepositoryImpl 
          return (T) new TemplateRepositoryImpl(singletonCImpl.templateDao());

          case 10: // com.myai.smartreplyai.features.ai.provider.GeminiProvider 
          return (T) new GeminiProvider(singletonCImpl.provideGeminiApiProvider.get());

          case 11: // com.myai.smartreplyai.features.ai.data.GeminiApiService 
          return (T) NetworkModule_ProvideGeminiApiFactory.provideGeminiApi(singletonCImpl.provideRetrofitProvider.get());

          case 12: // retrofit2.Retrofit 
          return (T) NetworkModule_ProvideRetrofitFactory.provideRetrofit(singletonCImpl.provideOkHttpClientProvider.get());

          case 13: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 14: // com.myai.smartreplyai.features.smartreply.SuggestionStateHolder 
          return (T) new SuggestionStateHolder();

          case 15: // com.myai.smartreplyai.features.smartreply.parser.WhatsAppNotificationParser 
          return (T) new WhatsAppNotificationParser();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
