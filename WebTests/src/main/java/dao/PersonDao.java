package dao;

import model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by Elizaveta on 20.05.2016.
 */
public class PersonDao {
    public static Person getPersonByLogin(Connection con, String login){
       try (ResultSet rs = con.createStatement().executeQuery("SELECT *FROM person WHERE email='" + login+"'")){
           if(rs.next()) {
               return new Person(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
                       rs.getBoolean("permission"), rs.getString("email"), rs.getString("password"), rs.getString("address"));
           }
           else return null;
       } catch (SQLException e1) {
           //логирование ошибка подключения
           return null;
       }
    }

    public static Person addPerson(Connection con, String firstName, String lastName, String email, String password, String address){
        // нужна проверка на корректность данных
        int id =0;
        Person otherPerson =getPersonByLogin(con, email);
        if (otherPerson!=null)  id= otherPerson.getId();
        if (id == 0) {
            try (Statement st = con.createStatement()) {
                st.executeUpdate("INSERT INTO person (first_name,last_name,email, password, address) VALUES ('" + firstName + "', '" + lastName +
                        "', '"+email+"', '"+password+"', '"+address+"')");
                try (ResultSet rs = st.executeQuery("SELECT (id)FROM person WHERE email='" + email +"'")) {
                     rs.next();
                    return new Person(rs.getInt("id"),firstName, lastName,
                            false, email, password, address);
                }
            } catch (SQLException e) {
                //логирование ошибка подключения
                return null;
            }
        } else {
            return null;
        }
    }
}
