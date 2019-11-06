package com.xiaoxie.community.mapper;


import com.xiaoxie.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}