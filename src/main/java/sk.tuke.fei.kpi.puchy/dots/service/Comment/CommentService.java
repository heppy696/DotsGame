package sk.tuke.fei.kpi.puchy.dots.service.Comment;

import sk.tuke.fei.kpi.puchy.dots.entity.Comment;
import sk.tuke.fei.kpi.puchy.dots.service.Comment.CommentException;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment) throws CommentException;
    List<Comment> getComments(String game) throws CommentException;
    void reset() throws CommentException;
}
