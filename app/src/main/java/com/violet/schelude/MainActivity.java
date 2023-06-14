package com.violet.schelude;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.violet.schelude.UI.SchedulePagerAdapter;
import com.violet.schelude.api.ScheduleApi;
import com.violet.schelude.models.Lesson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    String[] days = {"ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС"};
    public List<Lesson> lessons = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.11")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ScheduleApi scheduleApi = retrofit.create(ScheduleApi.class);

        Call<List<Lesson>> call = scheduleApi.getLessons();
        call.enqueue(new Callback<List<Lesson>>() {
            @Override
            public void onResponse(Call<List<Lesson>> call, Response<List<Lesson>> response) {
                if (response.isSuccessful()) {
                    lessons = response.body();
                    // Обработка полученного списка занятий
                } else {
                    // Обработка ошибки при получении данных
                }
            }

            @Override
            public void onFailure(Call<List<Lesson>> call, Throwable t) {
                // Обработка ошибки при выполнении запроса
            }
        });


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        SchedulePagerAdapter adapter = new SchedulePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);


        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) tab.setText(days[i]);
        }

        tabLayout.selectTab(tabLayout.getTabAt(3));

    }
}