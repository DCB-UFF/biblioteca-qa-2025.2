package biblioteca.livros;

import java.util.ArrayList;
import biblioteca.pessoas.*;

/* @author Luam */

public class Acervo {
    public int idsLivros= 1;
    public int idsEstantes = 1;
    protected ArrayList<Estante> estantes = new ArrayList<>();
    protected ArrayList<Emprestimo> emprestimos = new ArrayList<>();
    protected ArrayList<Autor> autores = new ArrayList<>();

    public void setEstantes(ArrayList<Estante> estantes) {
        this.estantes = estantes;
    }

    public void setAutores(ArrayList<Autor> autores) {
        this.autores = autores;
    }
    
    
    
    public boolean buscarAutor(String nome){
        for (Autor autor : autores){
            if (autor.getNome().equals(nome))
                return true;
        }
        return false;
    }
    
  
    public void addEstante(Estante nova) {
        estantes.add(nova);
        idsEstantes++;
    }
    
    public void addLivro(Livro novo){
        if (autores.contains(novo.getAutor())){
            for (Autor autor : autores){
                autor.addLivro(novo);
            }
        }
        
        else{
            Autor autor = new Autor(novo.getAutor().getNome());
            autor.addLivro(novo);
            this.autores.add(autor);
        }
        
        for (Estante e : estantes){
            if (e.getGenero().equals(novo.genero)){
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
    
    public void imprimirAcervo(String nomeUnidade){
        System.out.println("Acervo da Unidade - " + nomeUnidade);
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
