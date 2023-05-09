package com.api.movierental.converters;

import com.api.movierental.dtos.CustomerDto;
import com.api.movierental.exceptions.CustomerNotFoundException;
import com.api.movierental.exceptions.ResourceAlreadyExistsException;
import com.api.movierental.models.CustomerModel;
import com.api.movierental.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CustomerConverter {

    private final ModelMapper modelMapper;

    private final  CustomerRepository customerRepository;

    public CustomerConverter(ModelMapper modelMapper, CustomerRepository customerRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }

    private boolean cpfExists(CustomerDto customerDto, UUID id) throws CustomerNotFoundException {
        CustomerModel existingCustomer = customerRepository.findCustomerByCpf(customerDto.getCpf())
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with cpf %s not found", customerDto.getCpf())));
        return   !existingCustomer.getId().equals(id);
    }

    private void validateCpf(CustomerDto customerDto, UUID id) throws CustomerNotFoundException {
        if (cpfExists(customerDto, id)) {
            throw new ResourceAlreadyExistsException("Customer with CPF " + customerDto.getCpf() + " already exists.");
        }
    }

    public CustomerModel mapToCustomerModel(CustomerDto customerDto){
        return modelMapper.map(customerDto, CustomerModel.class);
    }

    public CustomerModel mapToCustomerModel(CustomerDto customerDto, UUID id) throws CustomerNotFoundException {
        CustomerModel customerModel = modelMapper.map(customerDto, CustomerModel.class);
        validateCpf(customerDto, id);
        return customerModel;
    }

    public CustomerModel mapToCustomerModel(CustomerDto customerDto, CustomerModel customerModel) throws CustomerNotFoundException {
        validateCpf(customerDto,customerModel.getId());
        modelMapper.map(customerDto, customerModel);
        return customerModel;
    }

    public CustomerDto convertToDto(CustomerModel customerModel) {
        return modelMapper.map(customerModel, CustomerDto.class);
    }

    public List<CustomerDto> convertToDtoList(List<CustomerModel> customers) {
        return customers.stream()
                .map(this::convertToDto)
                .toList();
    }

    public Page<CustomerDto> convertToDtoPage(Page<CustomerModel> customerPageDto) {
        return customerPageDto.map(this::convertToDto);
    }
}
