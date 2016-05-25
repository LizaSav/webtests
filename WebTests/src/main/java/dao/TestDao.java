package dao;

import model.Question;
import model.Subject;
import model.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * Created by Elizaveta on 20.05.2016.
 */
public class TestDao {
    public static Test getTestById(Connection con, String ID) {
        int id;
        try {
            id = Integer.parseInt(ID);
            if (id <= 0) return null;
        } catch (NumberFormatException e) {
            // логирование
            return null;
        }
        try (ResultSet rs = con.createStatement().executeQuery("SELECT *FROM test WHERE id=" + id)) {
            if (rs.next()) {
                String[] strings = rs.getString("questions").split("---");
                LinkedList<Question> questions = new LinkedList<>();
                for (String string : strings) {
                    String[] preAnswers = string.split("~~");
                    String question = preAnswers[0];
                    String[] answers = new String[preAnswers.length - 2];
                    System.arraycopy(preAnswers, 1, answers, 0, answers.length);
                    String[] preCorrectAnswers = preAnswers[preAnswers.length - 1].split(",");
                    int[] correctAnswers = new int[preCorrectAnswers.length];
                    for (int i = 0; i < correctAnswers.length; i++) {
                        correctAnswers[i] = Integer.parseInt(preCorrectAnswers[i]);
                    }
                    questions.add(new Question(question, answers, correctAnswers));
                }
                LocalDateTime ld = rs.getTimestamp("latest_update").toLocalDateTime(); //  переводит относительновремени системы
                Subject subj = Subject.valueOf(rs.getString("subject"));
                return new Test(id, rs.getInt("creator_id"), rs.getString("title"), ld,
                        questions, rs.getByte("deleted"), subj);
            } else return null;
        } catch (SQLException e1) {
            //логирование ошибка подключения
            return null;
        }

    }

    public static void addTest(Connection con, model.Test test) {
        Timestamp ts = Timestamp.valueOf(test.getLatest_update());
        StringBuffer questions = new StringBuffer();
        for (Question q : test.getQuestions()) {
            questions.append(q.getQuestion() + "~~");
            for (String answer : q.getAnswers()) {
                questions.append(answer + "~~");
            }
            for (int i : q.getCorrectAnswers()) {
                questions.append(i + ",");
            }
            questions.deleteCharAt(questions.length() - 1);
            questions.append("---");
        }
        questions.delete(questions.length() - 3, questions.length());
        try (Statement st = con.createStatement()) {
            st.executeUpdate("INSERT INTO test (creator_id, title, latest_update, questions, subject) VALUES (" + test.getCreatorId() + ", '" + test.getTitle() +
                    "', '" + ts.toString() + "', '" + questions + "', '" + test.getSubject().toString() + "')");

        } catch (SQLException e) {
            //логирование ошибка подключения
        }
    }

    public static void editTitle(Connection con, Test test) {
        try (Statement st = con.createStatement()) {
            st.executeUpdate("UPDATE test SET title='" + test.getTitle() + "' WHERE id=" + test.getId());
        } catch (SQLException e) {
            //логирование ошибка подключения
        }
    }

    public static void addQuestion(Connection con, int id, Question q) {
        StringBuffer question = new StringBuffer("---");
        question.append(q.getQuestion() + "~~");
        for (String answer : q.getAnswers()) {
            question.append(answer + "~~");
        }
        for (int i : q.getCorrectAnswers()) {
            question.append(i + ",");
        }
        question.deleteCharAt(question.length() - 1);
        String questions="";
        try (ResultSet rs = con.createStatement().executeQuery("SELECT questions FROM test WHERE id=" + id)) {
            if (rs.next()) {
                questions=rs.getString("questions");
            }
            try (Statement st = con.createStatement()) {
                st.executeUpdate("UPDATE test SET questions='" + questions+question + "',latest_update='"+Timestamp.valueOf(LocalDateTime.now()).toString()+"' WHERE id=" + id);
            } catch (SQLException e) {
                e.printStackTrace();
                //логирование ошибка подключения
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteQuestion(Connection con, int id, int questionNumber){
       String questions="";
        try (ResultSet rs = con.createStatement().executeQuery("SELECT questions FROM test WHERE id=" + id)) {
            if (rs.next()) {
                 questions=rs.getString("questions");
            }
            String [] question = questions.split("---");
            StringBuffer newQuestions=new StringBuffer();
            for (int i=0; i<question.length; i++){
                if (i!=questionNumber) newQuestions.append(question[i]+"---");
            }
            newQuestions.delete(newQuestions.length() - 3, newQuestions.length());
            try (Statement st = con.createStatement()) {
                st.executeUpdate("UPDATE test SET questions='" + newQuestions.toString() + "',latest_update='"+Timestamp.valueOf(LocalDateTime.now()).toString()+"' WHERE id=" + id);
            } catch (SQLException e) {
                e.printStackTrace();
                //логирование ошибка подключения
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  static void editQuestion(Connection con, int id, int questionNumber, Question q, boolean changed){
        String questions="";
        Timestamp ts=null;
        try (ResultSet rs = con.createStatement().executeQuery("SELECT questions, latest_update FROM test WHERE id=" + id)) {
            if (rs.next()) {
                questions=rs.getString("questions");
                ts=rs.getTimestamp("latest_update");
                if(changed) ts=Timestamp.valueOf(LocalDateTime.now());
            }

            String [] question = questions.split("---");
            StringBuffer newQuestions=new StringBuffer();
            for (int i=0; i<question.length; i++){
                if (i!=questionNumber) newQuestions.append(question[i]+"---");
                else {
                    newQuestions.append(q.getQuestion()+"~~");
                    for (String answer: q.getAnswers()) {
                        newQuestions.append(answer+"~~");
                    }
                    for (int cAnswer: q.getCorrectAnswers()) {
                        newQuestions.append(cAnswer+",");
                    }
                    newQuestions.deleteCharAt(newQuestions.length() - 1);
                    newQuestions.append("---");
                }
            }
            newQuestions.delete(newQuestions.length() - 3, newQuestions.length());
            try (Statement st = con.createStatement()) {
                st.executeUpdate("UPDATE test SET questions='" + newQuestions.toString() + "',latest_update='"+ts.toString()+"' WHERE id=" + id);
            } catch (SQLException e) {
                e.printStackTrace();
                //логирование ошибка подключения
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTest(Connection con, int id){
        try (Statement st = con.createStatement()) {
            st.executeUpdate("DELETE FROM test_result WHERE test_id="+id);
            st.executeUpdate("DELETE FROM test WHERE id="+id);
        } catch (SQLException e) {
            e.printStackTrace();
            //логирование ошибка подключения
        }
    }
}