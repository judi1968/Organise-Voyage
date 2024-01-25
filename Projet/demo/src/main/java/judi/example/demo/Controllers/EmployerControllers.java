package judi.example.demo.Controllers;

import java.sql.Connection;
import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import judi.example.demo.Models.DataObject.EmployeTaux;
import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.Objects.Employe;
import judi.example.demo.Models.Objects.FonctionEmploye;
import judi.example.demo.Models.Objects.Niveau_employe;
import judi.example.demo.Models.Utils.DateHeure;

@Controller
public class EmployerControllers {
    @PostMapping("/traiteCreationEmployer") // mbola tsy vita
    public String createEmployertraitement(@RequestParam String nom_employer,@RequestParam String prix, String id_fonction,String date,Model model){ 
        try {
            DateHeure datev = new DateHeure(date.concat(" 00:00:00"));
            FonctionEmploye fonctionEmployer = FonctionEmploye.getFonctionEmployeById(Integer.parseInt(id_fonction), null);
            Employe emp = new Employe(0, nom_employer,Double.parseDouble(prix),fonctionEmployer);
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
        Date datev = new Date(new java.util.Date().getTime());
        String dateString = datev.toString();
        System.out.println(dateString);
        String date = dateString;
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
        model.addAttribute("dateDonne", dates);
        model.addAttribute("employes", employes);
        return "employer/listeParNiveau";
    }
    @GetMapping("/listAllEmployer") 
    public String listAllEmploye(Model model) throws Exception{
        Employe[] employes = Employe.getAllEmploye(null);
        model.addAttribute("employes", employes);
        return "employer/listeAllEmployer";
    }
    
    @GetMapping("/listAllTauxEmployerByNiveau") 
    public String listAllTauxEmployerByNiveau(Model model) throws Exception{
        Niveau_employe[] niveau_employes = Niveau_employe.getAllNiveau_employes(null);
        // for (Niveau_employe niveau_employe : niveau_employes) {
        //     niveau_employe.getDesignation_niveau()
        //     niveau_employe.getNombre_jour_minimale()
        // }
        model.addAttribute("niveau_employes", niveau_employes);
        return "employer/listAllTauxEmployerByNiveau";
    }
}
