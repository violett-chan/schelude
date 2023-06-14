package com.violet.schelude.api;

import com.violet.schelude.models.Lesson;
import com.violet.schelude.models.Student;

import java.util.List;

import retrofit2.*;
import retrofit2.http.*;

public interface ScheduleApi {
    @GET("/students.php")
    Call<List<Student>> getStudents();
    @GET("/schelude.php")
    Call<List<Lesson>> getLessons();
    @FormUrlEncoded
    @POST("/students_info.php")
    Call<Void> addInfo(
            @Field("lessonId") int lessonId,
            @Field("skipList") String skipList,
            @Field("presenceList") String presenceList
    );
}
