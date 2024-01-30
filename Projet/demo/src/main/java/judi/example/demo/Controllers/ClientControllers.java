package judi.example.demo.Controllers;



import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import judi.example.demo.Models.DataObject.ClientAchatVoyageDurre;
import judi.example.demo.Models.DataObject.EmployeTaux;
import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.Objects.Client;
import judi.example.demo.Models.Objects.Employe;
import judi.example.demo.Models.Objects.FonctionEmploye;
import judi.example.demo.Models.Objects.Genre;
import judi.example.demo.Models.Objects.Niveau_employe;
import judi.example.demo.Models.Objects.VoyageDurre;
import judi.example.demo.Models.Utils.DateHeure;

@Controller
public class ClientControllers {
    @GetMapping("/createClient") // mbola tsy vita
    public String createClient(Model model){ 
        try {
            Genre[] genres = Genre.getAllGenres(null);
            // System.out.println(genres.length);
            String message = "Creation d'employer reussi";
            model.addAttribute("genre", genres);
            return "client/Create";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "index";
        }    
    }
    @PostMapping("/createNewClient") // mbola tsy vita
    public String createNewClient(@RequestParam String nom,@RequestParam String prenom, String genre,Model model){ 
        try {
            Client client = new Client();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setGenre(genre);
            client.insertNewClient(null);
            String message = "Creation d'employer reussi";
            model.addAttribute("success_messsage", message);
            return "otherPage/succees";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "index";
        }    
    }
    @GetMapping("/statistiqueAchatClient")
    public String StatistiqueAchatClient(Model model){ 
        try {
            ClientAchatVoyageDurre[] clientAchatVoyageDurres = ClientAchatVoyageDurre.getAllClientAchatVoyageDurres(null);
            int nombreHomme = ClientAchatVoyageDurre.getCountClientHommeAchat(null);
            int nombreFemme = ClientAchatVoyageDurre.getCountClientFemmeAchat(null);
            int totale = ClientAchatVoyageDurre.getCountClientAchat(null);
            model.addAttribute("nombreHomme", nombreHomme);
            model.addAttribute("nombreFemme", nombreFemme);
            model.addAttribute("nombreTotale", totale);
            double pourcentageHomme = 0;
            double pourcentageFemme = 0;
            try {
                pourcentageHomme = (nombreHomme*100)/totale;
                pourcentageFemme = (nombreFemme*100)/totale;
            } catch (Exception e) {
                
            }

                
            model.addAttribute("pourcentageHomme", pourcentageHomme);
            model.addAttribute("pourcentageFemme", pourcentageFemme);
            model.addAttribute("clientachat", clientAchatVoyageDurres);
            String message = "Creation d'employer reussi";
            model.addAttribute("success_messsage", message);
            return "client/StatistiqueAchatClient";
                
             } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "index";
        }    
    }
    @PostMapping("/vendreVoyageDurreByClientTraitement") // mbola tsy vita
    public String vendreVoyageDurreByClientTraitement(@RequestParam String voyage, String client,String date,Model model){ 
        try {
            String[] idString = voyage.split(";");
            int idv = Integer.parseInt(idString[0]);
            int idd = Integer.parseInt(idString[1]);            
            VoyageDurre vd = VoyageDurre.getVoyageByIdVoyageAndIdDurre(idv,idd,null);
            Client cliento = Client.getClientById(Integer.parseInt(client), null);
            DateHeure dateHeure = new DateHeure(date+" 00:00:00");
            ClientAchatVoyageDurre clientAchatVoyageDurre = new ClientAchatVoyageDurre();
            clientAchatVoyageDurre.setDateAchat(dateHeure);
            clientAchatVoyageDurre.setId_client(cliento.getId_client());
            clientAchatVoyageDurre.setGenre(cliento.getGenre());
            clientAchatVoyageDurre.setVoyageDurre(vd);
            clientAchatVoyageDurre.InsertClientAchatVoyageDurre(null);
            String message = "Creation d'employer reussi";
            model.addAttribute("success_messsage", message);
            return "otherPage/succees";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "index";
        }    
    }
    @GetMapping("/acheterVoyage") 
    public String AcheterVoyage(Model model){ 
        try {
            VoyageDurre[]  voyageDurres = VoyageDurre.getAllVoyageDurre(null);
            Client[] clients = Client.getAllClient(null);
            
            String message = "Creation d'employer reussi";
            model.addAttribute("clients", clients);
            model.addAttribute("voyageDurres", voyageDurres);
            model.addAttribute("success_messsage", message);
            return "client/AcheterVoyage";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "index";
        }    
    }

    @GetMapping("/validerPanier") 
    public String validerPanier( Model model){ 
        try {
            Client[] clients = Client.getAllClient(null);
            
            model.addAttribute("clients", clients);
            model.addAttribute("erreur", "none");
            return "client/validerPanier";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "index";
        }    
    }

    @GetMapping("/validePanierTraitement") 
    public void validePanierTraitement(HttpServletResponse res,@RequestParam String id_client, Model model){ 
        try {
            int id_clienti = Integer.parseInt(id_client);
            // alaina daholo ny activite sy ny nombre ny activite an ilay client
            Client client = Client.getClientById(id_clienti, null);
            if(!client.verifyStockActiviteSuffisantByIdClient(null)){
                res.sendRedirect("/stockSuffisant");
            }else{

                // rehefa azo io de verifiena amzay raha misy an io stock io is
                client.modifyStock(null);
                ClientAchatVoyageDurre.validePanier(id_clienti);
                Client[] clients = Client.getAllClient(null);
                
                model.addAttribute("clients", clients);
                res.sendRedirect("/validerPanier");
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }    
    } 

    
    @GetMapping("/stockSuffisant") 
    public String stockSuffisant(Model model){ 
        try {
            Client[] clients = Client.getAllClient(null);
            
            model.addAttribute("clients", clients);
            model.addAttribute("erreur", "stock suffisant");
            
            return "client/validerPanier";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "index";
        }    
    }
}

