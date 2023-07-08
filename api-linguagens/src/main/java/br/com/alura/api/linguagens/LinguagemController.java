package br.com.alura.api.linguagens;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/linguagens")
public class LinguagemController {

    private final LinguagemRepository repositorio;

    public LinguagemController(LinguagemRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Linguagem> obterLinguagens() {
        return repositorio.findByOrderByRanking();
    }

    // Ler uma linguagem
    @GetMapping("/{id}")
    public ResponseEntity<Linguagem> obterLinguagemPorId(@PathVariable String id) {
        Optional<Linguagem> linguagemOptional = repositorio.findById(id);

        return linguagemOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    // Publicar uma nova linguagem
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Define o status 201 Created
    public ResponseEntity<Linguagem> cadastrarLinguagem(@RequestBody Linguagem linguagem) {
        Linguagem linguagemSalva = repositorio.save(linguagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(linguagemSalva);
    }


    // Atualizar uma linguagem
    @PutMapping("/{id}")
    public ResponseEntity<Linguagem> atualizarLinguagem(@PathVariable String id, @RequestBody Linguagem linguagemAtualizada) {
        Optional<Linguagem> linguagemOptional = repositorio.findById(id);

        if (linguagemOptional.isPresent()) {
            Linguagem linguagem = linguagemOptional.get();
            linguagem.setTitle(linguagemAtualizada.getTitle());
            linguagem.setImage(linguagemAtualizada.getImage());
            linguagem.setRanking(linguagemAtualizada.getRanking());
            Linguagem linguagemAtualizadaSalva = repositorio.save(linguagem);
            return ResponseEntity.ok(linguagemAtualizadaSalva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Deletar uma linguagem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirLinguagem(@PathVariable String id) {
        Optional<Linguagem> linguagemOptional = repositorio.findById(id);

        if (linguagemOptional.isPresent()) {
            repositorio.delete(linguagemOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
