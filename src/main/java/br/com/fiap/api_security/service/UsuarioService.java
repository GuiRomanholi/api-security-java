package br.com.fiap.api_security.service;

import br.com.fiap.api_security.entity.Usuario;
import br.com.fiap.api_security.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // CRUD

    @Transactional
    @CachePut(value = "usuarios", key = "#result.id")
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Cacheable(value = "usuarios", key = "#id")
    public Usuario readUsuarioById(UUID id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "usuarios", key = "'todos'")
    public List<Usuario> readUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    @CachePut(value = "produtos", key = "#result.id")
    public Usuario updateUsuario(UUID id, Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            return null;
        }
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    @CacheEvict(value = "usuarios", key = "#id")
    public void deleteUsuario(UUID id) {
        usuarioRepository.deleteById(id);
        limparCacheTodosUsuarios(); // Atualiza o cache dos usuarios
        //limparTodoCacheUsuarios(); // Apaga todso os usuarios do cache
    }

    @CacheEvict(value = "usuarios", key = "'todos'")
    public void limparCacheTodosUsuarios(){}

    @CacheEvict(value = "usuarios", allEntries = true)
    public void limparTodoCacheUsuarios(){}

}

