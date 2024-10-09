import java.util.Scanner;

public class RotX {
    private final static char[] minus = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
        'á', 'à', 'é', 'è', 'í', 'ì', 'ó', 'ò', 'ú', 'ù', 'ü', 'ñ', 'ç'
    };
    private final static char[] mayus = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 
        'Á', 'À', 'É', 'È', 'Í', 'Ì', 'Ó', 'Ò', 'Ú', 'Ù', 'Ü', 'Ñ', 'Ç'
    };

    public static String xifraRotX(String paraula, int rot) {
        String result = "";

        for (int i = 0; i < paraula.length(); i++) {
            char charActual = paraula.charAt(i);

            if (Character.isLetter(charActual)) {
                boolean charEncontrado = false;
                
                for (int j = 0; j < minus.length; j++) {
                    if (charActual == minus[j]) {
                        int nuevaPosicion = (j + rot) % minus.length;
                        result += minus[nuevaPosicion];
                        charEncontrado = true;
                        break;
                    }
                }

                if (!charEncontrado) {
                    for (int j = 0; j < mayus.length; j++) {
                        if (charActual == mayus[j]) {
                            int nuevaPosicion = (j + rot) % mayus.length;
                            result += mayus[nuevaPosicion];
                            charEncontrado = true;
                            break;
                        }
                    }
                }
            } else {
                result += charActual;
            }
        }
        return result;
    }

    public static String desxifraRotX(String paraula, int rot) {
        return xifraRotX(paraula, minus.length - (rot % minus.length));
    }

    public static void fuerzaBruta(String paraulaXifrada) {
        for (int i = 1; i < minus.length; i++) {
            String result = desxifraRotX(paraulaXifrada, i);
            System.out.println("Desplazamiento " + i + ": " + result);
        }
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Escribe una palabra en minusculas o mayusculas");

        String paraula = entrada.nextLine();
        System.out.println("Introduce el desplazamiento?");

        int rot = entrada.nextInt();
        entrada.nextLine();
        
        String textoCifrado = xifraRotX(paraula, rot);
        String textoDescifrado = desxifraRotX(textoCifrado, rot);

        System.out.println("Texto original: " + paraula);
        System.out.println("Texto cifrado: " + textoCifrado);
        System.out.println("Texto descifrado: " + textoDescifrado);
        System.out.println("Descifrado por fuerza bruta");
        fuerzaBruta(textoCifrado);
    }
}
