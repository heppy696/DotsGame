package sk.tuke.fei.kpi.puchy.dots.service.Comment;

public class CommentException extends RuntimeException {
    public CommentException(String message) {
        super(message);
    }

    public CommentException(String message, Throwable cause) {
        super(message, cause);
    }
}
