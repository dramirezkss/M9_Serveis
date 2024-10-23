import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    // Constants definides a nivell de classe
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";
    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "Messiteextraño";

    // Mètode per xifrar un missatge amb AES-CBC
    public static byte[] xifraAES(String msg, String clau) throws Exception {
        // Obtenir els bytes de l'String
        byte[] msgBytes = msg.getBytes("UTF-8");

        // Generar IV (Vector d'Inicialització)
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Generar el hash de la clau utilitzant SHA-256
        MessageDigest shaDigest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] clauHash = shaDigest.digest(clau.getBytes("UTF-8"));

        // Crear una clau secreta a partir del hash
        SecretKey secretKey = new SecretKeySpec(clauHash, ALGORISME_XIFRAT);

        // Crear l'objecte Cipher en mode de xifrat
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

        // Xifrar el missatge
        byte[] msgXifrat = cipher.doFinal(msgBytes);

        // Combinar l'IV amb el missatge xifrat
        byte[] ivIMsgXifrat = new byte[MIDA_IV + msgXifrat.length];
        System.arraycopy(iv, 0, ivIMsgXifrat, 0, MIDA_IV);
        System.arraycopy(msgXifrat, 0, ivIMsgXifrat, MIDA_IV, msgXifrat.length);

        return ivIMsgXifrat; // Retornar IV + missatge xifrat
    }

    // Mètode per desxifrar el missatge xifrat amb AES-CBC
    public static String desxifraAES(byte[] bIvIMsgXifrat, String clau) throws Exception {
        // Extreure l'IV de les dades xifrades
        byte[] iv = new byte[MIDA_IV];
        System.arraycopy(bIvIMsgXifrat, 0, iv, 0, MIDA_IV);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extreure la part xifrada del missatge
        byte[] partXifrada = new byte[bIvIMsgXifrat.length - MIDA_IV];
        System.arraycopy(bIvIMsgXifrat, MIDA_IV, partXifrada, 0, partXifrada.length);

        // Generar el hash de la clau utilitzant SHA-256
        MessageDigest shaDigest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] clauHash = shaDigest.digest(clau.getBytes("UTF-8"));

        // Crear una clau secreta a partir del hash
        SecretKey secretKey = new SecretKeySpec(clauHash, ALGORISME_XIFRAT);

        // Crear l'objecte Cipher en mode de desxifrat
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        // Desxifrar el missatge
        byte[] msgDesxifrat = cipher.doFinal(partXifrada);

        // Retornar el missatge desxifrat com a String
        return new String(msgDesxifrat, "UTF-8");
    }

    // Mètode principal per executar la prova
    public static void main(String[] args) {
        String msgs[] = {
            "Lorem ipsum dicet",
            "Hola Andrés cómo está tu cuñado",
            "Àgora ïlla Ôtto"
        };

        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat = "";

            try {
                // Xifrar el missatge
                bXifrats = xifraAES(msg, CLAU);

                // Desxifrar el missatge
                desxifrat = desxifraAES(bXifrats, CLAU);

            } catch (Exception e) {
                System.err.println("Error de xifrat: " + e.getLocalizedMessage());
            }

            // Mostrar els resultats
            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats)); // Això només és per a visualitzar
            System.out.println("DEC: " + desxifrat);
        }
    }
}