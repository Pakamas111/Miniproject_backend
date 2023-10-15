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

import com.example.demo.model.Admin;
import com.example.demo.model.Course;
import com.example.demo.model.Trainer;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CourseRepository;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
public class AdminController {

	@RestController
	public class AdminControllere {

		@Autowired
		AdminRepository adminRepository;
		
		@Autowired
		CourseRepository courseRepository;

		@PostMapping("/admin")
		public ResponseEntity<Object> addCourse(@RequestBody Admin body) {
			try {

				adminRepository.save(body);

				return new ResponseEntity<>(body, HttpStatus.CREATED);

			} catch (Exception e) {

				return new ResponseEntity<>("Internal server  error", HttpStatus.OK);
			}
		}

		@GetMapping("/admin")
		public ResponseEntity<Object> getadmin() {
			try {

				List<Admin> admins = adminRepository.findAll();

				return new ResponseEntity<>(admins, HttpStatus.OK);

			} catch (Exception e) {

				return new ResponseEntity<>("Internal server error", HttpStatus.OK);
				// TODO: handle exception
			}
		}

		@PutMapping("/admin/{adminId}")
		public ResponseEntity<Object> updatAdmin(@PathVariable Integer adminId, @RequestBody Admin body) {
			try {
				Optional<Admin> admin = adminRepository.findById(adminId);

				if (admin.isPresent()) {

					Admin adminEdit = admin.get();

					adminEdit.setFirstName(body.getFirstName());
					adminEdit.setLastName(body.getLastName());
					adminEdit.setPassword(body.getPassword());
					adminRepository.save(adminEdit);

					return new ResponseEntity<>(adminEdit, HttpStatus.OK);

				} else {

					return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {

				return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

			}

		}

		@GetMapping("/admin/{adminId}")
		public ResponseEntity<Object> getcourseDetail(@PathVariable Integer adminId) {
			try {

				Optional<Admin> admin = adminRepository.findById(adminId);

				if (admin.isPresent()) {
					return new ResponseEntity<>(admin, HttpStatus.OK);

				} else {

					return new ResponseEntity<>("Admin not found", HttpStatus.OK);
				}

			} catch (Exception e) {

				return new ResponseEntity<>("Integer server  error", HttpStatus.OK);
			}
		}

		@DeleteMapping("/admin/{adminId}")
		public ResponseEntity<Object> deletCourse(@PathVariable Integer adminId) {

			try {
				Optional<Admin> findAdmin = adminRepository.findById(adminId);

				if (findAdmin.isPresent()) {

					List<Object[]> listReviews = courseRepository.findCourseByAdminId(adminId);

					for (Object[] row : listReviews) {
						Integer courseId = (Integer) row[0];
						String courseName = (String) row[1];
						Trainer trainer = (Trainer) row[2];
		

						Course course = new Course(courseId, courseName, null,trainer);

						courseRepository.delete(course);

					}

					adminRepository.delete(findAdmin.get());

					return new ResponseEntity<>("Delete Admin Success.", HttpStatus.OK);
				} else {

					return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {

				return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	}

}
