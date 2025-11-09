package com.fema.curricularizacao.services;

import com.fema.curricularizacao.models.Evento;
import com.fema.curricularizacao.models.ListaPresenca;
import com.fema.curricularizacao.repositories.EventoRepository;
import com.fema.curricularizacao.repositories.ListaPresencaRepository;
import com.fema.curricularizacao.utils.exceptions.custom.ErroConversaoHTMLPDF;
import com.fema.curricularizacao.utils.exceptions.custom.ObjetoNaoEncontradoException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired private TemplateEngine templateEngine;
    @Autowired private EventoRepository eventoRepository;
    @Autowired private ListaPresencaRepository listaPresencaRepository;

    private static final String CLASSPATH_BASE = "classpath:";


    public byte[] gerarRelatorioListaChamada(Long idEvento) {

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi encontrado nenhum evento com id: " + idEvento));

        List<ListaPresenca> listaPresenca = listaPresencaRepository.findById_IdEvento(idEvento);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String baseUri;

        try {

            File baseDir = ResourceUtils.getFile(CLASSPATH_BASE);
            baseUri = baseDir.toURI().toURL().toString();

            Context context = new Context();
            context.setVariable("evento", evento);
            context.setVariable("alunos", listaPresenca);

            context.setVariable("imgPrefeitura", "classpath:images/prefeitura.png");
            context.setVariable("imgAssistencia", "classpath:images/assistencia.png");
            context.setVariable("imgCCI", "classpath:images/cci.png");

            String htmlContent = templateEngine.process("relatorio_chamada", context);

            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.withHtmlContent(htmlContent, baseUri);
            builder.toStream(os);
            builder.run();

            return os.toByteArray();
        } catch (IOException e) {
            throw new ErroConversaoHTMLPDF("Falha de I/O ao carregar recursos ou diretório base: " + e.getMessage());
        } catch (Exception e) {
            throw new ErroConversaoHTMLPDF("Falha na conversão de HTML para PDF.");
        }
    }

    public byte[] buscarPdfSalvo(Long idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi encontrado nenhum evento com id: " + idEvento));

        byte[] pdfBytes = evento.getArquivo();
        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new ObjetoNaoEncontradoException("PDF do relatório Evento ID: " + idEvento +" não foi encontrado.");
        }
        return pdfBytes;
    }
}