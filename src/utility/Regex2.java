package utility;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Regex2 {
	
    public static void main(String[] args) {
    		 
        String s = "<p><img src=\"38220.png\" alt=\"test\" title=\"test\" /> <img src=\"32222.png\" alt=\"test\" title=\"test\" /></p>";
        Pattern p = Pattern.compile("<img[^>]*src=[\\\"']([^\\\"^']*)");
        Matcher m = p.matcher(s);
        while (m.find()) {
            String src = m.group();
            System.out.println("------"+src);
            int startIndex = src.indexOf("src=") + 5;
            String srcTag = src.substring(startIndex, src.length());
            System.out.println(srcTag);
        }
    }
}