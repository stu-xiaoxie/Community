package com.xiaoxie.community.mapper;

import com.xiaoxie.community.model.Question;
import com.xiaoxie.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}