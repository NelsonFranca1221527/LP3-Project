package com.example.oporto_olympics;

import com.example.oporto_olympics.Misc.Encriptacao;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a classe Encriptacao.
 * Testa o método StringtoHash que converte uma string em hash utilizando o algoritmo SHA-256.
 */
public class CriptografiaTest {
    /**
     * Testa a encriptação de uma string e verifica se o hash gerado corresponde ao esperado.
     *
     * @throws NoSuchAlgorithmException Se o algoritmo SHA-256 não estiver disponível.
     */
    @Test
    public void testStringtoHash() throws NoSuchAlgorithmException {
        Encriptacao encriptacao = new Encriptacao();

        // Dado
        String dadoOriginal = "1000000";
        String dadoHashEsperado = "6cce36d9f8a9e151b100234af75cca89d55bcb94c153f51847debdf1f39cae45"; // Hash SHA-256 de "Teste123"

        // Ação: Encriptação da string
        String hashGerado = encriptacao.StringtoHash(dadoOriginal);

        // Verifica se o hash gerado corresponde ao esperado
        assertEquals(dadoHashEsperado, hashGerado, "O hash gerado deve ser igual ao esperado.");
    }

    /**
     * Testa a consistência do hash. Verifica se o mesmo dado sempre gera o mesmo hash.
     *
     * @throws NoSuchAlgorithmException Se o algoritmo SHA-256 não estiver disponível.
     */
    @Test
    public void testConsistenciaHash() throws NoSuchAlgorithmException {
        Encriptacao encriptacao = new Encriptacao();

        // Dado
        String dadoOriginal = "ConsistenciaTest";

        // Ação: Encriptação de um dado
        String hashGerado1 = encriptacao.StringtoHash(dadoOriginal);
        String hashGerado2 = encriptacao.StringtoHash(dadoOriginal);

        // Verifica se o hash gerado para o mesmo dado é sempre o mesmo
        assertEquals(hashGerado1, hashGerado2, "O hash gerado deve ser consistente para a mesma entrada.");
    }
}
