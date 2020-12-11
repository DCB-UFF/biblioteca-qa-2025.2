package biblioteca.livros;

import biblioteca.arquivo.Editor;
import biblioteca.biblioteca.Unidade;
import biblioteca.pessoas.Autor;

public class Livro {
   protected String titulo;
   protected Autor autor;
   protected int numPaginas;
   protected String ISBN;
   protected String genero;
   protected String editora;
   protected boolean estaEmprestado;
   
    public Livro( Autor autor, String titulo, int numPaginas, String ISBN,
            String genero,  String editora, Boolean estaEmprestado) {
        this.autor = autor;
        this.titulo = titulo;
        this.ISBN = ISBN;
        this.numPaginas = numPaginas;
        this.genero = genero;
        this.editora = editora;
        this.estaEmprestado = estaEmprestado;

    }
    
    public void imprimirLivro(){
        if (this.estaEmprestado){
            System.out.printf("\nCód: %s [Emprestado]| %s \n%s - %s  \n",
                    this.ISBN, this.titulo, this.autor.getNome(), this.editora);    
        }
        else{
            System.out.printf("\nCód: %s | %s \n%s - %s  \n",
            this.ISBN, this.titulo, this.autor.getNome(), this.editora);
        }       
    }

    public void emprestar(Unidade unidade,Acervo acervo){
        this.estaEmprestado = true;
        Editor.modificarEmprestimo(acervo, this, unidade.getPath(), "true");
        System.out.println(this.titulo);
    }
    
    public void devolver(Unidade unidade,Acervo acervo){
       this.estaEmprestado = false;
       Editor.modificarEmprestimo(acervo, this, unidade.getPath(), "false");
       System.out.println(this.titulo);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public boolean isEstaEmprestado() {
        return estaEmprestado;
    }

    public void setEstaEmprestado(boolean estaEmprestado) {
        this.estaEmprestado = estaEmprestado;
    }
}
