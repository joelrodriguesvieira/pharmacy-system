package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import obj.Medicamento;
import obj.MedicamentoReceita;
import obj.Produto;
import obj.ProdutoGeral;

public class Main {
	static Scanner sc = new Scanner(System.in);
	static int opcao;
	static ArrayList<Produto> lista = new ArrayList<Produto>();
	static double faturamento;
	static String itensVendidos = "";
	
	public static void main(String[] args) {
		while (true) {
			opcao = menu();
			
			switch(opcao) {
			case 1:
				vender();
				break;
			case 2:
				cadastrar();
				break;
			case 3:
				adicionarQtd();
				break;
			case 4:
				procurarProduto();
				break;
			case 5:
				listarTodos();
				break;
			case 6:
				faturamento();
				break;
			case 0:
				System.out.println("ENCERRANDO SISTEMA");
				System.exit(0);
			default:
				System.out.println("OPÇÃO NÃO ENCONTRADA! TENTE NOVAMENTE");
				break;
			}
		}
	}
	

	private static void vender(){		
		if (lista.isEmpty()) {
			System.out.println("NÃO HÁ NENHUM PRODUTO CADASTRADO!");
			return;
		}
		
		System.out.println("DIGITE O CODIGO DE BARRA: ");
		String codBarra = sc.next().toUpperCase();
		
		boolean encontrado = procurarCodBarra(codBarra);
		if (encontrado) {
			for (Produto p: lista) {
				if (p.getCodigoDeBarras().equals(codBarra)) {				
				System.out.println("PRODUTO: " + p.getNome());
				encontrado = true;
				System.out.println("DIGITE A QUANTIDADE: ");
				int qtd = sc.nextInt();
				
				if (p.getQuantidade() < qtd) {
					System.out.println("QUANTIDADE INSUFICIENTE!\nESTOQUE ATUAL DE " + p.getNome() + " = " + p.getQuantidade() + " UNIDADES");
					return;
				} else {
					p.setQuantidade(p.getQuantidade()-qtd);
					
					itensVendidos += "\nNOME: " + p.getNome() + "\nQUANTIDADE: " + qtd + 
							"\nVALOR: R$ " + p.getPreco()*qtd; 
					faturamento += p.getPreco()*qtd;
					System.out.println("VENDA CADASTRADA!");
					if(p.getQuantidade()==0) {
						lista.remove(p);
						System.out.println("O ESTOQUE DESTE PRODUTO CHEGOU A ZERO!");
						System.out.println("REMOVENDO PRODUTO DO SISTEMA...");
					}
					return;
				}
			}
			}
		}
		
		if (!encontrado) {
			System.out.println("PRODUTO NÃO ENCONTRADO!");
		}
		
	}

	private static void cadastrar(){
		System.out.println("CÓDIGO DE BARRA: ");
		String codBarra = sc.next().toUpperCase();
		
		if (codBarra.length() != 13) {
			try {
				throw new CheckBarCode();
			} catch (CheckBarCode e) {
				System.out.println("ERRO! " + e.getMessage());
				return;
			}
		}
		boolean encontrado = procurarCodBarra(codBarra);
		
		if (encontrado) {
			System.out.println("ESTE PRODUTO JÁ FOI CADASTRADO ANTERIOMENTE!");
			while (true) {
				System.out.println("DESEJA ADICIONAR APENAS ADICIONAR QUANTIDADES A ELE?\nDIGITE 1 PARA SIM\nDIGITE 2 PARA NÃO\n: ");
				String resposta = sc.next();
				if (resposta.equals("1")) {
					adicionarQtd();
					return;
				} else if (resposta.equals("2")){
					System.out.println("ENCERRANDO CADASTRO!...");
					return;
				} else if ((!resposta.equals("1") && (!resposta.equals("2")))) {
					System.out.println("ENTRADA INVÁLIDA. DIGITE APENAS 1 OU 2.");
				}
			}
			
		}
		System.out.println("NOME: ");
		String nome = sc.next().toUpperCase();	
		sc.nextLine();
		System.out.println("PREÇO: ");
		double preco = sc.nextDouble();
		System.out.println("DATA DE VALIDADE format:(yyyy-mm-dd): ");
		String data = sc.next();
		sc.nextLine();
		System.out.println("QUANTIDADE: ");
		int qtdProduto = sc.nextInt();
			
		
		
		while (true) {
			System.out.println("SIGA AS INSTRUÇÕES REFERENTE AO TIPO DE CADASTRO:\nDIGITE 1 - PARA PRODUTO\nDIGITE 2 - PARA MEDICAMENTO\n:");
			String ask = sc.next();
			
			if (ask.equals("1")) {
				Produto produto = new ProdutoGeral(nome,preco,data,qtdProduto,codBarra);
				lista.add(produto);
				System.out.println("PRODUTO CADASTRADO!");
				break;
			} else  if (ask.equals("2")){
				System.out.println("PRINCÍPIO ATIVO DO REMÉDIO: ");
				String principio = sc.next().toUpperCase();
				
				System.out.println("SIGA AS INSTRUÇÕES REFERENTE AO TIPO DE MEDICAMENTO:\nDIGITE 1 - MEDICAMENTO SEM RECEITA\nDIGITE 2 - MEDICAMENTO COM RECEITA\n: ");
				String resposta = sc.next();
				while (true) {
					if (resposta.equals("1")) {
						Produto remedio = new Medicamento(nome,preco,data,qtdProduto,codBarra,principio);
						lista.add(remedio);
						System.out.println("MEDICAMENTO CADASTRADO!");
						return;
					} else if (resposta.equals("2")) {
						System.out.println("DIGITE A RECEITA: ");
						String receita = sc.next().toUpperCase();				
						Produto remedio2 = new MedicamentoReceita(nome,preco,data,qtdProduto,codBarra,principio,receita);
						lista.add(remedio2);
						System.out.println("MEDICAMENTO CADASTRADO!");
						return;
					} else if ((!resposta.equals("1")) && (!resposta.equals("2"))) {
						System.out.println("ENTRADA INVÁLIDA. DIGITE APENAS 1 OU 2.");
					}
				}
			} else if ((!ask.equals("1")) && (!ask.equals("2"))) {
				System.out.println("ENTRADA INVÁLIDA. DIGITE APENAS 1 OU 2.");
			}
		}
			
	}
	
	private static boolean procurarCodBarra(String codBarra) {
		for (Produto p: lista) {
			if (p.getCodigoDeBarras().equals(codBarra)) {
				return true;
			}
		}
		return false;
	}
	
	private static void adicionarQtd() {
		System.out.println("DIGITE O CODIGO DE BARRA: ");
		String codBarra = sc.next().toUpperCase();
		
		boolean encontrado = procurarCodBarra(codBarra);
		if (encontrado == true) {
			for (Produto p: lista) {
				if (p.getCodigoDeBarras().equals(codBarra)) {
					try {
						System.out.println("DIGITE A QUANTIDADE: ");
						int qtdProduto = sc.nextInt();
						p.setQuantidade(p.getQuantidade() + qtdProduto);
						System.out.println("A QUANTIDADE DE " + p.getNome() + " FOI ATUALIZADA!\nTOTAL QUANTIDADE : " + p.getQuantidade());
						
						sc.nextLine();
					} catch (Exception e) {
						System.out.println("ENTRADA INVÁLIDA! TENTE REALIZAR ESSA FUNÇÃO DO INÍCIO!");
						return;
					}
					
				}
			}
		} else {
			System.out.println("PRODUTO NÃO ENCONTRADO!\nCADASTRE O PRODUTO PRIMEIRO OU CONFIRA O CÓDIGO DE BARRA");
			return;
		}
		
	}
	
	
	private static void procurarProduto() {
		System.out.println("DIGITE O NOME DO PRODUTO: ");
		String nomeP = sc.next().toUpperCase();
		
		for(Produto p: lista) {
			if(p.getNome().equals(nomeP)) {
				System.out.println(p.exibirInformacoes());
			} else {
				System.out.println("O PRODUTO NÃO CONSTA NO SISTEMA! ");
			}
		}
	}

	private static void listarTodos(){
		if (lista.isEmpty()) {
			System.out.println("NÃO HÁ NENHUM PRODUTO CADASTRADO!");
			return;
		}
		for (Produto p: lista) {
			System.out.println(p.exibirInformacoes()); 
		}
	}
	

	private static void faturamento(){
		if(itensVendidos.equals("")) {
			System.out.println("NENHUM PRODUTO FOI VENDIDO AINDA!");
		} else {
			System.out.println("=== ITENS VENDIDOS ===");
			System.out.println(itensVendidos);
			System.out.println("FATURAMENTO:  R$ " + faturamento);
		}
	}

	public static int menu() {
		System.out.println("\n=======BEM-VINDO=======");
		System.out.println("==SELECIONE UMA OPÇÃO==");
		System.out.println("1 - VENDER PRODUTO");
		System.out.println("2 - CADASTRAR PRODUTO");
		System.out.println("3 - ADICIONAR QUANTIDADES");
		System.out.println("4 - PROCURAR PRODUTO");
		System.out.println("5 - LISTAR TODOS OS PRODUTOS");
		System.out.println("6 - FATURAMENTO");
		System.out.println("0 - SAIR");
		
		boolean inputValido = false;
        while (!inputValido) {
            try {
                System.out.print("DIGITE UMA OPÇÃO: ");
                opcao = sc.nextInt();
                sc.nextLine(); 
               
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("\nENTRADA INVÁLIDA! TENTE NOVAMENTE!");
                sc.nextLine();
            }
        }
		return opcao;
	}
}
