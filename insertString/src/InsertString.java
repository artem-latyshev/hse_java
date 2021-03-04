public class InsertString {
    public static void main(String[] args) {
        String a = "aaabbb";
        String b = "zzz";

        String beginning = a.substring(0, a.length()/2);
        String ending = a.substring(a.length()/2);

        System.out.println(beginning + b + ending);
    }
}
