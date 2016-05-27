package helperClasses;

import java.sql.Timestamp;
import java.util.Locale;
import java.util.ResourceBundle;


public class Function {
    /** Проверка актуальности пройденного теста
     *
     * @param pass дата сдачи теста
     * @param update дата последнего обновления теста создателем
     * @return был ли изменён тест с моменда его прохождения
     */
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
