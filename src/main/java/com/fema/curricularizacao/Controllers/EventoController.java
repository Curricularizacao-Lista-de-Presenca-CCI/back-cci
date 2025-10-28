package com.fema.curricularizacao.Controllers;

import com.fema.curricularizacao.form.ArquivoForm;
import com.fema.curricularizacao.services.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
