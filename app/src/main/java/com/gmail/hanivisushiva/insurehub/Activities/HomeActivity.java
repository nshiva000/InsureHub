package com.gmail.hanivisushiva.insurehub.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.gmail.hanivisushiva.insurehub.Activities.ServiceProvider.AddBillsServiceProviderActivity;
import com.gmail.hanivisushiva.insurehub.Activities.ServiceProvider.AllBillsServiceProviderActivity;
import com.gmail.hanivisushiva.insurehub.Activities.ServiceProvider.ProfileServiceProviderActivity;
import com.gmail.hanivisushiva.insurehub.Activities.ServiceProvider.ServiceProviderCustomerList;
import com.gmail.hanivisushiva.insurehub.Activities.ServiceProvider.ServiceproviderEnquiryActivity;
import com.gmail.hanivisushiva.insurehub.Activities.User.AddServiceUserActivity;
import com.gmail.hanivisushiva.insurehub.Activities.User.AllCustomerRequestActivity;
import com.gmail.hanivisushiva.insurehub.Activities.User.AllCustomerServicesActivity;
import com.gmail.hanivisushiva.insurehub.Activities.User.CustomerMyPolicyActivity;
import com.gmail.hanivisushiva.insurehub.Activities.User.CustomerMyRewardsActivity;
import com.gmail.hanivisushiva.insurehub.Activities.User.CustomerOffersActivity;
import com.gmail.hanivisushiva.insurehub.Activities.User.CustomerOurProductsActivity;
import com.gmail.hanivisushiva.insurehub.Activities.User.CustomerServicePartnersActivity;
import com.gmail.hanivisushiva.insurehub.Activities.User.MapsActivityCustomer;
import com.gmail.hanivisushiva.insurehub.Activities.User.ContactUsUserActivity;
import com.gmail.hanivisushiva.insurehub.Activities.User.ReferAndEarnUserActivity;
import com.gmail.hanivisushiva.insurehub.Adapter.HomeAdapter;
import com.gmail.hanivisushiva.insurehub.Model.BannerImagesModel.BannerImagesModel;
import com.gmail.hanivisushiva.insurehub.Model.DashBoardModel.DashBoardModel;
import com.gmail.hanivisushiva.insurehub.Model.ServiceProviderDashBoardModel.ServiceProviderDashBoardModel;
import com.gmail.hanivisushiva.insurehub.ProfileActivity;
import com.gmail.hanivisushiva.insurehub.R;
import com.gmail.hanivisushiva.insurehub.RetrofitClient;
import com.gmail.hanivisushiva.insurehub.Storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String role,sid;

    TextView u_name,u_email;

    HashMap<String,String> dashData = new HashMap<>();

    private ArrayList<String> names = new ArrayList<>();

    private int icon[] = {
            R.drawable.products, R.drawable.rewards, R.drawable.customers,R.drawable.bills
    };


    private SliderLayout mDemoSlider;
    private CircleImageView circleImageView;


    CardView products_cardView,offers_cardView,referAndEarnCardView,customer_cardView,bills_cardView;
    LinearLayout productAndOffersView,customerBillLinearLayout;
    RelativeLayout referAndEarnView;



    @Override
    protected void onStart() {
        super.onStart();

        if (role.equals("admin")){
            //get_dashboard_details();
        }else if(role.equals("serviceprovider")){
            //get_service_provide_dashboard(sid);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        products_cardView = findViewById(R.id.productListCardView);
        offers_cardView = findViewById(R.id.offersCardView);
        customer_cardView = findViewById(R.id.customersCardView);
        bills_cardView = findViewById(R.id.billsCardView);

        referAndEarnCardView = findViewById(R.id.referAndEarn);

        productAndOffersView = findViewById(R.id.cardViewRelativeLayout);
        referAndEarnView = findViewById(R.id.referAndEarnRelativeLayout);

        customerBillLinearLayout = findViewById(R.id.customerBillLinearLayout);



        mDemoSlider = findViewById(R.id.slider);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Dashboard");
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        role = SharedPrefManager.get_mInstance(getApplicationContext()).getRole().toLowerCase();

        View header=navigationView.getHeaderView(0);
        u_name = header.findViewById(R.id.header_name);
        u_email = header.findViewById(R.id.header_email);
        circleImageView = header.findViewById(R.id.header_imageView);


        sid = SharedPrefManager.get_mInstance(getApplicationContext()).getId().toString();



        if (role.equals("admin")){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_home_drawer);

            u_name.setText("Admin");
            u_email.setText("admin@insurehub.com");
            mDemoSlider.setVisibility(View.GONE);


            referAndEarnView.setVisibility(View.GONE);
            productAndOffersView.setVisibility(View.GONE);
            customerBillLinearLayout.setVisibility(View.GONE);



        }else if(role.equals("user")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.customer_menu);

            u_name.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getName());
            u_email.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getEmail());
            if (SharedPrefManager.get_mInstance(getApplicationContext()).getImage() != ""){
                Picasso.get().load(SharedPrefManager.get_mInstance(getApplicationContext()).getImage()).into(circleImageView);
            }
            mDemoSlider.setVisibility(View.VISIBLE);

            referAndEarnView.setVisibility(View.VISIBLE);
            productAndOffersView.setVisibility(View.VISIBLE);

            customerBillLinearLayout.setVisibility(View.GONE);

        } else if(role.equals("serviceprovider")){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.service_provider);
            u_name.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getName());
            u_email.setText(SharedPrefManager.get_mInstance(getApplicationContext()).getEmail());

            mDemoSlider.setVisibility(View.GONE);
            referAndEarnView.setVisibility(View.VISIBLE);
            productAndOffersView.setVisibility(View.VISIBLE);
            customerBillLinearLayout.setVisibility(View.VISIBLE);
        }





        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

         get_trending_posts();



         products_cardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(HomeActivity.this, CustomerOurProductsActivity.class);
                 startActivity(intent);
             }
         });

         offers_cardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(HomeActivity.this, CustomerOffersActivity.class);
                 startActivity(intent);
             }
         });

         referAndEarnCardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(HomeActivity.this, ReferAndEarnUserActivity.class);
                 startActivity(intent);
             }
         });

         customer_cardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(HomeActivity.this, ServiceProviderCustomerList.class);
                 startActivity(intent);
             }
         });

         bills_cardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(HomeActivity.this, AllBillsServiceProviderActivity.class);
                 startActivity(intent);
             }
         });



    }



    private void get_dashboard_details(){
        Call<DashBoardModel> call = RetrofitClient.getmInstance().getApi().getDashBoardDetails();
        call.enqueue(new Callback<DashBoardModel>() {
            @Override
            public void onResponse(Call<DashBoardModel> call, Response<DashBoardModel> response) {
                DashBoardModel dashBoardModel = response.body();

                if (dashBoardModel != null){
                    if (dashBoardModel.getStatus()){
                        if (dashBoardModel.getData().getCustomers() != null){
                            dashData.put(names.get(0),dashBoardModel.getData().getCustomers().toString());
                        }

                        if (dashBoardModel.getData().getServiceProviders() != null){
                            dashData.put(names.get(1),dashBoardModel.getData().getServiceProviders().toString());
                        }

                        if (dashBoardModel.getData().getBills() != null){
                            dashData.put(names.get(2),dashBoardModel.getData().getBills().toString());
                        }


                    }
                }else {
                    Toast_msg("server returned null");
                }
            }

            @Override
            public void onFailure(Call<DashBoardModel> call, Throwable t) {

            }
        });
    }

    private void get_service_provide_dashboard(String id){
        Toast_msg(id);
        Call<ServiceProviderDashBoardModel> call = RetrofitClient.getmInstance().getApi().getServiceProviderDashBoardDetails(id);
        call.enqueue(new Callback<ServiceProviderDashBoardModel>() {
            @Override
            public void onResponse(Call<ServiceProviderDashBoardModel> call, Response<ServiceProviderDashBoardModel> response) {
                ServiceProviderDashBoardModel serviceProviderDashBoardModel = response.body();
                if (serviceProviderDashBoardModel != null){
                    if (serviceProviderDashBoardModel.getStatus()){
                        if (serviceProviderDashBoardModel.getData().getCustomers() != null){
                            dashData.put(names.get(0),serviceProviderDashBoardModel.getData().getCustomers().toString());
                        }

                        if (serviceProviderDashBoardModel.getData().getBills() != null){
                            dashData.put(names.get(1),serviceProviderDashBoardModel.getData().getBills().toString());
                        }
                    }else{
                        Toast_msg("the server response status is false");
                    }
                }else {
                    Toast_msg("server returned null");
                }
            }

            @Override
            public void onFailure(Call<ServiceProviderDashBoardModel> call, Throwable t) {

            }
        });
    }



    private void get_trending_posts(){


        Call<BannerImagesModel> call = RetrofitClient.getmInstance().getApi().getBanners();

        call.enqueue(new Callback<BannerImagesModel>() {
            @Override
            public void onResponse(Call<BannerImagesModel> call, Response<BannerImagesModel> response) {
                BannerImagesModel updateModel = response.body();
                if (updateModel != null){
                    if (updateModel.getStatus()){


                        ArrayList<String> listUrl = new ArrayList<>();
                        ArrayList<String> listName = new ArrayList<>();


                        for (int i =0;i<updateModel.getData().size();i++){
                            listUrl.add(updateModel.getData().get(i).getBanner());
                            listName.add("");
                        }



                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.centerCrop();
                        //.diskCacheStrategy(DiskCacheStrategy.NONE)
                        //.placeholder(R.drawable.placeholder)
                        //.error(R.drawable.placeholder);

                        for (int i = 0; i < listUrl.size(); i++) {
                            TextSliderView sliderView = new TextSliderView(getApplicationContext());
                            // if you want show image only / without description text use DefaultSliderView instead

                            // initialize SliderLayout
                            sliderView
                                    .image(listUrl.get(i))
                                    .description(listName.get(i))
                                    .setRequestOption(requestOptions)
                                    .setProgressBarVisible(true);

                            //add your extra information
                            sliderView.bundle(new Bundle());
                            sliderView.getBundle().putString("extra", listName.get(i));
                            mDemoSlider.addSlider(sliderView);
                        }

                        // set Slider Transition Animation
                        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
                        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

                        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                        mDemoSlider.setDuration(4000);

                    }else {
                        Toast_msg("No Banners to show");
                    }
                }else{
                    Toast_msg("Null response from the server");
                }
            }

            @Override
            public void onFailure(Call<BannerImagesModel> call, Throwable t) {

            }
        });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_category) {
            Intent categoryIntent = new Intent(HomeActivity.this,CategoryActivity.class);
            startActivity(categoryIntent);
        } else if (id == R.id.nav_customer) {
            Intent intent = new Intent(HomeActivity.this,CustomerActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_customer_request) {
            Intent intent = new Intent(HomeActivity.this, AdminEnquiriesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_service_provider) {
            Intent intent = new Intent(HomeActivity.this,ServiceProviderActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_policy_request) {
            Intent intent = new Intent(HomeActivity.this, PolicyRequestListActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_service_provider_add_bills) {
            Intent intent = new Intent(HomeActivity.this, AddBillsServiceProviderActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_service_provider_all_bills) {
            Intent intent = new Intent(HomeActivity.this, AllBillsServiceProviderActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_service_provider_all_customers) {
            Intent intent = new Intent(HomeActivity.this, ServiceProviderCustomerList.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_customer_add_service) {
            Intent intent = new Intent(HomeActivity.this, AddServiceUserActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_customer_raise_request) {
            Intent intent = new Intent(HomeActivity.this, ContactUsUserActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_ads) {
            Intent intent = new Intent(HomeActivity.this, BannerAdsActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_all_bills) {
            Intent intent = new Intent(HomeActivity.this, AdminAllBillsActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.profile) {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_all_referals) {
            Intent intent = new Intent(HomeActivity.this, AllReferalsAdminActivity.class);
            startActivity(intent);

        }


        else if (id == R.id.nav_customer_all_service) {
            Intent intent = new Intent(HomeActivity.this, AllCustomerServicesActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_customer_all_request) {
            Intent intent = new Intent(HomeActivity.this, AllCustomerRequestActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_add_rewards) {
            Intent intent = new Intent(HomeActivity.this, AdminAddRewardsActivity.class);
            startActivity(intent);

        }


        else if (id == R.id.nav_add_offers) {
            Intent intent = new Intent(HomeActivity.this, AdminAddOffersActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_upload_policy_details) {
            Intent intent = new Intent(HomeActivity.this, AdminUploadPolicyDetailsActivity.class);
            startActivity(intent);

        }







        else if (id == R.id.nav_customer_my_policy) {
            Intent intent = new Intent(HomeActivity.this, CustomerMyPolicyActivity.class);
            startActivity(intent);

        }


        else if (id == R.id.nav_customer_my_rewards) {
            Intent intent = new Intent(HomeActivity.this, CustomerMyRewardsActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_customer_offers) {
            Intent intent = new Intent(HomeActivity.this, CustomerOffersActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_customer_our_products) {
            Intent intent = new Intent(HomeActivity.this, CustomerOurProductsActivity.class);
            startActivity(intent);

        }


        else if (id == R.id.nav_customer_service_partners) {
            Intent intent = new Intent(HomeActivity.this, CustomerServicePartnersActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_customer_services_near_by) {
            Intent intent = new Intent(HomeActivity.this, MapsActivityCustomer.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_service_provider_contact_us) {
            Intent intent = new Intent(HomeActivity.this, ContactUsUserActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_service_provider_profile) {
            Intent intent = new Intent(HomeActivity.this, ProfileServiceProviderActivity.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_service_provider_enquiry) {
            Intent intent = new Intent(HomeActivity.this, ServiceproviderEnquiryActivity.class);
            startActivity(intent);

        }




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void logout() {

        SharedPrefManager.get_mInstance(getApplicationContext()).clear();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    private void Toast_msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
