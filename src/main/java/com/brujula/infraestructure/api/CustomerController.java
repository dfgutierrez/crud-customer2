package com.brujula.infraestructure.api;

import javax.validation.Valid;

import com.brujula.application.service.CustomerService;
import com.brujula.infraestructure.api.dto.CustomerDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Este metodo se encarga de obtener el Customer por su ID
     *
     * @param id parametro con el cual se va a consultar el Customer
     * @return responseEntity
     */
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Customer", notes = "Use this operation to get a customer by Id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 504, message = "Gateway timeout")})
    public ResponseEntity<?> get(@ApiParam(value = "Customer Id", required = true) @PathVariable int id) {
        ResponseEntity<?> responseEntity = null;
        Map<String, Object> response = new HashMap<>();
        CustomerDto customerDto = null;
        try {
            customerDto = CustomerDto.createCustomerDto(customerService.get(id));
        } catch (DataAccessException e) {
            response.put("Mensaje", "Error consultando en la DB");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            responseEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
        }
        if (customerDto == null) {
            response.put("Mensaje", "El Customer   ID: ".concat(String.valueOf(id).concat(" No existe en la DB!")));
            responseEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
        } else {
            return ResponseEntity.ok(customerDto);
        }

    }

    /**
     * Este metodo se encarga de crear el Customer
     *
     * @param request Obj el cual trae la informacion para ser incertada en la DB
     * @return responseEntity
     */
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "create Customer", notes = "Use this operation to create a customer ")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 504, message = "Gateway timeout")})
    public ResponseEntity<?> create(@Valid @ApiParam(value = "Customer Object", required = true, type = "object") @RequestBody CustomerDto request, BindingResult bindingResult) {
        ResponseEntity<?> responseEntity = null;
        Map<String, Object> response = new HashMap<>();
        if (this.valid(bindingResult) == null) {
            try {
                responseEntity = ResponseEntity.ok(CustomerDto.createCustomerDto(customerService.create(request)));
            } catch (DataAccessException e) {
                response.put("Mensaje", "Error consultando en la DB");
                response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                responseEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        } else {
            responseEntity = this.valid(bindingResult);
        }
        return responseEntity;
    }


    /**
     * Este metodo se encarga de actualizar el Customer
     *
     * @param id      parametro con el cual se va a consultar el Customer
     * @param request Obj el cual trae la informacion para ser actualizada en la DB
     * @return responseEntity
     */
    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "update Customer", notes = "Use this operation to update a customer")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 504, message = "Gateway timeout")})
    public ResponseEntity<?> update(@Valid @ApiParam(value = "Customer Object", required = true, type = "object") @RequestBody CustomerDto request, BindingResult bindingResult,
                                    @ApiParam(value = "Customer Id", required = true) @PathVariable int id) {
        ResponseEntity<?> responseEntity = null;
        if (this.valid(bindingResult) == null) {
            Map<String, Object> response = new HashMap<>();
            try {
                responseEntity = ResponseEntity.ok(CustomerDto.createCustomerDto(customerService.update(id, request)));
            } catch (DataAccessException e) {
                response.put("Mensaje", "Error consultando en la DB");
                response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                responseEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                return responseEntity;
            }
        } else {
            responseEntity = this.valid(bindingResult);
        }
        return responseEntity;
    }


    /**
     * Este metodo se encarga de ejecuar las validaciones pertinentes para los request
     *
     * @param bindingResult Objeo con errores encontrados
     * @return responseEntity
     */
    private ResponseEntity<?> valid(BindingResult bindingResult) {
        ResponseEntity<?> responseEntity = null;
        Map<String, Object> response = new HashMap<>();
        if (bindingResult.hasErrors()) {

            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            responseEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
