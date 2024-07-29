package com.atividadeoxy.biblioteca.Service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.atividadeoxy.biblioteca.Class.DTO.EmprestimoDTO;
import com.atividadeoxy.biblioteca.Class.DTO.RecomendacaoLivroDTO;
import com.atividadeoxy.biblioteca.Class.Emprestimo;
import com.atividadeoxy.biblioteca.Class.Enum.StatusEmprestimo;
import com.atividadeoxy.biblioteca.Repository.EmprestimoRepository;
import com.atividadeoxy.biblioteca.Resource.Param.EmprestimoParam;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository repository;

    public Emprestimo createEmprestimo(Emprestimo novoEmprestimo){
        return repository.save(novoEmprestimo);
    }

    public Page<Emprestimo> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Optional<Emprestimo> findById(Long id){
        return repository.findById(id);
    }

    public Emprestimo devolverEmprestimo(Long id) throws Exception {
        Optional<Emprestimo> emprestimoAtual =  repository.findById(id);
        if (emprestimoAtual.isPresent()) {
            emprestimoAtual.get().setDataDevolucao(LocalDate.now());
            emprestimoAtual.get().setStatus(StatusEmprestimo.DEVOLVIDO);
            return repository.save(emprestimoAtual.get());
        }else{
            throw new Exception("Empréstimo não encontrado!");
        }
    }

    public void deleteEmprestimo(Long id){
        repository.deleteById(id);
    }

    public Page<RecomendacaoLivroDTO> findRecomendacaoLivros(Pageable pageable, Long usuarioId){
        return repository.findRecomendacaoLivros(pageable, usuarioId);
    }

    public Page<EmprestimoDTO> findEmprestimoByParam(Pageable pageable, EmprestimoParam emprestimoParam){
        return repository.findEmprestimoByParam(pageable, emprestimoParam);
    }
}
