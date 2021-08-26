package {PACKAGE}.service;

import {PACKAGE}.domain.{DOMAIN};
import {PACKAGE}.dto.{DOMAIN}Command;
import {PACKAGE}.dto.{DOMAIN}Info;
import {PACKAGE}.exception.NotFoundException;
import {PACKAGE}.repository.{DOMAIN}Repository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class {DOMAIN}Service {

    private final {DOMAIN}Repository {VARIABLE}Repository;
    private final ModelMapper modelMapper;

    public {DOMAIN}Service({DOMAIN}Repository {VARIABLE}Repository, ModelMapper modelMapper) {
        this.{VARIABLE}Repository = {VARIABLE}Repository;
        this.modelMapper = modelMapper;
    }

    public List<{DOMAIN}Info> findAll() {
        return {VARIABLE}Repository.findAll().stream().map(this::mapTo{DOMAIN}Info).collect(Collectors.toList());
    }

    public {DOMAIN}Info findById(Long id) {
        return mapTo{DOMAIN}Info({VARIABLE}Repository.findById(id).orElseThrow(this::getNotFoundException));
    }

    public {DOMAIN}Info save({DOMAIN}Command command) {
        return mapTo{DOMAIN}Info({VARIABLE}Repository.save(mapTo{DOMAIN}(command)));
    }

    public {DOMAIN}Info update(Long id, {DOMAIN}Command command) {
        if (!{VARIABLE}Repository.existsById(id)) {
            throw getNotFoundException();
        }
        {DOMAIN} toSave = mapTo{DOMAIN}(command);
        toSave.setId(id);
        return mapTo{DOMAIN}Info({VARIABLE}Repository.save(toSave));
    }

    public void delete(Long id) {
        if (!{VARIABLE}Repository.existsById(id)) {
            throw getNotFoundException();
        }
        {VARIABLE}Repository.deleteById(id);
    }


    private {DOMAIN}Info mapTo{DOMAIN}Info({DOMAIN} {VARIABLE}) {
        return modelMapper.map({VARIABLE}, {DOMAIN}Info.class);
    }

    private {DOMAIN} mapTo{DOMAIN}({DOMAIN}Command command) {
        return modelMapper.map(command, {DOMAIN}.class);
    }

    private NotFoundException getNotFoundException() {
        return new NotFoundException().addNotFound("{VARIABLE}", "{DOMAIN} not found!");
    }
}