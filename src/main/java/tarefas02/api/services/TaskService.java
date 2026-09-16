//Pacote onde esta esta classe
package tarefas02.api.services;

//Importa list da biblioteca padrão do java para manipular coleção de objto
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tarefas02.api.models.Task;
import tarefas02.api.models.User;
import tarefas02.api.repositories.TaskRepository;

//Anotação que indica as regras para a String reponsavel
@Service
public class TaskService {


    //Injeta automaticamente a instancia do TaskRepositorio gerencia pelo Spring
    @Autowired
    private TaskRepository taskRepository;

    //Injeta automaticamente a instancia do UserRepositorio gerencia pelo Spring
    @Autowired
    private UserService userService;

    //Metodo para buscar task apatir do id
    public Task findById(Long id) {
        //Executa a busca no banco, retorna um Optional contendo(ou nao) a Task
        Optional<Task> task = this.taskRepository.findById(id);

        // Se a tarefa existir. retorna o objto,se estiver vazio,lança um runTimeException
        return task.orElseThrow(() -> new RuntimeException(
            "Tarefa não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()
        ));
    }

    //Metodo para buscar todas as tarefas vinculadas a um determindo usuario
    public List<Task> findAllByUserId(Long userId) {

        //Chama o UserService para garantir que o usuario exista no banco (lança escuçao se não existir)
        this.userService.findById(userId);

        //Executa a busca a lista de tarefas
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);

        //Retona a lista de tarefas
        return tasks;
    }

    //Garante que criaçao ocorra dentro de uma  transação de banco de dados(rolback automatico se falhar)
    @Transactional
    public Task create(Task obj) {
        //Valida se o usuario informado no objeto realmete existe no banco a recupera seus dados
        User user = this.userService.findById(obj.getUser().getId());

        //Definie o id como null para garantir que o jpa realize um inserção(insert) e não uma atualização
        obj.setId(null);
        //Associa a entidade User completa a validadada a tarefa
        obj.setUser(user);
        // Salva a nva tarefa no banco de dados e atualiza"obj" com o id gerado
        obj = this.taskRepository.save(obj);
        //Retorna a tarefa salva 
        return obj;
    }
    //Garante que a atualização dentro de transação isolada no banco
    @Transactional
    public Task update(Task obj) {

        //Reaproveita o findByID para verficar se atarefa a ser atualizada exista realmente
        Task newObj = findById(obj.getId());

        //Atualiza apenas o campo descricao do objeto persistido com o novo valo
        newObj.setDescription(obj.getDescription());

        //Salva a alteração no banco de dados e retorna o objeto atualização
        return this.taskRepository.save(newObj);
    }

     //Metodo para deletar uma tarefa pelo id
    public void delete(Long id) {

        //Verifica se a tarefa existe antes de tentar deletar
        findById(id);

        try {

            //Solicita a remoção da tarefa no banco de dados pelo id
            this.taskRepository.deleteById(id);

        } catch (Exception e) {

            //Capctura exeçoes (como violaçoes de chave estrangeira e lança uma mensagem amigavel)
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
        }
    }
}