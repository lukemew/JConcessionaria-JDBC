package model.dao.impl;

import db.DB;
import model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeiculoDaoJDBC {

    private Connection conn;

    public VeiculoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        PreparedStatement st = null;
        String sql = "INSERT INTO veiculos (ano, modelo, cor) VALUES (?, ?, ?)";

        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, veiculo.getAno());
            st.setString(2, veiculo.getModelo());
            st.setString(3, veiculo.getCor());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Veículo adicionado com sucesso! Linhas geradas: " + rowsAffected);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(st);
        }
    }

    public void deletarVeiculoPelaCor(String modelo) {
        PreparedStatement st = null;
        String sql = "DELETE FROM veiculos WHERE modelo = ?";

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, modelo);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Veículo de cor " + modelo + " deletado com sucesso.");
            } else {
                System.out.println("Nenhum veículo encontrado com a cor: " + modelo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(st);
        }
    }

    public Veiculo encontrarVeiculoPorModelo(String modelo) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM veiculos WHERE modelo = ?";

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, modelo);

            rs = st.executeQuery();

            if (rs.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setAno(rs.getInt("ano"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setCor(rs.getString("cor"));
                return veiculo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
        return null;
    }
}
