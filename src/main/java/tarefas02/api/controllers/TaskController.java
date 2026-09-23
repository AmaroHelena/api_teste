package tarefas02.api.controllers;

import java.net.URI; // Importa a classe URI para contruir e manipular HTTP de novos recursos
import  java.util.List; // importa a interface list para manipular onde a classe controller esta localizada

import org.springframework.beans.factory.annotation.Autowired; // Injeta automatica do Spring
import org.springframework.http.ResponseEntity; // importa a classe para montar a resposta HTTP complete
import org.springframework.validation.annotation.Validated; // Importa anotação para habilitar suporte a validação no controller
import org.springframework.web.bind.annotation.DeleteMapping; // Mapeia requisiçoes do tipo delete
import org.springframework.web.bind.annotation.GetMapping; // Mapeia requisiçoes do tipo  get
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping; // Mapea variaveis passadas diretamente via caminho da URL
import org.springframework.web.bind.annotation.PostMapping;  // Mapea requisiçoes do tipo  post
import org.springframework.web.bind.annotation.PutMapping;  // Mapea requisiçoes do tipo put
import org.springframework.web.bind.annotation.RequestBody; // converte objto JSON em objeto Java
import org.springframework.web.bind.annotation.RequestMapping;  // Importa anotacoes para definir o caminho/rota bas do controler
import org.springframework.web.bind.annotation.RestController; // importa anotacao defini esta classe como um controller rest
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // importa para gerar a uri da requisisao atual dinamicamente

import jakarta.validation.Valid; // Importa a anotação para acionar a validade do corpo da requisicao
import tarefas02.api.models.Task;//importa a entidade Task do pacote de models do projeto
import tarefas02.api.services.TaskService; // import a cacasse de servico TaskService do projeto



@RestController  //Defini a clesseo com um controlador REST que retorna resposta em JSON
@RequestMapping ("/task") // defini / task como a rota base de todos os endpoint deste controlador
@Validated // Habilita o suporte as validacoes dentro do controlador

public class TaskController { // Declara de classe pibulica TaskController

    @Autowired
    public TaskService taskService;

    @GetMapping("/{id}") // mapea requisicao HTTP GET na rota "/task/{id}"
    public ResponseEntity<Task> findById(@PathVariable Long id){ // Busca tarefas especifica pelo seu id
        Task obj = this.taskService.findById(id); //Chama a camada de servica para buscar a tarefa pelo seu id
        return ResponseEntity.ok().body(obj); // Retorna HTTP 2000
    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable Long userId){
        List<Task>objs = this.taskService.findAllByUserId(userId);
        return ResponseEntity.ok().body(objs);

    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj){
        this.taskService.create(obj);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(url).build();
    } 

    @PostMapping ("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable Long id){
      obj.setId(id);
      this.taskService.update(obj);
      return ResponseEntity.noContent().build();

    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    }
    
    
    

    
    

    

