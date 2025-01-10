package pt.ipleiria.estg.dei.ei.dae.project.security;

import jakarta.enterprise.context.ApplicationScoped;
import pt.ipleiria.estg.dei.ei.dae.project.entities.User;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@ApplicationScoped
public class Hasher {

    //Metodo que recebe uma string e devolve a hash dessa string
    //Este metodo é usado para encriptar a password dos utilizadores
    public String hash(String content) {
        try {
            ByteBuffer passwdBuffer = Charset.defaultCharset().encode(CharBuffer.wrap(content));
            byte[] passwdBytes = passwdBuffer.array();
            MessageDigest mdEnc = MessageDigest.getInstance("SHA-256");
            mdEnc.update(passwdBytes, 0, content.toCharArray().length);
            char[] encoded = new BigInteger(1, mdEnc.digest()).toString(16).toCharArray();
            return new String(encoded);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).severe(ex.getMessage());
            return "";
        }
    }
}

