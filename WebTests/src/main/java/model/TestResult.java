package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by Elizaveta on 21.05.2016.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResult {
        private  int id;
        private  int studentId;
        private int testId;
        private LocalDateTime test_date;
        private String answers;
        private  int mark;

}
