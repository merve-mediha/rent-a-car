package com.kodlamaio.rentACar.core.utilities.adapters.concretes;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.FindexScoreCheckService;
@Service
public class FindexServiceAdapter implements FindexScoreCheckService {
	
	
	
	public int calculatePersonScore(String identityNumber) {
		int max = 1900;
		int min = 650;
		int score = (int) Math.floor(Math.random() * (max - min + 1) + min);
		System.out.println("Findeks Score :" + score);
		
		return score;
	}
	
	
	@Override
	public int CheckIfCorrectPerso(String identityNumber) {
		int score = calculatePersonScore(identityNumber);
		return score;
	}

	
	
}
