package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trainer")
public class Trainer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer trainerId;
	private String trainerName;
	private String trainertel;
	
	
	
	public Trainer( ) {
		super();
	}
	
	public Trainer(Integer trainerId, String trainerName, String trainertel) {
		super();
		this.trainerId = trainerId;
		this.trainerName = trainerName;
		this.trainertel = trainertel;
	}
	public Integer getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(Integer trainerId) {
		this.trainerId = trainerId;
	}
	public String getTrainerName() {
		return trainerName;
	}
	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}
	public String getTrainertel() {
		return trainertel;
	}
	public void setTrainertel(String trainertel) {
		this.trainertel = trainertel;
	}



}
