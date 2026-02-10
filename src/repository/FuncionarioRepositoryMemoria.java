package repository;

import entity.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepositoryMemoria implements FuncionarioRepository {
    private final List<Funcionario> funcionarios = new ArrayList<>();

    @Override
    public Funcionario salvar(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }

        // Verificar se CPF já existe
        if (buscarPorCpf(funcionario.getCpf()) != null) {
            throw new IllegalArgumentException("Já existe funcionário com este CPF");
        }

        // Adiciona na lista
        funcionarios.add(funcionario);
        return funcionario;
    }

    @Override
    public List<Funcionario> listarTodos() {
        return new ArrayList<>(funcionarios);
    }

    @Override
    public Funcionario buscarPorId(int id) {
        return funcionarios.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Funcionario buscarPorCpf(String cpf) {
        if (cpf == null) return null;
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getCpf().equals(cpfLimpo)) {
                return funcionario;
            }
        }
        return null;
    }

    @Override
    public boolean atualizar(Funcionario funcionario) {
        if (funcionario == null) {
            return false;
        }

        // Encontrar índice do funcionário existente pelo id
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario existente = funcionarios.get(i);
            if (existente.getId() == funcionario.getId()) {
                // Verificar se o CPF informado entra em conflito com outro funcionário
                Funcionario porCpf = buscarPorCpf(funcionario.getCpf());
                if (porCpf != null && porCpf.getId() != funcionario.getId()) {
                    throw new IllegalArgumentException("Já existe funcionário com este CPF");
                }

                // Substituir o funcionário na posição encontrada
                funcionarios.set(i, funcionario);
                return true;
            }
        }

        return false; // não encontrado
    }

    @Override
    public boolean deletar(int id) {
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getId() == id) {
                funcionarios.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int contar() {
        return funcionarios.size();
    }
}
