package com.blooddonation.model;

import java.util.Date;

public class AuditLog {
    private int id;
    private int userId;
    private String action;
    private String description;
    private Date timestamp;
    private String ipAddress;
    private String userAgent;

    // Constructors
    public AuditLog() {}
    
    public AuditLog(int userId, String action, String description, String ipAddress, String userAgent) {
        this.userId = userId;
        this.action = action;
        this.description = description;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.timestamp = new Date();
    }
    
    public AuditLog(int userId, String action, String description, int adminId, String ipAddress, String userAgent) {
        this.userId = userId;
        this.action = action;
        this.description = description;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.timestamp = new Date();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    // Business logic methods
    public boolean isLoginAction() {
        return "LOGIN".equals(action);
    }
    
    public boolean isLogoutAction() {
        return "LOGOUT".equals(action);
    }
    
    public boolean isCreateAction() {
        return action != null && action.startsWith("CREATE_");
    }
    
    public boolean isUpdateAction() {
        return action != null && action.startsWith("UPDATE_");
    }
    
    public boolean isDeleteAction() {
        return action != null && action.startsWith("DELETE_");
    }
    
    public boolean isReadAction() {
        return action != null && action.startsWith("READ_");
    }
    
    public boolean isSecurityAction() {
        return action != null && (action.contains("PASSWORD") || action.contains("LOGIN") || action.contains("LOGOUT"));
    }
    
    public boolean isDataAction() {
        return action != null && (action.contains("USER") || action.contains("DONOR") || action.contains("BLOOD") || action.contains("APPOINTMENT"));
    }
    
    public boolean isSystemAction() {
        return action != null && (action.contains("SYSTEM") || action.contains("CONFIG") || action.contains("SETTINGS"));
    }
    
    public String getActionCategory() {
        if (isSecurityAction()) {
            return "SECURITY";
        } else if (isDataAction()) {
            return "DATA";
        } else if (isSystemAction()) {
            return "SYSTEM";
        } else {
            return "OTHER";
        }
    }
    
    public boolean isRecent() {
        if (timestamp == null) {
            return false;
        }
        long diffInMilliseconds = new Date().getTime() - timestamp.getTime();
        long diffInHours = diffInMilliseconds / (1000 * 60 * 60);
        return diffInHours <= 24; // Within last 24 hours
    }
    
    public boolean isToday() {
        if (timestamp == null) {
            return false;
        }
        java.util.Calendar cal1 = java.util.Calendar.getInstance();
        java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal1.setTime(timestamp);
        cal2.setTime(new Date());
        return cal1.get(java.util.Calendar.DAY_OF_MONTH) == cal2.get(java.util.Calendar.DAY_OF_MONTH) && 
               cal1.get(java.util.Calendar.MONTH) == cal2.get(java.util.Calendar.MONTH) && 
               cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR);
    }
    
    public boolean isThisWeek() {
        if (timestamp == null) {
            return false;
        }
        long diffInMilliseconds = new Date().getTime() - timestamp.getTime();
        long diffInDays = diffInMilliseconds / (1000 * 60 * 60 * 24);
        return diffInDays <= 7;
    }
    
    public boolean isThisMonth() {
        if (timestamp == null) {
            return false;
        }
        java.util.Calendar cal1 = java.util.Calendar.getInstance();
        java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal1.setTime(timestamp);
        cal2.setTime(new Date());
        return cal1.get(java.util.Calendar.MONTH) == cal2.get(java.util.Calendar.MONTH) && 
               cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR);
    }
    
    public String getFormattedTimestamp() {
        if (timestamp == null) {
            return "Unknown";
        }
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }
    
    public String getShortDescription() {
        if (description == null || description.length() <= 50) {
            return description;
        }
        return description.substring(0, 47) + "...";
    }
}