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
    
    public void addLivro(Livro novo){
        for (Estante e : estantes){
            if (e.genero.equals(novo.genero)){
                e.addLivroNaEstante(novo);
            }
        }
        
    }
    
    public void buscarLivro (Livro livro){
        for (Estante e : estantes){
            if (e.genero.equals(livro.genero)){
                e.buscarLivroNaEstante(livro.titulo);
            }
        }
    }
    
    public Livro buscarLivroId (int idLivro){
        for (Estante e : estantes){
                Livro aux = e.buscarLivroNaEstanteId(idLivro);
                if (!aux.isEmpty()){
                    return aux;
            }
        }
        return null;
    }
    
    public void imprimirAcervo(){
        for (Estante e : estantes){
            e.imprimirEstante();
        }
    }
    
    
    
    
    public void registarEmprestimo(Emprestimo novo) {
        this.emprestimos.add(novo);
        
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
    
    public void registarDevolucao(int idEmprestimo) {
        // Checar atraso
        Emprestimo emp = this.buscarEmprestimo(idEmprestimo);
        // Buscar o livro no acervo e mudar para não-emprestado
        //Buscar Cliente no Admnistração e retirar livro do array dele 
        this.emprestimos.remove(emp);
        
    }
}
