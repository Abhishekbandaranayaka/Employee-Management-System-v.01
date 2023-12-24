package com.example.employeems.service;

import com.example.employeems.dto.EmployeeDTO;
import com.example.employeems.entity.Employee;
import com.example.employeems.repo.EmployeeRepo;
import com.example.employeems.utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@Service
@Transactional
public class EmplyeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private ModelMapper modelMapper;
    public String saveEmployee(EmployeeDTO employeeDTO){
        if(employeeRepo.existsById(employeeDTO.getEmpID())){
            return VarList.RSP_DUPLICATED;
        }else {
            employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));
            return VarList.RSP_SUCCESS;
        }
    }
    public String updateEmployee(int empID, EmployeeDTO employeeDTO) {
        if (employeeRepo.existsById(empID)) {
            Employee existingEmployee = employeeRepo.findById(empID).orElse(null);
            if (existingEmployee != null) {
                // Update the existing employee with the new data
                existingEmployee.setEmpName(employeeDTO.getEmpName());
                existingEmployee.setEmpAddress(employeeDTO.getEmpAddress());
                existingEmployee.setEmpNumber(employeeDTO.getEmpNumber());

                employeeRepo.save(existingEmployee);
                return VarList.RSP_SUCCESS;
            } else {
                // Handle the case where the employee with empID is not found
                return VarList.RSP_NO_DATA_FOUND;
            }
        } else {
            // Handle the case where the employee with empID does not exist
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
    public List<EmployeeDTO> getAllEmployee(){
        List<Employee> employeeList = employeeRepo.findAll();
        return modelMapper.map(employeeList,new TypeToken<ArrayList<EmployeeDTO>>(){
        }.getType());
    }

    public EmployeeDTO searchEmployee(int empID) {
        if(employeeRepo.existsById(empID)){
           Employee employee = employeeRepo.findById(empID).orElse(null);
            return modelMapper.map(employee,EmployeeDTO.class);
        }
        else {
            return null;
        }
    }
    public String deleteEmployee(int empID){
        if (employeeRepo.existsById(empID)){
            employeeRepo.deleteById(empID);
            return VarList.RSP_SUCCESS;
        }else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
}
