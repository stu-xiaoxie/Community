package com.xiaoxie.community.controller;

import com.xiaoxie.community.dto.PaginationDTO;
import com.xiaoxie.community.dto.QuestionDTO;
import com.xiaoxie.community.mapper.QuestionMapper;
import com.xiaoxie.community.mapper.UserMapper;
import com.xiaoxie.community.model.Question;
import com.xiaoxie.community.model.User;
import com.xiaoxie.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){

        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);

        return "index";
    }
}
