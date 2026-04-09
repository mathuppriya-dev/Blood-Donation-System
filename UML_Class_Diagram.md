# Blood Donation System - UML Class Diagram

```plantuml
@startuml BloodDonationSystem

!theme plain
skinparam classAttributeIconSize 0
skinparam classFontSize 10
skinparam packageStyle rectangle

package "Model Layer" {
    
    class User {
        - id: int
        - username: String
        - password: String
        - role: String
        - email: String
        - phone: String
        - status: String
        - createdAt: Timestamp
        + getId(): int
        + setId(int): void
        + getUsername(): String
        + setUsername(String): void
        + getPassword(): String
        + setPassword(String): void
        + getRole(): String
        + setRole(String): void
        + getEmail(): String
        + setEmail(String): void
        + getPhone(): String
        + setPhone(String): void
        + getStatus(): String
        + setStatus(String): void
        + getCreatedAt(): Timestamp
        + setCreatedAt(Timestamp): void
    }

    class Donor {
        - id: int
        - userId: int
        - name: String
        - age: int
        - gender: String
        - bloodGroup: String
        - weight: double
        - healthInfo: String
        - lastDonationDate: Date
        - status: String
        - createdAt: Date
        - updatedAt: Date
        + Donor()
        + Donor(int, String, int, String, String, double)
        + isEligibleForDonation(): boolean
        + getNextEligibleDate(): Date
        - isEligibleBasedOnLastDonation(): boolean
    }

    class Hospital {
        - id: int
        - userId: int
        - hospitalName: String
        - hospitalType: String
        - address: String
        - city: String
        - state: String
        - postalCode: String
        - contactPerson: String
        - contactPhone: String
        - contactEmail: String
        - licenseNumber: String
        - registrationNumber: String
        - status: String
        - createdAt: Date
        - updatedAt: Date
        + Hospital()
        + Hospital(int, String, String)
        + isPrivate(): boolean
        + isGovernment(): boolean
        + isActive(): boolean
        + isPending(): boolean
        + isSuspended(): boolean
        + activate(): void
        + suspend(): void
        + getFullAddress(): String
    }

    class Appointment {
        - id: int
        - donorId: int
        - campId: int
        - appointmentDate: Date
        - status: String
        - timeSlot: String
        - notes: String
        - createdAt: Date
        - updatedAt: Date
        - approvedAt: Date
        - approvedBy: int
        + Appointment()
        + Appointment(int, int, Date, String)
        + isPending(): boolean
        + isApproved(): boolean
        + isRejected(): boolean
        + isCompleted(): boolean
        + isCancelled(): boolean
        + canBeCancelled(): boolean
    }

    class DonationCamp {
        - id: int
        - campName: String
        - location: String
        - address: String
        - city: String
        - campDate: Date
        - startTime: Date
        - endTime: Date
        - maxDonors: int
        - currentDonors: int
        - organizerId: int
        - status: String
        - description: String
        - specialRequirements: String
        - contactPerson: String
        - contactPhone: String
        - createdAt: Date
        - updatedAt: Date
        + DonationCamp()
        + DonationCamp(String, String, Date, Date, int)
        + isPlanned(): boolean
        + isActive(): boolean
        + isCompleted(): boolean
        + isCancelled(): boolean
        + isUpcoming(): boolean
        + isPast(): boolean
        + isCurrentlyActive(): boolean
        + getDurationInDays(): long
        + canAcceptDonors(): boolean
        + activate(): void
        + complete(): void
        + cancel(): void
    }

    class BloodStock {
        - id: int
        - bloodGroup: String
        - quantity: int
        - expiryDate: Date
        - collectionDate: Date
        - status: String
        - screeningResult: String
        - donorId: int
        - volume: double
        - notes: String
        - createdAt: Date
        - updatedAt: Date
        + BloodStock()
        + BloodStock(String, int, Date, Date, String, int, double)
        + isExpired(): boolean
        + isUsable(): boolean
        + isQuarantined(): boolean
        + isDisposed(): boolean
        + isExpiringSoon(int): boolean
        + getDaysUntilExpiry(): int
    }

    class BloodRequest {
        - id: int
        - hospitalId: int
        - bloodGroup: String
        - quantity: int
        - urgency: String
        - status: String
        - priority: String
        - patientName: String
        - reason: String
        - rejectionReason: String
        - requestDate: Date
        - deliveryDate: Date
        - approvedAt: Date
        - approvedBy: int
        - createdAt: Date
        - updatedAt: Date
        + BloodRequest()
        + BloodRequest(int, String, int, String, String)
        + isPending(): boolean
        + isApproved(): boolean
        + isRejected(): boolean
        + isOnHold(): boolean
        + isCompleted(): boolean
        + canBeEdited(): boolean
        - calculatePriority(): void
    }

    class BloodReport {
        - id: int
        - donorId: int
        - appointmentId: Integer
        - hemoglobin: BigDecimal
        - rbc: BigDecimal
        - hct: BigDecimal
        - mcv: BigDecimal
        - mch: BigDecimal
        - mchc: BigDecimal
        - rdw: BigDecimal
        - wbc: BigDecimal
        - esr: BigDecimal
        - plt: BigDecimal
        - gra: BigDecimal
        - lym: BigDecimal
        - eos: BigDecimal
        - bas: BigDecimal
        - bloodPressureSystolic: Integer
        - bloodPressureDiastolic: Integer
        - pulseRate: Integer
        - temperature: BigDecimal
        - weightAtDonation: BigDecimal
        - donationVolume: Integer
        - medicalStaffNotes: String
        - status: String
        - testedBy: Integer
        - testedAt: Timestamp
        - createdAt: Timestamp
    }

    class Notification {
        - id: int
        - userId: int
        - title: String
        - message: String
        - type: String
        - status: String
        - channel: String
        - priority: String
        - isRead: boolean
        - scheduledAt: Date
        - sentAt: Date
        - readAt: Date
        - expiresAt: Date
        - actionUrl: String
        - errorMessage: String
        - retryCount: int
        - createdAt: Date
        - updatedAt: Date
        + Notification()
        + Notification(int, String, String, String)
        + isUnread(): boolean
        + isSent(): boolean
        + isFailed(): boolean
        + isUrgent(): boolean
        + canRetry(): boolean
        + markAsRead(): void
        + markAsSent(): void
        + markAsFailed(String): void
    }

    class Feedback {
        - id: int
        - userId: int
        - feedbackText: String
        - category: String
        - status: String
        - response: String
        - respondedBy: int
        - responseDate: Date
        - isUrgent: boolean
        - createdAt: Date
        - updatedAt: Date
        + Feedback()
        + Feedback(int, String, String)
        + isPending(): boolean
        + isResponded(): boolean
        + isEscalated(): boolean
        + isResolved(): boolean
        + needsEscalation(): boolean
    }

    class Alert {
        - id: int
        - type: String
        - severity: String
        - title: String
        - message: String
        - bloodGroup: String
        - quantity: int
        - expiryDate: Date
        - status: String
        - acknowledgedBy: int
        - acknowledgedAt: Date
        - escalatedTo: int
        - escalatedAt: Date
        - resolution: String
        - resolvedAt: Date
        - resolvedBy: int
        - createdAt: Date
        - updatedAt: Date
        + Alert()
        + Alert(String, String, String, String)
        + Alert(String, String, String, String, String, int)
        + isActive(): boolean
        + isAcknowledged(): boolean
        + isResolved(): boolean
        + isEscalated(): boolean
        + isCritical(): boolean
        + isHigh(): boolean
        + isLowStock(): boolean
        + isExpiryWarning(): boolean
        + isThresholdBreach(): boolean
        + acknowledge(int): void
        + escalate(int): void
        + resolve(int, String): void
    }

    class AuditLog {
        - id: int
        - userId: int
        - action: String
        - description: String
        - timestamp: Date
        - ipAddress: String
        - userAgent: String
        + AuditLog()
        + AuditLog(int, String, String, String, String)
        + AuditLog(int, String, String, int, String, String)
        + isLoginAction(): boolean
        + isLogoutAction(): boolean
        + isCreateAction(): boolean
        + isUpdateAction(): boolean
        + isDeleteAction(): boolean
        + isReadAction(): boolean
        + isSecurityAction(): boolean
        + isDataAction(): boolean
        + isSystemAction(): boolean
        + getActionCategory(): String
        + isRecent(): boolean
        + isToday(): boolean
        + isThisWeek(): boolean
        + isThisMonth(): boolean
        + getFormattedTimestamp(): String
        + getShortDescription(): String
    }
}

package "DAO Layer" {
    
    class UserDAO {
        + addUser(User): void
        + getUserById(int): User
        + getUserByUsername(String): User
        + getUserByEmail(String): User
        + getAllUsers(): List<User>
        + updateUser(User): void
        + deleteUser(int): void
        + authenticateUser(String, String): User
        + changePassword(int, String): void
        + updateUserStatus(int, String): void
        + getUsersByRole(String): List<User>
        + searchUsers(String): List<User>
        + getUsersCount(): int
        + getActiveUsersCount(): int
        + getInactiveUsersCount(): int
        + getRecentUsers(int): List<User>
    }

    class DonorDAO {
        + addDonor(Donor): void
        + getDonorById(int): Donor
        + getDonorByUserId(int): Donor
        + getAllDonors(): List<Donor>
        + updateDonor(Donor): void
        + deleteDonor(int): void
        + getDonorsByBloodGroup(String): List<Donor>
        + getEligibleDonors(): List<Donor>
        + getDonorsByStatus(String): List<Donor>
        + updateDonorStatus(int, String): void
        + updateLastDonationDate(int, Date): void
        + getDonorsCount(): int
        + getEligibleDonorsCount(): int
        + getDonorsByBloodGroupCount(String): int
        + searchDonors(String): List<Donor>
        + getRecentDonors(int): List<Donor>
    }

    class HospitalDAO {
        + addHospital(Hospital): void
        + getHospitalById(int): Hospital
        + getHospitalByUserId(int): Hospital
        + getAllHospitals(): List<Hospital>
        + updateHospital(Hospital): void
        + deleteHospital(int): void
        + getHospitalsByStatus(String): List<Hospital>
        + getHospitalsByType(String): List<Hospital>
        + updateHospitalStatus(int, String): void
        + getHospitalsCount(): int
        + getActiveHospitalsCount(): int
        + getPendingHospitalsCount(): int
        + searchHospitals(String): List<Hospital>
        + getRecentHospitals(int): List<Hospital>
    }

    class AppointmentDAO {
        + addAppointment(Appointment): void
        + getAllAppointments(): List<Appointment>
        + getPendingAppointmentsCount(): int
        + getCompletedAppointmentsByMonth(int, int): int
        + getRecentAppointments(int): List<Appointment>
        + getAppointmentsByDonorId(int): List<Appointment>
        + getAppointmentsByCampId(int): List<Appointment>
        + getAppointmentsByStatus(String): List<Appointment>
        + getPendingAppointments(): List<Appointment>
        + getApprovedAppointments(): List<Appointment>
        + getAppointmentsByDate(Date): List<Appointment>
        + getUpcomingAppointments(int): List<Appointment>
        + getAppointmentById(int): Appointment
        + updateAppointmentStatus(int, String): void
        + approveAppointment(int, int): void
    }

    class DonationCampDAO {
        + addDonationCamp(DonationCamp): void
        + getDonationCampById(int): DonationCamp
        + getAllDonationCamps(): List<DonationCamp>
        + updateDonationCamp(DonationCamp): void
        + deleteDonationCamp(int): void
        + getDonationCampsByStatus(String): List<DonationCamp>
        + getUpcomingCamps(): List<DonationCamp>
        + getActiveCamps(): List<DonationCamp>
        + getCompletedCamps(): List<DonationCamp>
        + getCampsByDateRange(Date, Date): List<DonationCamp>
        + updateCampStatus(int, String): void
        + getCampsCount(): int
        + getActiveCampsCount(): int
        + getUpcomingCampsCount(): int
        + searchCamps(String): List<DonationCamp>
    }

    class BloodStockDAO {
        + addBloodStock(BloodStock): void
        + getBloodStockById(int): BloodStock
        + getAllBloodStock(): List<BloodStock>
        + updateBloodStock(BloodStock): void
        + deleteBloodStock(int): void
        + getBloodStockByGroup(String): List<BloodStock>
        + getUsableBloodStock(): List<BloodStock>
        + getExpiringBloodStock(int): List<BloodStock>
        + getExpiredBloodStock(): List<BloodStock>
        + getBloodStockByStatus(String): List<BloodStock>
        + updateBloodStockStatus(int, String): void
        + getTotalQuantityByBloodGroup(String): int
        + getUsableQuantityByBloodGroup(String): int
        + getBloodStockSummary(): Map<String, Integer>
        + getLowStockAlerts(): List<BloodStock>
        + getExpiryAlerts(): List<BloodStock>
    }

    class BloodRequestDAO {
        + addBloodRequest(BloodRequest): void
        + getBloodRequestById(int): BloodRequest
        + getAllBloodRequests(): List<BloodRequest>
        + updateBloodRequest(BloodRequest): void
        + deleteBloodRequest(int): void
        + getBloodRequestsByHospital(int): List<BloodRequest>
        + getBloodRequestsByStatus(String): List<BloodRequest>
        + getPendingBloodRequests(): List<BloodRequest>
        + getApprovedBloodRequests(): List<BloodRequest>
        + getBloodRequestsByPriority(String): List<BloodRequest>
        + getBloodRequestsByBloodGroup(String): List<BloodRequest>
        + updateBloodRequestStatus(int, String): void
        + approveBloodRequest(int, int): void
        + rejectBloodRequest(int, String): void
        + getBloodRequestsCount(): int
        + getPendingRequestsCount(): int
        + getUrgentRequestsCount(): int
        + getRecentBloodRequests(int): List<BloodRequest>
    }

    class BloodReportDAO {
        + addBloodReport(BloodReport): void
        + getBloodReportById(int): BloodReport
        + getAllBloodReports(): List<BloodReport>
        + updateBloodReport(BloodReport): void
        + deleteBloodReport(int): void
        + getBloodReportsByDonor(int): List<BloodReport>
        + getBloodReportsByAppointment(int): List<BloodReport>
        + getBloodReportsByStatus(String): List<BloodReport>
        + getRecentBloodReports(int): List<BloodReport>
        + getBloodReportsCount(): int
        + searchBloodReports(String): List<BloodReport>
    }

    class NotificationDAO {
        + addNotification(Notification): void
        + getNotificationById(int): Notification
        + getAllNotifications(): List<Notification>
        + updateNotification(Notification): void
        + deleteNotification(int): void
        + getNotificationsByUser(int): List<Notification>
        + getUnreadNotifications(int): List<Notification>
        + getNotificationsByType(String): List<Notification>
        + getNotificationsByStatus(String): List<Notification>
        + markNotificationAsRead(int): void
        + markAllAsRead(int): void
        + getUnreadCount(int): int
        + getRecentNotifications(int, int): List<Notification>
        + deleteOldNotifications(int): void
    }

    class FeedbackDAO {
        + addFeedback(Feedback): void
        + getFeedbackById(int): Feedback
        + getAllFeedback(): List<Feedback>
        + updateFeedback(Feedback): void
        + deleteFeedback(int): void
        + getFeedbackByUser(int): List<Feedback>
        + getFeedbackByCategory(String): List<Feedback>
        + getFeedbackByStatus(String): List<Feedback>
        + getPendingFeedback(): List<Feedback>
        + getUrgentFeedback(): List<Feedback>
        + respondToFeedback(int, String, int): void
        + getFeedbackCount(): int
        + getPendingFeedbackCount(): int
        + getUrgentFeedbackCount(): int
        + getRecentFeedback(int): List<Feedback>
    }

    class AlertDAO {
        + addAlert(Alert): void
        + getAlertById(int): Alert
        + getAllAlerts(): List<Alert>
        + updateAlert(Alert): void
        + deleteAlert(int): void
        + getAlertsByType(String): List<Alert>
        + getAlertsBySeverity(String): List<Alert>
        + getAlertsByStatus(String): List<Alert>
        + getActiveAlerts(): List<Alert>
        + getCriticalAlerts(): List<Alert>
        + acknowledgeAlert(int, int): void
        + escalateAlert(int, int): void
        + resolveAlert(int, int, String): void
        + getAlertsCount(): int
        + getActiveAlertsCount(): int
        + getCriticalAlertsCount(): int
        + getRecentAlerts(int): List<Alert>
    }

    class AuditLogDAO {
        + addAuditLog(AuditLog): void
        + getAuditLogById(int): AuditLog
        + getAllAuditLogs(): List<AuditLog>
        + getAuditLogsByUser(int): List<AuditLog>
        + getAuditLogsByAction(String): List<AuditLog>
        + getAuditLogsByDateRange(Date, Date): List<AuditLog>
        + getRecentAuditLogs(int): List<AuditLog>
        + getSecurityLogs(): List<AuditLog>
        + getDataLogs(): List<AuditLog>
        + getSystemLogs(): List<AuditLog>
        + getAuditLogsCount(): int
        + getTodayLogsCount(): int
        + searchAuditLogs(String): List<AuditLog>
        + deleteOldLogs(int): void
    }
}

package "Service Layer" {
    
    class UserManagementService {
        - userDAO: UserDAO
        + createUser(User): boolean
        + authenticateUser(String, String): User
        + updateUserProfile(User): boolean
        + changePassword(int, String, String): boolean
        + getUserById(int): User
        + getAllUsers(): List<User>
        + deactivateUser(int): boolean
        + activateUser(int): boolean
        + getUsersByRole(String): List<User>
        + searchUsers(String): List<User>
        + getUserStats(): UserStats
        + getUserSummary(): UserSummary
    }

    class DonorService {
        - donorDAO: DonorDAO
        - userDAO: UserDAO
        + registerDonor(Donor): boolean
        + updateDonorProfile(Donor): boolean
        + getDonorById(int): Donor
        + getAllDonors(): List<Donor>
        + getEligibleDonors(): List<Donor>
        + getDonorsByBloodGroup(String): List<Donor>
        + checkEligibility(int): boolean
        + updateLastDonationDate(int, Date): boolean
        + getDonorDashboardData(int): DonorDashboardData
        + getDonorSummary(): DonorSummary
        + searchDonors(String): List<Donor>
    }

    class AppointmentService {
        - appointmentDAO: AppointmentDAO
        - donorDAO: DonorDAO
        - donationCampDAO: DonationCampDAO
        + bookAppointment(Appointment): boolean
        + approveAppointment(int, int): boolean
        + rejectAppointment(int, String): boolean
        + cancelAppointment(int): boolean
        + getAppointmentsByDonor(int): List<Appointment>
        + getAppointmentsByCamp(int): List<Appointment>
        + getPendingAppointments(): List<Appointment>
        + getUpcomingAppointments(): List<Appointment>
        + getAppointmentStats(): AppointmentStats
        + validateAppointment(Appointment): boolean
    }

    class BloodStockService {
        - bloodStockDAO: BloodStockDAO
        - alertDAO: AlertDAO
        + addBloodStock(BloodStock): boolean
        + updateBloodStock(BloodStock): boolean
        + getBloodStockByGroup(String): List<BloodStock>
        + getUsableBloodStock(): List<BloodStock>
        + checkLowStock(): void
        + checkExpiringStock(): void
        + getBloodStockSummary(): BloodStockSummary
        + getBloodStockStats(): BloodStockStats
        + disposeExpiredStock(): void
        + transferStock(int, int): boolean
    }

    class BloodRequestService {
        - bloodRequestDAO: BloodRequestDAO
        - hospitalDAO: HospitalDAO
        - bloodStockDAO: BloodStockDAO
        + createBloodRequest(BloodRequest): boolean
        + approveBloodRequest(int, int): boolean
        + rejectBloodRequest(int, String): boolean
        + fulfillBloodRequest(int): boolean
        + getBloodRequestsByHospital(int): List<BloodRequest>
        + getPendingBloodRequests(): List<BloodRequest>
        + getUrgentBloodRequests(): List<BloodRequest>
        + getBloodRequestStats(): BloodRequestStats
        + validateBloodRequest(BloodRequest): boolean
        + checkAvailability(String, int): boolean
    }

    class BloodReportService {
        - bloodReportDAO: BloodReportDAO
        - donorDAO: DonorDAO
        + createBloodReport(BloodReport): boolean
        + updateBloodReport(BloodReport): boolean
        + getBloodReportsByDonor(int): List<BloodReport>
        + getBloodReportById(int): BloodReport
        + validateBloodReport(BloodReport): boolean
        + generateReportSummary(int): String
        + getRecentBloodReports(int): List<BloodReport>
    }

    class NotificationService {
        - notificationDAO: NotificationDAO
        - emailUtil: EmailUtil
        - smsUtil: SMSUtil
        + sendNotification(Notification): boolean
        + sendEmailNotification(int, String, String): boolean
        + sendSMSNotification(int, String): boolean
        + getNotificationsByUser(int): List<Notification>
        + getUnreadNotifications(int): List<Notification>
        + markAsRead(int): boolean
        + markAllAsRead(int): boolean
        + scheduleNotification(Notification): boolean
        + processScheduledNotifications(): void
    }

    class FeedbackService {
        - feedbackDAO: FeedbackDAO
        - userDAO: UserDAO
        + submitFeedback(Feedback): boolean
        + respondToFeedback(int, String, int): boolean
        + getFeedbackByUser(int): List<Feedback>
        + getPendingFeedback(): List<Feedback>
        + getUrgentFeedback(): List<Feedback>
        + getFeedbackStats(): FeedbackStats
        + escalateFeedback(int): boolean
        + resolveFeedback(int): boolean
    }

    class AlertService {
        - alertDAO: AlertDAO
        - notificationService: NotificationService
        + createAlert(Alert): boolean
        + acknowledgeAlert(int, int): boolean
        + escalateAlert(int, int): boolean
        + resolveAlert(int, int, String): boolean
        + getActiveAlerts(): List<Alert>
        + getCriticalAlerts(): List<Alert>
        + processAlerts(): void
        + checkSystemAlerts(): void
        + generateStockAlerts(): void
        + generateExpiryAlerts(): void
    }

    class DashboardService {
        - userDAO: UserDAO
        - donorDAO: DonorDAO
        - appointmentDAO: AppointmentDAO
        - bloodStockDAO: BloodStockDAO
        - bloodRequestDAO: BloodRequestDAO
        + getDashboardStats(): DashboardStats
        + getRecentActivity(): List<ActivityItem>
        + getSystemOverview(): Map<String, Object>
        + getBloodStockOverview(): Map<String, Integer>
        + getAppointmentOverview(): Map<String, Integer>
        + getRequestOverview(): Map<String, Integer>
    }

    class ReportService {
        - donorDAO: DonorDAO
        - appointmentDAO: AppointmentDAO
        - bloodStockDAO: BloodStockDAO
        - bloodRequestDAO: BloodRequestDAO
        + generateDonorStatisticsReport(): DonorStatisticsReport
        + generateInventoryReport(): InventoryReport
        + generateAppointmentReport(): AppointmentReport
        + generateBloodRequestReport(): BloodRequestReport
        + generateComplianceReport(): ComplianceReport
        + generateFeedbackReport(): FeedbackReport
        + exportReportToPDF(String): byte[]
        + exportReportToExcel(String): byte[]
    }

    class SystemSettingsService {
        - configUtil: ConfigUtil
        + getSystemSettings(): Map<String, String>
        + updateSystemSetting(String, String): boolean
        + resetToDefaults(): boolean
        + validateSettings(): boolean
        + backupSettings(): boolean
        + restoreSettings(String): boolean
    }
}

package "Utility Layer" {
    
    class DatabaseUtil {
        - URL: String {static}
        - USER: String {static}
        - PASSWORD: String {static}
        + getConnection(): Connection {static}
    }

    class ConfigUtil {
        + getProperty(String): String {static}
        + setProperty(String, String): void {static}
        + loadProperties(): void {static}
        + saveProperties(): void {static}
    }

    class EmailUtil {
        + sendEmail(String, String, String): boolean {static}
        + sendHtmlEmail(String, String, String): boolean {static}
        + sendEmailWithAttachment(String, String, String, String): boolean {static}
        + validateEmail(String): boolean {static}
    }

    class SMSUtil {
        + sendSMS(String, String): boolean {static}
        + validatePhoneNumber(String): boolean {static}
    }

    class PasswordUtil {
        + hashPassword(String): String {static}
        + verifyPassword(String, String): boolean {static}
        + generateRandomPassword(): String {static}
        + validatePasswordStrength(String): boolean {static}
    }

    class ValidationUtil {
        + validateEmail(String): boolean {static}
        + validatePhone(String): boolean {static}
        + validateBloodGroup(String): boolean {static}
        + validateAge(int): boolean {static}
        + validateWeight(double): boolean {static}
        + sanitizeInput(String): String {static}
    }

    class PDFReportGenerator {
        + generateReport(String, Map<String, Object>): byte[] {static}
        + generateDonorReport(Donor): byte[] {static}
        + generateInventoryReport(List<BloodStock>): byte[] {static}
        + generateAppointmentReport(List<Appointment>): byte[] {static}
    }

    class SecurityFilter {
        + doFilter(ServletRequest, ServletResponse, FilterChain): void
        + isAuthenticated(HttpServletRequest): boolean
        + hasPermission(HttpServletRequest, String): boolean
        + redirectToLogin(HttpServletResponse): void
    }

    class PermissionUtil {
        + hasPermission(User, String): boolean {static}
        + canAccessResource(User, String): boolean {static}
        + getRolePermissions(String): List<String> {static}
        + validateAccess(int, String): boolean {static}
    }

    class SchedulerUtil {
        + scheduleTask(Runnable, long): void {static}
        + scheduleRecurringTask(Runnable, long, long): void {static}
        + cancelTask(String): void {static}
        + startScheduler(): void {static}
        + stopScheduler(): void {static}
    }
}

' Relationships
User ||--o{ Donor : "1 to 0..1"
User ||--o{ Hospital : "1 to 0..1"
User ||--o{ Notification : "1 to many"
User ||--o{ Feedback : "1 to many"
User ||--o{ AuditLog : "1 to many"

Donor ||--o{ Appointment : "1 to many"
Donor ||--o{ BloodStock : "1 to many"
Donor ||--o{ BloodReport : "1 to many"

Hospital ||--o{ BloodRequest : "1 to many"

DonationCamp ||--o{ Appointment : "1 to many"

Appointment ||--o| BloodReport : "1 to 0..1"

BloodStock ||--o{ Alert : "1 to many"

' DAO Dependencies
UserDAO ..> User : uses
DonorDAO ..> Donor : uses
HospitalDAO ..> Hospital : uses
AppointmentDAO ..> Appointment : uses
DonationCampDAO ..> DonationCamp : uses
BloodStockDAO ..> BloodStock : uses
BloodRequestDAO ..> BloodRequest : uses
BloodReportDAO ..> BloodReport : uses
NotificationDAO ..> Notification : uses
FeedbackDAO ..> Feedback : uses
AlertDAO ..> Alert : uses
AuditLogDAO ..> AuditLog : uses

' Service Dependencies
UserManagementService ..> UserDAO : uses
DonorService ..> DonorDAO : uses
DonorService ..> UserDAO : uses
AppointmentService ..> AppointmentDAO : uses
AppointmentService ..> DonorDAO : uses
AppointmentService ..> DonationCampDAO : uses
BloodStockService ..> BloodStockDAO : uses
BloodStockService ..> AlertDAO : uses
BloodRequestService ..> BloodRequestDAO : uses
BloodRequestService ..> HospitalDAO : uses
BloodRequestService ..> BloodStockDAO : uses
BloodReportService ..> BloodReportDAO : uses
BloodReportService ..> DonorDAO : uses
NotificationService ..> NotificationDAO : uses
NotificationService ..> EmailUtil : uses
NotificationService ..> SMSUtil : uses
FeedbackService ..> FeedbackDAO : uses
FeedbackService ..> UserDAO : uses
AlertService ..> AlertDAO : uses
AlertService ..> NotificationService : uses
DashboardService ..> UserDAO : uses
DashboardService ..> DonorDAO : uses
DashboardService ..> AppointmentDAO : uses
DashboardService ..> BloodStockDAO : uses
DashboardService ..> BloodRequestDAO : uses
ReportService ..> DonorDAO : uses
ReportService ..> AppointmentDAO : uses
ReportService ..> BloodStockDAO : uses
ReportService ..> BloodRequestDAO : uses
SystemSettingsService ..> ConfigUtil : uses

' All DAOs use DatabaseUtil
UserDAO ..> DatabaseUtil : uses
DonorDAO ..> DatabaseUtil : uses
HospitalDAO ..> DatabaseUtil : uses
AppointmentDAO ..> DatabaseUtil : uses
DonationCampDAO ..> DatabaseUtil : uses
BloodStockDAO ..> DatabaseUtil : uses
BloodRequestDAO ..> DatabaseUtil : uses
BloodReportDAO ..> DatabaseUtil : uses
NotificationDAO ..> DatabaseUtil : uses
FeedbackDAO ..> DatabaseUtil : uses
AlertDAO ..> DatabaseUtil : uses
AuditLogDAO ..> DatabaseUtil : uses

@enduml
```

## Key Features of the UML Class Diagram:

### 1. **Model Layer (Entity Classes)**
- **User**: Base user entity with authentication and role management
- **Donor**: Extends user functionality for blood donors with eligibility checking
- **Hospital**: Hospital entity with registration and approval workflow
- **Appointment**: Manages donation appointments with status tracking
- **DonationCamp**: Organizes blood donation events
- **BloodStock**: Tracks blood inventory with expiry and quality management
- **BloodRequest**: Handles hospital blood requests with priority management
- **BloodReport**: Medical reports for donors with comprehensive health data
- **Notification**: Multi-channel notification system
- **Feedback**: User feedback and complaint management
- **Alert**: System alerts for critical situations
- **AuditLog**: Security and activity logging

### 2. **DAO Layer (Data Access Objects)**
- Each model class has a corresponding DAO for database operations
- Comprehensive CRUD operations with business-specific queries
- Optimized methods for reporting and analytics

### 3. **Service Layer (Business Logic)**
- **UserManagementService**: User authentication and management
- **DonorService**: Donor registration and eligibility management
- **AppointmentService**: Appointment booking and approval workflow
- **BloodStockService**: Inventory management with automated alerts
- **BloodRequestService**: Request processing and fulfillment
- **NotificationService**: Multi-channel communication system
- **AlertService**: Critical alert management and escalation
- **DashboardService**: Real-time analytics and reporting
- **ReportService**: Comprehensive reporting system

### 4. **Utility Layer (Support Classes)**
- **DatabaseUtil**: Database connection management
- **SecurityFilter**: Authentication and authorization
- **EmailUtil/SMSUtil**: Communication utilities
- **ValidationUtil**: Input validation and sanitization
- **PDFReportGenerator**: Report generation
- **PasswordUtil**: Secure password handling
- **SchedulerUtil**: Task scheduling and automation

### 5. **Key Relationships**
- **One-to-One**: User ↔ Donor, User ↔ Hospital
- **One-to-Many**: User → Notifications, Donor → Appointments, Hospital → BloodRequests
- **Many-to-Many**: Implemented through junction entities where needed

### 6. **Design Patterns Used**
- **DAO Pattern**: Separates data access logic
- **Service Layer Pattern**: Encapsulates business logic
- **Singleton Pattern**: Database connections and utilities
- **Factory Pattern**: Object creation and initialization
- **Observer Pattern**: Notification and alert systems

This comprehensive UML diagram represents a robust, scalable blood donation management system with proper separation of concerns, security features, and extensive business logic for managing the complete blood donation lifecycle.