package com.blooddonation.servlet;

import com.blooddonation.dao.*;
import com.blooddonation.model.*;
import com.blooddonation.service.NotificationService;
import com.blooddonation.util.DatabaseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicalStaffServlet extends HttpServlet {
    private BloodReportDAO bloodReportDAO;
    private DonorDAO donorDAO;
    private UserDAO userDAO;
    private NotificationService notificationService;

    public MedicalStaffServlet() {
        this.bloodReportDAO = new BloodReportDAO();
        this.donorDAO = new DonorDAO();
        this.userDAO = new UserDAO();
        this.notificationService = new NotificationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"MEDICAL_STAFF".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/dashboard";
        }

        try {
            switch (pathInfo) {
                case "/dashboard":
                    handleDashboard(request, response, user);
                    break;
                case "/blood-reports":
                    handleBloodReports(request, response, user);
                    break;
                case "/donors":
                    handleDonors(request, response, user);
                    break;
                case "/blood-stock":
                    handleBloodStock(request, response, user);
                    break;
                case "/approve-report":
                    handleApproveReport(request, response, user);
                    break;
                case "/reject-report":
                    handleRejectReport(request, response, user);
                    break;
                case "/update-report":
                    handleUpdateReport(request, response, user);
                    break;
                case "/add-blood-stock":
                    handleAddBloodStock(request, response, user);
                    break;
                case "/delete-blood-stock":
                    handleDeleteBloodStock(request, response, user);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/jsp/medical/medical_dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void handleDashboard(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, SQLException {
        // Get statistics for medical staff dashboard
        List<BloodReport> pendingReports = bloodReportDAO.getBloodReportsByStatus("PENDING");
        List<BloodReport> approvedReports = bloodReportDAO.getBloodReportsByStatus("APPROVED");
        List<BloodReport> rejectedReports = bloodReportDAO.getBloodReportsByStatus("REJECTED");
        List<Donor> allDonors = donorDAO.getAllDonors();
        
        // Get recent blood reports
        List<BloodReport> recentReports = new ArrayList<>();
        for (BloodReport report : bloodReportDAO.getAllBloodReports()) {
            if (recentReports.size() < 10) {
                recentReports.add(report);
            }
        }

        request.setAttribute("pendingReports", pendingReports);
        request.setAttribute("approvedReports", approvedReports);
        request.setAttribute("rejectedReports", rejectedReports);
        request.setAttribute("allDonors", allDonors);
        request.setAttribute("recentReports", recentReports);
        
        request.getRequestDispatcher("/jsp/medical/medical_dashboard.jsp").forward(request, response);
    }

    private void handleBloodReports(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, SQLException {
        List<BloodReport> allReports = bloodReportDAO.getAllBloodReports();
        List<Donor> allDonors = donorDAO.getAllDonors();
        
        request.setAttribute("bloodReports", allReports);
        request.setAttribute("donors", allDonors);
        
        request.getRequestDispatcher("/jsp/medical/medical_blood_report.jsp").forward(request, response);
    }


    private void handleDonors(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, SQLException {
        List<Donor> allDonors = donorDAO.getAllDonors();
        List<User> donorUsers = userDAO.getAllDonors();
        
        request.setAttribute("donors", allDonors);
        request.setAttribute("donorUsers", donorUsers);
        
        request.getRequestDispatcher("/jsp/medical/medical_donor_details.jsp").forward(request, response);
    }


    private void handleApproveReport(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, SQLException {
        int reportId = Integer.parseInt(request.getParameter("report_id"));
        
        BloodReport report = bloodReportDAO.getBloodReportById(reportId);
        if (report != null) {
            report.setStatus("APPROVED");
            report.setTestedBy(user.getId());
            report.setTestedAt(new java.sql.Timestamp(System.currentTimeMillis()));
            
            bloodReportDAO.updateBloodReport(report);
            
            // Send notification to donor
            Donor donor = donorDAO.getDonorById(report.getDonorId());
            if (donor != null) {
                notificationService.sendNotification(
                    donor.getUserId(),
                    "Your blood test results have been approved by medical staff.",
                    "SYSTEM"
                );
            }
            
            response.sendRedirect(request.getContextPath() + "/medical/blood-reports?success=Report approved successfully");
        } else {
            response.sendRedirect(request.getContextPath() + "/medical/blood-reports?error=Report not found");
        }
    }

    private void handleRejectReport(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, SQLException {
        int reportId = Integer.parseInt(request.getParameter("report_id"));
        String reason = request.getParameter("reason");
        
        BloodReport report = bloodReportDAO.getBloodReportById(reportId);
        if (report != null) {
            report.setStatus("REJECTED");
            report.setTestedBy(user.getId());
            report.setTestedAt(new java.sql.Timestamp(System.currentTimeMillis()));
            
            if (reason != null && !reason.trim().isEmpty()) {
                report.setMedicalStaffNotes(reason);
            }
            
            bloodReportDAO.updateBloodReport(report);
            
            // Send notification to donor
            Donor donor = donorDAO.getDonorById(report.getDonorId());
            if (donor != null) {
                notificationService.sendNotification(
                    donor.getUserId(),
                    "Your blood test results have been rejected. Reason: " + (reason != null ? reason : "Please contact medical staff for details."),
                    "SYSTEM"
                );
            }
            
            response.sendRedirect(request.getContextPath() + "/medical/blood-reports?success=Report rejected successfully");
        } else {
            response.sendRedirect(request.getContextPath() + "/medical/blood-reports?error=Report not found");
        }
    }

    private void handleUpdateReport(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, SQLException {
        try {
            int reportId = Integer.parseInt(request.getParameter("report_id"));
            String status = request.getParameter("status");
            String notes = request.getParameter("notes");
            
            BloodReport report = bloodReportDAO.getBloodReportById(reportId);
            if (report != null) {
                if (status != null && !status.trim().isEmpty()) {
                    report.setStatus(status);
                }
                
                if (notes != null && !notes.trim().isEmpty()) {
                    report.setMedicalStaffNotes(notes);
                }
                
                report.setTestedBy(user.getId());
                report.setTestedAt(new java.sql.Timestamp(System.currentTimeMillis()));
                
                bloodReportDAO.updateBloodReport(report);
                
                // Send notification to donor
                Donor donor = donorDAO.getDonorById(report.getDonorId());
                if (donor != null) {
                    String message = "Your blood report has been updated. Status: " + status;
                    if (notes != null && !notes.trim().isEmpty()) {
                        message += " Notes: " + notes;
                    }
                    notificationService.sendNotification(
                        donor.getUserId(),
                        message,
                        "SYSTEM"
                    );
                }
                
                response.sendRedirect(request.getContextPath() + "/medical/blood-reports?success=Report updated successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/medical/blood-reports?error=Report not found");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/medical/blood-reports?error=Invalid report ID");
        }
    }

    private void handleBloodStock(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, SQLException {
        com.blooddonation.dao.BloodStockDAO stockDAO = new com.blooddonation.dao.BloodStockDAO();
        List<com.blooddonation.model.BloodStock> allStock = stockDAO.getAllBloodStock();
        
        request.setAttribute("bloodStock", allStock);
        request.setAttribute("now", new java.util.Date());
        
        request.getRequestDispatcher("/jsp/medical/medical_blood_stock.jsp").forward(request, response);
    }

    private void handleAddBloodStock(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, SQLException {
        try {
            String bloodGroup = request.getParameter("blood_group");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String expiryDateStr = request.getParameter("expiry_date");
            String collectionDateStr = request.getParameter("collection_date");
            String screeningResult = request.getParameter("screening_result");
            int donorId = Integer.parseInt(request.getParameter("donor_id"));
            double volume = Double.parseDouble(request.getParameter("volume"));
            
            // Parse dates
            java.util.Date expiryDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(expiryDateStr);
            java.util.Date collectionDate = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(collectionDateStr);
            
            // Create blood stock entry
            com.blooddonation.model.BloodStock stock = new com.blooddonation.model.BloodStock();
            stock.setBloodGroup(bloodGroup);
            stock.setQuantity(quantity);
            stock.setExpiryDate(expiryDate);
            stock.setCollectionDate(collectionDate);
            stock.setScreeningResult(screeningResult);
            stock.setDonorId(donorId);
            stock.setVolume(volume);
            stock.setStatus("NEGATIVE".equals(screeningResult) ? "USABLE" : "QUARANTINED");
            
            // Add to stock
            com.blooddonation.dao.BloodStockDAO stockDAO = new com.blooddonation.dao.BloodStockDAO();
            stockDAO.addBloodStock(stock);
            
            response.sendRedirect(request.getContextPath() + "/medical/blood-stock?success=Blood stock added successfully");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/medical/blood-stock?error=Error adding blood stock: " + e.getMessage());
        }
    }
    
    private void handleDeleteBloodStock(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException, SQLException {
        try {
            int stockId = Integer.parseInt(request.getParameter("stock_id"));
            
            com.blooddonation.dao.BloodStockDAO stockDAO = new com.blooddonation.dao.BloodStockDAO();
            
            // Get stock details before deletion for confirmation
            com.blooddonation.model.BloodStock stock = stockDAO.getBloodStockById(stockId);
            if (stock == null) {
                response.sendRedirect(request.getContextPath() + "/medical/blood-stock?error=Blood stock not found");
                return;
            }
            
            // Delete the blood stock
            stockDAO.deleteBloodStock(stockId);
            response.sendRedirect(request.getContextPath() + "/medical/blood-stock?success=Blood stock deleted successfully");
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/medical/blood-stock?error=Invalid stock ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/medical/blood-stock?error=Error deleting blood stock: " + e.getMessage());
        }
    }
}
