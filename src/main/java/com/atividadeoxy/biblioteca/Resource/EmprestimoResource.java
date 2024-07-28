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

import com.atividadeoxy.biblioteca.Class.Emprestimo;
import com.atividadeoxy.biblioteca.Service.EmprestimoService;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoResource {

    @Autowired
    private EmprestimoService service;

    @PostMapping
    public Emprestimo createEmprestimo(@RequestBody Emprestimo novoEmprestimo) {
        return service.createEmprestimo(novoEmprestimo);
    }

    @GetMapping
    public Page<Emprestimo> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> getEmprestimoById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity<Emprestimo> devolverEmprestimo(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(service.devolverEmprestimo(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmprestimo(@PathVariable Long id) {
        service.deleteEmprestimo(id);
        return ResponseEntity.noContent().build();
    }

}
