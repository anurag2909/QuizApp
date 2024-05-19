package com.anurag.QuizApp.service;

import com.anurag.QuizApp.dao.QuestionDao;
import com.anurag.QuizApp.dao.QuizDao;
import com.anurag.QuizApp.model.Question;
import com.anurag.QuizApp.model.QuestionWrapper;
import com.anurag.QuizApp.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id); // Optional data, the data might come or might not

        // Quiz object actually has the questions. But then we have to convert those questions into question Wrapper

        List<Question> questionsFromDb = quiz.get().getQuestions();

        // Now we have to manually convert each questions into question wrapper using for loop

        List<QuestionWrapper> questionsForUser = new ArrayList<>();

        for (Question q : questionsFromDb){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }
}
