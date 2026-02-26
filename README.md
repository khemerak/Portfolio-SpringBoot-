# 🌐 Portfolio Website — Spring Boot

A professional full-stack personal portfolio website powered by **Spring Boot 3**, **Thymeleaf**, **Spring Security**, **JPA + H2**, and a premium dark glassmorphism UI.

---

## ✨ Features

| Area | Details |
|------|---------|
| **Public Site** | Hero, About, Projects grid, Contact/Footer |
| **Design** | Glassmorphism cards, electric violet accent, Inter font, smooth animations |
| **Admin CMS** | Protected dashboard to manage all portfolio content |
| **Security** | BCrypt passwords, Spring Security, `/admin/**` route protection |
| **Database** | H2 file-based (auto-seeded on first run) |

---

## 🔐 Admin Panel Access

1. Navigate to [http://localhost:8080/admin/dashboard](http://localhost:8080/admin/dashboard)
   - You will be **automatically redirected** to the login page
2. Enter the default credentials:
   - **Username:** `admin`
   - **Password:** `password`
3. You'll land on the dashboard where you can:
   - ✏️ Edit your name, title, headline, About Me text
   - 🔗 Update profile image URL, CV link, and social URLs
   - ➕ Add new projects to the public grid
   - 📝 Edit existing projects
   - 🗑️ Delete projects

All changes are **immediately reflected** on the public landing page without a restart.

---

## 🗄️ H2 Database Console

Access the embedded database browser at:  
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

| Field | Value |
|-------|-------|
| JDBC URL | `jdbc:h2:file:./data/portfoliodb` |
| Username | `sa` |
| Password | *(leave empty)* |

> **Note:** H2 is file-based — data persists across restarts in the `data/` directory. The `DataInitializer` seeds sample data if the tables are empty.

---

## 📁 Project Structure

```
portfolio/
├── src/main/java/com/portfolio/portfolio/
│   ├── PortfolioApplication.java       # Main entry point
│   ├── config/
│   │   ├── SecurityConfig.java         # Spring Security configuration
│   │   └── DataInitializer.java        # Seed data on startup
│   ├── controller/
│   │   ├── PublicController.java       # GET / and /login
│   │   └── AdminController.java        # /admin/** endpoints
│   ├── model/
│   │   ├── Information.java            # Portfolio metadata entity
│   │   └── Project.java               # Project entity
│   └── repository/
│       ├── InformationRepository.java
│       └── ProjectRepository.java
├── src/main/resources/
│   ├── application.properties
│   ├── static/
│   │   ├── css/style.css              # Complete design system
│   │   └── js/main.js                 # Animations & interactions
│   └── templates/
│       ├── index.html                 # Public landing page
│       ├── login.html                 # Custom login page
│       └── admin/
│           └── dashboard.html         # Admin CMS
└── pom.xml
```

---

## 🔧 Customization

To personalize your portfolio, log into the admin dashboard and update:

1. **Your name & title** in the "Site Information" form
2. **About Me** text
3. **Experience, Location, Status, and Education** fields in the About & Hero sections
3. **Profile image** URL (use any publicly accessible image URL)
4. **Social links** (GitHub, LinkedIn, Twitter)
5. **Projects** — add your real projects with descriptions and tech tags

---

## 📦 Tech Stack

- **Backend:** Spring Boot 3.2, Spring Security, Spring Data JPA
- **Frontend:** Thymeleaf, Vanilla CSS (custom design system), Font Awesome 6
- **Database:** H2 (embedded), Lombok for boilerplate reduction
- **Build:** Maven
