package com.violet.schelude.UI.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.violet.schelude.MainActivity;
import com.violet.schelude.R;
import com.violet.schelude.StudentsActivity;
import com.violet.schelude.models.Lesson;

import java.util.ArrayList;
import java.util.List;

public class SundayFragment extends Fragment {
    public SundayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) requireActivity();
        List<Lesson> dayLessons = new ArrayList<>();
        for (Lesson lesson : mainActivity.lessons) {
            if (lesson.day == 6) {
                dayLessons.add(lesson);
            }
        }
        View rootView = inflater.inflate(R.layout.fragment_friday, container, false);
        LinearLayout linearLayout = rootView.findViewById(R.id.fragment_friday_LinearLayout);
        for (Lesson lesson : dayLessons) {
            View lessonView = inflater.inflate(R.layout.ui_lesson, container, false);
            TextView numTextView = lessonView.findViewById(R.id.ui_lesson_textViewNumber);
            TextView titleTextView = lessonView.findViewById(R.id.ui_lesson_textViewTitle);
            TextView teacherTextView = lessonView.findViewById(R.id.ui_lesson_textViewTeacher);
            CardView cardView = lessonView.findViewById(R.id.ui_lesson_cardView);
            cardView.setOnClickListener(v -> {
                // Создаем объект Intent с текущим контекстом (this) и целевой активити (TargetActivity.class)
                Intent intent = new Intent(requireContext(), StudentsActivity.class);
                // Дополнительно, если вам нужно передать данные в целевую активити, вы можете использовать методы putExtra()
                intent.putExtra("lesson", lesson.id);
                // Запускаем активити
                startActivity(intent);

            });
            numTextView.setText(String.valueOf(lesson.day + 1));
            titleTextView.setText(lesson.name);
            teacherTextView.setText(lesson.teacher);
            linearLayout.addView(lessonView);
        }
        return rootView;
    }
}