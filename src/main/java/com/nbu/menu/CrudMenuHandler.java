package com.nbu.menu;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CrudMenuHandler<T> {
    private final String entityName;
    private final Scanner scanner;
    private final Class<T> dtoClass;
    private final Set<String> excludedFields;

    private final Consumer<T> createService;
    private final Function<Long, T> readService;
    private final Supplier<List<T>> readAllService;
    private final BiConsumer<Long, T> updateService;
    private final Consumer<Long> deleteService;

    private CrudMenuHandler(Builder<T> builder) {
        this.entityName = builder.entityName;
        this.scanner = builder.scanner;
        this.dtoClass = builder.dtoClass;
        this.excludedFields = builder.excludedFields;
        this.createService = builder.createService;
        this.readService = builder.readService;
        this.readAllService = builder.readAllService;
        this.updateService = builder.updateService;
        this.deleteService = builder.deleteService;
    }

    public void handle() {
        int selectedOption = -1;
        while (selectedOption != 0) {
            printCRUDMenu();
            try {
                selectedOption = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (selectedOption) {
                case 1: handleCreate(); break;
                case 2: handleRead(); break;
                case 3: handleReadAll(); break;
                case 4: handleUpdate(); break;
                case 5: handleDelete(); break;
                case 0: System.out.println("Returning to main menu."); break;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printCRUDMenu() {
        String article = getArticle(entityName);
        System.out.println(entityName + " CRUD Menu:");
        System.out.println("1. Create " + article + " " + entityName);
        System.out.println("2. Read " + article + " " + entityName);
        System.out.println("3. Read all " + entityName);
        System.out.println("4. Update " + article + " " + entityName);
        System.out.println("5. Delete " + article + " " + entityName);
        System.out.println("0. Return to main menu");
    }

    private String getArticle(String word) {
        if (word != null && !word.isEmpty()) {
            char firstChar = Character.toLowerCase(word.charAt(0));
            if ("aeiou".indexOf(firstChar) >= 0) return "an";
        }
        return "a";
    }

    private void handleCreate() {
        try {
            T dto = readObject();
            createService.accept(dto);
            System.out.println(entityName + " created successfully.");
        } catch (Exception e) {
            System.out.println("Error creating " + entityName.toLowerCase() + ": " + e.getMessage());
        }
    }

    private void handleRead() {
        try {
            System.out.println("Enter " + entityName.toLowerCase() + " id:");
            long id = scanner.nextLong();
            scanner.nextLine();
            T entity = readService.apply(id);
            if (entity != null) System.out.println(entity);
            else System.out.println(entityName + " with id " + id + " not found.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error reading " + entityName.toLowerCase() + ": " + e.getMessage());
        }
    }

    private void handleReadAll() {
        try {
            List<T> entities = readAllService.get();
            if (entities.isEmpty()) System.out.println("No " + entityName.toLowerCase() + "s found.");
            else entities.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error reading " + entityName.toLowerCase() + "s: " + e.getMessage());
        }
    }

    private void handleUpdate() {
        try {
            System.out.println("Enter " + entityName.toLowerCase() + " id to update:");
            long id = scanner.nextLong();
            scanner.nextLine();

            T existing = readService.apply(id);
            if (existing == null) {
                System.out.println(entityName + " with id " + id + " not found.");
                return;
            }

            T dto = updateObject(existing);
            updateService.accept(id, dto);
            System.out.println(entityName + " updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating " + entityName.toLowerCase() + ": " + e.getMessage());
        }
    }

    private void handleDelete() {
        try {
            System.out.println("Enter " + entityName.toLowerCase() + " id to delete:");
            long id = scanner.nextLong();
            scanner.nextLine();
            System.out.println("Are you sure you want to delete " + entityName.toLowerCase() + " with id " + id + "? (y/n)");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                deleteService.accept(id);
                System.out.println(entityName + " deleted successfully.");
            } else {
                System.out.println("Delete operation cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting " + entityName.toLowerCase() + ": " + e.getMessage());
        }
    }

    private T readObject() throws Exception {
        T instance = dtoClass.getDeclaredConstructor().newInstance();
        populateFields(instance, null);
        return instance;
    }

    private T updateObject(T existing) throws Exception {
        T instance = dtoClass.getDeclaredConstructor().newInstance();
        copyIdField(existing, instance);
        System.out.println("Leave field empty to keep current value.");
        populateFields(instance, existing);
        return instance;
    }

    private void copyIdField(T source, T target) throws IllegalAccessException {
        for (Field field : source.getClass().getDeclaredFields()) {
            if (field.getName().equals("id")) {
                field.setAccessible(true);
                field.set(target, field.get(source));
                break;
            }
        }
    }

    private void populateFields(T instance, T existing) throws IllegalAccessException {
        for (Field field : dtoClass.getDeclaredFields()) {
            if (excludedFields.contains(field.getName())) continue;

            field.setAccessible(true);
            String fieldName = camelCaseToReadable(field.getName());
            Class<?> type = field.getType();
            Object currentValue = existing != null ? field.get(existing) : null;
            Object value = readFieldValue(fieldName, type, currentValue);
            field.set(instance, value);
        }
    }

    private Object readFieldValue(String fieldName, Class<?> type, Object currentValue) {
        boolean isUpdate = currentValue != null;

        if (type == String.class) {
            return readString(fieldName, (String) currentValue, isUpdate);
        } else if (type == long.class || type == Long.class) {
            return readLong(fieldName, currentValue != null ? (Long) currentValue : 0L, isUpdate);
        } else if (type == int.class || type == Integer.class) {
            return readInt(fieldName, currentValue != null ? (Integer) currentValue : 0, isUpdate);
        } else if (type == double.class || type == Double.class) {
            return readDouble(fieldName, currentValue != null ? (Double) currentValue : 0.0, isUpdate);
        } else if (type == boolean.class || type == Boolean.class) {
            return readBoolean(fieldName, currentValue != null && (Boolean) currentValue, isUpdate);
        } else if (type == LocalDate.class) {
            return readLocalDate(fieldName, (LocalDate) currentValue, isUpdate);
        } else if (type == LocalDateTime.class) {
            return readLocalDateTime(fieldName, (LocalDateTime) currentValue, isUpdate);
        } else if (type == BigDecimal.class) {
            return readBigDecimal(fieldName, (BigDecimal) currentValue, isUpdate);
        }
        System.out.println("Unsupported type: " + type.getSimpleName());
        return null;
    }

    private String readString(String fieldName, String currentValue, boolean isUpdate) {
        System.out.println(isUpdate ? "Enter " + fieldName + " (current: " + currentValue + "):" : "Enter " + fieldName + ":");
        String input = scanner.nextLine();
        return (isUpdate && input.isEmpty()) ? currentValue : input;
    }

    private long readLong(String fieldName, long currentValue, boolean isUpdate) {
        if (isUpdate) {
            System.out.println("Enter " + fieldName + " (current: " + currentValue + "):");
            String input = scanner.nextLine();
            return input.isEmpty() ? currentValue : Long.parseLong(input);
        }
        System.out.println("Enter " + fieldName + ":");
        long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }

    private int readInt(String fieldName, int currentValue, boolean isUpdate) {
        if (isUpdate) {
            System.out.println("Enter " + fieldName + " (current: " + currentValue + "):");
            String input = scanner.nextLine();
            return input.isEmpty() ? currentValue : Integer.parseInt(input);
        }
        System.out.println("Enter " + fieldName + ":");
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private double readDouble(String fieldName, double currentValue, boolean isUpdate) {
        if (isUpdate) {
            System.out.println("Enter " + fieldName + " (current: " + currentValue + "):");
            String input = scanner.nextLine();
            return input.isEmpty() ? currentValue : Double.parseDouble(input);
        }
        System.out.println("Enter " + fieldName + ":");
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    private boolean readBoolean(String fieldName, boolean currentValue, boolean isUpdate) {
        String current = currentValue ? "yes" : "no";
        System.out.println(isUpdate ? "Enter " + fieldName + " (current: " + current + ") (y/n):" : "Enter " + fieldName + " (y/n):");
        String input = scanner.nextLine();
        if (isUpdate && input.isEmpty()) return currentValue;
        return input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes");
    }

    private LocalDate readLocalDate(String fieldName, LocalDate currentValue, boolean isUpdate) {
        System.out.println(isUpdate ? "Enter " + fieldName + " (current: " + currentValue + ") (YYYY-MM-DD):" : "Enter " + fieldName + " (YYYY-MM-DD):");
        String input = scanner.nextLine();
        return (isUpdate && input.isEmpty()) ? currentValue : LocalDate.parse(input);
    }

    private LocalDateTime readLocalDateTime(String fieldName, LocalDateTime currentValue, boolean isUpdate) {
        System.out.println(isUpdate ? "Enter " + fieldName + " (current: " + currentValue + ") (YYYY-MM-DDTHH:MM:SS):" : "Enter " + fieldName + " (YYYY-MM-DDTHH:MM:SS):");
        String input = scanner.nextLine();
        return (isUpdate && input.isEmpty()) ? currentValue : LocalDateTime.parse(input);
    }

    private BigDecimal readBigDecimal(String fieldName, BigDecimal currentValue, boolean isUpdate) {
        System.out.println(isUpdate ? "Enter " + fieldName + " (current: " + currentValue + "):" : "Enter " + fieldName + ":");
        String input = scanner.nextLine();
        return (isUpdate && input.isEmpty()) ? currentValue : new BigDecimal(input);
    }

    private String camelCaseToReadable(String camelCase) {
        StringBuilder result = new StringBuilder();
        for (char c : camelCase.toCharArray()) {
            if (Character.isUpperCase(c)) result.append(" ").append(Character.toLowerCase(c));
            else result.append(c);
        }
        return result.toString();
    }

    public static class Builder<T> {
        private final String entityName;
        private final Scanner scanner;
        private final Class<T> dtoClass;
        private Set<String> excludedFields = new HashSet<>(Collections.singletonList("id"));

        private Consumer<T> createService;
        private Function<Long, T> readService;
        private Supplier<List<T>> readAllService;
        private BiConsumer<Long, T> updateService;
        private Consumer<Long> deleteService;

        public Builder(String entityName, Class<T> dtoClass, Scanner scanner) {
            this.entityName = entityName;
            this.dtoClass = dtoClass;
            this.scanner = scanner;
        }

        public Builder<T> excludeFields(String... fields) {
            this.excludedFields = new HashSet<>(Arrays.asList(fields));
            this.excludedFields.add("id");
            return this;
        }

        public Builder<T> onCreate(Consumer<T> service) { this.createService = service; return this; }
        public Builder<T> onRead(Function<Long, T> service) { this.readService = service; return this; }
        public Builder<T> onReadAll(Supplier<List<T>> service) { this.readAllService = service; return this; }
        public Builder<T> onUpdate(BiConsumer<Long, T> service) { this.updateService = service; return this; }
        public Builder<T> onDelete(Consumer<Long> service) { this.deleteService = service; return this; }

        public CrudMenuHandler<T> build() { return new CrudMenuHandler<>(this); }
    }
}
