package ch.hearc.stockarc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.RedirectView;

import ch.hearc.stockarc.model.Tool;
import ch.hearc.stockarc.repository.PersonRepository;
import ch.hearc.stockarc.repository.RentRepository;
import ch.hearc.stockarc.repository.ToolRepository;

/**
 * Tools controller, dispatch all the request concerning tool.
 * 
 * @author Alexandre Bianchi
 */

@Controller
@EnableWebMvc
@RequestMapping("/tools")
public class ToolController {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private PersonRepository personRepository;

    /**
     * Display all the tools.
     * 
     * @param model Model attributes to pass data to the view
     * @return String The views name
     */
    @GetMapping
    public String tools(Model model) {

        model.addAttribute("tools", toolRepository.findAll(Sort.by(Direction.ASC, "name")));

        return "tools/list";
    }

    /**
     * Create a new tool from the posted data.
     * 
     * @param person        The tool object
     * @param bindingResult Represent the binding result
     * @return RedirectView The view shown after processing
     */
    @PostMapping(value = "/create")
    public RedirectView store(@Valid @ModelAttribute Tool tool, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new RedirectView("/tools");
        }

        toolRepository.save(tool);
        return new RedirectView("/tools");
    }
}