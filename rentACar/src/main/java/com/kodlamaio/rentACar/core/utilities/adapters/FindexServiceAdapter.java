package com.kodlamaio.rentACar.core.utilities.adapters;

import java.util.HashMap;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.FindexScoreCheckService;
@Service
public class FindexServiceAdapter implements FindexScoreCheckService {
	Random random = new Random();
	HashMap<String, Integer> findexScore;
	
	@Override
	public int checkFindexScore(String identityNumber) {
		findexScore = new HashMap<String, Integer>();
		int score = random.nextInt(1900)+1;
		System.out.println(score);
		findexScore.put(identityNumber, score);
		
		return score;
	}
	
}
