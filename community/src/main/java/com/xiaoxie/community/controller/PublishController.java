package com.xiaoxie.community.controller;

import com.xiaoxie.community.cache.TagCache;
import com.xiaoxie.community.dto.QuestionDTO;
import com.xiaoxie.community.model.Question;
import com.xiaoxie.community.model.User;
import com.xiaoxie.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,Model model){

        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "title",required = false)String title,
                            @RequestParam(name = "description",required = false)String description,
                            @RequestParam(name = "tag",required = false)String tag,
                            @RequestParam(name = "id",required = false)Long id,
                            HttpServletRequest request,
                            Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());



        if (title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description == ""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }


        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNoneBlank(invalid)) {
            model.addAttribute("error","标签错误："+ invalid);
            return "publish";
        }

        Question qusetion = new Question();

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        qusetion.setTitle(title);
        qusetion.setDescription(description);
        qusetion.setTag(tag);
        qusetion.setCreator(user.getId());
        qusetion.setGmtCreate(System.currentTimeMillis());
        qusetion.setGmtModified(qusetion.getGmtCreate());
        qusetion.setId(id);
        questionService.createOrUpdate(qusetion);
        return "redirect:/";
    }
}
