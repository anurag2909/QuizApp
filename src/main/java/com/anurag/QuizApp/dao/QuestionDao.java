package com.anurag.QuizApp.dao;

import com.anurag.QuizApp.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> { // All the things like fetching data from the database wiil be done by JPA

    List<Question> findByCategory(String category);
}
