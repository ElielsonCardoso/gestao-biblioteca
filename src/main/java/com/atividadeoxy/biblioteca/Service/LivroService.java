package com.atividadeoxy.biblioteca.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.atividadeoxy.biblioteca.Class.DTO.LivroDTO;
import com.atividadeoxy.biblioteca.Class.Livro;
import com.atividadeoxy.biblioteca.Repository.LivroRepository;
import com.atividadeoxy.biblioteca.Resource.Param.LivroParam;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    public Livro createLivro(Livro novoLivro){
        return repository.save(novoLivro);
    }

    public Page<Livro> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Optional<Livro> findById(Long id){
        return repository.findById(id);
    }

    public Page<LivroDTO> findLivroByParam(Pageable pageable, LivroParam livroParam){
        return repository.findLivroByParam(pageable, livroParam);
    }

    public Livro updateLivro(Livro livroAtualizado) throws Exception {
        Optional<Livro> livroAtual =  repository.findById(livroAtualizado.getId());
        if (livroAtual.isPresent()) {
            livroAtual.get().setAutor(livroAtualizado.getAutor());
            livroAtual.get().setIsbn(livroAtualizado.getIsbn());
            livroAtual.get().setCategoria(livroAtualizado.getCategoria());
            livroAtual.get().setTitulo(livroAtualizado.getTitulo());
            livroAtual.get().setDataPublicacao(livroAtualizado.getDataPublicacao());
            return repository.save(livroAtual.get());
        }else{
            throw new Exception("Livro não encontrado!");
        }
    }

    public void deleteLivro(Long id){
        repository.deleteById(id);
    }
}
