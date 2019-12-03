package com.xiaoxie.community.mapper;

import com.xiaoxie.community.model.Comment;
import com.xiaoxie.community.model.CommentExample;
import com.xiaoxie.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}