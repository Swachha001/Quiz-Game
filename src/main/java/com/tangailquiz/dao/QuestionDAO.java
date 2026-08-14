package com.tangailquiz.dao;

import com.tangailquiz.model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the questions table lives here.
 */
public class QuestionDAO {

    private static final String BASE_SELECT =
            "SELECT id, question_text, option_a, option_b, option_c, option_d, "
                    + "correct_option, category, difficulty, explanation, source_url, "
                    + "active, created_at, updated_at FROM questions";

    /**
     * Picks 10 different active questions.
     * ORDER BY RAND() shuffles the rows, LIMIT 10 keeps the first 10.
     * This cannot return the same question twice in one quiz.
     */
    public List<Question> findRandomActive(int limit) throws SQLException {
        String sql = BASE_SELECT + " WHERE active = TRUE ORDER BY RAND() LIMIT ?";
        List<Question> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(readQuestion(rs));
                }
            }
        }
        return list;
    }

    public Question findById(int id) throws SQLException {
        String sql = BASE_SELECT + " WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return readQuestion(rs);
                }
            }
        }
        return null;
    }

    public List<Question> search(String keyword, String category, String difficulty) throws SQLException {
        StringBuilder sql = new StringBuilder(BASE_SELECT);
        sql.append(" WHERE 1 = 1");
        List<String> values = new ArrayList<>();

        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND question_text LIKE ?");
            values.add("%" + keyword + "%");
        }
        if (category != null && !category.isBlank()) {
            sql.append(" AND category = ?");
            values.add(category);
        }
        if (difficulty != null && !difficulty.isBlank()) {
            sql.append(" AND difficulty = ?");
            values.add(difficulty);
        }
        sql.append(" ORDER BY id");

        List<Question> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < values.size(); i++) {
                ps.setString(i + 1, values.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(readQuestion(rs));
                }
            }
        }
        return list;
    }

    public int insert(Question q) throws SQLException {
        String sql = "INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, "
                + "correct_option, category, difficulty, explanation, source_url, active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            fillSave(ps, q);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return 0;
    }

    public void update(Question q) throws SQLException {
        String sql = "UPDATE questions SET question_text = ?, option_a = ?, option_b = ?, option_c = ?, "
                + "option_d = ?, correct_option = ?, category = ?, difficulty = ?, explanation = ?, "
                + "source_url = ?, active = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            fillSave(ps, q);
            ps.setInt(12, q.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM questions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void setActive(int id, boolean active) throws SQLException {
        String sql = "UPDATE questions SET active = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, active);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public int countAll() throws SQLException {
        return count("SELECT COUNT(*) FROM questions");
    }

    public int countActive() throws SQLException {
        return count("SELECT COUNT(*) FROM questions WHERE active = TRUE");
    }

    private int count(String sql) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private void fillSave(PreparedStatement ps, Question q) throws SQLException {
        ps.setString(1, q.getQuestionText());
        ps.setString(2, q.getOptionA());
        ps.setString(3, q.getOptionB());
        ps.setString(4, q.getOptionC());
        ps.setString(5, q.getOptionD());
        ps.setString(6, q.getCorrectOption());
        ps.setString(7, q.getCategory());
        ps.setString(8, q.getDifficulty());
        ps.setString(9, q.getExplanation());
        ps.setString(10, q.getSourceUrl());
        ps.setBoolean(11, q.isActive());
    }

    private Question readQuestion(ResultSet rs) throws SQLException {
        Question q = new Question();
        q.setId(rs.getInt("id"));
        q.setQuestionText(rs.getString("question_text"));
        q.setOptionA(rs.getString("option_a"));
        q.setOptionB(rs.getString("option_b"));
        q.setOptionC(rs.getString("option_c"));
        q.setOptionD(rs.getString("option_d"));
        q.setCorrectOption(rs.getString("correct_option"));
        q.setCategory(rs.getString("category"));
        q.setDifficulty(rs.getString("difficulty"));
        q.setExplanation(rs.getString("explanation"));
        q.setSourceUrl(rs.getString("source_url"));
        q.setActive(rs.getBoolean("active"));
        q.setCreatedAt(rs.getTimestamp("created_at"));
        q.setUpdatedAt(rs.getTimestamp("updated_at"));
        return q;
    }
}
