package judi.example.demo.Controllers;

import java.util.Vector;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import judi.example.demo.Models.Objects.Test;


/**
 * TestControllers
 */
@Controller
public class TestControllers {

    @GetMapping("/0")
    public String hello() {
        return "index"; // Ceci renvoie "index.jsp"
    }

	@GetMapping("/login0")
    public String loginc(@RequestParam String nom, @RequestParam int age, Model model) {
		model.addAttribute("nom", nom);
        model.addAttribute("age", age);
        return "login/login"; // Ceci renvoie "index.jsp"
    }

	@PostMapping("/liste0")
    public String liste(@RequestParam String nom, @RequestParam String prenom, Model model) {
		Vector<String> noms = new Vector<>();
		noms.add("Rakoto");
		noms.add("Rabe");
		noms.add(nom);
		
		Vector<Test> prenoms = new Vector<Test>();
		prenoms.add(new Test("Jean"));
		prenoms.add(new Test("Paul"));
		prenoms.add(new Test(prenom));
		
		model.addAttribute("nom", noms);
        model.addAttribute("prenom", prenoms);
        return "liste/liste"; // Ceci renvoie "index.jsp"
    }

}