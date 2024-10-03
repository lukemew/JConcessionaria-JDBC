package model.dao.entities;

public class Venda {
    private Integer idVenda;
    private Carro carroId;
    private Cliente clienteId;

    public Venda(){
    }

    public Venda(Integer idVenda, Carro carroId, Cliente clienteId) {
        this.idVenda = idVenda;
        this.carroId = carroId;
        this.clienteId = clienteId;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Carro getCarroId() {
        return carroId;
    }

    public void setCarroId(Carro carroId) {
        this.carroId = carroId;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }
}
