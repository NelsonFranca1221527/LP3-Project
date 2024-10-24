package com.example.oporto_olympics.Controllers.DAO;

import com.example.oporto_olympics.Controllers.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.Equipa;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        List<Atleta> lst = new ArrayList<Atleta>();
        try {

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from atletas");
            if (!rs.isBeforeFirst()) {
               return null;
            } else {
                while (rs.next()) {
                    lst.add(new Atleta(rs.getInt("user_id"), rs.getString("nome"), rs.getString("pais_sigla"), rs.getString("genero"), rs.getInt("altura_cm"), rs.getInt("peso_kg"), rs.getDate("data_nascimento"), null));
                }
            }

            return lst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public void save(Atleta atleta) {

        Optional<Atleta> AtletaExiste = get(atleta.getNome());

        if(AtletaExiste.isPresent() && atleta.getPais().equals(AtletaExiste.get().getPais()) && atleta.getGenero().equals(AtletaExiste.get().getGenero())){
            return;
        }

        try {

            int numMecanografico = 0;

            PreparedStatement ps1 = conexao.prepareStatement("SELECT Max(num_mecanografico) as \"MaiorNumMecanografico\" FROM users");
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                int maiorNumMecanografico = rs1.getInt("MaiorNumMecanografico");

                if (maiorNumMecanografico > 0) {
                    numMecanografico = maiorNumMecanografico + 1;
                }else{
                    numMecanografico = 1000000;
                }
            }

            PreparedStatement ps2 = conexao.prepareStatement("INSERT INTO users (num_mecanografico , User_password, role, criado_em) VALUES(?,?,?,?)");
            ps2.setInt(1, numMecanografico);
            ps2.setString(2, String.valueOf(numMecanografico));
            ps2.setString(3,"Atleta");
            ps2.setDate(4, Date.valueOf(LocalDate.now()));
            ps2.executeUpdate();
            ps2.close();


            atleta.setId(numMecanografico);
            update(atleta);

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir o atleta: " + ex.getMessage());
        }
    }

    @Override
    public void update(Atleta atleta) {
        try {
            PreparedStatement ps = conexao.prepareStatement("UPDATE atletas SET atletas.nome = ?, atletas.data_nascimento = ?, atletas.genero = ?, atletas.altura_cm = ?, atletas.peso_kg = ?, atletas.pais_sigla = ? WHERE user_id = (SELECT id FROM users WHERE num_mecanografico = ?)");
            ps.setString(1, atleta.getNome());
            ps.setDate(2,  new Date(atleta.getDataNascimento().getTime()));
            ps.setString(3, atleta.getGenero());
            ps.setInt(4, atleta.getAltura());
            ps.setInt(5,atleta.getPeso());
            ps.setString(6, atleta.getPais());
            ps.setInt(7, atleta.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar o atleta: " + ex.getMessage());
        }
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
