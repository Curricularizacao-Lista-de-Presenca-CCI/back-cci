package com.fema.curricularizacao.Controllers;

import com.fema.curricularizacao.services.RelatorioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/buscar-pdf/{idEvento}")
    public ResponseEntity<Object> baixarArquivo(@PathVariable Long idEvento){
        byte[] pdfBytes = relatorioService.buscarPdfSalvo(idEvento);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String dataFormatada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String nomeArquivo = "Relatorio_Evento_" + idEvento + "_" + dataFormatada + ".pdf";

        headers.setContentDispositionFormData("attachment", nomeArquivo);
        headers.setContentLength(pdfBytes.length);

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
