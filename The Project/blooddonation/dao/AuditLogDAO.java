package com.blooddonation.dao;

import com.blooddonation.model.AuditLog;
import com.blooddonation.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDAO {
    
    public void addAuditLog(AuditLog auditLog) throws SQLException {
        String query = "INSERT INTO audit_logs (user_id, action, description, timestamp, ip_address, user_agent) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, auditLog.getUserId());
            stmt.setString(2, auditLog.getAction());
            stmt.setString(3, auditLog.getDescription());
            stmt.setTimestamp(4, new java.sql.Timestamp(auditLog.getTimestamp().getTime()));
            stmt.setString(5, auditLog.getIpAddress());
            stmt.setString(6, auditLog.getUserAgent());
            stmt.executeUpdate();
        }
    }
    
    public List<AuditLog> getAllAuditLogs() throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AuditLog auditLog = mapResultSetToAuditLog(rs);
                auditLogs.add(auditLog);
            }
        }
        return auditLogs;
    }
    
    public List<AuditLog> getAuditLogsByUserId(int userId) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs WHERE user_id = ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    public List<AuditLog> getAuditLogsByAction(String action) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs WHERE action = ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, action);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    public List<AuditLog> getAuditLogsByDateRange(java.util.Date startDate, java.util.Date endDate) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs WHERE timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    public List<AuditLog> getAuditLogsByIpAddress(String ipAddress) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs WHERE ip_address = ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ipAddress);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    public List<AuditLog> getRecentAuditLogs(int limit) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    public List<AuditLog> searchAuditLogs(String searchTerm) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs WHERE action LIKE ? OR description LIKE ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    public int getAuditLogCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM audit_logs";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    public int getAuditLogCountByUserId(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM audit_logs WHERE user_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    public int getAuditLogCountByAction(String action) throws SQLException {
        String query = "SELECT COUNT(*) FROM audit_logs WHERE action = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, action);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    public void deleteOldAuditLogs(int daysOld) throws SQLException {
        String query = "DELETE FROM audit_logs WHERE timestamp < DATE_SUB(NOW(), INTERVAL ? DAY)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, daysOld);
            stmt.executeUpdate();
        }
    }
    
    public void deleteAuditLogsByUserId(int userId) throws SQLException {
        String query = "DELETE FROM audit_logs WHERE user_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
    
    public List<AuditLog> getAuditLogsByUserAndAction(int userId, String action) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs WHERE user_id = ? AND action = ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, action);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    public List<AuditLog> getAuditLogsByUserAndDateRange(int userId, java.util.Date startDate, java.util.Date endDate) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs WHERE user_id = ? AND timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    public List<AuditLog> getAuditLogsByActionAndDateRange(String action, java.util.Date startDate, java.util.Date endDate) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs WHERE action = ? AND timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, action);
            stmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    public List<AuditLog> getAuditLogsByIpAddressAndDateRange(String ipAddress, java.util.Date startDate, java.util.Date endDate) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs WHERE ip_address = ? AND timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, ipAddress);
            stmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    public List<AuditLog> getAuditLogsByUserAndIpAddress(int userId, String ipAddress) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs WHERE user_id = ? AND ip_address = ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, ipAddress);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    public List<AuditLog> getAuditLogsByUserAndActionAndDateRange(int userId, String action, java.util.Date startDate, java.util.Date endDate) throws SQLException {
        List<AuditLog> auditLogs = new ArrayList<>();
        String query = "SELECT * FROM audit_logs WHERE user_id = ? AND action = ? AND timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, action);
            stmt.setTimestamp(3, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(4, new java.sql.Timestamp(endDate.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AuditLog auditLog = mapResultSetToAuditLog(rs);
                    auditLogs.add(auditLog);
                }
            }
        }
        return auditLogs;
    }
    
    private AuditLog mapResultSetToAuditLog(ResultSet rs) throws SQLException {
        AuditLog auditLog = new AuditLog();
        auditLog.setId(rs.getInt("id"));
        auditLog.setUserId(rs.getInt("user_id"));
        auditLog.setAction(rs.getString("action"));
        auditLog.setDescription(rs.getString("description"));
        auditLog.setTimestamp(rs.getTimestamp("timestamp"));
        auditLog.setIpAddress(rs.getString("ip_address"));
        auditLog.setUserAgent(rs.getString("user_agent"));
        return auditLog;
    }
}