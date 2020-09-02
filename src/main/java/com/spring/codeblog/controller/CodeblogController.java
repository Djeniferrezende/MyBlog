package com.spring.codeblog.controller;


import com.spring.codeblog.model.Post;


import com.spring.codeblog.service.CodeblogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CodeblogController {
    @Autowired
    CodeblogService codeblogService;


    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ModelAndView getPosts() {
        ModelAndView mv = new ModelAndView("posts");
        List<Post> posts =codeblogService.findAll();
        mv.addObject("posts", posts);
        return mv;
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public ModelAndView getPostDetalhes(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("postdetalhes");
        Post post = codeblogService.findById(id);
        mv.addObject("post", post);
        return mv;
    }
    @RequestMapping(value = "/newpost", method = RequestMethod.GET)
    public String getPOstForm(){
        return "postForm";
    }
    @RequestMapping(value = "/newpost", method = RequestMethod.POST)
    /*vai validar se nao tem campo nulos @valid*/
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes atributes){
        if(result.hasErrors()){
            atributes.addFlashAttribute( "mensagem", "Verifique se os campos obrigatorios foram preenchedos!");
            return "redirect:/newpost";
        }
        post.setDate(LocalDate.now());
        codeblogService.save(post);
        return "redirect:/posts";
    }


}