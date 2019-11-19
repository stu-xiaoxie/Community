package com.xiaoxie.community.service;

import com.xiaoxie.community.dto.PaginationDTO;
import com.xiaoxie.community.dto.QuestionDTO;
import com.xiaoxie.community.mapper.QuestionMapper;
import com.xiaoxie.community.mapper.UserMapper;
import com.xiaoxie.community.model.Question;
import com.xiaoxie.community.model.QuestionExample;
import com.xiaoxie.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());


        if (totalCount % size ==0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1;
        }

        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        Integer offset = size*(page - 1);
        if(offset < 0) {
            offset = 0;
        }
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOS);
        return paginationDTO;
    }

    public PaginationDTO list(long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);

        if (totalCount % size == 0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1;
        }

        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);


        Integer offset = size * (page - 1);
        if(offset < 0) {
            offset = 0;
        }

        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOS);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question qusetion) {
        if (qusetion.getId() == null){
            //创建
            qusetion.setGmtCreate(System.currentTimeMillis());
            qusetion.setGmtModified(qusetion.getGmtCreate());
            questionMapper.insert(qusetion);
        }else {
            //更新
            qusetion.setGmtModified(qusetion.getGmtCreate());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(qusetion.getTitle());
            updateQuestion.setTag(qusetion.getTag());
            updateQuestion.setDescription(qusetion.getDescription());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(qusetion.getId());
            questionMapper.updateByExampleSelective(updateQuestion, example);
        }
    }
}
