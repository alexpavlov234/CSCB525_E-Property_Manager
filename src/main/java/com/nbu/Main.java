package com.nbu;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.*;
import com.nbu.entity.Employee;
import com.nbu.entity.Pet;
import com.nbu.menu.CrudMenuHandler;
import com.nbu.service.*;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static void printMainMenu() {
        System.out.println("Select an option:");
        System.out.println("1. Create/Update/Read/Delete a company");
        System.out.println("2. Create/Update/Read/Delete an employee");
        System.out.println("3. Create/Update/Read/Delete a building");
        System.out.println("4. Create/Update/Read/Delete a resident");
        System.out.println("5. Create/Update/Read/Delete an apartment");
        System.out.println("6. Create/Update/Read/Delete a pet");
        System.out.println("7. Create/Update/Read/Delete a tax");
        System.out.println("8. Create/Update/Read/Delete a payment");
        System.out.println("9. Generate reports");
        System.out.println("0. Exit");
    }

    private static void handleMainMenu(){
        Scanner sc = new Scanner(System.in);
        int selectedOption = -1;
        while (selectedOption != 0) {
            printMainMenu();
            try {
                selectedOption = sc.nextInt();
                sc.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (selectedOption) {
                case 1:
                    handleCompanyCRUDMenu();
                    break;
                case 2:
                    handleEmployeeCRUDMenu();
                    break;
                case 3:
                    handleBuildingCRUDMenu();
                    break;
                case 4:
                    handleResidentCRUDMenu();
                    break;
                case 5:
                    handleApartmentCRUDMenu();
                    break;
                case 6:
                    handlePetCRUDMenu();
                    break;
                case 7:
                    handleTaxCRUDMenu();
                    break;
                case 8:
                    handlePaymentCRUDMenu();
                    break;
                case 9:
                    break;
                case 0:
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    private static void printCRUDMenu(String entityName) {
        String article = "a";
        if (entityName != null && !entityName.isEmpty()) {
            char firstChar = Character.toLowerCase(entityName.charAt(0));
            if (firstChar == 'a' || firstChar == 'e' || firstChar == 'i' || firstChar == 'o' || firstChar == 'u') {
                article = "an";
            }
        }
        System.out.println(entityName + " CRUD Menu:");
        System.out.println("1. Create " + article + " " + entityName);
        System.out.println("2. Read " + article + " " + entityName);
        System.out.println("3. Read all " + entityName);
        System.out.println("4. Update " + article + " " + entityName);
        System.out.println("5. Delete " + article + " " + entityName);
        System.out.println("0. Return to main menu");
    }
    private static void handleCompanyCRUDMenu() {
        CompanyService companyService = new CompanyService();
        Scanner scanner = new Scanner(System.in);

        new CrudMenuHandler.Builder<>("Company", CompanyDto.class, scanner)
                .onCreate(companyService::createCompany)
                .onRead(companyService::getCompany)
                .onReadAll(companyService::getAllCompanies)
                .onUpdate(companyService::updateCompany)
                .onDelete(companyService::deleteCompany)
                .build()
                .handle();
    }

    private static void handleEmployeeCRUDMenu() {
        EmployeeService employeeService = new EmployeeService();
        Scanner scanner = new Scanner(System.in);

        new CrudMenuHandler.Builder<>("Employee", EmployeeDto.class, scanner)
                .onCreate(employeeService::createEmployee)
                .onRead(employeeService::getEmployee)
                .onReadAll(employeeService::getAllEmployees)
                .onUpdate(employeeService::updateEmployee)
                .onDelete(employeeService::deleteEmployee)
                .build()
                .handle();
    }

    private static void handleBuildingCRUDMenu() {
        BuildingService buildingService = new BuildingService();
        Scanner scanner = new Scanner(System.in);

        new CrudMenuHandler.Builder<>("Building", BuildingDto.class, scanner)
                .onCreate(buildingService::createBuilding)
                .onRead(buildingService::getBuilding)
                .onReadAll(buildingService::getAllBuildings)
                .onUpdate(buildingService::updateBuilding)
                .onDelete(buildingService::deleteBuilding)
                .build()
                .handle();
    }

    private static void handleResidentCRUDMenu() {
        ResidentService residentService = new ResidentService();
        Scanner scanner = new Scanner(System.in);

        new CrudMenuHandler.Builder<>("Resident", ResidentDto.class, scanner)
                .onCreate(residentService::createResident)
                .onRead(residentService::getResident)
                .onReadAll(residentService::getAllResidents)
                .onUpdate(residentService::updateResident)
                .onDelete(residentService::deleteResident)
                .build()
                .handle();
    }

    private static void handleApartmentCRUDMenu() {
        ApartmentService apartmentService = new ApartmentService();
        Scanner scanner = new Scanner(System.in);

        new CrudMenuHandler.Builder<>("Apartment", ApartmentDto.class, scanner)
                .onCreate(apartmentService::createApartment)
                .onRead(apartmentService::getApartment)
                .onReadAll(apartmentService::getAllApartments)
                .onUpdate(apartmentService::updateApartment)
                .onDelete(apartmentService::deleteApartment)
                .build()
                .handle();
    }

    private static void handlePetCRUDMenu() {
        PetService petService = new PetService();
        Scanner scanner = new Scanner(System.in);
        new CrudMenuHandler.Builder<>("Pet", PetDto.class, scanner)
                .onCreate(petService::createPet)
                .onRead(petService::getPet)
                .onReadAll(petService::getAllPets)
                .onUpdate(petService::updatePet)
                .onDelete(petService::deletePet)
                .build()
                .handle();
    }

    private static void handleTaxCRUDMenu() {
        TaxService taxService = new TaxService();
        Scanner scanner = new Scanner(System.in);
        new CrudMenuHandler.Builder<>("Tax", TaxDto.class, scanner)
                .excludeFields("amount")
                .withCreateMessage("Note: The tax amount will be automatically calculated based on apartment area, elevator users, and pets.")
                .onCreate(taxService::createTax)
                .onRead(taxService::getTax)
                .onReadAll(taxService::getAllTaxes)
                .onUpdate(taxService::updateTax)
                .onDelete(taxService::deleteTax)
                .build()
                .handle();
    }

    private static void handlePaymentCRUDMenu() {
        PaymentService paymentService = new PaymentService();
        Scanner scanner = new Scanner(System.in);
        new CrudMenuHandler.Builder<>("Payment", PaymentDto.class, scanner)
                .withCreateMessage("Note: The payment amount must match the tax amount exactly.")
                .onCreate(paymentService::createPayment)
                .onRead(paymentService::getPayment)
                .onReadAll(paymentService::getPayments)
                .onUpdate(paymentService::updatePayment)
                .onDelete(paymentService::deletePayment)
                .build()
                .handle();
    }
    public static void main(String[] args) {
        Session session = SessionFactoryUtil.getSessionFactory().openSession();
        session.close();

        CompanyDto companyEntity = new CompanyDto();
        companyEntity.setName("Company 1");
        companyEntity.setUic("123456789");
        companyEntity.setVatNumber("BG123456789");
        companyEntity.setMailingAddress("123 Main St, City, Country");
        companyEntity.setRegisteredAddress("123 Main St, City, Country");

        CompanyService companyService = new CompanyService();
        companyService.createCompany(companyEntity);

        EmployeeDto employeeEntity = new EmployeeDto();
        employeeEntity.setFirstName("Ivan");
        employeeEntity.setMiddleName("Ivanov");
        employeeEntity.setLastName("Alexandrov");
        employeeEntity.setUcn("0444125566");
        employeeEntity.setBirthDate(LocalDate.of(2004, 4, 12));
        employeeEntity.setCompanyId(companyEntity.getId());
        EmployeeService employeeService = new EmployeeService();
        employeeService.createEmployee(employeeEntity);

        BuildingDto buildingEntity = new BuildingDto();
        buildingEntity.setAddress("456 Elm St, City, Country");
        buildingEntity.setNumberOfFloors(10);
        buildingEntity.setNumberOfApartments(20);
        buildingEntity.setCommonAreas(334.5);
        buildingEntity.setBuiltUpArea(1200.0);
        buildingEntity.setCompanyId(companyEntity.getId());
        BuildingService buildingService = new BuildingService();
        buildingService.createBuilding(buildingEntity);


        handleMainMenu();
    }

}