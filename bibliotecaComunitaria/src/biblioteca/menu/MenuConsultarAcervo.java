package biblioteca.menu;

import biblioteca.biblioteca.*;
import biblioteca.excecoes.*;
import biblioteca.livros.Livro;
import biblioteca.livros.Estante;
import biblioteca.livros.Acervo;

import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Scanner;

/*@author Luam */



public class MenuConsultarAcervo {
    public static void opcoesConsultarAcervo(){
        System.out.println("Escolha o que você deseja fazer:");
        System.out.println("1 - Buscar livro pelo título");
        System.out.println("2 - Buscar livro pelo autor");
        System.out.println("3 - Imprimir acervo");
        System.out.println("4 - Imprimir livros emprestados");
        System.out.println("5 - Sair\n");
    }

    public static void iniciar(Unidade unidadeAtual, Scanner teclado) throws LivroNaoExistenteException {
        int op = 0;
        int tentativas = 0;
        boolean continuar = true;

        while (continuar) {
            opcoesConsultarAcervo();
            if (teclado.hasNextInt()) {
                op = teclado.nextInt();
                teclado.nextLine();
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                teclado.nextLine();
                tentativas++;
                continue;
            }

            if (op == 1) {
                System.out.println("\nDigite o título do livro: ");
                String titulo = teclado.nextLine();
                Livro buscado = Util.buscarLivroTitulo(titulo, unidadeAtual);
                if (buscado != null) {
                    buscado.imprimirLivro();
                    if (buscado.isEstaEmprestado()) {
                        System.out.println("Este livro está emprestado.");
                    } else {
                        System.out.println("Este livro está disponível.");
                    }
                } else {
                    System.out.println("Livro não encontrado.");
                }
                tentativas++;
            } else if (op == 2) {
                System.out.println("\nDigite o nome do autor:");
                String autor = teclado.nextLine();
                ArrayList<Livro> achados = unidadeAtual.getAcervo().buscarLivroAutor(autor);
                if (achados.isEmpty()) {
                    System.out.println("Nenhum livro encontrado para este autor.");
                } else {
                    for (Livro l : achados) {
                        l.imprimirLivro();
                    }
                }
                tentativas++;
            } else if (op == 3) {
                unidadeAtual.getAcervo().imprimirAcervo(unidadeAtual.getNome());
                tentativas++;
            } else if (op == 4) {
                ArrayList<Livro> emprestados = new ArrayList<>();
                for (Estante est : unidadeAtual.getAcervo().getEstantes()) {
                    for (Livro l : est.getLivros()) {
                        if (l.isEstaEmprestado()) {
                            emprestados.add(l);
                        }
                    }
                }
                if (emprestados.isEmpty()) {
                    System.out.println("Nenhum livro emprestado.");
                } else {
                    for (Livro l : emprestados) {
                        l.imprimirLivro();
                    }
                }
                tentativas++;
            } else if (op == 5) {
                System.out.println("Saindo...");
                continuar = false;
            } else {
                System.out.println("Opção inválida.");
                tentativas++;
            }

            if (tentativas >= 5) {
                System.out.println("Você já realizou 5 operações.");
                continuar = false;
            }
            // Decisão extra: alerta se usuário só busca por autor
            if (tentativas > 0 && op == 2 && tentativas % 2 == 0) {
                System.out.println("Você está buscando muitos autores. Que tal buscar por título?");
            }
        }
    }

}
