package com.example.oporto_olympics.Misc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * A classe {@link Encriptacao} fornece métodos para encriptação de dados utilizando o algoritmo SHA-256.
 * Esta classe é responsável por transformar um dado de entrada em um valor de hash seguro, utilizando
 * o método SHA-256 da biblioteca Java.
 */
public class Encriptacao {

    /**
     *
     * Encripta dados recebidos em hash ("SHA-256")
     *
     * @param dado Recebe o dado para ser encriptado
     * @return Retorna o dado encriptado
     * @throws NoSuchAlgorithmException se o algortimo para encriptação não está disponível.
     */

    public String StringtoHash(String dado) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hashdado = md.digest(dado.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashdado) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();

    }
}
