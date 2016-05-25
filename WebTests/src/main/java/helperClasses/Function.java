package helperClasses;

import java.sql.Timestamp;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Elizaveta on 23.05.2016.
 */
public class Function {
    public static boolean correctResult(Timestamp pass, Timestamp update){
        if (pass.before(update)) return false;
        else return  true;
    }
    private static ResourceBundle rb = ResourceBundle.getBundle("localizator/locale");
    public static String get(String key, Locale locale){
        rb=ResourceBundle.getBundle("localizator/locale",locale);
        return rb.getString(key);
    }
}
