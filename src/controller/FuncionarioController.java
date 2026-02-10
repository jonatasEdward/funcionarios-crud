package controller;

import entity.Funcionario;
import service.FuncionarioService;

import java.util.List;

public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    // Adaptado para usar o método 'cadastrarFuncionario' do service
    public Funcionario salvarFuncionario(String nome,
                                  String email,
                                  String cpf,
                                  Double salario) {
        if (salario == null) {
            throw new IllegalArgumentException("Salário deve ser informado");
        }
        return this.funcionarioService.cadastrarFuncionario(nome, email, cpf, salario);
    }

    public List<Funcionario> listarFuncionarios() {
        return this.funcionarioService.listarFuncionarios();
    }

    public Funcionario buscarPorId(Integer id) {
        if (id == null) throw new IllegalArgumentException("ID deve ser informado");
        return this.funcionarioService.buscarFuncionarioPorId(id);
    }

    public boolean deletarFuncionario(Integer id) {
        if (id == null) throw new IllegalArgumentException("ID deve ser informado");
        return this.funcionarioService.removerFuncionario(id);
    }

    public boolean atualizarFuncionario(Integer id,
                                     String nome,
                                     String email,
                                     Double salario) {
        if (id == null) throw new IllegalArgumentException("ID deve ser informado");
        // salario pode ser null se não quiser atualizar salario
        double salarioPrimitive = (salario == null) ? 0.0 : salario;
        return this.funcionarioService.atualizarFuncionario(id, nome, email, salarioPrimitive);
    }

}
