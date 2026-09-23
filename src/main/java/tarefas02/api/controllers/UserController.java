package tarefas02.api.controllers;

import java.net.URI;// Importa a classe URI para contruir e manipular HTTP de novos recursos

import org.springframework.beans.factory.annotation.Autowired; // Injeta automatica do Spring
import org.springframework.http.ResponseEntity; // importa a classe para montar a resposta HTTP complete
import org.springframework.validation.annotation.Validated; // Importa anotação para habilitar suporte a validação no controller
import org.springframework.web.bind.annotation.DeleteMapping; // Mapeia requisiçoes do tipo delete
import org.springframework.web.bind.annotation.GetMapping; // Mapeia requisiçoes do tipo  get
import org.springframework.web.bind.annotation.PatchMapping; // Mapea variaveis passadas diretamente via caminho da URL
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;  // Mapea requisiçoes do tipo  post
import org.springframework.web.bind.annotation.PutMapping;  // Mapea requisiçoes do tipo put
import org.springframework.web.bind.annotation.RequestBody; // converte objto JSON em objeto Java
import org.springframework.web.bind.annotation.RequestMapping;  // Importa anotacoes para definir o caminho/rota bas do controler
import org.springframework.web.bind.annotation.RestController; // importa anotacao defini esta classe como um controller rest
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // importa para gerar a uri da requisisao atual dinamicamente

import tarefas02.api.models.User;
import tarefas02.api.models.User.createUser;
import tarefas02.api.models.UpdateUser;
import tarefas02.api.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController // Defini a classe como controlador REST que retorna resposta em Json
@RequestMapping ("/user") //Defini que toas as rotas desta classe tarao como prefixo o caminho "/user"
@Validated // Ativa a verificação nos paramentor recebidos no controller

public class UserController {

    @Autowired 
    private  UserService userService;

    @GetMapping("/{id}") // Mapeia requisitos HTTP GET na rota "/user/{id}"
    public ResponseEntity<User> findById(@PathVariable Long id){ // Metado para buscar usuarios por id captura da url
         User obj=this.userService.findById(id); // Invoca a busca do usuario atraves do id recebido
         return ResponseEntity.ok().body(obj) // Retorn codigo HTTP 200(ok) com obj USER no corpo da resposta

    } // Fim do metodo FindById

 @PostMapping("path") // Mapeia requisições HTTP POST na rota base "/user"(Criaçao de novo usuario)
 public ResponseEntity<Void> create(@Validated (CreateUser.class) @RequestBody User obj ){ //  Valida regra de CrateUser e desserializa o corpo JSON
      this.userService.create(obj); // Chama a camada de serviço para persistir o novo banco de dados
      URI url = ServletUriComponentsBuilder.fromCurrentRequest() // Obtem a rota da requisicao atual
      .path("/{id}").buildAndExpand(obj.getId()).toUri(); // Adiciona o id do usuario gerado no final do caminho da URI
      return ResponseEntity.created(url).build(); // Retorna codigo HTTP 201 (create) comtendo a URL no cabecolha location

 }
 
  @PutMapping("/{id}")// Mapeia requisicoes HTTP PUT na rota base "/user/ {id}" (atualizacao do usuario)
  public ResponseEntity<Void> update(@Validated (UpdateUser.class)@RequestBody User obj, @PathVariable Long id){ // Aplica a regra de updateuser a receber Id e Json
     obj.setId(id); // Garante que id do objto a ser atualizado corresponde ao ID informado no parametro da URL
     this.userService.update(obj); // Executa a atualização da senha d usuario no banco de dados
     return ResponseEntity.noContent().build();//Retorna codigo HTTP 204(No Content) indicando sucesso sem corpo de resposta

  }
   
  @DeleteMapping ("/{id}") // Mapeia requisicoes HTTP DELETE na rota "/user{id}" (excluisao de usuario)
  public ResponseEntity<Void> delete(@PathVariable Long id ){ // Captura o ID da URL a ser deletado
  this.userService.delete(id); // Invoca o metodo de deleçao do servico
  return ResponseEntity.noContent().build(); // Retorna codigo HTTP 204 (No Content) confirmado a exclusao

  }
 
  
 
 }
 

 
    

    

