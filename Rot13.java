import java.util.Scanner;

public class Rot13 {
    private static final char[] minus = {'a','b', 'c', 'd','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static final char[] mayus = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    public xifraRot13(String paraula) {

        for (int i = 0; i < minus.length(); i++) {
        }

    }
    public static void main(String[] args) {
        
        Scanner entrada = new Scanner(System.in);
        System.out.println("Escribe una letra en minusculas");
        String paraula = entrada.nextLine();
        System.out.println(paraula);
        xifraRot13("aeiou");
    }
}