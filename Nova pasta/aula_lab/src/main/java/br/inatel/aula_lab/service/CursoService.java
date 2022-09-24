package br.inatel.aula_lab.service;

import br.inatel.aula_lab.model.Curso;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private List<Curso> listaDeCursos=new ArrayList<>();

    @PostConstruct
    public void setup(){
        Curso c1=new Curso(1L, "Rest com Spring Boot",20);
        Curso c2=new Curso(2L, "Programação Java 11",80);
        Curso c3=new Curso(3L, "Dominado a IDE eclipse",120);

        listaDeCursos.add(c1);
        listaDeCursos.add(c2);
        listaDeCursos.add(c3);
    }

    public List<Curso> pesquisarCurso(){

        return listaDeCursos;
    }

    public Optional<Curso> buscarCursoPeloId(Long cursoId) {
        Optional<Curso> opCurso=listaDeCursos.stream()
                .filter(c->c.getId().equals(cursoId))
                .findFirst();
        return opCurso;
    }

    public Curso criarCurso(Curso curso) {
        Long id= new Random().nextLong();
        curso.setId(id);
        listaDeCursos.add(curso);
        return curso;
    }

    public void atualizarCurso(Curso curso) {
        listaDeCursos.remove(curso);
        listaDeCursos.add(curso);
    }

    public void removerCursoPeloId(Long cursoId) {
        Optional<Curso> opCurso= buscarCursoPeloId(cursoId);
        if(opCurso.isPresent()) {
            Curso cursoARemover=opCurso.get();
            listaDeCursos.remove(cursoARemover);
        }
    }

    public List<Curso>pesquisarCursoPeloFragDescricao(String fragDescricao){
        if(Objects.isNull(fragDescricao) || fragDescricao.isBlank()) {
            return List.of();
        }

        List<Curso>listaDeCursoEncontradas=this.listaDeCursos.stream()
                .filter(c->c.getDescricao().trim().toLowerCase().contains( fragDescricao.trim().toLowerCase()))
                .collect(Collectors.toList());
        return listaDeCursoEncontradas;
    }

}
