package com.fema.curricularizacao.Controllers;

import com.fema.curricularizacao.DTO.ListaPresencaDTO;
import com.fema.curricularizacao.form.ColocarPresencaForm;
import com.fema.curricularizacao.services.ListaPresencaService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lista-de-presenca")
public class ListaPresencaController {


    private final ListaPresencaService listaPresencaService;

    public ListaPresencaController(ListaPresencaService listaPresencaService) {
        this.listaPresencaService = listaPresencaService;
    }

    @PostMapping("/colocar-presenca")
    public ResponseEntity<Object> colocarPresenca (@RequestBody ColocarPresencaForm presencaForm){
        this.listaPresencaService.colocarPresenca(presencaForm);
        return ResponseEntity.ok(402);
    }

    @GetMapping("/buscar-todos-alunos/{idEvento}")
    public ResponseEntity<List<ListaPresencaDTO>> buscarTodosAlunos(@PathVariable Long idEvento){
        return ResponseEntity.status(200).body(this.listaPresencaService.buscarTodosAlunos(idEvento));
    }
}
