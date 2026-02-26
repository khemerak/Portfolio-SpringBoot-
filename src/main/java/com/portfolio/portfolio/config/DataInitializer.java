package com.portfolio.portfolio.config;

import com.portfolio.portfolio.model.Information;
import com.portfolio.portfolio.model.Project;
import com.portfolio.portfolio.model.AdminCredentials;
import com.portfolio.portfolio.repository.InformationRepository;
import com.portfolio.portfolio.repository.ProjectRepository;
import com.portfolio.portfolio.repository.AdminCredentialsRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

        private final InformationRepository informationRepository;
        private final ProjectRepository projectRepository;
        private final AdminCredentialsRepository adminCredentialsRepository;
        private final PasswordEncoder passwordEncoder;

        public DataInitializer(InformationRepository informationRepository,
                        ProjectRepository projectRepository,
                        AdminCredentialsRepository adminCredentialsRepository,
                        PasswordEncoder passwordEncoder) {
                this.informationRepository = informationRepository;
                this.projectRepository = projectRepository;
                this.adminCredentialsRepository = adminCredentialsRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(ApplicationArguments args) {
                // Seed default AdminCredentials if empty
                if (adminCredentialsRepository.count() == 0) {
                        AdminCredentials creds = new AdminCredentials();
                        creds.setUsername("admin");
                        creds.setPassword(passwordEncoder.encode("password"));
                        adminCredentialsRepository.save(creds);
                }

                // Seed Information if empty
                if (informationRepository.count() == 0) {
                        Information info = new Information();
                        info.setName("Alex Johnson");
                        info.setTitle("Full-Stack Developer & Software Engineer");
                        info.setHeadline("I build high-performance web applications that make an impact.");
                        info.setAboutMe(
                                        "Hi, I'm Alex — a passionate Full-Stack Developer with 5+ years of experience crafting "
                                                        +
                                                        "scalable, production-grade applications. I specialize in Java/Spring Boot on the backend "
                                                        +
                                                        "and modern JS frameworks on the frontend. I love solving hard problems, writing clean code, "
                                                        +
                                                        "and delivering beautiful user experiences.");
                        info.setProfileImageUrl("https://avatars.githubusercontent.com/u/583231?v=4");
                        info.setCvUrl("#");
                        info.setGithubUrl("https://github.com");
                        info.setLinkedinUrl("https://linkedin.com");
                        info.setTwitterUrl("https://twitter.com");
                        info.setEmail("alex@example.com");
                        info.setLocation("San Francisco, CA");
                        info.setExperienceYears(5);
                        info.setCurrentStatus("Open to Opportunities");
                        info.setEducation("B.S. Computer Science");
                        informationRepository.save(info);
                }

                // Seed Projects if empty
                if (projectRepository.count() == 0) {
                        Project p1 = new Project();
                        p1.setTitle("E-Commerce Platform");
                        p1.setDescription("A scalable multi-vendor e-commerce platform with real-time inventory, " +
                                        "payment integration (Stripe), and an admin dashboard for sellers.");
                        p1.setTechTags("Spring Boot, React, PostgreSQL, Stripe API, Docker");
                        p1.setProjectUrl("https://github.com");
                        p1.setImageUrl("https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?w=600&q=80");
                        p1.setDisplayOrder(1);

                        Project p2 = new Project();
                        p2.setTitle("AI Task Manager");
                        p2.setDescription(
                                        "Smart task management app powered by OpenAI GPT-4. Automatically categorizes, "
                                                        +
                                                        "prioritizes, and schedules tasks based on your natural language input.");
                        p2.setTechTags("Python, FastAPI, OpenAI API, Vue.js, Redis");
                        p2.setProjectUrl("https://github.com");
                        p2.setImageUrl("https://images.unsplash.com/photo-1677442136019-21780ecad995?w=600&q=80");
                        p2.setDisplayOrder(2);

                        Project p3 = new Project();
                        p3.setTitle("Real-Time Chat App");
                        p3.setDescription("WebSocket-based real-time chat application supporting rooms, " +
                                        "file uploads, end-to-end encryption, and a mobile-responsive UI.");
                        p3.setTechTags("Spring Boot, WebSocket, Thymeleaf, JWT, H2");
                        p3.setProjectUrl("https://github.com");
                        p3.setImageUrl("https://images.unsplash.com/photo-1611606063065-ee7946f0787a?w=600&q=80");
                        p3.setDisplayOrder(3);

                        Project p4 = new Project();
                        p4.setTitle("DevOps Pipeline Dashboard");
                        p4.setDescription("A centralized CI/CD monitoring dashboard that aggregates metrics from " +
                                        "Jenkins, GitHub Actions, and ArgoCD into a single, real-time view.");
                        p4.setTechTags("Java, Spring Boot, Kubernetes, Grafana, Prometheus");
                        p4.setProjectUrl("https://github.com");
                        p4.setImageUrl("https://images.unsplash.com/photo-1667372393119-3d4c48d07fc9?w=600&q=80");
                        p4.setDisplayOrder(4);

                        projectRepository.save(p1);
                        projectRepository.save(p2);
                        projectRepository.save(p3);
                        projectRepository.save(p4);
                }
        }
}
