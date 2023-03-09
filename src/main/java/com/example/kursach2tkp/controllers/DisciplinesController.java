package com.example.kursach2tkp.controllers;

import com.example.kursach2tkp.dao.PositionDAO;
import com.example.kursach2tkp.dao.DisciplineDAO;
import com.example.kursach2tkp.dao.UserDAO;
import com.example.kursach2tkp.models.Disciplines;
import com.example.kursach2tkp.models.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/disciplines")
public class DisciplinesController {

    private UserDAO userDAO;

    private DisciplineDAO disciplineDAO;

    private PositionDAO positionDAO;

    @Autowired
    public DisciplinesController(UserDAO userDAO, DisciplineDAO disciplineDAO, PositionDAO positionDAO){
        this.userDAO = userDAO;
        this.disciplineDAO = disciplineDAO;
        this.positionDAO = positionDAO;
    }

    @GetMapping()
    public String getAllSubjects(Model model,
                                 Authentication authentication){

        boolean isAuthenticated = false;
        boolean isAdmin = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isAdmin = isUserAdmin(authentication);
        }

        model.addAttribute("is_auth", isAuthenticated);
        model.addAttribute("is_admin", isAdmin);
        model.addAttribute("logged_user", username);
        model.addAttribute("disciplines", disciplineDAO.getAllDisciplesList());

        return "/disciplines/список-дисциплин";
    }

    @PostMapping("/new")
    public String addNewSubject(@RequestParam(value = "newDisciplineName") String newSubject){
        Disciplines disciplines = new Disciplines();
        disciplines.setName(newSubject);
        disciplineDAO.createDisciplines(disciplines);

        return "redirect:/disciplines";
    }

    @PostMapping("/position/new")
    public String addNewPosition(@RequestParam(value = "newPositionName") String newPost){
        Position position = new Position();
        position.setName(newPost);
        positionDAO.addPost(position);

        return "redirect:/disciplines";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable("id") int id,
                                Model model,
                                Authentication authentication){
        disciplineDAO.deleteDisciplinesById(id);
        return "redirect:/disciplines";
    }

    boolean isUserAdmin(Authentication authentication){
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
