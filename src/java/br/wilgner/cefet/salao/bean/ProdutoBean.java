package br.wilgner.cefet.salao.bean;

import br.wilgner.cefet.salao.dao.ProdutoDAO;
import br.wilgner.cefet.salao.entidade.Produto;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



@ManagedBean
@SessionScoped
public class ProdutoBean extends CrudBean<Produto, ProdutoDAO> {

    private ProdutoDAO produtoDAO;
    
    @Override
    public ProdutoDAO getDao() {
        if(produtoDAO == null){
            produtoDAO = new ProdutoDAO();
        }
        return produtoDAO;
    }

    @Override
    public Produto criarNovaEntidade() {
        return new Produto();
    }

}
