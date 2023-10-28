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
import com.example.demo.model.Trainer;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TrainerRepository;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
public class TrainerController {

	@Autowired
	TrainerRepository trainerRepository;
	
	@Autowired
	CourseRepository courseRepository;

	@PostMapping("/trainer")
	public ResponseEntity<Object> addtrainer(@RequestBody Trainer body) {
		try {

			Trainer newTrainer = trainerRepository.save(body);

			return new ResponseEntity<>(newTrainer, HttpStatus.CREATED);
		} catch (Exception e) {

			System.out.print(e.getMessage());
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/trainer")
	public ResponseEntity<Object> getAllTrainer() {
		try {

			List<Trainer> listTrainer = trainerRepository.findAll();

			return new ResponseEntity<>(listTrainer, HttpStatus.OK);

		} catch (Exception e) {

			System.out.print(e.getMessage());
			return new ResponseEntity<>("Integer server  error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/trainer/{trainerId}")
	public ResponseEntity<Object> getAllgetAllTrainerById(@PathVariable("trainerId") Integer trainerId) {
		try {

			Optional<Trainer> foundTrainer = trainerRepository.findById(trainerId);
			if (foundTrainer.isPresent()) {

				Trainer trainer = foundTrainer.get();

				return new ResponseEntity<>(trainer, HttpStatus.OK);

			} else {

			}
			return new ResponseEntity<>("Trainer Not Found.", HttpStatus.OK);

		} catch (Exception e) {

			System.out.print(e.getMessage());
			return new ResponseEntity<>("Integer server  error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/trainer/{trainerId}")
	public ResponseEntity<Object> deleteTrainer(@PathVariable Integer trainerId) {
		
		try {
			Optional<Trainer> findTrainer = trainerRepository.findById(trainerId);
			
			if (findTrainer.isPresent()) {
				

				List<Object[]> listTrainer = courseRepository.findCourseBytrainerId(trainerId);

				for (Object[] row : listTrainer) {
					Integer courseId = (Integer) row[0];
					String courseName = (String) row[1];
					User user = (User) row[2];
					
	

					Course course = new Course(courseId, courseName, user,null);

					courseRepository.delete(course);

				}

				trainerRepository.delete(findTrainer.get());

				return new ResponseEntity<>("Delete Admin Success.", HttpStatus.OK);
			} else {

				return new ResponseEntity<>("Not found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/trainer/{trainerId}")
	public ResponseEntity<Object> updatCourse(@PathVariable Integer trainerId, @RequestBody Trainer body) {
		try {
			Optional<Trainer> findTrainer = trainerRepository.findById(trainerId);

			if (findTrainer.isPresent()) {

				Trainer trainerEdit = findTrainer.get();

				trainerEdit.setTrainerName(body.getTrainerName());
				trainerEdit.setTrainertel(body.getTrainertel());
				trainerRepository.save(trainerEdit);

				return new ResponseEntity<>(trainerEdit,HttpStatus.OK);

			} else {

				return new ResponseEntity<>("Trainer not found",HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	
		}

	}

	

}
