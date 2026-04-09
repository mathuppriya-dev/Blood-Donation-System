# 🩸 BloodLink — Smart Blood Donation & Management System

> A comprehensive web-based platform to streamline blood donation, inventory management, and healthcare coordination.

---

## 🚀 Project Overview

**BloodLink** is a full-stack web application designed to efficiently manage the entire blood donation ecosystem — connecting **donors, hospitals, and administrators** in a unified system.

This project was developed as part of a **Software Engineering module** and demonstrates real-world application of **MVC architecture, role-based access control, and system design principles**.

---

## 🎯 Key Features

### 👥 Multi-Role System

* Donor
* Manager
* Medical Staff
* Donor Relation Officer
* Hospital
* Hospital Coordinator

### 🔐 Authentication & Security

* Role-based login system
* Secure password hashing using BCrypt
* Session management & access control

### 📅 Donation & Appointment Management

* Schedule blood donation appointments
* Track eligibility based on donation history
* Appointment approval system

### 📦 Blood Inventory Management

* Real-time blood stock tracking
* Low stock alerts & expiry monitoring
* Blood group-wise inventory visualization

### 📊 Dashboards & Reports

* Admin & user dashboards
* Data-driven insights (charts & stats)
* Report generation

### ✉️ Notifications

* Email notifications for updates
* Alerts for low stock & appointments

### 📝 Feedback System

* Submit and manage user feedback
* Status tracking (Pending / Resolved)

---

## 🛠️ Tech Stack

* **Backend:** Java, Servlets
* **Frontend:** JSP, HTML, CSS, JavaScript
* **Database:** MySQL
* **Build Tool:** Maven
* **Server:** Apache Tomcat

---

## 🧠 Software Engineering Practices

* 🧩 MVC Architecture (Model–View–Controller)
* 🤝 Team collaboration with Git
* 🧪 Testing & validation
* ⚙️ Modular and scalable design
* 🔐 Security best practices (input validation, hashing)
* 📐 UML-based system design

---

## 📂 Project Structure

```
BloodLink/
│
├── src/main/java/
│   ├── dao/
│   ├── model/
│   ├── service/
│   ├── servlet/
│   └── util/
│
├── src/main/webapp/
│   ├── views/
│   ├── css/
│   ├── js/
│   └── images/
│
├── database/
│   └── schema.sql
│
├── pom.xml
└── README.md
```

---

## ⚙️ How to Run the Project

### 🔧 Prerequisites

* Java JDK 8+
* Apache Tomcat
* MySQL
* Maven
* IDE (IntelliJ / Eclipse)

---

### ▶️ Steps

1. **Clone the repository**

```bash
git clone https://github.com/YOUR_USERNAME/BloodLink.git
```

2. **Import into IDE**

* Open project as Maven project

3. **Configure Database**

* Create a MySQL database
* Import the provided SQL file

4. **Update DB Credentials**

* Edit database configuration in project

5. **Run on Tomcat**

* Deploy project on Apache Tomcat server

6. **Access Application**

```
http://localhost:8080/BloodLink
```

---

## 📸 Screenshots

> Add your screenshots inside a `screenshots/` folder

```md
![Login](./screenshots/login.png)
![Dashboard](./screenshots/dashboard.png)
![Inventory](./screenshots/inventory.png)
```

---

## 👨‍💻 My Contribution

* Designed and developed core backend modules
* Implemented role-based authentication system
* Built key UI components and dashboards
* Integrated MySQL database with application logic
* Participated in system design and debugging

---

## 👥 Team Members

* Thanushi Ganesh
* Naguleswaran Mathuppriya
* K. Thamilchudar
* P. Jathusanan
* S. Sivaganka

---

## 🌟 Future Improvements

* REST API integration (Spring Boot / Node.js)
* Mobile application version
* Real-time notifications (WebSockets)
* Advanced analytics dashboard
* Cloud deployment (AWS / Firebase)

---

## 📌 Conclusion

This project demonstrates the practical application of **software engineering principles, full-stack development, and collaborative teamwork** to solve a real-world healthcare problem.

---

## ⭐ Support

If you like this project, give it a ⭐ on GitHub!

---

