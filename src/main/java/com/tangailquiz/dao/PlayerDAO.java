package com.tangailquiz.dao;

import com.tangailquiz.model.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * All SQL for the players table lives here.
 */
public class PlayerDAO {

    public int insert(Player player) throws SQLException {
        String sql = "INSERT INTO players (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, player.getName());
            ps.setString(2, emptyToNull(player.getEmail()));
            ps.setString(3, emptyToNull(player.getPhone()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return 0;
    }

    public Player findById(int id) throws SQLException {
        String sql = "SELECT id, name, email, phone, created_at FROM players WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return readPlayer(rs);
                }
            }
        }
        return null;
    }

    public List<Player> search(String keyword) throws SQLException {
        String sql = "SELECT id, name, email, phone, created_at FROM players "
                + "WHERE (? = '') OR name LIKE ? OR email LIKE ? "
                + "ORDER BY id DESC";
        String like = "%" + keyword + "%";
        List<Player> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, keyword);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(readPlayer(rs));
                }
            }
        }
        return list;
    }

    public void update(Player player) throws SQLException {
        String sql = "UPDATE players SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, player.getName());
            ps.setString(2, emptyToNull(player.getEmail()));
            ps.setString(3, emptyToNull(player.getPhone()));
            ps.setInt(4, player.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM players WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM players";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private Player readPlayer(ResultSet rs) throws SQLException {
        Player player = new Player();
        player.setId(rs.getInt("id"));
        player.setName(rs.getString("name"));
        player.setEmail(rs.getString("email"));
        player.setPhone(rs.getString("phone"));
        player.setCreatedAt(rs.getTimestamp("created_at"));
        return player;
    }

    private String emptyToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value;
    }
}
