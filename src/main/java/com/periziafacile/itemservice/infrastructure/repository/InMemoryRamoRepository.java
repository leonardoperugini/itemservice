package com.periziafacile.itemservice.infrastructure.repository;

import com.periziafacile.itemservice.domain.model.Ramo;
import com.periziafacile.itemservice.domain.port.RamoRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class InMemoryRamoRepository implements RamoRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryRamoRepository.class);
    private final Map<Long, Ramo> rami = new HashMap<>();
    private Long idSequence = 1L;

    @Override
    public Optional<Ramo> findById(Long id) {
        return Optional.ofNullable(rami.get(id));
    }

    @Override
    public List<Ramo> findAll() {
        return new ArrayList<>(rami.values());
    }

    @Override
    public Ramo save(Ramo ramo) {
        log.info("Salvataggio ramo: {}", ramo);
        if (ramo.getId() == null) {
            ramo = new Ramo(idSequence++, ramo.getName(), ramo.getDescription());
        }
        rami.put(ramo.getId(), ramo);
        log.debug("Ramo salvato in memoria: {}", ramo);
        return ramo;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Cancellazione ramo con id={}", id);
        rami.remove(id);
        log.debug("Ramo con id={} cancellato dalla memoria", id);
    }
}