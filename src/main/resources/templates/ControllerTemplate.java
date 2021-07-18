package {PACKAGE}.controller;

import {PACKAGE}.dto.{DOMAIN}Command;
import {PACKAGE}.dto.{DOMAIN}Info;
import {PACKAGE}.service.{DOMAIN}Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/{VARIABLE}s")
@Slf4j
@Tag(name = "Operations on {VARIABLE}s")
public class {DOMAIN}Controller {

    private static final String POSITIVE_RESPONSE = "HTTP Response: OK, Body: %s";

    private final {DOMAIN}Service {VARIABLE}Service;

    public {DOMAIN}Controller({DOMAIN}Service {VARIABLE}Service) {
        this.{VARIABLE}Service = {VARIABLE}Service;
    }

    @Operation(summary = "Query all stored {VARIABLE}s")
    @ApiResponse(
            responseCode = "200",
            description = "Return all stored {VARIABLE}s.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = {DOMAIN}Info.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<{DOMAIN}Info>> findAll() {
        log.info("HTTP GET /api/{VARIABLE}s - List all {VARIABLE}s");

        List<{DOMAIN}Info> {VARIABLE} = {VARIABLE}Service.findAll();
        log.info(String.format(POSITIVE_RESPONSE, {VARIABLE}));

        return new ResponseEntity<>({VARIABLE}, HttpStatus.OK);
    }


    @Operation(summary = "Query one stored {VARIABLE}")
    @ApiResponse(
            responseCode = "200",
            description = "Return the specified {VARIABLE} by id.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = {DOMAIN}Info.class))
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "{DOMAIN} not found by the given id."
    )
    @GetMapping("/{id}")
    public ResponseEntity<{DOMAIN}Info> findById(@PathVariable("id") Long id) {
        log.info(String.format("HTTP GET /api/{VARIABLE}s/%s - Query this {VARIABLE}", id));

        {DOMAIN}Info {VARIABLE} = {VARIABLE}Service.findById(id);
        log.info(String.format(POSITIVE_RESPONSE, {VARIABLE}));

        return new ResponseEntity<>({VARIABLE}, HttpStatus.OK);
    }


    @Operation(summary = "Create a new {VARIABLE}")
    @ApiResponse(
            responseCode = "201",
            description = "Return the newly created {VARIABLE}.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = {DOMAIN}Info.class))
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Bad request on validation error."
    )
    @PostMapping()
    public ResponseEntity<{DOMAIN}Info> save(@Valid @RequestBody {DOMAIN}Command command) {
        log.info(String.format("HTTP POST /api/{variable}s - Create a new {VARIABLE} with these data: %s", command));

        {DOMAIN}Info {VARIABLE} = {VARIABLE}Service.save(command);
        log.info(String.format("HTTP Response: CREATED, Body: %s", {VARIABLE}));

        return new ResponseEntity<>({VARIABLE}, HttpStatus.CREATED);
    }


    @Operation(summary = "Modify an existing {VARIABLE}")
    @ApiResponse(
            responseCode = "200",
            description = "Return the modified {VARIABLE}.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = {DOMAIN}Info.class))
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Bad request on validation error."
    )
    @PutMapping("/{id}")
    public ResponseEntity<{DOMAIN}Info> update(
            @PathVariable Long id,
            @Valid @RequestBody {DOMAIN}Command command) {
        log.info(String.format("HTTP PUT /api/{VARIABLE}s/%s - Modify an existing {VARIABLE} with these data: %s", id, command));

        {DOMAIN}Info {VARIABLE} = {VARIABLE}Service.update(id, command);
        log.info(String.format(POSITIVE_RESPONSE, {VARIABLE}));

        return new ResponseEntity<>({VARIABLE}, HttpStatus.OK);
    }

    @Operation(summary = "Delete an existing {VARIABLE}")
    @ApiResponse(
            responseCode = "200",
            description = "Successfully deleted the {VARIABLE} by the given id."
    )
    @ApiResponse(
            responseCode = "400",
            description = "Bad request on nonexistent {VARIABLE}."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<{DOMAIN}Info> delete(@PathVariable Long id) {
        log.info(String.format("HTTP DELETE /api/{VARIABLE}s/%s - Delete an existing {VARIABLE}", id));

        {VARIABLE}Service.delete(id);
        log.info("HTTP Response: OK, {DOMAIN} successfully deleted.");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
