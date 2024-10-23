package com.example.oporto_olympics.Controllers.DAO;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Models.Equipa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipaDAOImp implements DAO<Equipa>{

    private Connection conexao;

    private ConnectionBD database;

    public EquipaDAOImp(Connection conexao) {
        this.conexao=conexao;
    }

    @Override
    public List<Equipa> getAll() {
        List<Equipa> lst=new ArrayList<Equipa>();
        try {

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("Select equipas.* From equipas, modalidades where equipas.modalidade_id = modalidades.id");
            while (rs.next()) {
                lst.add(new Equipa(rs.getInt("id"), rs.getString("nome"),rs.getString("pais_sigla"),rs.getString("genero"), rs.getString("des"),rs.getInt("modalidade_id"), rs.getInt("ano_fundacao"), null));
            }
            return lst;

        }catch (SQLException ex){
            throw new RuntimeException("Erro em mostrar a lista das equipas: " + ex.getMessage());
        }
    }

    @Override
    public void save(Equipa equipa) {

        Optional<Equipa> EquipaExiste = get(equipa.getNome());

        if(EquipaExiste.isPresent() && equipa.getPais().equals(EquipaExiste.get().getPais()) && equipa.getModalidadeID() == EquipaExiste.get().getModalidadeID()){
          return;
        }

        try {

            PreparedStatement ps = conexao.prepareStatement("INSERT INTO equipa (pais_sigla, ano_fundacao, modalidade_id, participacoes, medalhas, nome) VALUES(?,?,?,?,?,?)");

            ps.setString(1, equipa.getPais());
            ps.setInt(2, equipa.getAnoFundacao());
            ps.setInt(3, equipa.getModalidadeID());
            ps.setInt(4, 0);
            ps.setInt(5,0);
            ps.setString(6, equipa.getNome());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a equipa: " + ex.getMessage());
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(Equipa equipa) {

    }

    @Override
    public void delete(Equipa equipa) {

    }

    @Override
    public Optional<Equipa> get(String nome) {

        try {

            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM equipas WHERE nome = ?");
            ps.setString(1, nome);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Equipa(rs.getInt("id"), rs.getString("nome"),rs.getString("pais_sigla"),rs.getString("genero"),rs.getString("desporto"), rs.getInt("modalidade_id"), rs.getInt("ano_fundacao"), null));
            }
        }catch (SQLException ex){
            throw new RuntimeException("Erro em mostrar a equipa: " + ex.getMessage());
        }

        return Optional.empty();
    }
}
