package com.aws.controller;

import com.aws.Repository.AgentRepository;
import com.aws.Repository.EquipmentRepository;
import com.aws.Repository.PlaceRepository;
import com.aws.Repository.ProjectRepository;
import com.aws.model.Agent;
import com.aws.model.Equipment;
import com.aws.model.Place;
import com.aws.model.Project;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v2")
public class SubjController {
    @Autowired
    AgentRepository agentRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/projects")
    public Iterable<Project> getProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/projects/agent/place/equipment/{equipmentName}")
    public List<Equipment> searchByEquipmentName(@PathVariable String equipmentName) {
        return equipmentRepository.findByEquipmentNameContains(equipmentName);
    }

    @PostMapping("/projects")
    public String addProject(@RequestBody Project project) {
        projectRepository.save(project);
        return "inserted";
    }

    @PostMapping("/projects/agent")
    public String addAgent(@RequestBody Agent agent) {
        agentRepository.save(agent);
        return "inserted";
    }

    @PostMapping("/projects/agent/place")
    public String addPlace(@RequestBody Place place) {
        placeRepository.save(place);
        return "inserted";
    }

    @PostMapping("/projects/agent/place/equipment")
    public String addEquipment(@RequestBody Equipment equipment) {
        equipmentRepository.save(equipment);
        return "inserted";
    }

    @PutMapping("/projects/{projectId}")
    public String editProject(@PathVariable String projectId, @RequestBody Project project) {
        Project updatedProject = projectRepository.findById(projectId).get();
        BeanUtils.copyProperties(project, updatedProject);
        projectRepository.save(updatedProject);
        return "updated";
    }

    @PutMapping("/projects/agent/{agentId}")
    public String editAgent(@PathVariable String agentId, @RequestBody Agent agent) {
        Agent updatedAgent = agentRepository.findById(agentId).get();
        BeanUtils.copyProperties(agent, updatedAgent);
        agentRepository.save(updatedAgent);
        return "updated";
    }

    @PutMapping("/projects/agent/place/{placeId}")
    public String editPlace(@PathVariable String placeId, @RequestBody Place place) {
        Place updatedPlace = placeRepository.findById(placeId).get();
        BeanUtils.copyProperties(place, updatedPlace);
        placeRepository.save(updatedPlace);
        return "updated";
    }

    @PutMapping("/projects/agent/place/equipment/{equipmentId}")
    public String editEquipment(@PathVariable String equipmentId, @RequestBody Equipment equipment) {
        Equipment updatedEquipment = equipmentRepository.findById(equipmentId).get();
        BeanUtils.copyProperties(equipment, updatedEquipment);
        equipmentRepository.save(updatedEquipment);
        return "updated";
    }

    @DeleteMapping("/projects")
    public String deleteProject(@RequestBody Project project) {
        projectRepository.delete(project);
        return "deleted";
    }

    @DeleteMapping("/projects/agent")
    public String deleteAgent(@RequestBody Agent agent) {
        agentRepository.delete(agent);
        return "deleted";
    }

    @DeleteMapping("/projects/agent/place")
    public String deletePlace(@RequestBody Place place) {
        placeRepository.delete(place);
        return "deleted";
    }

    @DeleteMapping("/projects/agent/place/equipment")
    public String deleteEquipment(@RequestBody Equipment equipment) {
        equipmentRepository.delete(equipment);
        return "deleted";
    }
}
