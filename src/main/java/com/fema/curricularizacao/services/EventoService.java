package com.fema.curricularizacao.services;

import com.fema.curricularizacao.form.ArquivoForm;
import com.fema.curricularizacao.models.Evento;
import com.fema.curricularizacao.models.Funcionario;
import com.fema.curricularizacao.repositories.EventoRepository;
import com.fema.curricularizacao.repositories.FuncionarioRepository;
import com.fema.curricularizacao.utils.conversao.arquivo.ExcelReaderUtil;
import com.fema.curricularizacao.utils.conversao.dataHora.LocalDateUtils;
import com.fema.curricularizacao.utils.exceptions.custom.ArquivoInvalidoException;
import com.fema.curricularizacao.utils.exceptions.custom.ObjetoNaoEncontradoException;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;

@Service
public class EventoService {

    private final ListaPresencaService listaPresencaService;
    private final EventoRepository eventoRepository;
    private final FuncionarioRepository funcionarioRepository;

    private static final int COLUNA_DATA_EVENTO = 0;

    @Autowired
    public EventoService(ListaPresencaService listaPresencaService, EventoRepository eventoRepository, FuncionarioRepository funcionarioRepository) {
        this.listaPresencaService = listaPresencaService;
        this.eventoRepository = eventoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public void cadastrarEventoComLista(ArquivoForm arquivoForm, MultipartFile arquivo) {

        if (arquivo.isEmpty()) {
            throw new ArquivoInvalidoException("O arquivo de upload não pode estar vazio.");
        }

        Iterator<Row> rowIterator = ExcelReaderUtil.buscarExcel(arquivo);
        if (rowIterator.hasNext()) rowIterator.next();

        Row linhaMetadados = null;
        if (rowIterator.hasNext()) {
            linhaMetadados = rowIterator.next();
        } else {
            throw new ArquivoInvalidoException("O arquivo não possui a linha com os dados do evento (Linha 2).");
        }

        Cell cellMetadados = linhaMetadados.getCell(0);
        if (cellMetadados == null) {
            throw new ArquivoInvalidoException("Não foi possível ler os metadados do evento (célula A2 está vazia).");
        }

        if (rowIterator.hasNext()) rowIterator.next();
        if (!rowIterator.hasNext()) {
            throw new ArquivoInvalidoException("O arquivo não possui dados de participantes (linha 5).");
        }

        Row linhaPrimeiroRegistro = rowIterator.next();
        Cell cellDataEvento = linhaPrimeiroRegistro.getCell(COLUNA_DATA_EVENTO);
        if (cellDataEvento == null) {
            throw new ArquivoInvalidoException("Não foi possível extrair a data do evento da primeira linha de dados.");
        }

        String dataString = ExcelReaderUtil.convertNumericForString(cellDataEvento);
        String metadadosCompletos = ExcelReaderUtil.convertNumericForString(cellMetadados);

        Funcionario funcionarioEncontrado = this.funcionarioRepository.findById(arquivoForm.getIdFuncionario())
                .orElseThrow(()-> new ObjetoNaoEncontradoException("Não foi encontrado nenhum funcionário com id: " + arquivoForm.getIdFuncionario()));
        Evento novoEvento = new Evento();
            novoEvento.setFuncionario(funcionarioEncontrado);
            novoEvento.setLocal(arquivoForm.getLocal());
            novoEvento.setTitulo(extrairTituloDoMetadado(metadadosCompletos));
            novoEvento.setData(LocalDateUtils.converterStringParaLocalDate(dataString));
            novoEvento.setArquivo(null);

        Evento eventofinal = this.eventoRepository.save(novoEvento);
        this.listaPresencaService.processarESalvarListaChamada(arquivo ,eventofinal.getId());
    }

    private String extrairTituloDoMetadado(String metadadosCompletos) {
        try {
            int inicio = metadadosCompletos.indexOf('-') + 1;
            int fim = metadadosCompletos.indexOf(" CCI", inicio);
            if (fim == -1) fim = metadadosCompletos.length();

            String tituloBruto = metadadosCompletos.substring(inicio, fim);

            return tituloBruto.trim().split(" ")[0];
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao extrair título do metadado: " + metadadosCompletos);
        }
    }
}