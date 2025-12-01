package biblioteca.menu;

import biblioteca.biblioteca.*;
import biblioteca.excecoes.LivroNaoExistenteException;
import biblioteca.livros.Livro;
import biblioteca.pessoas.Autor;
import java.util.Scanner;
import java.util.logging.Logger;

public class MenuEditarAcervo {

    private static final Logger LOGGER = Logger.getLogger(MenuEditarAcervo.class.getName());

    public static void opcoesEditarAcervo(){
        System.out.println("Escolha o que você deseja fazer:");
        System.out.println("1 - Adicionar livro");
        System.out.println("2 - Remover livro");
        System.out.println("3 - Sair\n");
    }

    public static void iniciar(Unidade unidadeAtual, Scanner teclado) throws LivroNaoExistenteException{
        int op=0;
        opcoesEditarAcervo();

        while(op != 3){
            op = teclado.nextInt();
            teclado.nextLine();
            switch (op) {
                case 1:
                    adicionarLivro(unidadeAtual, teclado);
                    break;
                case 2:
                    removerLivro(unidadeAtual, teclado);
                    break;
                case 3:
                    return;
                default:
                    LOGGER.warning("Opção inválida. Tente novamente:");
                    opcoesEditarAcervo();
                    break;
            }
        }
    }

    private static void adicionarLivro(Unidade unidadeAtual, Scanner teclado) {
        LOGGER.info("\nDigite o nome do autor: ");
        String nome = teclado.nextLine();
        LOGGER.info("\nDigite o país do autor: ");
        String pais = teclado.nextLine();
        Autor autor = new Autor(nome, pais);
        LOGGER.info("\nDigite o título do livro: ");
        String titulo = teclado.nextLine();
        LOGGER.info("\nDigite o isbn do livro: ");
        String isbn = teclado.nextLine();
        LOGGER.info("\nDigite a quantidade de páginas do livro: ");
        int pag = teclado.nextInt();
        teclado.nextLine();
        LOGGER.info("\nDigite o gênero do livro: ");
        String gene = teclado.nextLine();
        LOGGER.info("\nDigite a editora do livro: ");
        String edit = teclado.nextLine();

        Livro adicionado = new Livro(autor, titulo,  pag,isbn, gene, edit, false);
        unidadeAtual.getAcervo().addLivro2(adicionado, unidadeAtual.getPath());

        LOGGER.info("Livro adicionado!");
    }

    private static void removerLivro(Unidade unidadeAtual, Scanner teclado) throws LivroNaoExistenteException {
        // Mantido com LOGGER para evitar mais issues
        LOGGER.info("\nDigite o título do arquivo que você deseja remover:");
        String titulo2 = teclado.nextLine();
        Livro removido = Util.buscarLivroTitulo(titulo2, unidadeAtual);

        if (removido != null) {
            unidadeAtual.getAcervo().removeLivro(removido, unidadeAtual.getPath());
            LOGGER.info("Livro removido!");
        } else {
            LOGGER.warning("Livro não encontrado para remoção.");
        }
    }
}