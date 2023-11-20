package com.cloudthat.elearningbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cloudthat.elearningbackend.entity.Options;
import com.cloudthat.elearningbackend.entity.Question;
import com.cloudthat.elearningbackend.entity.Quiz;
import com.cloudthat.elearningbackend.exceptions.ResourceNotFoundException;
import com.cloudthat.elearningbackend.model.OptionModel;
import com.cloudthat.elearningbackend.model.QuestionModel;
import com.cloudthat.elearningbackend.model.QuizModel;
import com.cloudthat.elearningbackend.repository.OptionRepository;
import com.cloudthat.elearningbackend.repository.QuestionRepository;
import com.cloudthat.elearningbackend.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {
	
	@Autowired
	QuizRepository quizRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	OptionRepository optionRepository;
	
	protected Quiz quizModelToQuiz(QuizModel quizModel) {
		Quiz quiz = new Quiz();
		quiz.setName(quizModel.getName());
		quiz.setDescription(quizModel.getDescription());
		quiz.setDuration(quizModel.getDuration());
		//quiz.setQuestions(quizModel.getQuestions());
		
		return quiz;
		
	}
	
	protected QuizModel quizToQuizModel(Quiz quiz) {
		QuizModel quizModel = new QuizModel();
		quizModel.setId(quiz.getId());
		quizModel.setName(quiz.getName());
		quizModel.setDescription(quiz.getDescription());
		quizModel.setDuration(quiz.getDuration());
		
//		List<Question> questions = questionRepository.findAllByQuiz(quiz);
//		List<QuestionModel> questionList = new ArrayList<QuestionModel>();
//		for(Question q: questions) {
//			questionList.add(questionToQuestionModel(q));
//		}
//		
//		quizModel.setQuestions(questionList);
		
		return quizModel;
	}
	
	protected Question questionModelToQuestion(QuestionModel questionModel) {
		Question question = new Question();
		question.setQuestionText(questionModel.getText());
		question.setQuestionImage(questionModel.getImage());
//		question.setOptions(questionModel.getOptions());
		
		Quiz quizDB;
		
		try {
			quizDB = quizRepository.findById(questionModel.getQuizId()).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Quiz", "Id", questionModel.getQuizId());
		}
		question.setQuiz(quizDB);
		
		return question;
	}
	
	protected QuestionModel  questionToQuestionModel(Question question) {
		QuestionModel questionModel = new QuestionModel();
		questionModel.setId(question.getId());
		questionModel.setText(question.getQuestionText());
		questionModel.setImage(question.getQuestionImage());
		
		
		questionModel.setOptions(this.getAllOptions(question.getQuiz().getId(), question.getId()));
		
		
		questionModel.setQuizId(question.getQuiz().getId());
		
		return questionModel;
	}
	
	
	protected Options optionModelToOption(OptionModel optionModel) {
		Options options = new Options();
		options.setOptionText(optionModel.getText());
		options.setOptionImage(optionModel.getImage());
		options.setIsCorrect(optionModel.getIsCorrect());
		
		Question question;
		try {
			question = questionRepository.findById(optionModel.getQuestionId()).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Question", "Id", optionModel.getQuestionId());
		}
		options.setQuestion(question);
		
		return options;
	}
	
	protected OptionModel optionToOptionModel(Options options) {
		OptionModel optionModel = new OptionModel();
		optionModel.setId(options.getId());
		optionModel.setText(options.getOptionText());
		optionModel.setImage(options.getOptionImage());
		optionModel.setIsCorrect(options.getIsCorrect());
		optionModel.setQuestionId(options.getQuestion().getId());
		
		return optionModel;
	}
	
	@Override
	public QuizModel addQuiz(QuizModel quizModel) {
		Quiz quiz;
		try {
			// TODO Auto-generated method stub
			quiz = quizRepository.save(quizModelToQuiz(quizModel));
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityViolationException("Error Saving quiz");
		}
		return quizToQuizModel(quiz);
	}

	@Override
	public QuizModel getQuiz(Long quizId) {
		// TODO Auto-generated method stub
		Quiz quiz;
		try {
			quiz = quizRepository.findById(quizId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Quiz", "Id", quizId);
		}
		return quizToQuizModel(quiz);
	}

	@Override
	public QuizModel updateQuiz(Long quizId, QuizModel quizModel) {
		// TODO Auto-generated method stub
		Quiz quizDB;
		try {
			quizDB = quizRepository.findById(quizId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Quiz", "Id", quizId);
		}
		if (Objects.nonNull(quizModel.getName()) && !"".equalsIgnoreCase(quizModel.getName())) {
			quizDB.setName(quizModel.getName());
		}
		
		if (Objects.nonNull(quizModel.getDescription()) && !"".equalsIgnoreCase(quizModel.getDescription())) {
			quizDB.setDescription(quizModel.getDescription());
		}
		
		if (Objects.nonNull(quizModel.getDuration())) {
			quizDB.setDuration(quizModel.getDuration());;
		}
//		
//		if (Objects.nonNull(quizModel.Questions())) {
//			quizDB.setQuizQuestions(quizModel.getQuizQuestions());
//		}
		
		quizRepository.save(quizDB);
		
		return quizToQuizModel(quizDB);
	}

	@Override
	public void deleteQuiz(Long quizId) {
		// TODO Auto-generated method stub
		Quiz quizDB;
		
		try {
			quizDB = quizRepository.findById(quizId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Quiz", "Id", quizId);
		}
		quizDB.setIsActive(false);
		quizRepository.save(quizDB);
		
	}


	@Override
	public QuestionModel addQuestion(QuestionModel questionModel) {
		// TODO Auto-generated method stub
		Question questionDB;
		questionDB = questionRepository.save(questionModelToQuestion(questionModel));
		return questionToQuestionModel(questionDB);
	}

	@Override
	public QuestionModel getQuestion(Long questionId) {
		// TODO Auto-generated method stub
		Question question;
		
		try {
			question = questionRepository.findById(questionId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Question", "Id", questionId);
		}
		return questionToQuestionModel(question);
	}

	@Override
	public QuestionModel updateQuestion(Long questionId, QuestionModel questionModel) {
		// TODO Auto-generated method stub
		Question questionDB; 
		try {
			questionDB = questionRepository.findById(questionId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Question", "Id", questionId);
		}
		if (Objects.nonNull(questionModel.getText()) && !"".equalsIgnoreCase(questionModel.getText())) {
			questionDB.setQuestionText(questionModel.getText());
		}
		
		if (Objects.nonNull(questionModel.getImage()) && !"".equalsIgnoreCase(questionModel.getImage())) {
			questionDB.setQuestionImage(questionModel.getImage());;
		}
		
		if(Objects.nonNull(questionModel.getQuizId())){
			Quiz quiz;
			
			try {
				quiz = quizRepository.findById(questionModel.getQuizId()).get();
			} catch (NoSuchElementException e) {
				// TODO: handle exception
				throw new ResourceNotFoundException("Quiz", "Id", questionModel.getQuizId());
			}
			questionDB.setQuiz(quiz);
		}
		
//		if (Objects.nonNull(questionModel.getOptions())) {
//			questionDB.setOptions(questionModel.getOptions());;
//		}
		
		questionRepository.save(questionDB);
		return questionToQuestionModel(questionDB);
	}

	@Override
	public void deleteQuestion(Long questionId) {
		// TODO Auto-generated method stub
		Question questionDB; 
		try {
			questionDB = questionRepository.findById(questionId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Question", "Id", questionId);
		}
		
		questionRepository.delete(questionDB);
	}

	@Override
	public OptionModel addOption(OptionModel optionModel) {
		// TODO Auto-generated method stub
		Options option;
		try {
			option = optionRepository.save(optionModelToOption(optionModel));
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityViolationException("Error Saving option");
		}
		return optionToOptionModel(option);
	}

	@Override
	public OptionModel getOption(Long optionId) {
		// TODO Auto-generated method stub
		Options option;
		
		try {
			option = optionRepository.findById(optionId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Option", "Id", optionId);
		}
		return optionToOptionModel(option);
	}

	@Override
	public OptionModel updateOption(Long optionId, OptionModel optionModel) {
		// TODO Auto-generated method stub
		Options optionDB;
		
		try {
			optionDB = optionRepository.findById(optionId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Option", "Id", optionId);
		}
		
		if (Objects.nonNull(optionModel.getText()) && !"".equalsIgnoreCase(optionModel.getText())) {
			optionDB.setOptionText(optionModel.getText());
		}
		
		if (Objects.nonNull(optionModel.getImage()) && !"".equalsIgnoreCase(optionModel.getImage())) {
			optionDB.setOptionImage(optionModel.getImage());
		}
		
		if (Objects.nonNull(optionModel.getIsCorrect())) {
			optionDB.setIsCorrect(optionModel.getIsCorrect());
		}
		
		optionRepository.save(optionDB);
		
		return optionToOptionModel(optionDB);
	}

	@Override
	public void deleteOption(Long optionId) {
		// TODO Auto-generated method stub
		Options optionDB;
		
		try {
			optionDB = optionRepository.findById(optionId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Option", "Id", optionId);
		}
		optionRepository.delete(optionDB);
	}

	@Override
	public List<QuestionModel> getAllQuestions(Long quizId) {
		// TODO Auto-generated method stub
		
		// Get the quiz
		Quiz quiz;
		try {
			quiz = quizRepository.findById(quizId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Quiz", "Id", quizId);
		}
		
		// Get All Questions
		List<Question> questions = questionRepository.findAllByQuiz(quiz);
		List<QuestionModel> questionList = new ArrayList<QuestionModel>();
		for(Question q: questions) {
			questionList.add(questionToQuestionModel(q));
		}
		
		return questionList;
	}

	@Override
	public List<OptionModel> getAllOptions(Long quizId, Long questionId) {
		// TODO Auto-generated method stub
		// Get the quiz
		Quiz quiz;
		try {
			quiz = quizRepository.findById(quizId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Quiz", "Id", quizId);
		}
		
		Question question;
		
		try {
			question = questionRepository.findById(questionId).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Question", "Id", questionId);
		}
		
		// Get All Options
		List<Options> options = optionRepository.findAllByQuestion(question);
		List<OptionModel> optionsList = new ArrayList<OptionModel>();
		for(Options q: options) {
			optionsList.add(optionToOptionModel(q));
		}
		
		return optionsList;
	}
	
	
}
