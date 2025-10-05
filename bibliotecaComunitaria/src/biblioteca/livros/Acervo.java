package biblioteca.livros;

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
            Emprestimo.escreverEmprestimo(novo, unidade.getPath());
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
        Emprestimo.removerEmprestimo(atual, unidade.getPath());
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
        if (novo == null) {
            return;
        }

        if (this.autores == null) {
            this.autores = new ArrayList<>();
        }

        // Tratamento avançado do autor (múltiplos ramos)
        if (novo.getAutor() == null) {
            Autor autorDesconhecido = new Autor("Desconhecido");
            autorDesconhecido.addLivro(novo);
            this.autores.add(autorDesconhecido);
        } else {
            boolean contemAutor = autores.contains(novo.getAutor());
            boolean autorAssociado = false;

            if (contemAutor) {
                for (Autor autor : autores) {
                    if (autor == null) continue;

                    if (autor.equals(novo.getAutor())) {
                        String nomeExistente = autor.getNome();
                        String nomeNovo = novo.getAutor().getNome();

                        if (nomeExistente != null && nomeExistente.equals(nomeNovo)) {
                            autor.addLivro(novo);
                            autorAssociado = true;
                            break;
                        } else if (nomeExistente != null && nomeNovo != null && nomeExistente.equalsIgnoreCase(nomeNovo)) {
                            autor.addLivro(novo);
                            autorAssociado = true;
                            break;
                        } else {
                            Autor novoAutor = new Autor(nomeNovo);
                            novoAutor.addLivro(novo);
                            this.autores.add(novoAutor);
                            autorAssociado = true;
                            break;
                        }
                    }
                }
            }

            if (!contemAutor || !autorAssociado) {
                Autor autor = new Autor(novo.getAutor().getNome());
                autor.addLivro(novo);
                this.autores.add(autor);
            }
        }

        // Associação à estante com múltiplos ramos de decisão
        if (this.estantes == null) {
            this.estantes = new ArrayList<>();
        }

        boolean colocado = false;
        for (Estante e : estantes) {
            if (e == null) continue;

            String generoEstante = e.getGenero();
            String generoLivro = novo.genero;

            if (generoEstante == null && generoLivro == null) {
                e.addLivroNaEstante(novo);
                colocado = true;
                break;
            } else if (generoEstante != null && generoEstante.equals(generoLivro)) {
                e.addLivroNaEstante(novo);
                colocado = true;
                break;
            } else if (generoEstante != null && generoLivro != null && generoEstante.equalsIgnoreCase(generoLivro)) {
                e.addLivroNaEstante(novo);
                colocado = true;
                break;
            } else if (generoLivro == null || generoLivro.trim().isEmpty()) {
                if (generoEstante == null || generoEstante.trim().isEmpty()) {
                    e.addLivroNaEstante(novo);
                    colocado = true;
                    break;
                }
            }
        }

        if (!colocado) {
            if (novo.genero != null && !novo.genero.trim().isEmpty()) {
                // passa o id atual ao construtor conforme assinatura de Estante
                Estante nova = new Estante(this.idsEstantes, novo.genero);
                nova.addLivroNaEstante(novo);
                addEstante(nova);
            } else {
                // fallback: não há estante adequada; mantém comportamento sem lançar erro
            }
        }
    }


    public void addLivro2(Livro novo, String path){
        // 1) validação básica do objeto livro
        if (novo == null) {
            return;
        }

        // 2) path seguro
        if (path == null) {
            path = "";
        }

        // 3) grava o livro no arquivo (comportamento original)
        Livro.escreverLivro(novo, path);

        // 4) tratativa avançada do autor (vários ramos)
        if (novo.getAutor() == null) {
            // autor ausente: cria um autor genérico e associa
            Autor autorDesconhecido = new Autor("Desconhecido");
            autorDesconhecido.addLivro(novo);
            this.autores.add(autorDesconhecido);
        } else {
            boolean contemAutor = autores.contains(novo.getAutor());
            boolean autorAssociado = false;

            if (contemAutor) {
                for (Autor autor : autores) {
                    if (autor == null) continue;

                    // 5) comparação por igualdade de objeto
                    if (autor.equals(novo.getAutor())) {
                        // 6) nome exato
                        if (autor.getNome() != null && autor.getNome().equals(novo.getAutor().getNome())) {
                            autor.addLivro(novo);
                            autorAssociado = true;
                            break;
                        }
                        // 7) nome igual ignorando caixa
                        else if (autor.getNome() != null && autor.getNome().equalsIgnoreCase(novo.getAutor().getNome())) {
                            autor.addLivro(novo);
                            autorAssociado = true;
                            break;
                        }
                        // 8) autor parcialmente compatível: cria nova entrada para evitar sobrescrever
                        else {
                            Autor novoAutor = new Autor(novo.getAutor().getNome());
                            novoAutor.addLivro(novo);
                            this.autores.add(novoAutor);
                            autorAssociado = true;
                            break;
                        }
                    }
                }
            }

            // 9) se não achou ou não estava contido, adiciona novo autor
            if (!contemAutor || !autorAssociado) {
                Autor autor = new Autor(novo.getAutor().getNome());
                autor.addLivro(novo);
                this.autores.add(autor);
            }
        }

        // 10) associação à estante com múltiplos ramos de decisão
        boolean colocado = false;
        if (this.estantes == null) {
            this.estantes = new ArrayList<>();
        }

        for (Estante e : estantes) {
            if (e == null) continue;

            String generoEstante = e.getGenero();
            String generoLivro = novo.genero;

            // 11) ambos nulos -> coloca
            if (generoEstante == null && generoLivro == null) {
                e.addLivroNaEstante(novo);
                colocado = true;
                break;
            }
            // 12) comparação exata
            else if (generoEstante != null && generoEstante.equals(generoLivro)) {
                e.addLivroNaEstante(novo);
                colocado = true;
                break;
            }
            // 13) comparação ignorando caixa
            else if (generoEstante != null && generoLivro != null && generoEstante.equalsIgnoreCase(generoLivro)) {
                e.addLivroNaEstante(novo);
                colocado = true;
                break;
            }
            // 14) livro sem gênero -> tenta estante vazia
            else if (generoLivro == null || generoLivro.trim().isEmpty()) {
                if (generoEstante == null || generoEstante.trim().isEmpty()) {
                    e.addLivroNaEstante(novo);
                    colocado = true;
                    break;
                }
            }
            // continua tentando outras estantes
        }

        // 15) se não foi colocado, tenta criar nova estante quando possível
        if (!colocado) {
            if (novo.genero != null && !novo.genero.trim().isEmpty()) {
                // passa o id atual ao construtor conforme assinatura de Estante
                Estante nova = new Estante(this.idsEstantes, novo.genero);
                nova.addLivroNaEstante(novo);
                addEstante(nova);
            } else {
                // fallback: não há estante adequada; mantém comportamento sem lançar erro
            }
        }
    }

    public void removeLivro(Livro l, String path){
        Livro.removerLivro(l, path);

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
