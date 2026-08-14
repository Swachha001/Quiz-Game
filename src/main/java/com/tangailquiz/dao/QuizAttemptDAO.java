package com.tangailquiz.dao;

import com.tangailquiz.model.QuizAttempt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the quiz_attempts table lives here.
 */
public class QuizAttemptDAO {

    public int insert(Connection conn, QuizAttempt attempt) throws SQLException {
        String sql = "INSERT INTO quiz_attempts "
                + "(player_id, total_questions, correct_answers, wrong_answers, score, percentage, started_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, attempt.getPlayerId());
            ps.setInt(2, attempt.getTotalQuestions());
            ps.setInt(3, attempt.getCorrectAnswers());
            ps.setInt(4, attempt.getWrongAnswers());
            ps.setInt(5, attempt.getScore());
            ps.setDouble(6, attempt.getPercentage());
            ps.setTimestamp(7, attempt.getStartedAt());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return 0;
    }

    public QuizAttempt findById(int id) throws SQLException {
        String sql = "SELECT a.id, a.player_id, p.name AS player_name, a.total_questions, "
                + "a.correct_answers, a.wrong_answers, a.score, a.percentage, "
                + "a.started_at, a.completed_at "
                + "FROM quiz_attempts a JOIN players p ON p.id = a.player_id "
                + "WHERE a.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return readAttempt(rs);
                }
            }
        }
        return null;
    }

    public List<QuizAttempt> findAll() throws SQLException {
        return queryList(
                "SELECT a.id, a.player_id, p.name AS player_name, a.total_questions, "
                        + "a.correct_answers, a.wrong_answers, a.score, a.percentage, "
                        + "a.started_at, a.completed_at "
                        + "FROM quiz_attempts a JOIN players p ON p.id = a.player_id "
                        + "ORDER BY a.completed_at DESC",
                null
        );
    }

    public List<QuizAttempt> findByPlayerId(int playerId) throws SQLException {
        return queryList(
                "SELECT a.id, a.player_id, p.name AS player_name, a.total_questions, "
                        + "a.correct_answers, a.wrong_answers, a.score, a.percentage, "
                        + "a.started_at, a.completed_at "
                        + "FROM quiz_attempts a JOIN players p ON p.id = a.player_id "
                        + "WHERE a.player_id = ? ORDER BY a.completed_at DESC",
                playerId
        );
    }

    public List<QuizAttempt> findRecent(int limit) throws SQLException {
        String sql = "SELECT a.id, a.player_id, p.name AS player_name, a.total_questions, "
                + "a.correct_answers, a.wrong_answers, a.score, a.percentage, "
                + "a.started_at, a.completed_at "
                + "FROM quiz_attempts a JOIN players p ON p.id = a.player_id "
                + "ORDER BY a.completed_at DESC LIMIT ?";
        List<QuizAttempt> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(readAttempt(rs));
                }
            }
        }
        return list;
    }

    public List<QuizAttempt> findTopPlayers(int limit) throws SQLException {
        String sql = "SELECT p.id AS player_id, p.name AS player_name, "
                + "MAX(a.score) AS score, MAX(a.percentage) AS percentage "
                + "FROM quiz_attempts a JOIN players p ON p.id = a.player_id "
                + "GROUP BY p.id, p.name "
                + "ORDER BY score DESC LIMIT ?";
        List<QuizAttempt> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    QuizAttempt attempt = new QuizAttempt();
                    attempt.setPlayerId(rs.getInt("player_id"));
                    attempt.setPlayerName(rs.getString("player_name"));
                    attempt.setScore(rs.getInt("score"));
                    attempt.setPercentage(rs.getDouble("percentage"));
                    list.add(attempt);
                }
            }
        }
        return list;
    }

    public List<QuizAttempt> findHighestScores(int limit) throws SQLException {
        String sql = "SELECT a.id, a.player_id, p.name AS player_name, a.total_questions, "
                + "a.correct_answers, a.wrong_answers, a.score, a.percentage, "
                + "a.started_at, a.completed_at "
                + "FROM quiz_attempts a JOIN players p ON p.id = a.player_id "
                + "ORDER BY a.score DESC, a.completed_at DESC LIMIT ?";
        List<QuizAttempt> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(readAttempt(rs));
                }
            }
        }
        return list;
    }

    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM quiz_attempts";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public double averagePercentage() throws SQLException {
        String sql = "SELECT AVG(percentage) FROM quiz_attempts";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }

    private List<QuizAttempt> queryList(String sql, Integer playerId) throws SQLException {
        List<QuizAttempt> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (playerId != null) {
                ps.setInt(1, playerId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(readAttempt(rs));
                }
            }
        }
        return list;
    }

    private QuizAttempt readAttempt(ResultSet rs) throws SQLException {
        QuizAttempt attempt = new QuizAttempt();
        attempt.setId(rs.getInt("id"));
        attempt.setPlayerId(rs.getInt("player_id"));
        attempt.setPlayerName(rs.getString("player_name"));
        attempt.setTotalQuestions(rs.getInt("total_questions"));
        attempt.setCorrectAnswers(rs.getInt("correct_answers"));
        attempt.setWrongAnswers(rs.getInt("wrong_answers"));
        attempt.setScore(rs.getInt("score"));
        attempt.setPercentage(rs.getDouble("percentage"));
        attempt.setStartedAt(rs.getTimestamp("started_at"));
        attempt.setCompletedAt(rs.getTimestamp("completed_at"));
        return attempt;
    }
}
