package com.rd.projetointegrador.rdservicesapi.service;

import com.rd.projetointegrador.rdservicesapi.dto.Genero;
import com.rd.projetointegrador.rdservicesapi.dto.Planos;
import com.rd.projetointegrador.rdservicesapi.dto.ServicoPlano;
import com.rd.projetointegrador.rdservicesapi.entity.GeneroEntity;
import com.rd.projetointegrador.rdservicesapi.entity.PlanosEntity;
import com.rd.projetointegrador.rdservicesapi.entity.ServicoPlanoEntity;
import com.rd.projetointegrador.rdservicesapi.repository.PlanosRepository;
import com.rd.projetointegrador.rdservicesapi.repository.ServicoPlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanosService {

    @Autowired private PlanosRepository repository;
    @Autowired private ServicoPlanoRepository servRepository;
    @Autowired private ServicoPlanoService servPlanService;

    //EntityToDto
    public Planos conversaoPlanoDTO(PlanosEntity planoEntity, Planos plano) {
        plano.setIdPlano(planoEntity.getIdPlano());
        plano.setNmPlano(planoEntity.getNmPlano());
        plano.setDsPlano(planoEntity.getDsPlano());
        plano.setVlPlano(planoEntity.getVlPlano());

        return plano;
    }
    //ListEntityToListDTO
    public List<Planos> conversaoPlanosDTO(List<PlanosEntity> planosEntities, List<Planos> planos){
        for(PlanosEntity planoEntity : planosEntities){
            Planos plano = new Planos();
            plano = conversaoPlanoDTO(planoEntity,plano);
            planos.add(plano);
        }
        return planos;
    }

    // DtoToEntity
    public PlanosEntity conversaoPlanoEntity(Planos plano, PlanosEntity planoEntity) {
        planoEntity.setIdPlano(plano.getIdPlano());
        planoEntity.setNmPlano(plano.getNmPlano());
        planoEntity.setDsPlano(plano.getDsPlano());
        planoEntity.setVlPlano(plano.getVlPlano());

        return planoEntity;
    }
    //ListDTOToListEntity
    public List<PlanosEntity> conversaoPlanosEntity( List<Planos> planos, List<PlanosEntity> planosEntities){
        for(Planos plano : planos){
            PlanosEntity planoEntity = new PlanosEntity();
            planoEntity = conversaoPlanoEntity(plano,planoEntity);
            planosEntities.add(planoEntity);
        }
        return planosEntities;
    }



    /*public List<Planos> conversaoPlanosDTO(List<PlanosEntity> planosEntities, List<Planos> planos) {

        for (PlanosEntity planoEntity : planosEntities) {
            Planos plano = new Planos();
            plano.setIdPlano(planoEntity.getIdPlano());
            plano.setDsPlano(planoEntity.getDsPlano());
            plano.setVlPlano(planoEntity.getVlPlano());
            plano.setNmPlano(planoEntity.getNmPlano());

            List<ServicoPlano> servsPlanoDTO = new ArrayList<>();

            for (ServicoPlanoEntity servPlanoEntity : planoEntity.getServicos()) {
                ServicoPlano servPlano = new ServicoPlano();
                servPlano.setIdServicoPlano(servPlanoEntity.getIdServicoPlano());
                servPlano.setDsServico(servPlano.getDsServico());

                servsPlanoDTO.add(servPlano);
            }

            plano.setServicos(servsPlanoDTO);
            planos.add(plano);
        }
        return planos;
    }
*/
    public PlanosEntity getPlano(BigInteger idPlano) {
        System.out.println("IdPlano: " + idPlano);
        Optional<PlanosEntity> optional = repository.findById(idPlano);
        return optional.get();
    }

    public List<PlanosEntity> getPlanos() {
        return repository.findAll();
    }
        public List<Planos> getPlanosDTO() {
            List<PlanosEntity> planosEntities = getPlanos();
            List<Planos> planos = new ArrayList<>();
            planos = conversaoPlanosDTO(planosEntities, planos);

            return planos;
        }

    @Transactional
    public String cadastrarPlano(Planos plano){

        PlanosEntity planosEntity = new PlanosEntity();

        planosEntity.setNmPlano(plano.getNmPlano());
        planosEntity.setDsPlano(plano.getDsPlano());
        planosEntity.setVlPlano(plano.getVlPlano());
        planosEntity.setIdServicoPlano(plano.getIdServicoPlano());

        repository.save(planosEntity);

        System.out.println(plano.getIdPlano() + " . " + plano.getNmPlano() + " . " +plano.getDsPlano() + " . " + plano.getVlPlano());

        return "Plano cadastrado com sucesso";

    }

    @Transactional
    public String alterarPlano(Planos plano, BigInteger idPlano){

        PlanosEntity planoEntity = getPlano(idPlano);

        planoEntity.setNmPlano(plano.getNmPlano());
        planoEntity.setDsPlano(plano.getDsPlano());
        planoEntity.setVlPlano(plano.getVlPlano());
        //planoEntity.setIdServicoPlano(plano.getIdServicoPlano());

        List<ServicoPlanoEntity> listaServPlano = new ArrayList<>();
        for(ServicoPlano servico : plano.getServicos()){

            BigInteger idServicoPlano = servico.getIdServicoPlano();
            Optional<ServicoPlanoEntity> optional = servRepository.findById(idServicoPlano);
            ServicoPlanoEntity servPlanoEntity = optional.get();
            listaServPlano.add(servPlanoEntity);
        }

        planoEntity.setServicos(listaServPlano);

        planoEntity = repository.save(planoEntity);
        return "Alteração realizada com sucesso";
    }

    public String excluirPlano(BigInteger idPlano){
        repository.deleteById(idPlano);
        return "Exclusão de plano realizada com sucesso";

    }


}
