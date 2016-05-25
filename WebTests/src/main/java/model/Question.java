package model;

/**
 * Created by Elizaveta on 20.05.2016.
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String question;
    private String [] answers;
    private int [] correctAnswers; //нумерация с 0

}
