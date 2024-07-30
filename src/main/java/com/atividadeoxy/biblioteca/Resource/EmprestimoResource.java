package com.atividadeoxy.biblioteca.Resource;

import java.time.LocalDate;

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

import com.atividadeoxy.biblioteca.Class.DTO.EmprestimoDTO;
import com.atividadeoxy.biblioteca.Class.DTO.RecomendacaoLivroDTO;
import com.atividadeoxy.biblioteca.Class.Emprestimo;
import com.atividadeoxy.biblioteca.Class.Enum.StatusEmprestimo;
import com.atividadeoxy.biblioteca.Resource.Param.EmprestimoParam;
import com.atividadeoxy.biblioteca.Service.EmprestimoService;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoResource {

    @Autowired
    private EmprestimoService service;

    @PostMapping
    public Emprestimo createEmprestimo(@RequestBody Emprestimo novoEmprestimo) {
        if (novoEmprestimo.getId() == null) {
            novoEmprestimo.setId(service.getProximoId());
        }
        novoEmprestimo.setDataEmprestimo(LocalDate.now());
        novoEmprestimo.setStatus(StatusEmprestimo.EMPRESTADO);
        return service.createEmprestimo(novoEmprestimo);
    }

    @GetMapping
    public Page<Emprestimo> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/recomendacao/{usuarioId}")
    public ResponseEntity<Page<RecomendacaoLivroDTO>> findRecomendacaoLivros(@PathVariable Long usuarioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
            Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findRecomendacaoLivros(pageable, usuarioId));
    }

    @GetMapping("/findByParam")
    public ResponseEntity<Page<EmprestimoDTO>> findEmprestimoByParam(EmprestimoParam param,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findEmprestimoByParam(pageable, param));
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
