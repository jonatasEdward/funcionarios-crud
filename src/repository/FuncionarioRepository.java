package repository;

import entity.Funcionario;

import java.util.List;

public interface FuncionarioRepository {
    /**
     * Salva um funcionário no repositório
     *
     * @param funcionario o funcionário a ser salvo
     * @return o funcionário salvo com ID atribuído
     */
    Funcionario salvar(Funcionario funcionario);

    /**
     * Lista todos os funcionários
     *
     * @return lista de todos os funcionários
     */
    List<Funcionario> listarTodos();

    /**
     * Busca funcionário por ID
     *
     * @param id o ID do funcionário
     * @return Funcionário se encontrado
     */
    Funcionario buscarPorId(int id);

    /**
     * Busca funcionário por CPF
     *
     * @param cpf o CPF do funcionário
     * @return Funcionário se encontrado
     */
    Funcionario buscarPorCpf(String cpf);

    /**
     * Atualiza um funcionário existente
     *
     * @param funcionario o funcionário com dados atualizados
     * @return true se a atualização foi bem-sucedida
     */
    boolean atualizar(Funcionario funcionario);

    /**
     * Remove um funcionário do repositório
     *
     * @param id o ID do funcionário a ser removido
     * @return true se a remoção foi bem-sucedida
     */
    boolean deletar(int id);

    /**
     * Conta o número total de funcionários
     *
     * @return o número de funcionários
     */
    int contar();
}
