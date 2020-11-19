package biblioteca.livros;

public class Main {
    
    public static void main(String[] args) {
        Livro l1 = new Livro("Percy Jackson e o Ladrão de Raios", "Rick Riordan");
        Livro l2 = new Livro("Percy Jackson e o Mar de Monstros", "Rick Riordan");
        Livro l3 = new Livro("Percy Jackson e a Maldição do Titã", "Rick Riordan");
        Livro l4 = new Livro("Percy Jackson e a Batalha do Labirinto", "Rick Riordan");
        Livro l5 = new Livro("Percy Jackson e o Último Olimpiano", "Rick Riordan");
        Livro l6 = new Livro("1984", "George Orwell");
        Livro l7 = new Livro("O Extraordinário", "R. J. Palacio");
        Livro l8 = new Livro("Dom Casmurro", "Machado de Assis");
        
        Estante mitologia = new Estante(100, "Mitologia");
        mitologia.add5Livros(l1, l2, l3, l4, l5);

        
        Acervo acv = new Acervo();
        acv.addEstante(mitologia);
        acv.imprimirAcervo();
        
        l1.emprestarLivro(acv);
        acv.imprimirAcervo();
        
        
    }
}   
