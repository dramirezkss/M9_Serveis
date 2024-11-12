package iticbcn.xifratge;

import javax.crypto.Cipher;
import java.security.*;

public class ClauPublica {

    // Método para generar un par de claves RSA
    public KeyPair generaParellClausRSA() throws Exception {
        // Crear un generador de claves
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        // Inicializar el generador con un tamaño de clave de 2048 bits
        keyGen.initialize(2048);
        // Generar el par de claves
        return keyGen.generateKeyPair();
    }

    // Método para cifrar un mensaje con una clave pública
    public byte[] xifraRSA(String msg, PublicKey clauPublica) throws Exception {
        // Obtener una instancia de Cipher para RSA
        Cipher cipher = Cipher.getInstance("RSA");
        // Configurar el cifrado en modo ENCRYPT_MODE con la clave pública
        cipher.init(Cipher.ENCRYPT_MODE, clauPublica);
        // Cifrar el mensaje (convertido a bytes)
        return cipher.doFinal(msg.getBytes());
    }

    // Método para descifrar un mensaje con una clave privada
    public String desxifraRSA(byte[] msgXifrat, PrivateKey clauPrivada) throws Exception {
        // Obtener una instancia de Cipher para RSA
        Cipher cipher = Cipher.getInstance("RSA");
        // Configurar el cifrado en modo DECRYPT_MODE con la clave privada
        cipher.init(Cipher.DECRYPT_MODE, clauPrivada);
        // Descifrar el mensaje y convertirlo a String
        return new String(cipher.doFinal(msgXifrat));
    }
}