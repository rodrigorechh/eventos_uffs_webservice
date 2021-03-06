package com.eventos.uffs.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventos.uffs.demo.business.CrudEventosBusiness.ConsultarEventosBusiness;
import com.eventos.uffs.demo.param.ConsultarEventosParam;
import com.eventos.uffs.demo.sql.entity.Evento;
import com.eventos.uffs.demo.sql.repository.EventoRepository;

@RequestMapping("eventos")
@RestController
@CrossOrigin(origins = "*")
public class CrudEventosController {

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    ConsultarEventosBusiness consultarEventosBusiness;

    @GetMapping
    public ResponseEntity<List<Evento>> getEventos() {
        return ResponseEntity.ok(eventoRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEvento(@PathVariable final String id) {
    	return ResponseEntity.ok(eventoRepository.findById(Integer.valueOf(id)).get());
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Evento> createEventos(@RequestBody Evento evento) {
    	Evento eventoSalvo = eventoRepository.save(evento);
    	return new ResponseEntity<Evento>(eventoSalvo, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Evento> updateEventos(@RequestBody Evento evento) { 
            Evento eventoAtualizado =  eventoRepository.save(evento);

            return new ResponseEntity<>(eventoAtualizado, HttpStatus.OK);
    }

  
    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Evento>> createEventos(@RequestParam(required = false) Integer idEvento, @RequestParam(required = false) Integer areaConhecimentoId,
                                                        @RequestParam(required = false) Integer modalidadeId, @RequestParam(required = false) Boolean data,
                                                        @RequestParam(required = false) Boolean horas, @RequestParam(required = false) Boolean custo) {
       horas = (horas == null) ? false : horas;
       custo = (custo == null) ? false : custo;
       data = (data == null) ? false : data;
       var parametrosConsulta = new ConsultarEventosParam(idEvento, areaConhecimentoId, modalidadeId, data, horas, custo);
       return consultarEventosBusiness.consultaFiltradaEventos(parametrosConsulta);
    }
}
