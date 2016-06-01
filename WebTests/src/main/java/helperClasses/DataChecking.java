package helperClasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Проверка введённых пользователем данных
 *
 */
public class DataChecking {
    public static boolean checkOneWord(String s){
        String regexp="[\\w|[А-Я]|[а-я]@|\\-|\\.|_]{0,64}+";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
    public static boolean checkQA(String s){
        String regexp1="---";
        String regexp2="~~";
        Pattern pattern1=Pattern.compile(regexp1);
        Pattern pattern2=Pattern.compile(regexp2);
        Matcher matcher=pattern1.matcher(s);
        if(matcher.find()) return false;
        matcher=pattern2.matcher(s);
        if(matcher.find())return false;
        return true;
    }
    public static boolean checkPassword(String s){
        String regexp="[\\S]+";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
