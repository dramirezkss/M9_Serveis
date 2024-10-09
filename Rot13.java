import java.util.Scanner;

public class Rot13 {

    private final static char[] minus = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private final static char[] mayus = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static String xifraRot13(String paraula) {
        String result = "";

        for (int i = 0; i < paraula.length(); i++) {
            char charActual = paraula.charAt(i);

            for (int j = 0; j < minus.length; j++) {
                if (charActual == minus[j]) {
                    int nuevaPosicion;
                    if (j + 13 < minus.length) {
                        nuevaPosicion = j + 13;
                    } else {
                        nuevaPosicion = j - 13;
                    }
                    result = result + minus[nuevaPosicion];
                    break;
                }
            }

            for (int j = 0; j < mayus.length; j++) {
                if (charActual == mayus[j]) {
                    int nuevaPosicion;
                    if (j + 13 < mayus.length) {
                        nuevaPosicion = j + 13;
                    } else {
                        nuevaPosicion = j - 13;
                    }
                    result = result + mayus[nuevaPosicion];
                    break;
                }
            }
        }
        return result;
    }

    public static String desxifraRot13(String paraula) {
        return xifraRot13(paraula);
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Escribe una palabra en minusculas o mayusculas");
        String paraula = entrada.nextLine();
        String textoCifrado = xifraRot13(paraula);
        String textoDescifrado = paraula;

        System.out.println("Texto original: " + paraula);
        System.out.println("Texto cifrado: " + textoCifrado);
        System.out.println("Texto descifrado: " + textoDescifrado);
    }
}
