package ip;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IPMatcher {
    public static void main(String[] args)  {
        Pattern pattern = Pattern.compile("^[a-zA-Z]*$"); //영문자만
        String val = "abcdef"; //대상문자열

        Matcher matcher = pattern.matcher(val);
    }
}
