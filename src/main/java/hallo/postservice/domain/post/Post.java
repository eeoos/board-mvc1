package hallo.postservice.domain.post;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Post {
    /**
     * id
     * 제목
     * 작성자
     * 작성 내용
     * 작성일자
     */
    private Long id;
    private String title;
    private String writer;
    private String content;
    private LocalDate date;

    public Post() {
        this.date = LocalDate.now();
    }

    public Post(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.date = LocalDate.now();
    }
}
