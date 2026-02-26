package com.portfolio.portfolio.controller;

import com.portfolio.portfolio.model.Information;
import com.portfolio.portfolio.model.Project;
import com.portfolio.portfolio.repository.InformationRepository;
import com.portfolio.portfolio.repository.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PublicController {

    private final InformationRepository informationRepository;
    private final ProjectRepository projectRepository;

    public PublicController(InformationRepository informationRepository,
            ProjectRepository projectRepository) {
        this.informationRepository = informationRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        Information info = informationRepository.findAll()
                .stream().findFirst().orElse(new Information());
        List<Project> projects = projectRepository.findAllByOrderByDisplayOrderAsc();
        model.addAttribute("info", info);
        model.addAttribute("projects", projects);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
