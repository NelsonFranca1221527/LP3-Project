import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Equipas.InscricaoEquipaDAO;
import com.example.oporto_olympics.DAO.Equipas.InscricaonaEquipaDAOImp;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class InscricaoEquipaTest {

    @Test
    void testInscricaoBemSucedida() throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        InscricaoEquipaDAO dao = new InscricaonaEquipaDAOImp(conexao);

            int atleta_id = 1;
            int modalidadeId = 1;
            int equipaId = 1;

            String status = "Pendente";
            dao.inserirInscricao(status, modalidadeId, atleta_id, equipaId);

            boolean existePedidoPendente = dao.existePedidoPendente(atleta_id, equipaId);

            assertTrue(existePedidoPendente, "Inscrição deveria Existir");
    }

    @Test
    void testInscricaoAceite() throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        InscricaoEquipaDAO dao = new InscricaonaEquipaDAOImp(conexao);

            int atleta_id = 1;
            int equipaId = 1;

            boolean existePedidoAprovado = dao.existePedidoAprovado(atleta_id, equipaId);

            assertTrue(existePedidoAprovado, "Inscrição deveria Existir");
    }

    @Test
    void testInscricaoRejeitada() throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();
            InscricaoEquipaDAO dao = new InscricaonaEquipaDAOImp(conexao);

            int atleta_id = 1;
            int equipaId = 1;

            boolean existePedidoAprovado = dao.existePedidoAprovado(atleta_id, equipaId);

            assertFalse(existePedidoAprovado, "Inscrição deveria Existir");
    }
}
