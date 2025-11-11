package com.rotarywebsite.backend.service;

import com.rotarywebsite.backend.model.Noticia;
import com.rotarywebsite.backend.model.Miembro;
import com.rotarywebsite.backend.repository.NoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private MiembroService miembroService;

    // Crear nueva noticia
    public Noticia crearNoticia(String titulo, String lead, String contenido, 
                               Long autorId, String que, String cuando, 
                               String donde, String porQue, String como) {
        Miembro autor = miembroService.obtenerPorId(autorId)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        Noticia noticia = new Noticia(titulo, lead, autor);
        noticia.setContenido(contenido);
        noticia.setQue(que);
        noticia.setCuando(cuando);
        noticia.setDonde(donde);
        noticia.setPorQue(porQue);
        noticia.setComo(como);

        return noticiaRepository.save(noticia);
    }

    // Obtener todas las noticias
    public List<Noticia> obtenerTodas() {
        return noticiaRepository.findAll();
    }

    // Obtener noticia por ID
    public Noticia obtenerPorId(Long id) {
        return noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));
    }

    // Obtener últimas noticias
    public List<Noticia> obtenerUltimasNoticias() {
        return noticiaRepository.findTop10ByOrderByFechaPublicacionDesc();
    }

    // Buscar noticias por título
    public List<Noticia> buscarPorTitulo(String titulo) {
        return noticiaRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // Buscar noticias por contenido
    public List<Noticia> buscarPorContenido(String texto) {
        return noticiaRepository.findByLeadContainingIgnoreCase(texto);
    }

    // Obtener noticias por fecha
    public List<Noticia> obtenerPorFecha(LocalDate fecha) {
        return noticiaRepository.findByFechaPublicacion(fecha);
    }

    // Actualizar noticia
    public Noticia actualizarNoticia(Long noticiaId, Noticia noticiaActualizada) {
        Noticia noticia = obtenerPorId(noticiaId);
        
        noticia.setTitulo(noticiaActualizada.getTitulo());
        noticia.setLead(noticiaActualizada.getLead());
        noticia.setContenido(noticiaActualizada.getContenido());
        noticia.setQue(noticiaActualizada.getQue());
        noticia.setCuando(noticiaActualizada.getCuando());
        noticia.setDonde(noticiaActualizada.getDonde());
        noticia.setPorQue(noticiaActualizada.getPorQue());
        noticia.setComo(noticiaActualizada.getComo());

        return noticiaRepository.save(noticia);
    }

    // Eliminar noticia
    public void eliminarNoticia(Long noticiaId) {
        Noticia noticia = obtenerPorId(noticiaId);
        noticiaRepository.delete(noticia);
    }
}