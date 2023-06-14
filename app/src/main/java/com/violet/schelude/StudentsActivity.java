package com.violet.schelude;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.violet.schelude.api.ScheduleApi;
import com.violet.schelude.models.Lesson;
import com.violet.schelude.models.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentsActivity extends AppCompatActivity {
    public List<Student> students = new ArrayList<>();
    Integer lessonId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("lesson")) {
            lessonId = intent.getIntExtra("lesson", -1);
        }
        LinearLayout linearLayout = findViewById(R.id.studentsLinearLayout);
        LayoutInflater inflater = LayoutInflater.from(this);
        CheckBox checkBox = findViewById(R.id.checkAllCheckBox);
        Button button = findViewById(R.id.sendButton);
        List<CheckBox> checkBoxes = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.11")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ScheduleApi scheduleApi = retrofit.create(ScheduleApi.class);

        Call<List<Student>> call = scheduleApi.getStudents();
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    students = response.body();
                    for (Student student : students) {
                        View inflatedView = inflater.inflate(R.layout.ui_student, null);
                        CheckBox checkBox = inflatedView.findViewById(R.id.checkBox);
                        checkBox.setId(View.generateViewId());
                        checkBoxes.add(checkBox);
                        String text = student.firstname + " " + student.lastname;
                        checkBox.setText(text);
                        linearLayout.addView(inflatedView);
                    }
                    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        for (CheckBox c : checkBoxes) {
                            c.setChecked(isChecked);
                        }
                    });
                    button.setOnClickListener(v -> {
                        List<Integer> skipList = new ArrayList<>();
                        List<Integer> presenceList = new ArrayList<>();

                        for (int i = 0; i < students.size(); i++) {
                            if (checkBoxes.get(i).isChecked()) presenceList.add(students.get(i).id);
                            else skipList.add(students.get(i).id);
                        }
                        Call<Void> sendCall = scheduleApi.addInfo(lessonId, skipList.toString(), presenceList.toString());
                        sendCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Отправлено", Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Ошибка", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    });
                    // Обработка полученного списка студентов
                } else {
                    // Обработка ошибки при получении данных
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                // Обработка ошибки при выполнении запроса
            }
        });
    }
}