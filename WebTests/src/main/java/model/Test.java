package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    private  int id;
    private  int creatorId;
    private String title;
    private LocalDateTime latest_update;
    private LinkedList <Question> questions = new LinkedList<>();
    private  byte deleted;
    private Subject subject;
}
