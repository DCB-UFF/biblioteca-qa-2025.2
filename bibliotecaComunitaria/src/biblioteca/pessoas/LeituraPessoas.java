/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca.pessoas;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author victoria
 */
public class LeituraPessoas {
    public static ArrayList<Cliente> leitorClientes() {
        BufferedReader br = null;
        String linha = "";
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("clientes.csv"));
            br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] cliente = linha.split(",");
                Cliente novo = new Cliente();
                novo.cadastro(cliente[0], cliente[1], cliente[2]);
                clientes.add(novo);
            }
            return clientes;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
      }
    
    public static ArrayList<Funcionario> leitorFuncionarios() {
        BufferedReader br = null;
        String linha = "";
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("funcionarios.csv"));
            br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] funcionario = linha.split(",");
                Funcionario novo = new Funcionario();
                novo.cadastro(funcionario[0], funcionario[1], funcionario[2], 1000, funcionario[4]);
                funcionarios.add(novo);
            }
            return funcionarios;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
      }
    
    public static void imprimir(ArrayList a){
        for (Object i: a){
           System.out.println(i);
        }
    }
    
}
