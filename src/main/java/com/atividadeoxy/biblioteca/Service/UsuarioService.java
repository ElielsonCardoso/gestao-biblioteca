package com.atividadeoxy.biblioteca.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.atividadeoxy.biblioteca.Class.DTO.UsuarioDTO;
import com.atividadeoxy.biblioteca.Class.Usuario;
import com.atividadeoxy.biblioteca.Repository.UsuarioRepository;
import com.atividadeoxy.biblioteca.Resource.Param.UsuarioParam;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario createUsuario(Usuario novoUsuario){
        return repository.save(novoUsuario);
    }

    public Page<Usuario> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Optional<Usuario> findById(Long id){
        return repository.findById(id);
    }

    public Page<UsuarioDTO> findUsuarioByParam(Pageable pageable, UsuarioParam usuarioParam){
        return repository.findUsuarioByParam(pageable, usuarioParam);
    }

    public Usuario updateUsuario(Usuario usuarioAtualizado) throws Exception {
        Optional<Usuario> usuarioAtual =  repository.findById(usuarioAtualizado.getId());
        if (usuarioAtual.isPresent()) {
            usuarioAtual.get().setNome(usuarioAtualizado.getNome());
            usuarioAtual.get().setEmail(usuarioAtualizado.getEmail());
            usuarioAtual.get().setTelefone(usuarioAtualizado.getTelefone());
            return repository.save(usuarioAtual.get());
        }else{
            throw new Exception("Usuário não encontrado!");
        }
    }

    public void deleteUsuario(Long id){
        repository.deleteById(id);
    }

    public Long getProximoId(){
        return repository.getProximoId();
    }
}
