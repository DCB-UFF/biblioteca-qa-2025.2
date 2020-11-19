package biblioteca.livros;
/* @author Luam  */

public interface Emprestavel {
    public void emprestarLivro(Acervo a);/*, Cliente c*/
    public void devolverLivro(Acervo a, int idEmprestimo/*, Cliente c*/);/*, Cliente c*/
}
