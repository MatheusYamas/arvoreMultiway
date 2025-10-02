package arvore23;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Arvore23 arvore = new Arvore23();
        System.out.println("Insira os valores na arvore (Digite 0 para interromper): ");
        int item, valorRemover, valorBuscar;
        while (true) {
            item = scanner.nextInt();
            if (item == 0) {
                break;
            }
            arvore.inserir(item);
        }
//        int[] numerosParaTeste = {14, 15, 4, 9, 7, 18, 3, 5, 16, 20, 17, 9, 5};
//        for (int numero : numerosParaTeste) {
//            System.out.println("Inserindo o valor: " + numero);
//            arvore.inserir(numero);
//        }
        System.out.println("\n\nPercurso Em-Ordem:");
        arvore.inOrdem();
        System.out.println("\n\nBuscar valor: ");
        valorBuscar = scanner.nextInt();
        System.out.println("O valor buscado " + valorBuscar + ", resultado: " + arvore.buscar(valorBuscar));
        System.out.println("\n\nRemover valor: ");
        valorRemover = scanner.nextInt();
        arvore.remover(valorRemover);
        arvore.inOrdem();
        System.out.println("\n\nAlunos: Gustavo Tasca Lazzari, Mateus Roese Tocunduva, Matheus Yamamoto Dias, Victor Ryuki Tamezava.");
    }
}
