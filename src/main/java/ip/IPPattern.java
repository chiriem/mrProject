package ip;

import java.util.regex.Pattern;

public class IPPattern {
    public static void main(String[] args)  {

        String pattern = "^[0-9]*$"; //숫자만
        String val = "123456789"; //대상문자열

        boolean regex = Pattern.matches(pattern, val);
    }
}
