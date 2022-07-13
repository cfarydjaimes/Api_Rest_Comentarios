package dev.cris.comentarios.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.cris.comentarios.Excepciones.ResourceNotFoundException;
import dev.cris.comentarios.Models.Comentario;
import dev.cris.comentarios.Repositories.ComentarioRepository;
import dev.cris.comentarios.Repositories.PublicacionRepository;

@RestController
public class ComentarioController {
    
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @GetMapping("/publicaciones/{id}/comentarios")
    public Page<Comentario> listarComentariosPorPublicacion(@PathVariable(value="id") Long id, Pageable pageable) {
        return comentarioRepository.findByPublicacionId(id, pageable);
    }

    @PostMapping("/publicaciones/{id}/comentarios")
    public Comentario guardarComentario(@PathVariable(value = "id") Long id, @Valid @RequestBody Comentario comentario){
        return publicacionRepository.findById(id).map(publicacion -> {
            comentario.setPublicacion(publicacion);
            return comentarioRepository.save(comentario);
        }).orElseThrow(() -> new ResourceNotFoundException("Publicacion con el ID: " + id + " no encontrada"));
    }

    @PutMapping("/publicaciones/{publicacionId}/comentario/{comentarioId}")
    public Comentario actualizarComentario(@PathVariable (value = "publicacionId") Long publicacionId, @PathVariable(value = "comentarioId") Long comentarioId, @Valid @RequestBody Comentario comentarioRequest){

        if(publicacionRepository.existsById(publicacionId)){
            throw new ResourceNotFoundException("Publicacion con el ID: " + comentarioId + "Publicacion no encontrada");
        }
        return comentarioRepository.findById(comentarioId).map(comentario -> {
            comentario.setTexto(comentarioRequest.getTexto());
            return comentarioRepository.save(comentario);
        }).orElseThrow(() -> new ResourceNotFoundException("Comentario con el ID: " + comentarioId + " no encontrada"));
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<?> eliminarComentario(@PathVariable(value = "publicacionId") Long publicacionId, @PathVariable(value = "comentarioId") Long comentarioId ){
        return comentarioRepository.findByIdAndPublicacionId(comentarioId, publicacionId).map(comentario -> {
            comentarioRepository.delete(comentario);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comentario con el ID: " + comentarioId + " no encontrado"));
    }
}
 