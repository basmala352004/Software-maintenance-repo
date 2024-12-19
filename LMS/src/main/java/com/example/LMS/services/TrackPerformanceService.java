package com.example.LMS.services;
import com.example.LMS.models.CourseModel;
import com.example.LMS.models.StudentModel;
import com.example.LMS.models.LessonModel;
import com.example.LMS.models.AttendanceModel;
import com.example.LMS.repositories.CourseRepository;
import com.example.LMS.repositories.StudentRepository;
import com.example.LMS.repositories.AttendanceRepository;
import com.example.LMS.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Optional;



@Service
public class TrackPerformanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;


    public List<Map<String, Object>> getPerformanceForCourses(List<String> courseNames, String lessonName) {
        List<Map<String, Object>> result = new ArrayList<>();


        for (String courseName : courseNames) {

            Optional<CourseModel> courseOpt = courseRepository.findByTitle(courseName);
            if (courseOpt.isPresent()) {
                CourseModel course = courseOpt.get();


                Optional<LessonModel> lessonOpt = course.getListLessons().stream()
                        .filter(lesson -> lesson.getTitle().equals(lessonName))
                        .findFirst();

                if (lessonOpt.isPresent()) {
                    LessonModel lesson = lessonOpt.get();

                    List<AttendanceModel> attendanceRecords = attendanceRepository.findByLesson(lesson);

                    Map<String, Object> coursePerformance = new HashMap<>();
                    coursePerformance.put("Course", course.getTitle());

                    List<Map<String, Object>> performanceDetails = new ArrayList<>();


                    for (AttendanceModel record : attendanceRecords) {
                        Map<String, Object> performanceDetail = new HashMap<>();
                        performanceDetail.put("Student ID", record.getStudent().getId());
                        performanceDetail.put("Attendance Status", record.isAttended() ? "Attended" : "Not Attended");
                        performanceDetail.put("Lesson", lesson.getTitle());
                        performanceDetail.put("Date", record.getTimestamp().toLocalDate()); //اليوم



                        performanceDetails.add(performanceDetail);
                    }

                    coursePerformance.put("Performance", performanceDetails);
                    result.add(coursePerformance);
                }
            }
        }

        return result;
    }

    // ريبورت
}
