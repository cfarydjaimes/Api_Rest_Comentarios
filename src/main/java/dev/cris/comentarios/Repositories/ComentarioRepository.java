package dev.cris.comentarios.Repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cris.comentarios.Models.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    
    Page<Comentario> findByPublicacionId(Long id, Pageable pageable);

    Optional<Comentario> findByIdAndPublicacionId(Long comentarioId, Long publicacionId);
}
