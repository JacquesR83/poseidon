package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RatingController {

    // TODO: Inject Rating service
    RatingRepository ratingRepository;

    @Autowired
    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // TODO: find all Rating, add to model
        model.addAttribute("ratings", ratingRepository.findAll());
        return "rating/list";
    }




    //ADD needs a sequence of 2 steps
    /**
     * // ADD
     * @param rating
     * @return
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * // Validate
     * @param rating
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        // Si ne renvoie pas d'erreur
        if(!result.hasErrors()){
            //Save in DataBase
            ratingRepository.save(rating);
            //Renvoie la vue
            model.addAttribute("ratings", ratingRepository.findAll()); // .html thymeleafé
            return "rating/list"; // URI
        }

        return "rating/add";
    }




    // Update in 2 steps
    /**
     * Update Form
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        Rating rating = ratingRepository.findById(id).orElseThrow( ()-> new IllegalArgumentException("Invalid rating id" + id));
        // Renvoie la vue du formulaire
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * Confirm Update -> displays Updated page
     * @param id
     * @param rating
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        if(result.hasErrors()){
            return "rating/update";
        }
        ratingRepository.save(rating);
        model.addAttribute("ratings",ratingRepository.findAll());

        return "redirect:/rating/list";
    }



    /**
     * Delete by id
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        // Find rating by Id
        Rating rating = ratingRepository.findById(id).orElseThrow( ()-> new IllegalArgumentException("Invalid rating id" + id));

        //Delete method from repository
        ratingRepository.delete(rating);

        // Displays rating list
        model.addAttribute("ratings", ratingRepository.findAll());

        return "redirect:/rating/list";
    }
}
