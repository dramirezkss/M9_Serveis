package iticbcn.xifratge;

import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
interface Xifrador {
    TextXifrat xifra(String msg, String clau) throws ClauNoSuportada;
    String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada;
}
class ClauNoSuportada extends Exception {
    public ClauNoSuportada(String message) {
        super(message);
    }
}
class TextXifrat {
    private byte[] data;
    public TextXifrat(byte[] data) { this.data = data; }
    public byte[] getBytes() { return data; }
    public String toString() { return new String(data); }
}
abstract class AlgorismeFactory {
    public abstract Xifrador creaXifrador();
}
class AlgorismeAES extends AlgorismeFactory {
    public Xifrador creaXifrador() { return new XifradorAES(); }
}
class AlgorismeMonoalfabetic extends AlgorismeFactory {
    public Xifrador creaXifrador() { return new XifradorMonoalfabetic(); }
}
class AlgorismePolialfabetic extends AlgorismeFactory {
    public Xifrador creaXifrador() { return new XifradorPolialfabetic(); }
}
class AlgorismeRotX extends AlgorismeFactory {
    public Xifrador creaXifrador() { return new XifradorRotX(); }
}
class XifradorAES implements Xifrador {
    private SecretKey key;
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            key = keyGen.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return new TextXifrat(cipher.doFinal(msg.getBytes()));
        } catch (Exception e) {
            System.out.println("Error en xifratge AES");
            System.exit(1);
            return null;
        }
    }
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(xifrat.getBytes()));
        } catch (Exception e) {
            System.out.println("Error en desxifratge AES");
            System.exit(1);
            return null;
        }
    }
}
class XifradorMonoalfabetic implements Xifrador {
    private static final String permutacio = "QWERTYUIOPLKJHGFDSAZXCVBNM";
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null) throw new ClauNoSuportada("Xifrat Monoalfabetic no accepta clau");
        StringBuilder result = new StringBuilder();
        for (char c : msg.toUpperCase().toCharArray()) {
            int index = c - 'A';
            if (index >= 0 && index < permutacio.length()) {
                result.append(permutacio.charAt(index));
            } else {
                result.append(c);
            }
        }
        return new TextXifrat(result.toString().getBytes());
    }
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if (clau != null) throw new ClauNoSuportada("Xifrat Monoalfabetic no accepta clau");
        String text = xifrat.toString();
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            int index = permutacio.indexOf(c);
            if (index >= 0) {
                result.append((char) ('A' + index));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
class XifradorPolialfabetic implements Xifrador {
    private Random random;
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            long seed = Long.parseLong(clau);
            random = new Random(seed);
            byte[] data = msg.getBytes();
            for (int i = 0; i < data.length; i++) {
                data[i] ^= random.nextInt(256);
            }
            return new TextXifrat(data);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per al xifratge Polialfabètic ha de ser numèrica");
        }
    }
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            long seed = Long.parseLong(clau);
            random = new Random(seed);
            byte[] data = xifrat.getBytes();
            for (int i = 0; i < data.length; i++) {
                data[i] ^= random.nextInt(256);
            }
            return new String(data);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per al desxifratge Polialfabètic ha de ser numèrica");
        }
    }
}
class XifradorRotX implements Xifrador {
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        int shift;
        try {
            shift = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per al xifratge RotX ha de ser un número enter");
        }
        StringBuilder result = new StringBuilder();
        for (char c : msg.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                result.append((char) ((c - base + shift) % 26 + base));
            } else {
                result.append(c);
            }
        }
        return new TextXifrat(result.toString().getBytes());
    }
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        int shift;
        try {
            shift = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per al desxifratge RotX ha de ser un número enter");
        }
        StringBuilder result = new StringBuilder();
        for (char c : xifrat.toString().toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                result.append((char) ((c - base - shift + 26) % 26 + base));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
public class TestXifratge {
    public static void main(String[] args) {
        AlgorismeFactory[] aFactory = { new AlgorismeAES(), new AlgorismeMonoalfabetic(), new AlgorismePolialfabetic(), new AlgorismeRotX() };
        String[] aNames = { "AES", "Monoalfabètic", "Polialfabètic", "RotX" };
        String[] msgs = { "Test 01: Àlgid, Ülrich, Vàlid", "Test 02: Caràcters especials ¡!¿?*-123[]{}@#" };
        String[][] claus = { { "Clau Secreta 01" }, { "ErrorClau", null }, { "ErrorClau2", "123456" }, { "-1", "13", "1000", "Errorclau" } };
        
        for (int i = 0; i < aFactory.length; i++) {
            AlgorismeFactory alg = aFactory[i];
            String nom = aNames[i];
            Xifrador xifrador = alg.creaXifrador();
            
            System.out.println("==========================");
            
            for (String msg : msgs) {
                for (String clau : claus[i]) {
                    System.out.println("msg: " + msg);
                    System.out.println("Algorisme: " + nom);
                    System.out.println("Clau: " + clau);
                    
                    TextXifrat tx = null;
                    try {
                        tx = xifrador.xifra(msg, clau);
                    } catch (ClauNoSuportada e) {
                        System.err.println(e.getLocalizedMessage());
                    }
                    System.out.println("TextXifrat: " + tx);
                    
                    String desxifrat = null;
                    try {
                        desxifrat = xifrador.desxifra(tx, clau);
                    } catch (ClauNoSuportada e) {
                        System.err.println(e.getLocalizedMessage());
                    }
                    System.out.println("Desxifrat: " + desxifrat);
                    System.out.println("------------------");
                }
            }
        }
    }
}