package service;

import entity.Funcionario;
import repository.FuncionarioRepository;

import java.util.List;

public class FuncionarioService {
    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra um novo funcionário com validações de negócio
     */
    public Funcionario cadastrarFuncionario(String nome, String email, String cpf, double salario) {
        try {
            // Validações de negócio específicas
            validarDadosNegocio(nome, email, cpf, salario);

            Funcionario funcionario = new Funcionario(nome, email, cpf, salario);
            return repository.salvar(funcionario);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar funcionário: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todos os funcionários ordenados por nome
     */
    public List<Funcionario> listarFuncionarios() {
        List<Funcionario> funcionarios = repository.listarTodos();
        funcionarios.sort((f1, f2) -> f1.getNome().compareToIgnoreCase(f2.getNome()));
        return funcionarios;
    }

    /**
     * Busca funcionário por ID com tratamento de erro
     */
    public Funcionario buscarFuncionarioPorId(int id) {
        Funcionario f = repository.buscarPorId(id);
        if (f == null) {
            throw new RuntimeException("Funcionário não encontrado com ID: " + id);
        }
        return f;
    }

    /**
     * Atualiza dados de um funcionário
     */
    public boolean atualizarFuncionario(int id, String nome, String email, double salario) {
        Funcionario funcionario = repository.buscarPorId(id);

        if (funcionario == null) {
            throw new RuntimeException("Funcionário não encontrado com ID: " + id);
        }

        // Atualizar apenas campos fornecidos
        if (nome != null && !nome.trim().isEmpty()) {
            funcionario.setNome(nome);
        }
        if (email != null && !email.trim().isEmpty()) {
            funcionario.setEmail(email);
        }
        if (salario > 0) {
            funcionario.setSalario(salario);
        }

        return repository.atualizar(funcionario);
    }

    /**
     * Remove funcionário com validações
     */
    public boolean removerFuncionario(int id) {
        if (repository.buscarPorId(id) == null) {
            throw new RuntimeException("Funcionário não encontrado com ID: " + id);
        }

        return repository.deletar(id);
    }

    /**
     * Aplica aumento salarial
     */
    public boolean aplicarAumento(int id, double percentual) {
        Funcionario funcionario = buscarFuncionarioPorId(id);
        funcionario.aumentarSalario(percentual);
        return repository.atualizar(funcionario);
    }

    /**
     * Relatório de funcionários por faixa salarial
     */
    public List<Funcionario> buscarPorFaixaSalarial(double salarioMin, double salarioMax) {
        return repository.listarTodos().stream()
                .filter(f -> f.getSalario() >= salarioMin && f.getSalario() <= salarioMax)
                .sorted((f1, f2) -> Double.compare(f2.getSalario(), f1.getSalario()))
                .toList();
    }

    private void validarDadosNegocio(String nome, String email, String cpf, double salario) {
        // Validações específicas de negócio
        if (repository.buscarPorCpf(cpf) != null) {
            throw new IllegalArgumentException("Já existe funcionário cadastrado com este CPF");
        }

        // Validar domínio do email (exemplo: apenas emails corporativos)
        if (!email.toLowerCase().endsWith("@senai.com.br")) {
            throw new IllegalArgumentException("Email deve ser corporativo (@empresa.com ou @g4f.com)");
        }
    }
}
