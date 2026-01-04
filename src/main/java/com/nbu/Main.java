package com.nbu;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.*;
import com.nbu.menu.CrudMenuHandler;
import com.nbu.service.*;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;
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
                    handleReportsMenu();
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

    private static void printReportsMenu() {
        System.out.println("\n=== Reports Menu ===");
        System.out.println("1. Filter & Sort: Companies by revenue");
        System.out.println("2. Filter & Sort: Employees by name");
        System.out.println("3. Filter & Sort: Employees by building count");
        System.out.println("4. Filter & Sort: Residents by name");
        System.out.println("5. Filter & Sort: Residents by age");
        System.out.println("6. Summary: Buildings per employee");
        System.out.println("7. Summary: Apartments in building");
        System.out.println("8. Summary: Residents in building");
        System.out.println("9. Summary: Taxes by company/building/employee");
        System.out.println("10. Summary: Payments by company/building/employee");
        System.out.println("0. Return to main menu");
    }

    private static void handleReportsMenu() {
        Scanner sc = new Scanner(System.in);
        ReportService reportService = new ReportService();
        int selectedOption = -1;

        while (selectedOption != 0) {
            printReportsMenu();
            System.out.print("Select option: ");
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
                    handleCompaniesByRevenue(sc, reportService);
                    break;
                case 2:
                    handleEmployeesByName(sc, reportService);
                    break;
                case 3:
                    handleEmployeesByBuildingCount(sc, reportService);
                    break;
                case 4:
                    handleResidentsByName(sc, reportService);
                    break;
                case 5:
                    handleResidentsByAge(sc, reportService);
                    break;
                case 6:
                    handleBuildingsPerEmployee(sc, reportService);
                    break;
                case 7:
                    handleApartmentsInBuilding(sc, reportService);
                    break;
                case 8:
                    handleResidentsInBuilding(sc, reportService);
                    break;
                case 9:
                    handleTaxSummary(sc, reportService);
                    break;
                case 10:
                    handlePaymentSummary(sc, reportService);
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void handleCompaniesByRevenue(Scanner sc, ReportService reportService) {
        System.out.print("Enter year: ");
        int year = sc.nextInt();
        System.out.print("Enter month (1-12): ");
        int month = sc.nextInt();
        sc.nextLine();
        System.out.print("Sort ascending? (y/n): ");
        boolean ascending = sc.nextLine().trim().equalsIgnoreCase("y");

        List<CompanyPaymentDto> results = reportService.getCompaniesByRevenue(year, month, ascending);
        System.out.println("\n=== Companies by Revenue (" + month + "/" + year + ") ===");
        if (results.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (CompanyPaymentDto dto : results) {
                System.out.println("Company: " + dto.getCompanyName() + " | Revenue: " + dto.getTotalAmountOfPayments());
            }
        }
    }

    private static void handleEmployeesByName(Scanner sc, ReportService reportService) {
        System.out.print("Enter company ID: ");
        long companyId = sc.nextLong();
        sc.nextLine();
        System.out.print("Sort ascending? (y/n): ");
        boolean ascending = sc.nextLine().trim().equalsIgnoreCase("y");

        List<EmployeeBuildingsSummaryDto> results = reportService.getEmployeesByName(companyId, ascending);
        System.out.println("\n=== Employees sorted by name ===");
        if (results.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (EmployeeBuildingsSummaryDto dto : results) {
                System.out.println("Employee: " + dto.getFirstName() + " " + dto.getMiddleName() + " " + dto.getLastName() +
                        " | UCN: " + dto.getUcn() + " | Buildings: " + dto.getBuildingCount());
            }
        }
    }

    private static void handleEmployeesByBuildingCount(Scanner sc, ReportService reportService) {
        System.out.print("Enter company ID: ");
        long companyId = sc.nextLong();
        sc.nextLine();
        System.out.print("Sort ascending? (y/n): ");
        boolean ascending = sc.nextLine().trim().equalsIgnoreCase("y");

        List<EmployeeBuildingsSummaryDto> results = reportService.getEmployeesByBuildingCount(companyId, ascending);
        System.out.println("\n=== Employees sorted by building count ===");
        if (results.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (EmployeeBuildingsSummaryDto dto : results) {
                System.out.println("Employee: " + dto.getFirstName() + " " + dto.getLastName() +
                        " | Buildings: " + dto.getBuildingCount());
            }
        }
    }

    private static void handleResidentsByName(Scanner sc, ReportService reportService) {
        System.out.print("Enter building ID: ");
        long buildingId = sc.nextLong();
        sc.nextLine();
        System.out.print("Sort ascending? (y/n): ");
        boolean ascending = sc.nextLine().trim().equalsIgnoreCase("y");

        List<BuildingResidentDto> results = reportService.getResidentsByName(buildingId, ascending);
        System.out.println("\n=== Residents sorted by name ===");
        if (results.isEmpty()) {
            System.out.println("No residents found.");
        } else {
            for (BuildingResidentDto dto : results) {
                System.out.println("Resident: " + dto.getFirstName() + " " + dto.getMiddleName() + " " + dto.getLastName() +
                        " | Age: " + dto.getAge() + " | Apt: " + dto.getApartmentNumber() + " | Floor: " + dto.getFloor());
            }
        }
    }

    private static void handleResidentsByAge(Scanner sc, ReportService reportService) {
        System.out.print("Enter building ID: ");
        long buildingId = sc.nextLong();
        sc.nextLine();
        System.out.print("Sort ascending? (y/n): ");
        boolean ascending = sc.nextLine().trim().equalsIgnoreCase("y");

        List<BuildingResidentDto> results = reportService.getResidentsByAge(buildingId, ascending);
        System.out.println("\n=== Residents sorted by age ===");
        if (results.isEmpty()) {
            System.out.println("No residents found.");
        } else {
            for (BuildingResidentDto dto : results) {
                System.out.println("Resident: " + dto.getFirstName() + " " + dto.getLastName() +
                        " | Age: " + dto.getAge() + " | Apt: " + dto.getApartmentNumber());
            }
        }
    }

    private static void handleBuildingsPerEmployee(Scanner sc, ReportService reportService) {
        System.out.print("Enter company ID: ");
        long companyId = sc.nextLong();
        sc.nextLine();
        System.out.print("Show detailed list? (y/n): ");
        boolean detailed = sc.nextLine().trim().equalsIgnoreCase("y");

        if (detailed) {
            List<EmployeeBuildingDetailDto> results = reportService.getEmployeeBuildingsDetail(companyId);
            System.out.println("\n=== Buildings per Employee (Detailed) ===");
            if (results.isEmpty()) {
                System.out.println("No data found.");
            } else {
                for (EmployeeBuildingDetailDto dto : results) {
                    System.out.println("Employee: " + dto.getEmployeeFirstName() + " " + dto.getEmployeeLastName() +
                            " | Building: " + dto.getBuildingAddress() +
                            " | Floors: " + dto.getNumberOfFloors() + " | Apartments: " + dto.getNumberOfApartments());
                }
            }
        } else {
            List<EmployeeBuildingsSummaryDto> results = reportService.getEmployeeBuildingsSummary(companyId);
            System.out.println("\n=== Buildings per Employee (Summary) ===");
            if (results.isEmpty()) {
                System.out.println("No data found.");
            } else {
                for (EmployeeBuildingsSummaryDto dto : results) {
                    System.out.println("Employee: " + dto.getFirstName() + " " + dto.getLastName() +
                            " | Total Buildings: " + dto.getBuildingCount());
                }
            }
        }
    }

    private static void handleApartmentsInBuilding(Scanner sc, ReportService reportService) {
        System.out.print("Enter building ID: ");
        long buildingId = sc.nextLong();
        sc.nextLine();
        System.out.print("Show detailed list? (y/n): ");
        boolean detailed = sc.nextLine().trim().equalsIgnoreCase("y");

        if (detailed) {
            List<BuildingApartmentDto> results = reportService.getBuildingApartmentsDetail(buildingId);
            System.out.println("\n=== Apartments in Building (Detailed) ===");
            if (results.isEmpty()) {
                System.out.println("No apartments found.");
            } else {
                for (BuildingApartmentDto dto : results) {
                    System.out.println("Apt #" + dto.getApartmentNumber() + " | Floor: " + dto.getFloor() +
                            " | Area: " + dto.getArea() + " sqm | Owner: " + dto.getOwnerFirstName() + " " + dto.getOwnerLastName());
                }
            }
        } else {
            int count = reportService.getBuildingApartmentsCount(buildingId);
            System.out.println("\n=== Apartments in Building (Summary) ===");
            System.out.println("Total apartments: " + count);
        }
    }

    private static void handleResidentsInBuilding(Scanner sc, ReportService reportService) {
        System.out.print("Enter building ID: ");
        long buildingId = sc.nextLong();
        sc.nextLine();
        System.out.print("Show detailed list? (y/n): ");
        boolean detailed = sc.nextLine().trim().equalsIgnoreCase("y");

        if (detailed) {
            List<BuildingResidentDto> results = reportService.getBuildingResidentsDetail(buildingId);
            System.out.println("\n=== Residents in Building (Detailed) ===");
            if (results.isEmpty()) {
                System.out.println("No residents found.");
            } else {
                for (BuildingResidentDto dto : results) {
                    System.out.println("Resident: " + dto.getFirstName() + " " + dto.getMiddleName() + " " + dto.getLastName() +
                            " | Age: " + dto.getAge() + " | Apt: " + dto.getApartmentNumber() + " | Floor: " + dto.getFloor() +
                            " | Uses Elevator: " + (dto.isUseElevator() ? "Yes" : "No"));
                }
            }
        } else {
            int count = reportService.getBuildingResidentsCount(buildingId);
            System.out.println("\n=== Residents in Building (Summary) ===");
            System.out.println("Total residents: " + count);
        }
    }

    private static void handleTaxSummary(Scanner sc, ReportService reportService) {
        System.out.println("Tax summary by:");
        System.out.println("1. Company");
        System.out.println("2. Building");
        System.out.println("3. Employee");
        System.out.print("Select option: ");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                List<TaxSummaryDto> companyTaxes = reportService.getTaxSummaryByCompany();
                System.out.println("\n=== Taxes by Company ===");
                for (TaxSummaryDto dto : companyTaxes) {
                    System.out.println("Company: " + dto.getEntityName() + " | Total: " + dto.getTotalTaxAmount() + " | Count: " + dto.getTaxCount());
                }
                break;
            case 2:
                List<TaxSummaryDto> buildingTaxes = reportService.getTaxSummaryByBuilding();
                System.out.println("\n=== Taxes by Building ===");
                for (TaxSummaryDto dto : buildingTaxes) {
                    System.out.println("Building: " + dto.getEntityName() + " | Total: " + dto.getTotalTaxAmount() + " | Count: " + dto.getTaxCount());
                }
                break;
            case 3:
                List<TaxSummaryDto> employeeTaxes = reportService.getTaxSummaryByEmployee();
                System.out.println("\n=== Taxes by Employee ===");
                for (TaxSummaryDto dto : employeeTaxes) {
                    System.out.println("Employee: " + dto.getEntityName() + " | Total: " + dto.getTotalTaxAmount() + " | Count: " + dto.getTaxCount());
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void handlePaymentSummary(Scanner sc, ReportService reportService) {
        System.out.println("Payment summary by:");
        System.out.println("1. Company");
        System.out.println("2. Building");
        System.out.println("3. Employee");
        System.out.print("Select option: ");
        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                List<PaymentSummaryDto> companyPayments = reportService.getPaymentSummaryByCompany();
                System.out.println("\n=== Payments by Company ===");
                for (PaymentSummaryDto dto : companyPayments) {
                    System.out.println("Company: " + dto.getEntityName() + " | Total: " + dto.getTotalPaymentAmount() + " | Count: " + dto.getPaymentCount());
                }
                break;
            case 2:
                List<PaymentSummaryDto> buildingPayments = reportService.getPaymentSummaryByBuilding();
                System.out.println("\n=== Payments by Building ===");
                for (PaymentSummaryDto dto : buildingPayments) {
                    System.out.println("Building: " + dto.getEntityName() + " | Total: " + dto.getTotalPaymentAmount() + " | Count: " + dto.getPaymentCount());
                }
                break;
            case 3:
                List<PaymentSummaryDto> employeePayments = reportService.getPaymentSummaryByEmployee();
                System.out.println("\n=== Payments by Employee ===");
                for (PaymentSummaryDto dto : employeePayments) {
                    System.out.println("Employee: " + dto.getEntityName() + " | Total: " + dto.getTotalPaymentAmount() + " | Count: " + dto.getPaymentCount());
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    static void main(String[] args) {
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