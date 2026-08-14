package com.tangailquiz.dao;

import com.tangailquiz.model.QuizAnswer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the quiz_answers table lives here.
 */
public class QuizAnswerDAO {

    public void insert(Connection conn, QuizAnswer answer) throws SQLException {
        String sql = "INSERT INTO quiz_answers "
                + "(attempt_id, question_id, selected_option, correct_option, is_correct) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, answer.getAttemptId());
            ps.setInt(2, answer.getQuestionId());
            ps.setString(3, answer.getSelectedOption());
            ps.setString(4, answer.getCorrectOption());
            ps.setBoolean(5, answer.isCorrect());
            ps.executeUpdate();
        }
    }

    public List<QuizAnswer> findByAttemptId(int attemptId) throws SQLException {
        String sql = "SELECT qa.id, qa.attempt_id, qa.question_id, qa.selected_option, "
                + "qa.correct_option, qa.is_correct, "
                + "q.question_text, q.option_a, q.option_b, q.option_c, q.option_d, q.explanation "
                + "FROM quiz_answers qa JOIN questions q ON q.id = qa.question_id "
                + "WHERE qa.attempt_id = ? ORDER BY qa.id";
        List<QuizAnswer> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, attemptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    QuizAnswer answer = new QuizAnswer();
                    answer.setId(rs.getInt("id"));
                    answer.setAttemptId(rs.getInt("attempt_id"));
                    answer.setQuestionId(rs.getInt("question_id"));
                    answer.setSelectedOption(rs.getString("selected_option"));
                    answer.setCorrectOption(rs.getString("correct_option"));
                    answer.setCorrect(rs.getBoolean("is_correct"));
                    answer.setQuestionText(rs.getString("question_text"));
                    answer.setOptionA(rs.getString("option_a"));
                    answer.setOptionB(rs.getString("option_b"));
                    answer.setOptionC(rs.getString("option_c"));
                    answer.setOptionD(rs.getString("option_d"));
                    answer.setExplanation(rs.getString("explanation"));
                    list.add(answer);
                }
            }
        }
        return list;
    }
}
