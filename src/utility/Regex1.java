package utility;

public class Regex1 {

	public static void main(String[] args) {
		
		String val = "<html><body background=\"red\">will I Am<img src=\"ddddddddddddddddd\"/>This is a test file.<hr></body>YYY</html>";
        String tagPattern = "<[^>]{1,}>{1}";
        System.out.println("Before:" + val);
        System.out.println("After :" + val.replaceAll(tagPattern, ""));
          
	}

}
