package com.xiaoxie.community.controller;

import com.xiaoxie.community.dto.CommentCreateDTO;
import com.xiaoxie.community.dto.CommentDTO;
import com.xiaoxie.community.dto.QuestionDTO;
import com.xiaoxie.community.enums.CommentTypeEnum;
import com.xiaoxie.community.mapper.CommentMapper;
import com.xiaoxie.community.service.CommentService;
import com.xiaoxie.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comment = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //增加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comment);
        model.addAttribute("relatedQuestions", relatedQuestions);


        return "question";
    }
}
