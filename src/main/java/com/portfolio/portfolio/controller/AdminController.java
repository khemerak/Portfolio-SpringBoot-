package com.portfolio.portfolio.controller;

import com.portfolio.portfolio.model.Information;
import com.portfolio.portfolio.model.Project;
import com.portfolio.portfolio.model.AdminCredentials;
import com.portfolio.portfolio.repository.InformationRepository;
import com.portfolio.portfolio.repository.ProjectRepository;
import com.portfolio.portfolio.repository.AdminCredentialsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

        private final InformationRepository informationRepository;
        private final ProjectRepository projectRepository;
        private final AdminCredentialsRepository adminCredentialsRepository;
        private final PasswordEncoder passwordEncoder;

        public AdminController(InformationRepository informationRepository,
                        ProjectRepository projectRepository,
                        AdminCredentialsRepository adminCredentialsRepository,
                        PasswordEncoder passwordEncoder) {
                this.informationRepository = informationRepository;
                this.projectRepository = projectRepository;
                this.adminCredentialsRepository = adminCredentialsRepository;
                this.passwordEncoder = passwordEncoder;
        }

        // ── Dashboard ──────────────────────────────────────────────
        @GetMapping("/dashboard")
        public String dashboard(Model model) {
                Information info = informationRepository.findAll()
                                .stream().findFirst().orElse(new Information());
                List<Project> projects = projectRepository.findAllByOrderByDisplayOrderAsc();

                String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
                AdminCredentials creds = adminCredentialsRepository.findByUsername(currentUsername)
                                .orElse(new AdminCredentials());

                model.addAttribute("info", info);
                model.addAttribute("projects", projects);
                model.addAttribute("newProject", new Project());
                model.addAttribute("credentialsForm", creds);
                return "admin/dashboard";
        }

        // ── Save Site Info ─────────────────────────────────────────
        @PostMapping("/info")
        public String saveInfo(@ModelAttribute Information formInfo,
                        RedirectAttributes redirectAttributes) {
                Information existing = informationRepository.findAll()
                                .stream().findFirst().orElse(new Information());

                existing.setName(formInfo.getName());
                existing.setTitle(formInfo.getTitle());
                existing.setHeadline(formInfo.getHeadline());
                existing.setAboutMe(formInfo.getAboutMe());
                existing.setProfileImageUrl(formInfo.getProfileImageUrl());
                existing.setCvUrl(formInfo.getCvUrl());
                existing.setGithubUrl(formInfo.getGithubUrl());
                existing.setLinkedinUrl(formInfo.getLinkedinUrl());
                existing.setTwitterUrl(formInfo.getTwitterUrl());
                existing.setEmail(formInfo.getEmail());
                existing.setLocation(formInfo.getLocation());
                existing.setExperienceYears(formInfo.getExperienceYears());
                existing.setCurrentStatus(formInfo.getCurrentStatus());
                existing.setEducation(formInfo.getEducation());

                informationRepository.save(existing);
                redirectAttributes.addFlashAttribute("successInfo", "Site information updated successfully!");
                return "redirect:/admin/dashboard";
        }

        // ── Add / Save Project ─────────────────────────────────────
        @PostMapping("/projects/save")
        public String saveProject(@ModelAttribute Project project,
                        RedirectAttributes redirectAttributes) {

                if (project.getId() != null && project.getId() > 0) {
                        // Edit Mode: Fetch existing and apply updates
                        Project existing = projectRepository.findById(project.getId())
                                        .orElseThrow(() -> new IllegalArgumentException(
                                                        "Invalid project ID: " + project.getId()));

                        existing.setTitle(project.getTitle());
                        existing.setDescription(project.getDescription());
                        existing.setTechTags(project.getTechTags());
                        existing.setProjectUrl(project.getProjectUrl());
                        existing.setImageUrl(project.getImageUrl());

                        if (project.getDisplayOrder() != null) {
                                existing.setDisplayOrder(project.getDisplayOrder());
                        }

                        projectRepository.save(existing);
                        redirectAttributes.addFlashAttribute("successProject", "Project updated!");
                } else {
                        // Add Mode: Set display order if missing and save new
                        if (project.getDisplayOrder() == null) {
                                int maxOrder = projectRepository.findAll().stream()
                                                .mapToInt(p -> p.getDisplayOrder() != null ? p.getDisplayOrder() : 0)
                                                .max().orElse(0);
                                project.setDisplayOrder(maxOrder + 1);
                        }
                        projectRepository.save(project);
                        redirectAttributes.addFlashAttribute("successProject", "Project added!");
                }

                return "redirect:/admin/dashboard";
        }

        // ── Edit Project Form ──────────────────────────────────────
        @GetMapping("/projects/{id}/edit")
        public String editProject(@PathVariable Long id, Model model) {
                Information info = informationRepository.findAll()
                                .stream().findFirst().orElse(new Information());
                List<Project> projects = projectRepository.findAllByOrderByDisplayOrderAsc();
                Project project = projectRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + id));

                String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
                AdminCredentials creds = adminCredentialsRepository.findByUsername(currentUsername)
                                .orElse(new AdminCredentials());

                model.addAttribute("info", info);
                model.addAttribute("projects", projects);
                model.addAttribute("newProject", project);
                model.addAttribute("credentialsForm", creds);
                model.addAttribute("editMode", true);
                return "admin/dashboard";
        }

        // ── Delete Project ─────────────────────────────────────────
        @PostMapping("/projects/{id}/delete")
        public String deleteProject(@PathVariable Long id,
                        RedirectAttributes redirectAttributes) {
                projectRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("successProject", "Project deleted.");
                return "redirect:/admin/dashboard";
        }

        // ── Update Credentials ─────────────────────────────────────────
        @PostMapping("/credentials")
        public String updateCredentials(@RequestParam("username") String username,
                        @RequestParam(value = "newPassword", required = false) String newPassword,
                        RedirectAttributes redirectAttributes) {
                String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
                AdminCredentials creds = adminCredentialsRepository.findByUsername(currentUsername)
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                boolean isChanged = false;

                if (username != null && !username.isBlank() && !username.equals(currentUsername)) {
                        if (adminCredentialsRepository.findByUsername(username).isPresent()) {
                                redirectAttributes.addFlashAttribute("errorCredentials", "Username already exists!");
                                return "redirect:/admin/dashboard";
                        }
                        creds.setUsername(username.trim());
                        isChanged = true;
                }

                if (newPassword != null && !newPassword.isBlank()) {
                        creds.setPassword(passwordEncoder.encode(newPassword));
                        isChanged = true;
                }

                if (isChanged) {
                        adminCredentialsRepository.save(creds);
                        redirectAttributes.addFlashAttribute("successCredentials",
                                        "Credentials updated! Please login with your new credentials next time.");
                }

                return "redirect:/admin/dashboard";
        }
}
