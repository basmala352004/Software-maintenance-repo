package com.example.LMS;

import com.example.LMS.models.*;
import com.example.LMS.repositories.QuizRepository;
import com.example.LMS.repositories.QuestionRepository;
import com.example.LMS.services.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuizService quizService;

    private QuizModel quiz;
    private QuestionModel question;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up a mock Quiz and Question
        quiz = new QuizModel();
        quiz.setId(1L);
        quiz.setQuizTitle("Test Quiz");

        question = new QuestionModel();
        question.setId(1L);
        question.setQuestionText("What is 2+2?");
        question.setType(QuestionModel.QuestionType.MCQ); // Set the type as MCQ
        question.setCorrectAnswer("4");
    }

    @Test
    public void testCreateQuiz() {
        quizService.createQuiz(quiz);

        // Verify that the quiz repository's save method is called once
        verify(quizRepository, times(1)).save(quiz);
    }

    @Test
    public void testAddQuestionToQuiz() {
        // Arrange: Mock the behavior of quizRepository to return the quiz when searching by id
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        // Set question ID to null to trigger the save() call in addQuestionToQuiz
        question.setId(null);  // This will allow questionRepository.save() to be invoked

        // Act: Call the method to add the question to the quiz
        quizService.addQuestionToQuiz(1L, question);

        // Assert: Verify that the questionRepository.save() method is called once
        verify(questionRepository, times(1)).save(any(QuestionModel.class));

        // Assert: Verify that the quizRepository.save() method is called once
        verify(quizRepository, times(1)).save(any(QuizModel.class));

        // Assert: Verify that the question is indeed added to the quiz's questions list
        assertTrue(quiz.getQuestions().contains(question), "The question was not added to the quiz.");
    }


    @Test
    public void testGetRandomQuestions() {
        // Adding some questions to the quiz
        QuestionModel question1 = new QuestionModel();
        question1.setId(2L);
        question1.setQuestionText("What is 3+3?");
        question1.setType(QuestionModel.QuestionType.MCQ); // Set the type
        question1.setCorrectAnswer("6");

        QuestionModel question2 = new QuestionModel();
        question2.setId(3L);
        question2.setQuestionText("What is 4+4?");
        question2.setType(QuestionModel.QuestionType.MCQ); // Set the type
        question2.setCorrectAnswer("8");

        quiz.setQuestions(Arrays.asList(question, question1, question2));

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        // Test random question selection
        var questions = quizService.getRandomQuestions(1L, 2);

        // Verify the correct number of questions are selected
        assertEquals(2, questions.size());
        assertTrue(questions.contains(question));
        assertTrue(questions.contains(question1) || questions.contains(question2)); // One of the other two
    }

    @Test
    public void testGetQuizById() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        Optional<QuizModel> foundQuiz = quizService.getQuizById(1L);

        assertTrue(foundQuiz.isPresent());
        assertEquals(quiz.getId(), foundQuiz.get().getId());
    }

    @Test
    public void testGetQuizByIdNotFound() {
        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<QuizModel> foundQuiz = quizService.getQuizById(1L);

        assertFalse(foundQuiz.isPresent());
    }
}
