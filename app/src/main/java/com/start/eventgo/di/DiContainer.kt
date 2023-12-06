package com.start.eventgo.di

import android.app.Application
import android.content.Context
import com.start.eventgo.arch.data.SharedPreferencesRepository
import com.start.eventgo.create.organization.create.data.CreateOrganizationApi
import com.start.eventgo.create.organization.create.data.CreateOrganizationRepository
import com.start.eventgo.create.organization.create.presentation.CreateOrganizationViewModel
import com.start.eventgo.create.organization.list.data.OrganizationApi
import com.start.eventgo.create.organization.list.data.OrganizationRepository
import com.start.eventgo.create.organization.list.presentation.OrganizationsListViewModel
import com.start.eventgo.create.steps.about.AboutStepViewModel
import com.start.eventgo.create.steps.booking.BookingStepViewModel
import com.start.eventgo.create.steps.bottomsheet.BottomSheetSelectCategoryListViewModel
import com.start.eventgo.create.steps.calendar.TimeIntervalStepViewModel
import com.start.eventgo.create.steps.cancelrule.CancelRuleStepViewModel
import com.start.eventgo.create.steps.category.CategoryStepViewModel
import com.start.eventgo.create.steps.classification.ClassificationStepViewModel
import com.start.eventgo.create.steps.data.EventCreateApi
import com.start.eventgo.create.steps.data.EventCreateRepository
import com.start.eventgo.create.steps.eventrule.EventRuleStepViewModel
import com.start.eventgo.create.steps.freeorpay.EntryConditionStepViewModel
import com.start.eventgo.create.steps.guestcount.GuestCountStepViewModel
import com.start.eventgo.create.steps.language.LanguageStepViewModel
import com.start.eventgo.create.steps.location.LocationStepViewModel
import com.start.eventgo.create.steps.name.NameStepViewModel
import com.start.eventgo.create.steps.needings.NeededItemsStepViewModel
import com.start.eventgo.create.steps.photo.PhotoStepViewModel
import com.start.eventgo.create.steps.price.PriceStepViewModel
import com.start.eventgo.create.steps.service.AdditionalServiceStepViewModel
import com.start.eventgo.create.steps.teamcount.TeamCountStepViewModel
import com.start.eventgo.create.steps.type.presentation.TypeStepViewModel
import com.start.eventgo.create.steps.visitperson.AllowedGuestStepViewModel
import com.start.eventgo.main.ui.detail.data.EventDetailApi
import com.start.eventgo.main.ui.detail.data.EventDetailRepository
import com.start.eventgo.main.ui.detail.presentation.EventDetailViewModel
import com.start.eventgo.main.ui.home.data.HomePageApi
import com.start.eventgo.main.ui.home.data.HomePageRepository
import com.start.eventgo.main.ui.home.presentation.HomeViewModel
import com.start.eventgo.main.ui.order.data.TicketOrderApi
import com.start.eventgo.main.ui.order.data.TicketOrderRepository
import com.start.eventgo.main.ui.order.presentation.payment.PaymentViewModel
import com.start.eventgo.main.ui.order.presentation.steps.TicketOrderViewModel
import com.start.eventgo.main.ui.profile.data.api.ManageEventsApi
import com.start.eventgo.main.ui.profile.data.api.ProfileApi
import com.start.eventgo.main.ui.profile.data.manageEvents.ManageEventsViewModel
import com.start.eventgo.main.ui.profile.data.manageEvents.organizations.ManageOrganizationViewModel
import com.start.eventgo.main.ui.profile.data.repository.ManageEventsRepository
import com.start.eventgo.main.ui.profile.data.repository.ProfileRepository
import com.start.eventgo.main.ui.profile.presentation.ProfileViewModel
import com.start.eventgo.main.ui.tickets.mytickets.data.MyTicketsApi
import com.start.eventgo.main.ui.tickets.mytickets.data.MyTicketsRepository
import com.start.eventgo.main.ui.tickets.mytickets.presentation.MyTicketsViewModel
import com.start.eventgo.main.ui.tickets.ticket.data.TicketApi
import com.start.eventgo.main.ui.tickets.ticket.data.TicketRepository
import com.start.eventgo.main.ui.tickets.ticket.presentation.TicketViewModel
import com.start.eventgo.network.ApiErrorExceptionFactory
import com.start.eventgo.network.DefaultApiErrorExceptionFactory
import com.start.eventgo.registration.createpassword.domain.PasswordGenerationRepository
import com.start.eventgo.registration.createpassword.presentation.PasswordGenerationViewModel
import com.start.eventgo.registration.registr.data.RegistrationApi
import com.start.eventgo.registration.registr.data.RegistrationRepository
import com.start.eventgo.registration.registr.domain.VerificationRepository
import com.start.eventgo.registration.registr.presentation.RegistrationViewModel
import com.start.eventgo.registration.varification.data.VerificationApi
import com.start.eventgo.registration.varification.presentation.VerificationViewModel
import com.start.eventgo.search.search.presentation.SearchViewModel
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal const val APP_RETROFIT = "app_retrofit"
internal const val APP_RETROFIT_HTTP_CLIENT = "app_retrofit_http_client"

object DiContainer {

    private val networkModule = module {

        single { getOkHttpClient(androidContext()) }

        single(named(APP_RETROFIT)) { getRetrofit(get()) }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            RegistrationRepository(
                registrationApi = appRetrofit.create(RegistrationApi::class.java),
                apiErrorExceptionFactory = get()
            )
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            HomePageRepository(
                homePageApi = appRetrofit.create(HomePageApi::class.java),
                apiErrorExceptionFactory = get()
            )
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            TicketOrderRepository(
                ticketOrderApi = appRetrofit.create(TicketOrderApi::class.java),
                apiErrorExceptionFactory = get()
            )
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            VerificationRepository(
                verificationApi = appRetrofit.create(VerificationApi::class.java),
                apiErrorExceptionFactory = get()
            )
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            PasswordGenerationRepository(
                registrationApi = appRetrofit.create(RegistrationApi::class.java),
                apiErrorExceptionFactory = get()
            )
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            OrganizationRepository(
                organizationApi = appRetrofit.create(OrganizationApi::class.java),
            )
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            CreateOrganizationRepository(
                createOrganizationApi = appRetrofit.create(CreateOrganizationApi::class.java),
            )
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            EventCreateRepository(
                eventCreateApi = appRetrofit.create(EventCreateApi::class.java),
            )
        }

        factory<ApiErrorExceptionFactory> {
            DefaultApiErrorExceptionFactory() as ApiErrorExceptionFactory
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            EventDetailRepository(
                eventDetailApi = appRetrofit.create(EventDetailApi::class.java)
            )
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            ProfileRepository(
                profileApi = appRetrofit.create(ProfileApi::class.java)
            )
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            MyTicketsRepository(
                myTicketsApi = appRetrofit.create(MyTicketsApi::class.java)
            )
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            TicketRepository(
                ticketApi = appRetrofit.create(TicketApi::class.java)
            )
        }

        factory {
            val appRetrofit: Retrofit = get(named(APP_RETROFIT))
            ManageEventsRepository(
                manageEventsApi = appRetrofit.create(ManageEventsApi::class.java)
            )
        }
    }

    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://157.230.117.5")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun getOkHttpClient(context: Context): OkHttpClient {
// 		val localStore = SharedPreferencesRepository(context)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS) // Увеличьте значение таймаута
            .readTimeout(30, TimeUnit.SECONDS) // Увеличьте значение таймаута
// 			.addInterceptor {
// 				val requestBuilder = it.request().newBuilder()
// 					.addHeader("Authorization", "Bearer ${localStore.getUserToken()}")
// 				it.proceed(requestBuilder.build())
// 			}
            .build()
    }

    val viewModelModule: Module = module {

        viewModel {
            RegistrationViewModel(
                registrationRepository = get()
            )
        }

        viewModel {
            HomeViewModel(
                homePageRepository = get()
            )
        }

        viewModel {
            VerificationViewModel(
                verificationRepo = get(),
                sharedPreferencesRepository = get()
            )
        }

        viewModel {
            PasswordGenerationViewModel(
                passwordGenerationRepository = get()
            )
        }

        viewModel {
            OrganizationsListViewModel(
                organizationRepository = get()
            )
        }

        viewModel {
            ManageOrganizationViewModel(
                application = androidApplication(),
                manageEventsRepository = get(),
            )
        }

        viewModel {
            CreateOrganizationViewModel(
                createOrganizationRepository = get()
            )
        }

        viewModel {
            BottomSheetSelectCategoryListViewModel(
                homePageRepository = get()
            )
        }

        viewModel {
            LanguageStepViewModel(
                eventCreateRepository = get(),
            )
        }

        viewModel {
            TypeStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            CategoryStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            AboutStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            LocationStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            TimeIntervalStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            BookingStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            AdditionalServiceStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            NeededItemsStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            AllowedGuestStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            NameStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            PhotoStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            ClassificationStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            GuestCountStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            TeamCountStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            EntryConditionStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            PriceStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            CancelRuleStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            EventRuleStepViewModel(
                eventCreateRepository = get()
            )
        }

        viewModel {
            EventDetailViewModel(
                eventDetailRepository = get()
            )
        }

        viewModel {
            ProfileViewModel(
                profileRepository = get()
            )
        }

        viewModel {
            MyTicketsViewModel(
                myTicketsRepository = get()
            )
        }

        viewModel {
            TicketViewModel(
                ticketRepository = get()
            )
        }

        viewModel {
            SearchViewModel(
                homePageRepository = get()
            )
        }

        viewModel {
            TicketOrderViewModel(
                ticketOrderRepository = get()
            )
        }

        viewModel {
            PaymentViewModel(
                application = androidApplication()
            )
        }

        viewModel {
            ManageEventsViewModel(
                application = androidApplication(),
                manageEventsRepository = get(),
            )
        }
    }

    private val mainModule: Module = module {

        factory {
            SharedPreferencesRepository(androidContext())
        }
    }

    fun startKoinDi(application: Application) {
        startKoin {
            androidContext(application)
            modules(
                viewModelModule,
                networkModule,
                mainModule
            )
        }
    }
}
