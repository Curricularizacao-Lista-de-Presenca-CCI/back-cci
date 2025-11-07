package com.fema.curricularizacao.Controllers;

import com.fema.curricularizacao.DTO.BuscarEventosCadastradoDTO;
import com.fema.curricularizacao.form.ArquivoForm;
import com.fema.curricularizacao.form.EventoForm;
import com.fema.curricularizacao.services.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping(value = "/enviar-arquivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> cadastrarEventoComLista(@ModelAttribute ArquivoForm arquivoForm, @RequestPart("arquivo") MultipartFile multipartFile) {
            this.eventoService.cadastrarEventoComLista(arquivoForm, multipartFile);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/finalizar-chamada/{idEvento}")
    public ResponseEntity<Object> finalizarChamada(@PathVariable Long idEvento){
        this.eventoService.finalizarChamada(idEvento);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/buscar-lista-chamadas/{idFuncionario}")
    public ResponseEntity<List<BuscarEventosCadastradoDTO>> buscarListasChamada(@PathVariable Long idFuncionario){
        return ResponseEntity.status(200).body(this.eventoService.buscarListasChamada(idFuncionario));
    }

    @PostMapping("/alterar-lista-chamada/{idEvento}")
    public ResponseEntity<Object> alterarListaChamada(@PathVariable Long idEvento, @RequestBody EventoForm eventoForm){
        this.eventoService.alterarListaChamada(idEvento, eventoForm);
        return ResponseEntity.ok(200);
    }

    @DeleteMapping("/remover-lista-chamada/{idEvento}")
    public ResponseEntity<Object> removerListaChamada(@PathVariable Long idEvento){
        this.eventoService.removerListaChamada(idEvento);
        return ResponseEntity.status(200).body(200);
    }
}
