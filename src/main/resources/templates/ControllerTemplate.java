package {PACKAGE}.controller;

import {PACKAGE}.dto.{DOMAIN}Command;
import {PACKAGE}.dto.{DOMAIN}Info;
import {PACKAGE}.service.{DOMAIN}Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static {PACKAGE}.log.GeneralLogger.logRequest;
import static {PACKAGE}.log.GeneralLogger.logResponse;


@RestController
@RequestMapping("/api/{PL_VARIABLE}")
public class {DOMAIN}Controller {

    private final {DOMAIN}Service {VARIABLE}Service;

    public {DOMAIN}Controller({DOMAIN}Service {VARIABLE}Service) {
        this.{VARIABLE}Service = {VARIABLE}Service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<{DOMAIN}Info> findAll() {
        logRequest();

        List<{DOMAIN}Info> {PL_VARIABLE} = {VARIABLE}Service.findAll();

        logResponse(HttpStatus.OK, {PL_VARIABLE});
        return {PL_VARIABLE};
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public {DOMAIN}Info findById(@PathVariable("id") Long id) {
        logRequest();

        {DOMAIN}Info {VARIABLE} = {VARIABLE}Service.findById(id);

        logResponse(HttpStatus.OK, {VARIABLE});
        return {VARIABLE};
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public {DOMAIN}Info save(@Valid @RequestBody {DOMAIN}Command command) {
        logRequest(command);

        {DOMAIN}Info {VARIABLE} = {VARIABLE}Service.save(command);

        logResponse(HttpStatus.CREATED, {VARIABLE});
        return {VARIABLE};
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public {DOMAIN}Info update(
            @PathVariable Long id,
            @Valid @RequestBody {DOMAIN}Command command) {
        logRequest(command);

        {DOMAIN}Info {VARIABLE} = {VARIABLE}Service.update(id, command);

        logResponse(HttpStatus.OK, {VARIABLE});
        return {VARIABLE};
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        logRequest();

        {VARIABLE}Service.delete(id);

        logResponse(HttpStatus.OK, null);
    }
}
