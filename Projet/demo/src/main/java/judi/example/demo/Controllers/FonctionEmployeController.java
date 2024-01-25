package judi.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import judi.example.demo.Models.Objects.FonctionEmploye;

import java.sql.Connection;

@Controller
public class FonctionEmployeController {

    @PostMapping("/insertFonctionEmploye")
    public ModelAndView insertFonctionEmploye(@RequestParam String nom_designation, Model model) {
        Connection connection = null;
        FonctionEmploye fonctionEmploye = new FonctionEmploye();
        fonctionEmploye.setNom_designation(nom_designation);
        String message = "";
        try {
            fonctionEmploye.insertNewFonctionEmploye(connection);
             message = "Création de la fonction employé réussie";
            model.addAttribute("successMessage", message);
        } catch (Exception e) {
             message = "Erreur lors de la création de la fonction employé: " + e.getMessage();
            model.addAttribute("errorMessage", message);
        }

        return new ModelAndView("redirect:/listFonctionEmploye").addObject("successMessage", message);
    }

    @GetMapping("/listFonctionEmploye")
    public String getAllFonctionEmploye(Model model) {
        Connection connection = null;
        try {
            FonctionEmploye[] fonctionEmployes = FonctionEmploye.getAllFonctionEmploye(connection);
            model.addAttribute("fonctionEmployes", fonctionEmployes);
        } catch (Exception e) {
            String errorMessage = "Erreur lors de la récupération des fonctions employé: " + e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
        }

        return "fonctionEmploye/listAllFonctionEmploye";
    }
}

