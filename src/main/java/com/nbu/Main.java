package com.nbu;

import com.nbu.configuration.SessionFactoryUtil;
import com.nbu.dto.CompanyDto;
import com.nbu.dto.EmployeeDto;
import com.nbu.entity.Employee;
import com.nbu.menu.CrudMenuHandler;
import com.nbu.service.CompanyService;
import com.nbu.service.EmployeeService;
import org.hibernate.Session;

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
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
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

    public static void main(String[] args) {
        Session session = SessionFactoryUtil.getSessionFactory().openSession();
        session.close();

        handleMainMenu();
    }

}