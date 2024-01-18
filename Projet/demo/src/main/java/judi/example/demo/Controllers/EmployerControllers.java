package judi.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import judi.example.demo.Models.Objects.Employe;

@Controller
public class EmployerControllers {
    @PostMapping("/traiteCreationEmployer") // mbola tsy vita
    public String createEmployertraitement(@RequestParam String nom_employer,@RequestParam String prix, Model model){ 
        try {
            
            Employe emp = new Employe(0, nom_employer, Double.parseDouble(prix));
            emp.insertNewEmploye(null);
            String message = "Creation d'employer reussi";
            model.addAttribute("success_messsage", message);
            return "otherPage/succees";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "index";
        }    
    }

    @PostMapping("/traiteAddEmployerToVoyageDurre") 
    public String traiteAddEmployerToVoyageDurre(@RequestParam int id_voyage,@RequestParam int id_durre,@RequestParam int id_employer, Model model){     
        String message = "Creation d'activite reussi";
        model.addAttribute("success_messsage", message);
        return "otherPage/succees";
    }
}
