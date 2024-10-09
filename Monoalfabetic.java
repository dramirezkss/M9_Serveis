import java.util.*;

public class Monoalfabetic {
    private static final String ALFABET = "aàáäbcçdeèéëfghiïíjklmnñoòóöpqrstuüúùvwxyzAÀÁÄBCÇDEÈÉËFGHIÏÍJKLMNÑOÒÓÖPQRSTUÜÚÙVWXYZ";

    public static char[] permutaAlfabet() {
        char[] permutat = ALFABET.toCharArray();
        List<Character> lista = new ArrayList<>();

        for (char caracter : permutat) {
            lista.add(caracter);
        }

        Collections.shuffle(lista);
        for (int i = 0; i < permutat.length; i++) {
            permutat[i] = lista.get(i);
        }

        return permutat;
    }

    public static String xifraMonoAlfa(String cadena, char[] permutat) {
        StringBuilder resultado = new StringBuilder();

        for (char caracter : cadena.toCharArray()) {
            int indice = ALFABET.indexOf(caracter);
            if (indice != -1) {
                resultado.append(permutat[indice]);
            } else {
                resultado.append(caracter);
            }
        }

        return resultado.toString();
    }

    public static String desxifraMonoAlfa(String cadena, char[] permutat) {
        StringBuilder resultado = new StringBuilder();

        for (char caracter : cadena.toCharArray()) {
            int indice = new String(permutat).indexOf(caracter);
            if (indice != -1) {
                resultado.append(ALFABET.charAt(indice));
            } else {
                resultado.append(caracter);
            }
        }

        return resultado.toString();
    }

    public static void main(String[] args) {
        char[] permutat = permutaAlfabet();

        System.out.println("Permutació de l'alfabet: " + new String(permutat));

        String original = "Hola com estàs? L'altra dia m'han dit que sóc un capçalera, t'ho creus?";
        System.out.println("Original: " + original);

        String xifrat = xifraMonoAlfa(original, permutat);
        System.out.println("Xifrat: " + xifrat);

        String desxifrat = desxifraMonoAlfa(xifrat, permutat);
        System.out.println("Desxifrat: " + desxifrat);
    }
}