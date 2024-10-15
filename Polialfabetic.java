import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Polialfabetic {
    private StringBuilder alfabetOriginal;
    private StringBuilder alfabet;

    public Polialfabetic() {
        alfabetOriginal = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) {
            alfabetOriginal.append(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            alfabetOriginal.append(c);
        }
    }

    // Permuta el alfabeto en base a una clave
    public void permutaAlfabet(long clau) {
        List<Character> alfabetLower = new ArrayList<>();
        List<Character> alfabetUpper = new ArrayList<>();

        for (char c = 'a'; c <= 'z'; c++) {
            alfabetLower.add(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            alfabetUpper.add(c);
        }

        Collections.shuffle(alfabetLower, new java.util.Random(clau));
        Collections.shuffle(alfabetUpper, new java.util.Random(clau));

        alfabet = new StringBuilder();
        for (char c : alfabetLower) {
            alfabet.append(c);
        }
        for (char c : alfabetUpper) {
            alfabet.append(c);
        }
    }

    // Cifra un mensaje usando el alfabeto permutado
    public String xifraPoliAlfa(String msg, long clau) {
        permutaAlfabet(clau);
        StringBuilder xifrat = new StringBuilder();

        for (int i = 0; i < msg.length(); i++) {
            char lletra = msg.charAt(i);
            if (Character.isLetter(lletra)) {
                int index = alfabetOriginal.indexOf(String.valueOf(Character.toLowerCase(lletra)));
                if (index != -1) {
                    char lletraXifrada = alfabet.charAt(index);
                    if (Character.isUpperCase(lletra)) {
                        lletraXifrada = Character.toUpperCase(lletraXifrada);
                    }
                    xifrat.append(lletraXifrada);
                } else {
                    xifrat.append(lletra);
                }
            } else {
                xifrat.append(lletra);
            }
        }

        return xifrat.toString();
    }

    // Descifra un mensaje cifrado usando el alfabeto permutado
    public String desxifraPoliAlfa(String msgXifrat, long clau) {
        permutaAlfabet(clau);
        StringBuilder desxifrat = new StringBuilder();

        for (int i = 0; i < msgXifrat.length(); i++) {
            char lletraXifrada = msgXifrat.charAt(i);
            if (Character.isLetter(lletraXifrada)) {
                int index = alfabet.indexOf(String.valueOf(Character.toLowerCase(lletraXifrada)));
                if (index != -1) {
                    char lletraDesxifrada = alfabetOriginal.charAt(index);
                    if (Character.isUpperCase(lletraXifrada)) {
                        lletraDesxifrada = Character.toUpperCase(lletraDesxifrada);
                    }
                    desxifrat.append(lletraDesxifrada);
                } else {
                    desxifrat.append(lletraXifrada);
                }
            } else {
                desxifrat.append(lletraXifrada);
            }
        }

        return desxifrat.toString();
    }

    private static long clauSecreta;

    public static void initRandom(long clau) {
        clauSecreta = clau;
    }

    public static void main(String[] args) {
        String msgs[] = {
            "Test 01 àrbritre, coixí, Perímetre",
            "Test 02 Taüll, DÍA, año",
            "Test 03 Peça, Òrrius, Bòvila"
        };
        String msgsXifrats[] = new String[msgs.length];
        long clau = 123456;
        System.out.println("Xifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clau);
            msgsXifrats[i] = new Polialfabetic().xifraPoliAlfa(msgs[i], clau);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }
        System.out.println("Desxifratge: \n-----------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clau);
            String msg = new Polialfabetic().desxifraPoliAlfa(msgsXifrats[i], clau);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }
}