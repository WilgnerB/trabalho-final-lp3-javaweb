/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.wilgner.cefet.salao.dao;

import br.wilgner.cefet.salao.dao.CrudDAO;
import br.wilgner.cefet.salao.entidade.Produto;
import br.wilgner.cefet.salao.util.FabricaConexao;
import br.wilgner.cefet.salao.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wilgn
 */
public class ProdutoDAO implements CrudDAO<Produto>{
    private final String INSERT_SQL = "INSERT INTO `tbproduto`(`NmProduto`,`VlAtual`,`QtdProduto`,`DtEntrada`,`Status`)"
                                        + "VALUES(?,?,?,now(),'A');";        
    private final String UPDATE_SQL = "UPDATE `tbproduto` "
                                    + "SET `NmProduto` = ?,`VlAtual` = ?,`QtdProduto` = ? "
                                    + "WHERE `CdProduto` = ?;";
    private final String DELETE_SQL = "DELETE FROM `tbproduto`WHERE `CdProduto`= ?;";
    private final String SELECT_SQL = "SELECT `CdProduto`,`NmProduto`,`VlAtual`,`QtdProduto`,`DtEntrada`,`Status`FROM `tbproduto` WHERE `Status` = 'A';";
    
    
    @Override
    public void salvar(Produto produto) throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if(produto.getCdProduto() == null){
                ps = conexao.prepareStatement(INSERT_SQL);
            } else {
                ps = conexao.prepareStatement(UPDATE_SQL);
                ps.setInt(4,produto.getCdProduto());
            }
            ps.setString(1,produto.getNmProduto());
            ps.setDouble(2, produto.getVlAtual());
            ps.setInt(3,produto.getQtdProduto());

            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar salvar!", ex);
        }
    }
    
    @Override
    public void deletar(Produto produto) throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps  = conexao.prepareStatement(DELETE_SQL);
            ps.setInt(1, produto.getCdProduto());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o produto!", ex);
        }
    }
    
    @Override
    public List<Produto> buscar() throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement(SELECT_SQL);
            ResultSet resultSet = ps.executeQuery();
            List<Produto> produtos = new ArrayList<>();
            while(resultSet.next()){
                Produto produto = getProdutoFromRs(resultSet);
                produtos.add(produto);
            }
            FabricaConexao.fecharConexao();
            return produtos;
            
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar os produtos!",ex);
        }
    }
        private Produto getProdutoFromRs(ResultSet rs) throws ErroSistema {
        Produto p = new Produto();
        try {
            p.setCdProduto(rs.getInt("CdProduto"));
            p.setNmProduto(rs.getString("NmProduto"));
            p.setVlAtual(rs.getDouble("VlAtual"));
            p.setQtdProduto(rs.getInt("QtdProduto"));
            p.setDtEntrada(rs.getDate("DtEntrada"));
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar os produtos!",ex);
        }
        return p;

    }
    
}
