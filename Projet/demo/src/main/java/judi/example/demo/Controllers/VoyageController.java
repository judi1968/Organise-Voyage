package judi.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import judi.example.demo.Models.DataObject.ResultatVoyageDurrePrixBenefice;
import judi.example.demo.Models.DataObject.Voyage_prix;
import judi.example.demo.Models.Objects.Activite;
import judi.example.demo.Models.Objects.ActiviteVoyage;
import judi.example.demo.Models.Objects.Bouquet;
import judi.example.demo.Models.Objects.Durre;
import judi.example.demo.Models.Objects.Employe;
import judi.example.demo.Models.Objects.FonctionEmploye;
import judi.example.demo.Models.Objects.Lieu;
import judi.example.demo.Models.Objects.Voyage;
import judi.example.demo.Models.Objects.VoyageDurre;
import judi.example.demo.Models.Utils.DateHeure;

@Controller
public class VoyageController {
    // controller
    @PostMapping("/voyage")
    public String createActivitetraitement(@RequestParam String nom_activite,@RequestParam String id_lieuString, @RequestParam String id_bouqueString,  Model model){
        try {
            Bouquet bouquet = Bouquet.getBouquetById(Integer.parseInt(id_bouqueString), null);
            Lieu lieu = Lieu.getLieuById(Integer.parseInt(id_lieuString),null);
            Voyage.createNewVoyage(nom_activite, lieu, bouquet, null);
            String message = "Creation de voyage reussi";
            model.addAttribute("success_messsage", message);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "otherPage/succees";
    }



    // rooter
    @GetMapping("/")
    public String login(){
        return "index";
    }
    @GetMapping("/createActivite")
    public String createActivite(){
        return "activite/create";
    }
    @GetMapping("/createBouquet")
    public String createBouquet(){
        return "bouquet/create";
    }
    @GetMapping("/addEmployerToVoyage")
    public String addEmployerToVoyage(Model model){
        try {
            
            Employe[] employes = Employe.getAllEmploye(null);
            VoyageDurre[] voyageDurres = VoyageDurre.getAllVoyageDurre(null);
            model.addAttribute("employes", employes);
            model.addAttribute("voyageDurres",voyageDurres);
            // model.addAttribute("lieux", lieux);
            return "employer/addToVoyage";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "index";
        }
    }
    @GetMapping("/creerEmployer")
    public String creerEmployer(Model model ){
        try {
            FonctionEmploye[] fonctionEmployes = FonctionEmploye.getAllFonctionEmploye(null);
            model.addAttribute("fonctionEmployes", fonctionEmployes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.printStackTrace();
        }
        return "employer/create";
    }
    @GetMapping("/ajouter_prix_voyage")
    public String ajouter_prix_voyage(Model model){
        try {    
            VoyageDurre[] voyageDurres = VoyageDurre.getAllVoyageDurre(null);
            model.addAttribute("voyageDurres",voyageDurres);
            return "voyage/ajouter_prix_voyage";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "index";
        }
    }
    @GetMapping("/list_voyage_in_benefice")
    public String listVoyageInBenefice(@RequestParam(defaultValue = "0") String max, @RequestParam(defaultValue = "0") String min,Model model) {
        try {    
            ResultatVoyageDurrePrixBenefice[] resultatVoyageDurrePrixBenefices = ResultatVoyageDurrePrixBenefice.getAlResultatVoyageDurrePrixBeneficesInIntervallePrix(Double.parseDouble(min), Double.parseDouble(max), null);
            model.addAttribute("resultatVoyageDurrePrixBenefices", resultatVoyageDurrePrixBenefices);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            return "voyage/list_voyage_in_benefice";
        }
    }

    
    @GetMapping("/createVoyage")
    public String createVoyage(Model model) throws Exception{
        Bouquet[] bouquets = Bouquet.getAllBouquet(null);
        Lieu[] lieux = Lieu.getAllLieu(null);
        model.addAttribute("bouquets", bouquets);
        model.addAttribute("lieux", lieux);
        return "voyage/create";
    }
    @GetMapping("/list_voyage")
    public String list_voyage(Model model) throws Exception{
        Voyage[] voyages = Voyage.getAllVoyages(null);
        model.addAttribute("voyage", voyages);
        return "voyage/list_all_voyage";
    }
    @GetMapping("/vendre_voyage")
    public String vendre_voyage(Model model) throws Exception{
        ActiviteVoyage[] voyages = ActiviteVoyage.getAllActiviteVoyage(null);
        model.addAttribute("voyage", voyages);
        return "voyage/vendre_voyage";
    }
    @PostMapping("/vendre_voyage_traitement")
    public String vendre_voyage_Traitement(@RequestParam String id_voyage,@RequestParam int quantite, Model model) throws Exception{
        String[] idString = id_voyage.split(";");
        int id_v = Integer.parseInt(idString[0]);
        int id_c = Integer.parseInt(idString[1]);
        ActiviteVoyage activiteVoyage = ActiviteVoyage.getActiviteVoyageByIdVoyageAndIdDurre(id_v,id_c,null);
        Voyage voyage = activiteVoyage.getVoyage();
        voyage.sortieVoyageAction(null);
        ActiviteVoyage[] voyages = ActiviteVoyage.getAllActiviteVoyage(null);
        model.addAttribute("voyage", voyages);
        return "voyage/vendre_voyage";
    }
    
    @GetMapping("/ajouter_durre_activite_to_voyage")
    public String ajouter_durre_activite_to_voyage(@RequestParam String id_voyage,  Model model){
        try {
            Voyage voyage = Voyage.getVoyageById(Integer.parseInt(id_voyage), null);
            Bouquet bouquet = voyage.getBouquet();
            Activite[] activite = Activite.getAllActiviteWithBouquet(bouquet, null);
            Durre[] durres = Durre.getAllDurres(null);
            model.addAttribute("voyage", voyage);
            model.addAttribute("activites", activite);
            model.addAttribute("durres", durres);
            
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "voyage/ajouter_durre_activite_to_voyage";
    }
    @PostMapping("/ajout_activite_durre_traitement")
    public String ajout_activite_durre_traitement(@RequestParam String id_voyage,@RequestParam String id_activite ,@RequestParam String id_duree, Model model){
        try {
            Voyage voyage = Voyage.getVoyageById(Integer.parseInt(id_voyage), null);
            Activite activite = Activite.getActiviteById(Integer.parseInt(id_activite), null);
            Durre durre = Durre.getDurreById(Integer.parseInt(id_duree),null);
            ActiviteVoyage activiteVoyage = new ActiviteVoyage(voyage, activite, durre);
            activiteVoyage.insertNewActiviteVoyage(null);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ajouter_durre_activite_to_voyage(id_voyage,model);
    }

    @PostMapping("/ajout_prix_to_voyage")
    public String ajout_prix_to_voyage(@RequestParam String id_voyage,@RequestParam double prix, Model model){
        try {
            String[] idString = id_voyage.split(";");
            int id_v = Integer.parseInt(idString[0]);
            int id_c = Integer.parseInt(idString[1]);
            Voyage voyage = Voyage.getVoyageById(id_v, null);
            voyage.insertPrixVoyage(id_c, prix, null);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "index";
    }

    
    @GetMapping("/form_search_activite_prix")
    public String form_search_activite_prix(Model model) throws Exception{
        return "voyage/formSearchActivitePrix";
    }

    @PostMapping("/result_search_voyage_prix_activite")
    public String getAllVoyageByPrix(@RequestParam String min,@RequestParam String max, Model model){
        try {
            Voyage_prix[] voyage_prixs = Voyage_prix.getAllVoyagesPrix(Double.parseDouble(min), Double.parseDouble(max), null);
            
            model.addAttribute("resultat", voyage_prixs);
            // System.out.println(min+" "+max);
            // model.addAttribute("resultat", null);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "voyage/listVoyageByPrix";
    }   
    
    @PostMapping("/addEmployerToVoyageTraitement")
    public String addEmployerToVoyageTraitement(@RequestParam String id_voyage,@RequestParam int id_employe,@RequestParam String hour, Model model){
        try {
            String[] idString = id_voyage.split(";");
            int idv = Integer.parseInt(idString[0]);
            int idd = Integer.parseInt(idString[1]);
            System.out.println(hour);
            DateHeure dateHeure = new DateHeure();
            dateHeure.setTime(hour.concat(":00"));
            double tempsDouble = dateHeure.getDoubleTime();
            VoyageDurre vd = VoyageDurre.getVoyageByIdVoyageAndIdDurre(idv,idd,null);
            Employe emp = Employe.getEmployeById(id_employe, null);
            vd.insertEmployerToVoyageDurre(emp,tempsDouble,null);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return addEmployerToVoyage(model);
    }    
}
