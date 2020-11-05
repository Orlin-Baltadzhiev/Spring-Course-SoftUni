import model.Address;
import model.Employee;
import model.Project;
import model.Town;

import javax.persistence.EntityManager;
import javax.print.attribute.standard.Media;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader reader;

    public Engine(EntityManager entityManager) {

        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Chose one exercise: ");
        String exercise = sc.nextLine();
        while (!exercise.equals("End")){
            switch (exercise){
                case "2":
                    changeCasingEx2();
                    break;
                case "3":
                    try {
                        containsEmployeeEx3();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "4":
                    employeeWithSalaryOver50000Ex4();
                    break;
                case "5":
                    employeesFromDepartmentsEx5();
                    break;
                case "6":
                    try {
                        addingNewAddressAndUpdatingEmployeeEx6();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "7":
                    addressWithEmployeeCountEx7();
                    break;
                case "8":
                    try {
                        getEmployeeWithProjectsEx8();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "9":
                    findLatest10ProjectsEx9();
                    break;
                case "10":
                    increaseSalaryEx10();
                    break;
                case "11":
                    try {
                        findEmployeeByPatternEx11();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "12":
                    employeesMaximumSalariesEx12();
                    break;
                case "13":
                    try {
                        removesTownByNameEx13();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

            }
            System.out.println("Chose other exercise. With command \"End\" the program will stop");
            exercise = sc.nextLine();
        }
    }

    private void removesTownByNameEx13() throws IOException {
        System.out.println("Enter valid town name to delete: ");
        String townName = reader.readLine();
        Town town = entityManager
                .createQuery("SELECT t FROM Town t " +
                        "WHERE t.name = :name", Town.class)
                .setParameter("name", townName)
                .getSingleResult();
        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address a " +
                        "WHERE a.town.name = :name", Address.class)
                .setParameter("name", townName)
                .getResultList();
        String output = String.format("%d address%s in %s deleted%n",
                addresses.size(),(addresses.size() != 1) ? "es" : "",town.getName());

        entityManager.getTransaction().begin();
        addresses.forEach(address -> {
            for (Employee employee : address.getEmployees()) {
                employee.setAddress(null);
            }
            address.setTown(null);
            entityManager.remove(address);
        });
        entityManager.remove(town);
        entityManager.getTransaction().commit();
        System.out.println(output);




    }

    private void employeesMaximumSalariesEx12() {
        List<Employee> employees = entityManager

                .createQuery("SELECT e FROM Employee e " +
                        "GROUP BY e.department.id " +
                        "HAVING MAX(e.salary)  NOT BETWEEN (30000) AND (70000) ", Employee.class)
                .getResultList();


        employees
                .forEach(e -> {
                    System.out.printf("%s - %.2f%n",
                            e.getDepartment().getName(),
                            e.getSalary());
                });


    }

    private void findEmployeeByPatternEx11() throws IOException {
        //System.out.println("Enter valid pattern: ");
        //String pattern = reader.readLine();

        List<Employee> employees = entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.firstName LIKE 'Sa%'", Employee.class)
                .getResultList();


        employees
                .forEach(e -> {
                    System.out.printf("%s %s - %s - ($%.2f)%n",
                            e.getFirstName(),
                            e.getLastName(),
                            e.getJobTitle(),
                            e.getSalary());
                });


    }

    private void findLatest10ProjectsEx9() {
        List<Project> projectList = entityManager
                .createQuery("SELECT p FROM Project p " +
                        "ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();

        projectList.stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    System.out.printf("Project name: %s%n\tProject Description: %s%n\tProject Start Date: %s%n\tProject End Date: %s%n",
                            p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate());
                });

    }

    private void increaseSalaryEx10() {
        entityManager.getTransaction().begin();
        int affectedRows = entityManager
                .createQuery("UPDATE Employee e " +
                        "SET e.salary = e.salary * 1.12" +
                        "WHERE e.department.id IN (1,2,4,11)")
                .executeUpdate(); // --> return affectedRows INT
        entityManager.getTransaction().commit();
        System.out.println("Affected rows " + affectedRows);

        entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.department.id IN (1,2,4,11)", Employee.class)
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s ($%.2f)%n", employee.getFirstName(),
                            employee.getLastName(), employee.getSalary());
                });


    }

    private void getEmployeeWithProjectsEx8() throws IOException {
        System.out.println("Enter valid employee id: ");
        int id = Integer.parseInt(reader.readLine());

        Employee employee = entityManager
                .find(Employee.class, id);

        System.out.printf("%s %s - %s%n", employee.getFirstName(),
                employee.getLastName(), employee.getJobTitle());

        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> {
                    System.out.printf("\t%s%n", project.getName());
                });
    }

    private void addressWithEmployeeCountEx7() {
        List<Address> addresses = entityManager
                .createQuery("SELECT a FROM Address a " +
                        "ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList();

        addresses
                .forEach(address -> {
                    System.out.printf("%s, %s - %d employees %n",
                            address.getText(), address.getTown().getName(),
                            address.getEmployees().size());
                });
    }

    private void addingNewAddressAndUpdatingEmployeeEx6() throws IOException {
        Address address = createAddress("Vitoshka 15");

        System.out.println("Enter employee id:");
        String lastName = reader.readLine();

        Employee employee = entityManager
                .find(Employee.class, 291);
        //variant 2
//              entityManager.createQuery("SELECT e FROM Employee  e " +
//                      "WHERE e.lastName = :last_name" , Employee.class)
//                      .setParameter("last_name", lastName)
//                      .getSingleResult();


        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();

    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();
        return address;
    }

    private void employeesFromDepartmentsEx5() {
//        List <Employee> employees = entityManager
//                .createQuery("SELECT e FROM Employee e " +
//                        "WHERE e.department.name = 'Research and Development'" +
//                        "ORDER BY e.salary, e.id",
//                        Employee.class)
//                .getResultList();

        //variant2
        entityManager.createQuery("SELECT e FROM Employee e " +
                        "WHERE e.department.name = 'Research and Development'" +
                        "ORDER BY e.salary, e.id",
                Employee.class)
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s from Research and Development - $%.2f%n",
                            employee.getFirstName(), employee.getLastName(), employee.getSalary());
                });
    }

    private void employeeWithSalaryOver50000Ex4() {
        List<Employee> employees = entityManager
                .createQuery("SELECT e FROM Employee e " +
                        "WHERE e.salary > 50000", Employee.class)
                .getResultList();

        //variant 2
//        entityManager
//                .createQuery("SELECT e FROM Employee e " +
//                        "WHERE e.salary>50000", Employee.class)
//                .getResultStream()
//                .map(Employee::getFirstName)
//                .forEach(System.out::println);

    }

    private void containsEmployeeEx3() throws IOException {
        System.out.println("Enter employee full:");
        String fullName = reader.readLine();

        List<Employee> employees = entityManager
                .createQuery("SELECT e FROM Employee  e " +
                        "WHERE concat(e.firstName, ' ',e.lastName) = :name", Employee.class)
                .setParameter("name", fullName)
                .getResultList();
        System.out.println(employees.size() == 0 ? "NO" : "YES");
    }

    private void changeCasingEx2() {
        List<Town> towns = entityManager
                .createQuery("SELECT t FROM Town t " +
                        "WHERE length(t.name)<= 5", Town.class)
                .getResultList();

        entityManager.getTransaction().begin();
        towns.forEach(entityManager::detach);
        for (Town town : towns) {
            town.setName((town.getName().toLowerCase()));
        }
        towns.forEach(entityManager::merge);
        entityManager.flush();
        entityManager.getTransaction().commit();


    }
}
