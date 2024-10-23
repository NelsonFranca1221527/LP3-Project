package com.example.oporto_olympics.Controllers.DAO;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.Equipa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtletaDAOImp implements DAO<Atleta>{

    private Connection conexao;

    private ConnectionBD database;

    public AtletaDAOImp(Connection conexao) {
        this.conexao=conexao;
    }

    @Override
    public List<Atleta> getAll() {
        List<Atleta> lst=new ArrayList<Atleta>();
        try {

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from atletas");
            while (rs.next()) {
                lst.add(new Atleta(rs.getInt("user_id") ,rs.getString("nome"),rs.getString("pais_sigla"),rs.getString("genero"),rs.getInt("altura_cm"),rs.getInt("peso_kg"),rs.getDate("data_nascimento"),null));
            }
            return lst;

        }catch (SQLException ex){
            throw new RuntimeException("Erro em mostrar a lista de atletas: " + ex.getMessage());
        }
    }

    @Override
    public void save(Atleta atleta) {

        Optional<Atleta> AtletaExiste = get(atleta.getNome());

        if(AtletaExiste.isPresent() && atleta.getPais().equals(AtletaExiste.get().getPais()) && atleta.getGenero().equals(AtletaExiste.get().getGenero())){
            return;
        }

        try {
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO atletas (nome , data_nascimento, genero, altura_cm, peso_kg, pais_sigla) VALUES(?,?,?,?,?,?)");

            ps.setString(1, atleta.getNome());
            ps.setDate(2,  new java.sql.Date(atleta.getDataNascimento().getTime()));
            ps.setString(3, atleta.getGenero());
            ps.setInt(4, atleta.getAltura());
            ps.setInt(5,atleta.getPeso());
            ps.setString(5, atleta.getPais());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir o atleta: " + ex.getMessage());
        }
    }

    @Override
    public void update(Atleta atleta) {

    }

    @Override
    public void delete(Atleta atleta) {

    }

    @Override
    public Optional<Atleta> get(String nome) {
        try {

            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM atletas WHERE nome = ?");
            ps.setString(1, nome);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(new Atleta(rs.getInt("user_id") ,rs.getString("nome"),rs.getString("pais_sigla"),rs.getString("genero"),rs.getInt("altura_cm"),rs.getInt("peso_kg"),rs.getDate("data_nascimento"),null));
            }
        }catch (SQLException ex){
            throw new RuntimeException("Erro em mostrar o atleta: " + ex.getMessage());
        }

        return Optional.empty();
    }
}
