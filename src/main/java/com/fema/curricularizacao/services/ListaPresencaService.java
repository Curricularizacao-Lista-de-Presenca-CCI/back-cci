package com.fema.curricularizacao.services;

import com.fema.curricularizacao.DTO.ListaPresencaDTO;
import com.fema.curricularizacao.enums.Presenca;
import com.fema.curricularizacao.form.ColocarPresencaForm;
import com.fema.curricularizacao.models.ListaPresenca;
import com.fema.curricularizacao.models.ListaPresencaPK;
import com.fema.curricularizacao.repositories.ListaPresecaRepository;
import com.fema.curricularizacao.utils.conversao.arquivo.ExcelReaderUtil;
import com.fema.curricularizacao.utils.exceptions.custom.ObjetoNaoEncontradoException;
import com.fema.curricularizacao.utils.exceptions.custom.PersistenciaDadosException;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ListaPresencaService {

    private final ListaPresecaRepository listaPresencaRepository;

    private static final int COLUNA_DADOS_PARTICIPANTE = 2;

    public ListaPresencaService(ListaPresecaRepository listaPresencaRepository) {
        this.listaPresencaRepository = listaPresencaRepository;
    }

    public void salvarListaDePresenca(List<ListaPresenca> presencasLista) {
        try {
            listaPresencaRepository.saveAll(presencasLista);
        } catch (Exception e) {
            throw new PersistenciaDadosException("Falha ao salvar a lista de presença no banco de dados.", e);
        }
    }

    @Transactional
    public void processarESalvarListaChamada(MultipartFile arquivo, Long idEventoGerado) {
        List<ListaPresenca> presencasParaSalvar = new ArrayList<>();

        Iterator<Row> rowIterator = ExcelReaderUtil.buscarExcel(arquivo);

        if (rowIterator.hasNext()) rowIterator.next();
        if (rowIterator.hasNext()) rowIterator.next();
        if (rowIterator.hasNext()) rowIterator.next();
        if (rowIterator.hasNext()) rowIterator.next();

        while (rowIterator.hasNext()) {
            Row linhaAtual = rowIterator.next();
            Cell cellDadosParticipante = linhaAtual.getCell(COLUNA_DADOS_PARTICIPANTE);

            if (cellDadosParticipante != null && !cellDadosParticipante.toString().trim().isEmpty()) {
                String dadosCompletos = ExcelReaderUtil.convertNumericForString(cellDadosParticipante);
                String indenficidaorUnicoAluno = dadosCompletos.trim();

                ListaPresencaPK chave = new ListaPresencaPK();
                chave.setIdEvento(idEventoGerado);
                chave.setNomeAluno(indenficidaorUnicoAluno);

                ListaPresenca registro = new ListaPresenca();
                registro.setId(chave);
                registro.setPresencaEnum(Presenca.NAO);

                presencasParaSalvar.add(registro);
            }
        }

        if (!presencasParaSalvar.isEmpty()) {
            this.salvarListaDePresenca(presencasParaSalvar);
        }
    }

    private String extrairNomeDoParticipante(String dadosCompletos) {
        int indiceHifen = dadosCompletos.indexOf(" - ");
        if (indiceHifen == -1) {
            return dadosCompletos.trim();
        }
        String nome = dadosCompletos.substring(indiceHifen + 3);
        return nome.trim();
    }

    @Transactional
    public void colocarPresenca(ColocarPresencaForm presencaForm) {
        ListaPresenca encontrarAluno = this.listaPresencaRepository.findById_IdEventoAndId_NomeAluno(presencaForm.getIdEvento() ,presencaForm.getNomeAluno())
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi encontrado nenhum aluno com nome: " + extrairNomeDoParticipante(presencaForm.getNomeAluno()) + " no evento de id: " + presencaForm.getIdEvento() + "."));
        if(encontrarAluno.getPresencaEnum() == Presenca.NAO) {
            encontrarAluno.setPresencaEnum(Presenca.SIM);
        } else {
            encontrarAluno.setPresencaEnum(Presenca.NAO);
        }
        this.listaPresencaRepository.save(encontrarAluno);
    }

    public List<ListaPresencaDTO> buscarTodosAlunos(Long idEvento) {
        return ListaPresencaDTO.converter(this.listaPresencaRepository.findAllById_IdEvento(idEvento));
    }
}