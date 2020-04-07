/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsc.slo.tecinfo.devweb.controller;

import br.edu.ifsc.slo.tecinfo.devweb.repository.PacienteRepository;
import br.edu.ifsc.slo.tecinfo.devweb.model.Paciente;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ramao
 */
@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteRepository pacienteRepositorio;

    @Autowired
    public PacienteController(PacienteRepository pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    //teste
    @GetMapping("/listar")
    public String getPacientes(Model model) {
	//teste
        model.addAttribute("pacientes", pacienteRepositorio.findAll());
        return "index";
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Paciente paciente) {
        
        return "add-paciente";
    }

    @PostMapping("/adicionar")
    public String addPaciente(@Valid Paciente paciente) {

        pacienteRepositorio.save(paciente);
        return "redirect:listar";
    }

    @GetMapping("/editar/{codigo}")
    public String showUpdateFormPaciente(@PathVariable("codigo") int codigo, Model model) {

        Paciente paciente = pacienteRepositorio.findById(codigo)
				.orElseThrow(() -> new IllegalArgumentException("Código de usuario inválido:" + codigo));
		model.addAttribute("paciente", paciente);
        return "update-paciente";
    }

    @PostMapping("/update/{codigo}")
    public String updatePaciente(@PathVariable("codigo") int codigo, @Valid Paciente paciente, Model model) {
        
        pacienteRepositorio.save(paciente);
        return "redirect:/pacientes/listar";
    }

    @GetMapping("apagar/{codigo}")
    public String deletePaciente(@PathVariable("codigo") int codigo, Model model) {
        
        Paciente paciente = pacienteRepositorio.findById(codigo)
				.orElseThrow(() -> new IllegalArgumentException("Código de produto inválido:" + codigo));
        pacienteRepositorio.delete(paciente);
        return "redirect:/pacientes/listar";
    }

}
