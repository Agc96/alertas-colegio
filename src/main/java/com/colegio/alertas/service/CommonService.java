package com.colegio.alertas.service;

import com.colegio.alertas.entity.Anio;
import com.colegio.alertas.entity.Grado;
import com.colegio.alertas.repository.AnioRepository;
import com.colegio.alertas.repository.GradoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Anthony Gutiérrez
 */
@Service
public class CommonService {

    @Autowired
    private AnioRepository anioRepository;

    @Autowired
    private GradoRepository gradoRepository;

    public List<Anio> listarAnios() {
        return anioRepository.findAll();
    }

    public List<Grado> listarGrados() {
        return gradoRepository.findAll();
    }

}
