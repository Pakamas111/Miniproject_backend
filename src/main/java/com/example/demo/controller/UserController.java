package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@PostMapping("/user")
	public ResponseEntity<Object> addUser(@RequestBody User body) {
		try {

			User newUser = userRepository.save(body);
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);

		} catch (Exception e) {

			System.out.print(e.getMessage());
			return new ResponseEntity<>("Internal server  error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Object> getAllUserById(@PathVariable("userId") Integer userId) {
		try {

			Optional<User> foundUser = userRepository.findById(userId);
			if (foundUser.isPresent()) {

				User user = foundUser.get();

				return new ResponseEntity<>(user, HttpStatus.OK);

			} else {

			}
			return new ResponseEntity<>("User Not Found.", HttpStatus.OK);

		} catch (Exception e) {

			System.out.print(e.getMessage());
			return new ResponseEntity<>("Internal server  error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user")
	public ResponseEntity<Object> getAllUser() {
		try {

			List<User> listUser = userRepository.findAll();

			return new ResponseEntity<>(listUser, HttpStatus.OK);

		} catch (Exception e) {

			System.out.print(e.getMessage());
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/user/{userId}")
	public ResponseEntity<Object> updatUser(@PathVariable Integer userId, @RequestBody User body) {
		try {
			Optional<User> user = userRepository.findById(userId);

			if (user.isPresent()) {

				User userEdit = user.get();
				
				userEdit.setFirstName(body.getFirstName());
				userEdit.setLastName(body.getLastName());
				userEdit.setPassword(body.getPassword());
				userEdit.setUsertel(body.getUsertel());
				userRepository.save(userEdit);

				return new ResponseEntity<>("PUT SUCSESS", HttpStatus.OK);

			} else {

				return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {

			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody User body) {
		try {
			Optional<User> foundUser = userRepository.findByUserName(body.getUserName());
			if(foundUser.isPresent()) {
				return new ResponseEntity<>("username exits", HttpStatus.OK);
			}
			User newUser = userRepository.save(body);
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);

		} catch (Exception e) {

			System.out.print(e.getMessage());
			return new ResponseEntity<>("Internal server  error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody User loginRequest) {
		try {

			Optional<User> userFound = userRepository.findByUserName(loginRequest.getUserName());

			if (userFound.isPresent() && userFound.get().getPassword().equals(loginRequest.getPassword())) {

				userFound.get().setPassword(null);
				return new ResponseEntity<>(userFound, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Invalid credentials.", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
