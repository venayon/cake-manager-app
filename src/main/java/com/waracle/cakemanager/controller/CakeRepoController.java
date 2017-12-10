package com.waracle.cakemanager.controller;

import com.waracle.cakemanager.dao.CakeRepository;
import com.waracle.cakemanager.entity.CakeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class CakeRepoController {

    private static final String REDIRECT_HOME = "redirect:/";
    @Autowired
    private CakeRepository cakeRepository;

    /**
     * Cake Manager Landing page with all existing cakes from cake repo
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    public String getCakes(Model model) {
        updateModel(model);
        return "cakeManager";
    }

    /**
     * Add new cake to Repo
     *
     * @param cake
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "")
    public String addCake(CakeEntity cake, Model model) {
        CakeEntity cakeEntity = cakeRepository.findByTitle(cake.getTitle());
        String errMsg = null;
        // TODO : add model validation instead thses if conditions
        if (isNull(cake) || !StringUtils.hasText(cake.getTitle())) {
            errMsg = String.format("Cake title is required ");
            model.addAttribute("error", errMsg);
            return "cakeManager";
        } else if (isNull(cakeEntity)) {
            cakeRepository.save(cake);
            return REDIRECT_HOME;
        } else {
            errMsg = String.format("Cake title [%s] already exists , Please choose different cake title", cake.getTitle());
            model.addAttribute("error", errMsg);
            updateModel(model);
            return "cakeManager";
        }
    }

    private void updateModel(Model model) {
        List<CakeEntity> cakes = cakeRepository.findAll();
        if (cakes != null) {
            model.addAttribute("cakes", cakes);
        }
    }
}

