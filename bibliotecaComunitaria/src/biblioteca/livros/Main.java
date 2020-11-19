package biblioteca.livros;

public class Main {
    
    public static void main(String[] args) {
        Livro l1 = new Livro("Percy Jackson e o Ladrão de Raios", "Rick Riordan", 2005, "Intrinseca", 1);
        Livro l2 = new Livro("Percy Jackson e o Mar de Monstros", "Rick Riordan", 2006, "Intrinseca", 1);
        Livro l3 = new Livro("Percy Jackson e a Maldição do Titã", "Rick Riordan", 2007, "Intrinseca", 1);
        Livro l4 = new Livro("Percy Jackson e a Batalha do Labirinto", "Rick Riordan", 2008, "Intrinseca", 1);
        Livro l5 = new Livro("Percy Jackson e o Último Olimpiano", "Rick Riordan", 2009, "Intrinseca", 1);
  
        
        Estante mitologia = new Estante(100, "Mitologia");
        mitologia.add5Livros(l1, l2, l3, l4, l5);

        
        Acervo acv = new Acervo();
        acv.addEstante(mitologia);
        acv.imprimirAcervo();
        
        l1.emprestarLivro(acv);
        acv.imprimirAcervo();
        
        
    }
}   
