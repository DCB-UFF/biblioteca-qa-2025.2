package biblioteca.menu;

/* @author victoria */

public class MenuPrincipal {
    
    public static void opcoesIniciais(){
        System.out.println("Seja bem-vindo(a) ao Sistema de Bibliotecas Comunitárias!");
        System.out.println("Escolha o que deseja fazer:");
        System.out.println("1 - Escolher unidade");
        System.out.println("2 - Adicionar nova unidade");
        System.out.println("3 - Sair\n");
        
    }
    
    public static void opcoesAcessar(){
        System.out.println("Escolha o que você deseja fazer:");
        System.out.println("\n1 - Acervo");
        System.out.println("2 - Emprestimo");
        System.out.println("3 - Administração\n");
        
    }
    
    public static void opcoesAcessarAcervo(){
        System.out.println("Escolha o que você deseja fazer:");
        System.out.println("1 - Consultar acervo\n2 - Editar acervo\n3 - Sair\n");
    }
    
    public static void opcoesAcessarAdmin(){
        
        System.out.println("\n1 - Cliente");
        System.out.println("2 - Funcionário");
        System.out.println("3 - Sair\n");
        
    }
    
    public static void opcoesCriarUnidade(){

        System.out.println("\nEscolha o que deseja adicionar: ");
        System.out.println("1 - Livro");
        System.out.println("2 - Funcionário");
        System.out.println("3 - Autor");
        System.out.println("4 - Cliente");
        System.out.println("5 - Sair\n");
        
    }
}
