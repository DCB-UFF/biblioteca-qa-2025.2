package biblioteca.livros;
import biblioteca.pessoas.Cliente;

/* @author Luam */

public class Emprestimo {
    private String CPF;
    private String ISNB;
    private String dataEmprestimo;
    private String dataDevolucao;

    public Emprestimo(String CPF, String ISNB, String dataEmprestimo, String dataDevolucao) {
        this.CPF= CPF;
        this.ISNB = ISNB;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }
    
    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getISNB() {
        return ISNB;
    }

    public void setISNB(String ISNB) {
        this.ISNB = ISNB;
    }

    
    

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    
     @Override
    public String toString() {
        return ("IdLivro: " + this.getISNB() + " - " +"CPF: " + this.getCPF() 
                + " - " +  "Data de Emprestimo: " + this.getDataEmprestimo() +
                " - " + "Data de Devolução: " + this.getDataDevolucao());
    }
    // checar devolução
    
    
    
}
