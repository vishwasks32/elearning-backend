package com.cloudthat.elearningbackend.service;

import java.util.List;

import com.cloudthat.elearningbackend.model.OptionModel;
import com.cloudthat.elearningbackend.model.QuestionModel;
import com.cloudthat.elearningbackend.model.QuizAttemptModel;
import com.cloudthat.elearningbackend.model.QuizModel;
import com.cloudthat.elearningbackend.model.QuizScoreModel;

public interface QuizService {

	QuizModel addQuiz(QuizModel quizModel);

	QuizModel getQuiz(Long quizId);

	QuizModel updateQuiz(Long quizId, QuizModel quizModel);

	void deleteQuiz(Long quizId);

	QuestionModel addQuestion(QuestionModel questionModel);

	QuestionModel getQuestion(Long questionId);

	QuestionModel updateQuestion(Long questionId, QuestionModel questionModel);

	void deleteQuestion(Long questionId);

	OptionModel addOption(OptionModel optionModel);

	OptionModel getOption(Long optionId);

	OptionModel updateOption(Long optionId, OptionModel optionModel);

	void deleteOption(Long optionId);

	List<QuestionModel> getAllQuestions(Long quizId);

	List<OptionModel> getAllOptions(Long quizId, Long questionId);

	QuizAttemptModel attemptQuiz(QuizAttemptModel quizAttemptModel);

	List<QuizScoreModel> getQuizScores(Long quizId);

	List<QuizAttemptModel> getAllAttempts(Long quizId);


}
