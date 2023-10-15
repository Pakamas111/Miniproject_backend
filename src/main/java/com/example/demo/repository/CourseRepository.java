package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Course;

@Repository
	public interface CourseRepository extends JpaRepository<Course,Integer>{

	@Query("SELECT f.courseId, f.courseName, f.trainer FROM Course f WHERE f.user.userId = :userId")
	List<Object[]> findCourseByUserId(@Param ("userId")Integer userId);

	@Query("SELECT f.courseId, f.courseName, f.user FROM Course f WHERE f.trainer.trainerId = :trainerId")
	List<Object[]> findCourseBytrainerId(@Param ("trainerId")Integer trainerId);
	


}
