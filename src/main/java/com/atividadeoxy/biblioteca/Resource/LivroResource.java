package com.atividadeoxy.biblioteca.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atividadeoxy.biblioteca.Class.DTO.LivroDTO;
import com.atividadeoxy.biblioteca.Class.Livro;
import com.atividadeoxy.biblioteca.Resource.Param.LivroParam;
import com.atividadeoxy.biblioteca.Service.LivroService;

@RestController
@RequestMapping("/api/livros")
public class LivroResource {

    @Autowired
    private LivroService service;

    @PostMapping
    public Livro createLivro(@RequestBody Livro novoLivro) {
        return service.createLivro(novoLivro);
    }

    @GetMapping
    public Page<Livro> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> getLivroById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByParam")
    public ResponseEntity<Page<LivroDTO>> findLivroByParam(LivroParam param,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findLivroByParam(pageable, param));
    }

    @GetMapping("/recomendacao/{usuarioid}")
    public ResponseEntity<Page<LivroDTO>> findLivrosRecomendados(@PathVariable Long usuarioid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findLivrosRecomendados(pageable, usuarioid));
    }

    @PutMapping
    public ResponseEntity<Livro> updateLivro(@RequestBody Livro livroAtualizado) throws Exception {
        return ResponseEntity.ok(service.updateLivro(livroAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        service.deleteLivro(id);
        return ResponseEntity.noContent().build();
    }

}
