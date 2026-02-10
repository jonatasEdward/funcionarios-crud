import controller.FuncionarioController;
import entity.Funcionario;
import repository.FuncionarioRepository;
import repository.FuncionarioRepositoryMemoria;
import service.FuncionarioService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        FuncionarioRepository funcionarioRepository = new FuncionarioRepositoryMemoria();
        FuncionarioService funcionarioService = new FuncionarioService(funcionarioRepository);
        FuncionarioController funcionarioController = new FuncionarioController(funcionarioService);

        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Salvar funcionário");
            System.out.println("2. Listar funcionários");
            System.out.println("3. Buscar funcionário por ID");
            System.out.println("4. Deletar funcionário");
            System.out.println("5. Atualizar funcionário");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarFuncionario(funcionarioController);
                    break;
                case 2:
                    listarFuncionarios(funcionarioController);
                    break;
                case 3:
                    buscarFuncionarioPorId(funcionarioController);
                    break;
                case 4:
                    deletarFuncionario(funcionarioController);
                    break;
                case 5:
                    atualizarFuncionario(funcionarioController);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarFuncionario(FuncionarioController funcionarioController) {
        try {
            System.out.print("Digite o nome do funcionário: ");
            String nome = scanner.nextLine().trim();

            System.out.print("Digite o CPF do funcionário: ");
            String cpf = scanner.nextLine().trim();

            System.out.print("Digite o email do funcionário: ");
            String email = scanner.nextLine().trim();

            System.out.print("Digite o salário do funcionário: ");
            String salarioStr = scanner.nextLine().trim();
            double salario = Double.parseDouble(salarioStr);

            Funcionario f = funcionarioController.salvarFuncionario(nome, email, cpf, salario);
            System.out.println("Funcionário salvo: " + f);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    private static void listarFuncionarios(FuncionarioController funcionarioController) {
        System.out.println("\nLista de funcionários:");
        List<Funcionario> funcionarios = funcionarioController.listarFuncionarios();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }
    }

    private static void buscarFuncionarioPorId(FuncionarioController funcionarioController) {
        try {
            System.out.print("Digite o ID do funcionário: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Funcionario f = funcionarioController.buscarPorId(id);
            System.out.println("Funcionário encontrado: " + f);
        } catch (Exception e) {
            System.out.println("Erro ao buscar funcionário: " + e.getMessage());
        }
    }

    private static void deletarFuncionario(FuncionarioController funcionarioController) {
        try {
            System.out.print("Digite o ID do funcionário a deletar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            boolean ok = funcionarioController.deletarFuncionario(id);
            if (ok) System.out.println("Funcionário deletado com sucesso.");
            else System.out.println("Funcionário não encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao deletar funcionário: " + e.getMessage());
        }
    }

    private static void atualizarFuncionario(FuncionarioController funcionarioController) {
        try {
            System.out.print("Digite o ID do funcionário a atualizar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Digite o novo nome (ou Enter para manter): ");
            String nome = scanner.nextLine();
            if (nome != null && nome.trim().isEmpty()) nome = null;

            System.out.print("Digite o novo email (ou Enter para manter): ");
            String email = scanner.nextLine();
            if (email != null && email.trim().isEmpty()) email = null;

            System.out.print("Digite o novo salário (ou Enter para manter): ");
            String salarioStr = scanner.nextLine().trim();
            Double salario = null;
            if (!salarioStr.isEmpty()) {
                salario = Double.parseDouble(salarioStr);
            }

            boolean atualizado = funcionarioController.atualizarFuncionario(id, nome, email, salario);
            if (atualizado) System.out.println("Funcionário atualizado com sucesso.");
            else System.out.println("Funcionário não encontrado ou não foi possível atualizar.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }
}