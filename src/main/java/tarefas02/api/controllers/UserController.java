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

 @PostMapping("path")
 public ResponseEntity<Void> create(@Validated (CreateUser.class) @RequestBody User obj ){
      this.userService.create(obj);
      URI url = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}").buildAndExpand(obj.getId()).toUri();
      return ResponseEntity.created(url).build();

 }
 
 
 }
 

 
    

    

