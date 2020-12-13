package biblioteca.livros;

import biblioteca.arquivo.*;
import biblioteca.biblioteca.Unidade;
import java.util.ArrayList;
import biblioteca.pessoas.*;

/* @author Luam */

public class Acervo {
    protected int idsEstantes = 1;
    protected ArrayList<Estante> estantes = new ArrayList<>();
    protected ArrayList<Emprestimo> emprestimos = new ArrayList<>();
    protected ArrayList<Autor> autores = new ArrayList<>();
    
     public void emprestarLivro(Unidade unidade, Emprestimo novo) {
        if (!buscarClienteNosEmprestimos(novo.getCPF())){
            this.emprestimos.add(novo);
            Escritor.escreverEmprestimo(novo, unidade.getPath());
            Livro emprestado = buscarLivroISNB(novo.getISNB());
            emprestado.emprestar(unidade, this);
            System.out.println("\nO livro de ISBN "+novo.getISNB()+" foi emprestado para o cliente de cpf "+novo.getISNB());
        }
        else{
            System.out.println("O cliente não pode pegar um novo livro emprestado até que devolva o anterior");
        }
    }

    public void devolverLivro(Unidade unidade, String CPF, String ISNB) {
        Emprestimo atual = buscarEmprestimo(CPF,ISNB);
        this.emprestimos.remove(atual);
        Removedor.removerEmprestimo(atual, unidade.getPath());
        Livro emprestado = buscarLivroISNB(ISNB);
        emprestado.emprestar(unidade, this);
        System.out.println("\nO livro de ISBN "+ISNB+" foi devolvido pelo cliente de cpf "+CPF);   
    }

    public void imprimirAcervo(String nomeUnidade){
        System.out.println("Acervo da Unidade - " + nomeUnidade);
        for (Estante e : estantes){
            e.imprimirEstante();
        }
    }
    
    public void addLivro(Livro novo){
        if (autores.contains(novo.getAutor())){
            for (Autor autor : autores){
                if (autor.equals(novo.getAutor())){
                    autor.addLivro(novo);
                }
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
    
    public void addLivro2(Livro novo, String path){
        Escritor.escreverLivro(novo, path);
        
        if (autores.contains(novo.getAutor())){
            for (Autor autor : autores){
                if (autor.equals(novo.getAutor())){
                    autor.addLivro(novo);
                }
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
    
    public void removeLivro(Livro l, String path){
        Removedor.removerLivro(l, path);
        
        for (Autor autor : autores){
            if (autor.equals(l.getAutor())){
                autor.getLivrosAutor().remove(l);
            }
        }
        
        for (Estante e : estantes){
            if (e.getGenero().equals(l.genero)){
                e.getLivros().remove(l);
            }
        }
    }
        
    public void addEstante(Estante nova) {
        estantes.add(nova);
        idsEstantes++;
    }
    
    public Livro buscarLivroISNB (String ISNB){
        for (Estante e : estantes){
            for (Livro l : e.livros){
                if (l.getISBN().equals(ISNB)){
                    return l;
                }
            }
        }
        return null;
    }
    
    public ArrayList<Livro> buscarLivroAutor (String autor){
        ArrayList<Livro> livros = new ArrayList();
        for (Estante e : estantes){
            for (Livro l : e.livros){
                if (l.autor.getNome().equals(autor)){
                    livros.add(l);
                }
            }
        }
        return livros;
    }
    
    public boolean buscarAutor(String nome){
        for (Autor autor : autores){
            if (autor.getNome().equals(nome))
                return true;
        }
        return false;
    }
    
    public Emprestimo buscarEmprestimo(String CPF, String ISNB){
        for (Emprestimo e : emprestimos){
            if ((e.getCPF().equals(CPF))&&(e.getISNB().equals(ISNB))){
                return e;
            }
                
        }
        return null;
    }
    
    public boolean buscarClienteNosEmprestimos(String CPF){
        for (Emprestimo e : emprestimos){
            if (e.getCPF().equals(CPF)){
                return true;
            }
                
        }
        return false;
    }
    
    public int getIdsEstantes() {
        return idsEstantes;
    }

    public void setIdsEstantes(int idsEstantes) {
        this.idsEstantes = idsEstantes;
    }

    public ArrayList<Estante> getEstantes() {
        return estantes;
    }

    public void setEstantes(ArrayList<Estante> estantes) {
        this.estantes = estantes;
    }

    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(ArrayList<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public ArrayList<Autor> getAutores() {
        return autores;
    }

    public void setAutores(ArrayList<Autor> autores) {
        this.autores = autores;
    }
    
   
}
