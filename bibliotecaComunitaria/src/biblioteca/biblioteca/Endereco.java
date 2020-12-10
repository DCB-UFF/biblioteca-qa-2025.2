package biblioteca.biblioteca;

/* @author victoria */

public class Endereco {
    private String rua;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
    
    public Endereco(String rua, String bairro, String cep, String cidade, String estado){
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return ("Rua: " + this.getRua() + " - " + "Bairro: " + this.getBairro() + " - " + "CEP: " + this.getCep() + " - " + "Cidade: " + this.getCidade() + " - " + "Estado: " + this.getEstado());
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }
}
