package biblioteca.livros;

import java.util.ArrayList;

/* @author Luam */
public class Estante {
    protected ArrayList<Livro> livros = new ArrayList();
    protected int idEstante;
    protected int capacidade;
    protected String genero;

    public Estante(int capacidade, String genero) {
        this.idEstante = Acervo.idsEstantes++;
        this.capacidade = capacidade;
        this.genero = genero;
    }
 
    public void imprimirEstante(){
        System.out.printf("\nEstante %d - %s - %d Livros\n", this.idEstante, this.genero, this.livros.size() );
        for (Livro livro:livros){
            if (!livro.estaEmprestado)
                System.out.println(livro.titulo);
            else
                System.out.println(livro.titulo + " (Emprestado)"); 
        }
    }

    public void addLivro(Livro livro) {
        if(livros.size()<this.capacidade){
            this.livros.add(livro);
        }
        
    }
    public void add5Livros(Livro livro1, Livro livro2, Livro livro3, Livro livro4, Livro livro5 ) {
        this.livros.add(livro1);
        this.livros.add(livro2);
        this.livros.add(livro3);
        this.livros.add(livro4);
        this.livros.add(livro5);
        
    }
}
