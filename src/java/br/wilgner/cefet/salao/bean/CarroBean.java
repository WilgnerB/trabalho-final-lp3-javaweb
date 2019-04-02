package br.wilgner.cefet.salao.bean;

import br.wilgner.cefet.salao.dao.CarroDAO;
import br.wilgner.cefet.salao.entidade.Carro;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CarroBean extends CrudBean<Carro, CarroDAO> {

    private CarroDAO carroDAO;
    
    @Override
    public CarroDAO getDao() {
        if(carroDAO == null){
            carroDAO = new CarroDAO();
        }
        return carroDAO;
    }

    @Override
    public Carro criarNovaEntidade() {
        return new Carro();
    }

}
