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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/{VARIABLE}s")
@Slf4j
@Tag(name = "Operations on {VARIABLE}s")
public class {DOMAIN}Controller {

    private final {DOMAIN}Service {VARIABLE}Service;
    private final HttpServletRequest request;

    public {DOMAIN}Controller({DOMAIN}Service {VARIABLE}Service, HttpServletRequest request) {
        this.{VARIABLE}Service = {VARIABLE}Service;
        this.request = request;
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
    @ResponseStatus(HttpStatus.OK)
    public List<{DOMAIN}Info> findAll() {
        logRequest();

        List<{DOMAIN}Info> {VARIABLE}s = {VARIABLE}Service.findAll();

        logResponse(HttpStatus.OK, {VARIABLE}s);
        return {VARIABLE}s;
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
    @ResponseStatus(HttpStatus.OK)
    public {DOMAIN}Info findById(@PathVariable("id") Long id) {
        logRequest();

        {DOMAIN}Info {VARIABLE} = {VARIABLE}Service.findById(id);

        logResponse(HttpStatus.OK, {VARIABLE});
        return {VARIABLE};
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public {DOMAIN}Info save(@Valid @RequestBody {DOMAIN}Command command) {
        logRequest(command);

        {DOMAIN}Info {VARIABLE} = {VARIABLE}Service.save(command);

        logResponse(HttpStatus.CREATED, {VARIABLE});
        return {VARIABLE};
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
    @ResponseStatus(HttpStatus.OK)
    public {DOMAIN}Info update(
            @PathVariable Long id,
            @Valid @RequestBody {DOMAIN}Command command) {
        logRequest(command);

        {DOMAIN}Info {VARIABLE} = {VARIABLE}Service.update(id, command);

        logResponse(HttpStatus.OK, {VARIABLE});
        return {VARIABLE};
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
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        logRequest();

        {VARIABLE}Service.delete(id);

        logResponse(HttpStatus.OK, null);
    }


    private void logRequest() {
        logRequest(null);
    }

    private void logRequest(Object body) {
        log.info(request.getMethod() + " " + request.getRequestURI() +
        (request.getQueryString() != null ? "?" + request.getQueryString() : "") +
        (body != null ? "; Body: " + body : ""));
    }

    private static void logResponse(HttpStatus status, Object body) {
        String responseFormat = "HTTP Response: %s %s";
        responseFormat += (message != null ? "; Body: %s" : "");
        log.info(String.format(responseFormat, status.value(), status.getReasonPhrase(), body));
    }
}
