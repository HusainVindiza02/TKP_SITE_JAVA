package com.example.kursach2tkp.controllers;

import com.example.kursach2tkp.dao.DisciplineDAO;
import com.example.kursach2tkp.dao.PositionDAO;
import com.example.kursach2tkp.dao.UserDAO;
import com.example.kursach2tkp.models.Employee;
import com.example.kursach2tkp.models.Position;
import com.example.kursach2tkp.models.Disciplines;
import com.example.kursach2tkp.models.User;
import com.example.kursach2tkp.dao.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeesController {

    private EmployeeDAO employeeDAO;

    private UserDAO userDAO;

    private DisciplineDAO disciplineDAO;

    private PositionDAO positionDAO;

    @Autowired
    public EmployeesController(EmployeeDAO employeeDAO, UserDAO userDAO, DisciplineDAO disciplineDAO, PositionDAO positionDAO){
        this.employeeDAO = employeeDAO;
        this.userDAO = userDAO;
        this.disciplineDAO = disciplineDAO;
        this.positionDAO = positionDAO;
    }


    @GetMapping("/all")
    public String GetAllWorkers(Model model,
                                Authentication authentication){

        boolean isAuthenticated = false;
        boolean isAdmin = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isAdmin = isUserAdmin(authentication);
        }

        model.addAttribute("employees", employeeDAO.getAllWorkersList());
        model.addAttribute("is_auth", isAuthenticated);
        model.addAttribute("is_admin", isAdmin);
        model.addAttribute("logged_user", username);

        return "employees/список-работников";
    }


    @GetMapping("/info/{id}")
    public String GetWorkerByID(@PathVariable("id") int id, Model model){
        //model.addAttribute("worker", employeeDAO.getWorkerByID(id));
        model.addAttribute("worker", employeeDAO.getEmployeesByID(id));
        return "workers/worker";
    }

    @GetMapping("/discipline/{id}")
    public String GetAllWorkersBySubject(@PathVariable("id") int subject,
                                            Model model,
                                            Authentication authentication){

        boolean isAuthenticated = false;
        boolean isAdmin = false;
        String username = null;


        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isAdmin = isUserAdmin(authentication);
        }

        model.addAttribute("employees", employeeDAO.getWorkersByDisciplineID(subject));
        model.addAttribute("is_auth", isAuthenticated);
        model.addAttribute("is_admin", isAdmin);
        model.addAttribute("logged_user", username);
        model.addAttribute("discipline_name", subject);

        return "employees/список-работников";
    }

    @GetMapping("/new")
    public String NewWorker(Model model,
                            Authentication authentication){

        boolean isAuthenticated = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        model.addAttribute("disciplinesList", disciplineDAO.getAllDisciplesList());
        model.addAttribute("positionsList", positionDAO.getPositionList());
        model.addAttribute("is_auth", isAuthenticated);
        model.addAttribute("logged_user", username);

        return "employees/новый-работник";
    }

    @PostMapping("/new")
    public String AddWorker(@RequestParam("first_name") String first_name,
                            @RequestParam("last_name") String last_name,
                            @RequestParam("patronym") String patronym,
                            @RequestParam("position") int position_id,
                            @RequestParam("discipline") int discipline_id,
                            @RequestParam("birthday") String birthday,
                            Model model,
                            Authentication authentication){

        Employee employee = new Employee();

        UserDetails principals = (UserDetails) authentication.getPrincipal();
        User user = userDAO.getUserByLogin(principals.getUsername());
        Position position = positionDAO.getPositionById(position_id);
        Disciplines disciplines = disciplineDAO.getDisciplinesById(discipline_id);

        employee.setFirst_name(first_name);
        employee.setLast_name(last_name);
        employee.setPatronym(patronym);
        employee.setPosition(position);
        employee.setBirthday(birthday);
        employee.setDiscipline(disciplines);

        employeeDAO.addNewEmployees(employee);

        model.addAttribute("workers", employeeDAO.getAllWorkersList());

        return "redirect:/employees/all";
    }

    @GetMapping(value = "/edit/{id}")
    public String editWorker(@PathVariable("id") int id,
                             Model model,
                             Authentication authentication){

        boolean isAuthenticated = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        model.addAttribute("employees", employeeDAO.getEmployeesByID(id));
        model.addAttribute("disciplineList", disciplineDAO.getAllDisciplesList());
        model.addAttribute("positionList", positionDAO.getPositionList());
        model.addAttribute("is_auth", isAuthenticated);
        model.addAttribute("logged_user", username);

        return "employees/редактирование-работника";
    }

    @PostMapping(value = "/edit/{id}")
    public String confirmEditWorker(@PathVariable("id") int id,
                                    @RequestParam("first_name") String first_name,
                                    @RequestParam("last_name") String last_name,
                                    @RequestParam("patronym") String patronym,
                                    @RequestParam("position") int position_id,
                                    @RequestParam("discipline") int discipline_id,
                                    @RequestParam("birthday") String birthday,
                             Model model,
                             Authentication authentication){

        boolean isAuthenticated = false;
        String username = null;

        Employee employee = employeeDAO.getEmployeesByID(id);
        employee.setFirst_name(first_name);
        employee.setLast_name(last_name);
        employee.setPatronym(patronym);
        employee.setPosition(positionDAO.getPositionById(position_id));
        employee.setDiscipline(disciplineDAO.getDisciplinesById(discipline_id));
        employee.setBirthday(birthday);

        employeeDAO.updateEmployees(employee);

        return "redirect:/employees/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteWorker(@PathVariable("id") int id,
                               Model model,
                               Authentication authentication){

        employeeDAO.deleteEmployeesById(id);

        return "redirect:/employees/all";
    }

    boolean isUserAdmin(Authentication authentication){
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
