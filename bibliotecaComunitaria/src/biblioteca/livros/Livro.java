package biblioteca.livros;

import biblioteca.pessoas.Autor;

public class Livro implements Emprestavel {
   int idLivro;
   boolean estaEmprestado;
   
   protected String titulo;
   protected Autor autor;
   protected int numPaginas;
   protected String ISBN;
   protected String genero;
   protected String editora;
   
   
    public Livro(int idLivro, Autor autor, String titulo, String ISBN, int numPaginas ,
            String genero,  String editora, Boolean estaEmprestado) {
        this.idLivro = idLivro;
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
            System.out.printf("Cód: %d [Emprestado]| %s \n%s - %s  \n",
                    this.idLivro, this.titulo, this.autor.getNome(), this.editora);    
        }
        else{
            System.out.printf("Cód: %d | %s \n%s - %s  \n",
            this.idLivro, this.titulo, this.autor.getNome(), this.editora);
        }       
    }

    @Override
    public void emprestarLivro(Acervo acervo/*, Cliente c*/) {
        Emprestimo novo = new Emprestimo(this, "12/10/20", "17/12/20" );
        acervo.registarEmprestimo(novo);
        // Adicionar livro emprestado ao array do Cliente
        this.estaEmprestado = true;
        
    }
    
   @Override
    public void devolverLivro() {
        this.estaEmprestado = false;
    }

    public boolean isEmpty() {
       return this.idLivro==0;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public boolean isEstaEmprestado() {
        return estaEmprestado;
    }

    public void setEstaEmprestado(boolean estaEmprestado) {
        this.estaEmprestado = estaEmprestado;
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

   
}
