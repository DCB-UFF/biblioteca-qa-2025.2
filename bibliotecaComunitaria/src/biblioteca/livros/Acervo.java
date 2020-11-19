package biblioteca.livros;
import java.util.ArrayList;
/* @author Luam */

public class Acervo {
    public static int idsLivros= 1;
    public static int idsEstantes = 1;
    protected ArrayList<Estante> estantes = new ArrayList();
    protected ArrayList<Emprestimo> emprestimos = new ArrayList();
    
    public void addEstante(Estante nova) {
        this.estantes.add(nova);
        
    }
    
    public void registarEmprestimo(Emprestimo novo) {
        this.emprestimos.add(novo);
        
    }
    
    public void registarDevolucao(Emprestimo e) {
        this.emprestimos.remove(e);
        
    }
    
    public Emprestimo buscarEmprestimo(int idEmprestimo){
        /// Criar exceção dps aqui
        for (Emprestimo e : emprestimos){
            if (Integer.compare(e.getIdEmprestimo(), idEmprestimo)==0){
                return e;
            }
        }
        return null;
    }
    
    
    public void imprimirAcervo(){
        for (Estante e : estantes){
            e.imprimirEstante();
        }
    }

}
