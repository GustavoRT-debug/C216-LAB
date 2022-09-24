package br.inatel.aula_lab.controller;

import br.inatel.aula_lab.model.Curso;
import br.inatel.aula_lab.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoService servico;

    @GetMapping
    public List<Curso> listar() {
        List<Curso> listaCurso = servico.pesquisarCurso();
        return listaCurso;

    }

    @GetMapping("/{id}")
    public Curso buscar(@PathVariable("id") Long cursoId) {
        Optional<Curso> opCurso = servico.buscarCursoPeloId(cursoId);

        if (opCurso.isPresent()) {
            return opCurso.get();
        } else {
            String message = String.format("nenhum curso encontrado com id igual a [%s]", cursoId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }

    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Curso criar(@RequestBody Curso curso) {
        Curso cursoCriado = servico.criarCurso(curso);
        return cursoCriado;
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void atualizar(@RequestBody Curso curso) {
        servico.atualizarCurso(curso);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("id") Long cursoIdParaRemover) {
        Optional<Curso> opCurso = servico.buscarCursoPeloId(cursoIdParaRemover);
        if (opCurso.isEmpty()) {
            String message = String.format("Nenhum curso encontrado", cursoIdParaRemover);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        } else {
            Curso cursoASerRemovido = opCurso.get();
            servico.removerCursoPeloId(cursoIdParaRemover);
        }
    }

    @GetMapping("/pesquisa")
    public List<Curso> listarPeloFragDescricao(@RequestParam("descricao") String fragDescricao) {
        List<Curso> cursoEncontrados = servico.pesquisarCursoPeloFragDescricao(fragDescricao);
            return cursoEncontrados;

    }

}
