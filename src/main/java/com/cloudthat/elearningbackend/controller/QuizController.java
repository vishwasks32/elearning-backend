package com.cloudthat.elearningbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudthat.elearningbackend.model.ApiResponse;
import com.cloudthat.elearningbackend.model.OptionModel;
import com.cloudthat.elearningbackend.model.QuestionModel;
import com.cloudthat.elearningbackend.model.QuizAttemptModel;
import com.cloudthat.elearningbackend.model.QuizModel;
import com.cloudthat.elearningbackend.model.QuizScoreModel;
import com.cloudthat.elearningbackend.service.QuizService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class QuizController {
	
	@Autowired
	QuizService quizService;
	
	@PostMapping("/quizzes")
	public ResponseEntity<ApiResponse> addQuiz(@RequestBody QuizModel quizModel){
		log.info("Add a Quiz");
		QuizModel quiz = quizService.addQuiz(quizModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Quiz added Successfully", quiz), HttpStatus.OK);
	}
	
	@GetMapping("/quizzes/{quizId}")
	public ResponseEntity<ApiResponse> getQuiz(@PathVariable("quizId") Long quizId){
		log.info("Retrieve a Quiz");
		QuizModel quiz = quizService.getQuiz(quizId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Quiz added Successfully", quiz), HttpStatus.OK);
	}
	
	@PutMapping("/quizzes/{quizId}")
	public ResponseEntity<ApiResponse> updateQuiz(@PathVariable("quizId") Long quizId, @RequestBody QuizModel quizModel){
		log.info("update a Quiz");
		QuizModel quiz = quizService.updateQuiz(quizId, quizModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Quiz updated Successfully", quiz), HttpStatus.OK);
	}
	
	@DeleteMapping("/quizzes/{quizId}")
	public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable("quizId") Long quizId){
		log.info("deleting a lesson");
		quizService.deleteQuiz(quizId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Quiz deleted Successfully", true), HttpStatus.OK);
	}
	
	@PostMapping("/questions")
	public ResponseEntity<ApiResponse> addQuestion(@RequestBody QuestionModel questionModel){
		log.info("Add a Question");
		QuestionModel question = quizService.addQuestion(questionModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Question added Successfully", question), HttpStatus.OK);
	}
	
	@GetMapping("/questions/{questionId}")
	public ResponseEntity<ApiResponse> getQuestion(@PathVariable("questionId") Long questionId){
		log.info("Retrieve a Question");
		QuestionModel question = quizService.getQuestion(questionId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Question retrieved Successfully", question), HttpStatus.OK);
	}
	
	@PutMapping("/questions/{questionId}")
	public ResponseEntity<ApiResponse> updateQuestion(@PathVariable("questionId") Long questionId, @RequestBody QuestionModel questionModel){
		log.info("Update a Question");
		QuestionModel question = quizService.updateQuestion(questionId, questionModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Question updated Successfully", question), HttpStatus.OK);
	}
	
	@DeleteMapping("/questions/{questionId}")
	public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable("questionId") Long questionId){
		log.info("Delete a Question");
		quizService.deleteQuestion(questionId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Question deleted Successfully", true), HttpStatus.OK);
	}
	
	@PostMapping("/options")
	public ResponseEntity<ApiResponse> addOption(@RequestBody OptionModel optionModel){
		log.info("Add a Option");
		OptionModel option = quizService.addOption(optionModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Option added Successfully", option), HttpStatus.OK);
	}
	
	@GetMapping("/options/{optionId}")
	public ResponseEntity<ApiResponse> getOption(@PathVariable("optionId") Long optionId){
		log.info("Retrieve a option");
		OptionModel option = quizService.getOption(optionId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Option retrieved Successfully", option), HttpStatus.OK);
	}
	
	@PutMapping("/options/{optionId}")
	public ResponseEntity<ApiResponse> updateOption(@PathVariable("optionId") Long optionId, @RequestBody OptionModel optionModel){
		log.info("Update a Question");
		OptionModel option = quizService.updateOption(optionId, optionModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Question updated Successfully", option), HttpStatus.OK);
	}
	
	@DeleteMapping("/options/{optionId}")
	public ResponseEntity<ApiResponse> deleteOption(@PathVariable("optionId") Long optionId){
		log.info("Delete a Option");
		quizService.deleteOption(optionId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Option deleted Successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/quizzes/{quizId}/questions")
	public ResponseEntity<ApiResponse> getAllQuestions(@PathVariable("quizId") Long quizId){
		log.info("Retrieve a option");
		List<QuestionModel> questions = quizService.getAllQuestions(quizId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Option retrieved Successfully", questions), HttpStatus.OK);
	}
	
	@GetMapping("/quizzes/{quizId}/questions/{questionId}/options")
	public ResponseEntity<ApiResponse> getAllOptions(@PathVariable("quizId") Long quizId,@PathVariable("questionId") Long questionId ){
		log.info("Retrieve all options");
		List<OptionModel> options = quizService.getAllOptions(quizId,questionId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Option retrieved Successfully", options), HttpStatus.OK);
	}
	
	@PostMapping("/quizzes/submit")
	public ResponseEntity<ApiResponse> attemptQuiz(@RequestBody QuizAttemptModel quizAttemptModel){
		log.info("Attempt Quiz");
		QuizAttemptModel quizAttempt = quizService.attemptQuiz(quizAttemptModel);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Quiz Attempt Successful", quizAttempt), HttpStatus.OK);
	}
	
	@GetMapping("/quizzes/{quizId}/scores")
	public ResponseEntity<ApiResponse> getQuizScores(@PathVariable("quizId") Long quizId){
		log.info("Quiz Score");
		List<QuizScoreModel> quizScores = quizService.getQuizScores(quizId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Quiz scores retreived successfully", quizScores), HttpStatus.OK);
	}
	
	@GetMapping("/quizzes/{quizId}/attempts")
	public ResponseEntity<ApiResponse> getAllAttempts(@PathVariable("quizId") Long quizId){
		log.info("Quiz Attempts");
		List<QuizAttemptModel> quizAttempts = quizService.getAllAttempts(quizId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Quiz attempts retreived successfully", quizAttempts), HttpStatus.OK);
	}
	
}
