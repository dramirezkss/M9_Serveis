import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    private int npass = 0;
    
    public String getSHA512AmbSalt(String pw, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(pw.getBytes());
            HexFormat hex = HexFormat.of();
            return hex.formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public String getPBKDF2AmbSalt(String pw, String salt) {
        try {
            int iterations = 10000;
            char[] chars = pw.toCharArray();
            byte[] saltBytes = salt.getBytes();
            PBEKeySpec spec = new PBEKeySpec(chars, saltBytes, iterations, 512);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            HexFormat hex = HexFormat.of();
            return hex.formatHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    
    public String forcaBruta(String alg, String hash, String salt) {
        char[] charset = "abcdefABCDEF1234567890!".toCharArray();
        this.npass = 0;
        char[] attempt = new char[6];
        return forcaBrutaRec(alg, hash, salt, charset, attempt, 0);
    }
    
    private String forcaBrutaRec(String alg, String hash, String salt, char[] charset, char[] attempt, int pos) {
        if (pos == attempt.length) {
            npass++;
            String attemptStr = new String(attempt);
            String attemptHash = alg.equals("SHA-512") ? getSHA512AmbSalt(attemptStr, salt) : getPBKDF2AmbSalt(attemptStr, salt);
            if (attemptHash.equals(hash)) return attemptStr;
            return null;
        }
        for (char c : charset) {
            attempt[pos] = c;
            String result = forcaBrutaRec(alg, hash, salt, charset, attempt, pos + 1);
            if (result != null) return result;
        }
        return null;
    }
    
    public String getInterval(long t1, long t2) {
        long millis = t2 - t1;
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / (1000 * 60)) % 60;
        long hours = (millis / (1000 * 60 * 60)) % 24;
        long days = millis / (1000 * 60 * 60 * 24);
        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis", days, hours, minutes, seconds, millis % 1000);
    }
    
    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt), h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = {"SHA-512", "PBKDF2"};
        for(int i=0; i< aHashes.length; i++){
            System.out.printf("===========================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n",aHashes[i]);
            System.out.printf("---------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("---------------------------\n\n");
        }
    }
}