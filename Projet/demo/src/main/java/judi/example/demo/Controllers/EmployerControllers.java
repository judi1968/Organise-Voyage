package judi.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import judi.example.demo.Models.DataObject.EmployeTaux;
import judi.example.demo.Models.Objects.Employe;
import judi.example.demo.Models.Utils.DateHeure;

@Controller
public class EmployerControllers {
    @PostMapping("/traiteCreationEmployer") // mbola tsy vita
    public String createEmployertraitement(@RequestParam String nom_employer,@RequestParam String prix, String date,Model model){ 
        try {

            DateHeure datev = new DateHeure(date.concat(" 00:00:00"));
            Employe emp = new Employe(0, nom_employer,Double.parseDouble(prix));
            emp.setDateEmbauche(datev);
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

    @GetMapping("/listByNiveaudefault") 
    public String listByNiveauDefault(/*@RequestParam String date,*/Model model){
        String date = "2100-01-01";
        try {
            return listByNiveau(date, model);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "index";
        }
    }

    @GetMapping("/listByNiveau") 
    public String listByNiveau(@RequestParam String dates, Model model) throws Exception{
        DateHeure date = new DateHeure(dates.concat(" 00:00:00"));
        EmployeTaux empTaux = new EmployeTaux();
        EmployeTaux[] employes = EmployeTaux.getAllEmployeTaux(date, null);
        String message = "Creation d'activite reussi";
        model.addAttribute("employes", employes);
        return "employer/listeParNiveau";
    }
}
