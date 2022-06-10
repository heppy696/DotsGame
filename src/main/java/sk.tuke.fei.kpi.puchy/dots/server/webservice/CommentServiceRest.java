package sk.tuke.fei.kpi.puchy.dots.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.fei.kpi.puchy.dots.entity.Comment;
import sk.tuke.fei.kpi.puchy.dots.entity.Rating;
import sk.tuke.fei.kpi.puchy.dots.entity.Score;
import sk.tuke.fei.kpi.puchy.dots.service.Comment.CommentService;
import sk.tuke.fei.kpi.puchy.dots.service.Rating.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game){
        return commentService.getComments(game);
    }

    @PostMapping
    public void addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
    }
}
