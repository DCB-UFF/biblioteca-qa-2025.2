package biblioteca.livros;

import biblioteca.pessoas.Cliente;

/* @author Luam */

public class Emprestimo {
    private Cliente cliente;
    private Livro livro;
    private int idEmprestimo;
    private String dataEmprestimo;
    private String dataDevolucao;

    public Emprestimo(Livro livro, String dataEmprestimo, String dataDevolucao) {
        /*this.idCliente = idCliente;*/
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }
    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    // checar devolução
    
    
    
}
