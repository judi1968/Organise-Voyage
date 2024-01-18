package judi.example.demo.Controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import judi.example.demo.Models.Objects.Activite;
import judi.example.demo.Models.Objects.Bouquet;
@Controller
public class BouquetControllers {
    @PostMapping("/traiteCreationBouquet")
    public String createActivitetraitement(@RequestParam String nom_bouquet, Model model){
        Bouquet.insert_bouquet(nom_bouquet);
        String message = "Creation d'activite reussi";
        model.addAttribute("success_messsage", message);
        return "otherPage/succees";
    }
    @GetMapping("/listBouquet")
    public String getAllBouquet(Model model) throws Exception{
        Bouquet[] bouquets = Bouquet.getAllBouquet(null);
        model.addAttribute("bouquets", bouquets);
        return "bouquet/listAllBouquet";
    }
    @GetMapping("/addActiviteInBouquet")
    public String addActivite(@RequestParam String id_bouquet,Model model) throws Exception{
        Bouquet bouquet = Bouquet.getBouquetById(Integer.parseInt(id_bouquet), null);
        Activite[] activite = Activite.getAllActiviteWithoutBouquet(bouquet ,null);
        int id = Integer.parseInt(id_bouquet);
        model.addAttribute("bouquet", Bouquet.getBouquetById(id, null));
        model.addAttribute("activites", activite);
        return "bouquet/addActivite";
    }
    @GetMapping("/listAllActiviteToBouquet")
    public String listActivite(@RequestParam String id_bouquet,Model model) throws Exception{
        Bouquet bouquet = Bouquet.getBouquetById(Integer.parseInt(id_bouquet), null);
        Activite[] activite = Activite.getAllActiviteWithBouquet(bouquet ,null);
        int id = Integer.parseInt(id_bouquet);
        model.addAttribute("bouquet", Bouquet.getBouquetById(id, null));
        model.addAttribute("activites", activite);
        return "bouquet/listAllActiviteIntoBouquet";
    }
    @GetMapping("/addActiviteToBouquet")
    public void addActiviteToBouquet(@RequestParam String id_activite,@RequestParam String id_bouquet, Model model,HttpServletResponse res) throws NumberFormatException, Exception{
        Bouquet bouquet = Bouquet.getBouquetById(Integer.parseInt(id_bouquet), null);
        Activite activite = Activite.getActiviteById(Integer.parseInt(id_activite), null);
        if (activite.isInBouquet(bouquet, null)) {
            new Exception("L'activite existe deja dans le bouquet ");
        }else{
            bouquet.addActivite(activite, null);
        }
        
        try {
            res.sendRedirect("/addActiviteInBouquet?id_bouquet="+id_bouquet);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
