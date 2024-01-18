package judi.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import judi.example.demo.Models.DataObject.ActiviteVoyageResultSearch;
import judi.example.demo.Models.Objects.Activite;
import judi.example.demo.Models.Objects.Bouquet;
@Controller
public class ActiviteControllers {
    @PostMapping("/traiteCreationActivite")
    public String createActivitetraitement(@RequestParam String nom_activite, Model model){
        Activite.insert_activite(nom_activite);
        String message = "Creation d'activite reussi";
        model.addAttribute("success_messsage", message);
        return "otherPage/succees";
    }
    @GetMapping("/listActivite")
    public String getAllActivite(Model model) throws Exception{
        Activite[] activite = Activite.getAllActivite(null);
        model.addAttribute("activites", activite);
        return "activite/listAllActivite";
    }
    @GetMapping("/searchActiviteToVoyage")
    public String searchActiviteToVoyage(Model model) throws Exception{
        Activite[] activite = Activite.getAllActivite(null);
        model.addAttribute("activites", activite);
        return "activite/formSearchActiviteToVoyage";
    }

    @GetMapping("/updateActivitePrix")
    public String updatePrixActivite(Model model) throws Exception{
        Activite[] activite = Activite.getAllActivite(null);
        model.addAttribute("activites", activite);
        return "activite/updatePrixActivte";
    }
    @GetMapping("/addActivite")
    public String addActivite(Model model) throws Exception{
        Activite[] activite = Activite.getAllActivite(null);
        model.addAttribute("activites", activite);
        return "activite/addActivite";
    }
    @PostMapping("/addActiviteTraitement")
    public String addActiviteTraitement(@RequestParam String id_activite,int quantite,Model model) throws Exception{
        Activite activite = Activite.getActiviteById(Integer.parseInt(id_activite), null);
        // ActiviteVoyageResultSearch[] resultat = ActiviteVoyageResultSearch.getAllActiviteVoyageResultByIdActivite(activite, null); 
        activite.addActiviteToStock(quantite,null);
        
        return addActivite(model);
    }
    
    @PostMapping("/searchActiviteToBouquetTraitement")
    public String searchActiviteToBouquetTraitement(@RequestParam String id_activite,Model model) throws Exception{
        Activite activite = Activite.getActiviteById(Integer.parseInt(id_activite), null);
        ActiviteVoyageResultSearch[] resultat = ActiviteVoyageResultSearch.getAllActiviteVoyageResultByIdActivite(activite, null); 
        
        model.addAttribute("resultat", resultat);
        
        return "activite/resultSearchActiviteToVoyage";
    }

    @PostMapping("/modifier_prix_traitement")
    public String modifier_prix_traitement(@RequestParam String id_activite, @RequestParam String prix,Model model) throws Exception{
        Activite activite = Activite.getActiviteById(Integer.parseInt(id_activite), null);
        // ActiviteVoyageResultSearch[] resultat = ActiviteVoyageResultSearch.getAllActiviteVoyageResultByIdActivite(activite, null); 
        // model.addAttribute("resultat", resultat);
        activite.addNewActivitePrix(Double.parseDouble(prix), null);
        System.out.println(id_activite+" "+prix);
        return updatePrixActivite(model);
    }
    
    
}
