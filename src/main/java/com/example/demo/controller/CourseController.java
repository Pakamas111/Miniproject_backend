package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
public class CourseController {
	
	@CrossOrigin(origins = "http://localhost:8082")
	@RestController
	public class CourseControllere {

		@Autowired
		CourseRepository courseRepository;

		@GetMapping("/course")
		public ResponseEntity<Object> getcourse() {
			try {
				List<Course> course = courseRepository.findAll();
				return new ResponseEntity<>(course, HttpStatus.OK);
				
			} catch (Exception e) {
				
				return new ResponseEntity<>("Internal server error", HttpStatus.OK);
				// TODO: handle exception
			}
		}

		@PostMapping("/course")
		public ResponseEntity<Object> addCourse(@RequestBody Course body) {
			try {
				courseRepository.save(body);
				
				return new ResponseEntity<>(body, HttpStatus.CREATED);
				
			} catch (Exception e) {
				
				return new ResponseEntity<>("Internal server  error", HttpStatus.OK);
			}
		}

		@GetMapping("/course/{courseId}")
		public ResponseEntity<Object> getcourseDetail(@PathVariable ("courseId")Integer courseId) {
			try {
				
				Optional<Course> course = courseRepository.findById(courseId);
				if (course.isPresent()) {
					
					return new ResponseEntity<>(course, HttpStatus.OK);
					
				} else {
					
					return new ResponseEntity<>("course not found", HttpStatus.OK);
				}
				
			} catch (Exception e) {
				
				return new ResponseEntity<>("Internal server  error", HttpStatus.OK);
			}
		}

		@PutMapping("/course/{courseId}")
		public ResponseEntity<Object> updatCourse(@PathVariable Integer courseId, @RequestBody Course body) {
			try {
				Optional<Course> course = courseRepository.findById(courseId);

				if (course.isPresent()) {

					Course courseEdit = course.get();

					courseEdit.setCourseName(body.getCourseName());	
					courseEdit.setUser(body.getUser());
					courseEdit.setTrainer(body.getTrainer());
					courseRepository.save(courseEdit);

					return new ResponseEntity<>(courseEdit,HttpStatus.OK);

				} else {

					return new ResponseEntity<>(" course not found",HttpStatus.BAD_REQUEST);
				}
				
			} catch (Exception e) {
				
				return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		
			}

		}

		@DeleteMapping("/course/{courseId}")
		public ResponseEntity<Object> deletCourse(@PathVariable Integer courseId) {
			
			try {
				
				Optional<Course> course = courseRepository.findById(courseId);
				
				if (course.isPresent()) {
					courseRepository.delete(course.get());

					return new ResponseEntity<>("DELETE SUCSESS",HttpStatus.OK);
					
			}  else {
				
				return new ResponseEntity<>("course not found",HttpStatus.BAD_REQUEST);
			}
				
			} catch (Exception e) {
				
				return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	}

}
