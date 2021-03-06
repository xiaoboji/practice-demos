package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: practice-demos
 * @description: a detail demo
 * @author: xiaoboji
 * @create: 2020-07-09 23:41
 */
public class ExampleEmployee {
  private static List<Employee> employeeList = new ArrayList();

  static {
    employeeList.add(new Employee("Matt", 5000, "New York"));
    employeeList.add(new Employee("Steve", 6000, "London"));
    employeeList.add(new Employee("Carrie", 20000, "New York"));
    employeeList.add(new Employee("Peter", 7000, "New York"));
    employeeList.add(new Employee("Pat", 8000, "London"));
    employeeList.add(new Employee("Tammy", 29000, "Shanghai"));
  }

  public static void main(String[] args) {
    // anyMatch
    boolean isMatch =
        employeeList.stream().anyMatch(employee -> employee.getOffice().equals("London"));
    System.out.println(isMatch);

    // 返回所有salary大于6000
    boolean matched = employeeList.stream().allMatch(employee -> employee.getSalary() > 4000);
    System.out.println(matched);

    // 找出工资最高
    Optional<Employee> hightestSalary =
        employeeList.stream().max((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));
    System.out.println(hightestSalary);

    // 返回姓名列表
    List<String> names =
        employeeList.stream().map(employee -> employee.getName()).collect(Collectors.toList());
    System.out.println(names);

    // List转换成Map
    Map<String, Employee> employeeMap =
        employeeList.stream().collect(Collectors.toMap((key -> key.getName()), (value -> value)));
    employeeMap.forEach((key, value) -> System.out.println(key + "=" + value));

    // 统计办公室是New York的个数
    long officeCount =
        employeeList.stream().filter(employee -> employee.getOffice().equals("Shanghai")).count();
    System.out.println(officeCount);

    // List转换为Set
    Set<String> officeSet =
        employeeList.stream()
            .map(employee -> employee.getOffice())
            .distinct()
            .collect(Collectors.toSet());
    System.out.println(officeSet);

    // 查找办公室地点是New York的员工
    Optional<Employee> allMatchedEmployees =
        employeeList.stream().filter(employee -> employee.getOffice().equals("New York")).findAny();
    System.out.println(allMatchedEmployees);

    // 按照工资的降序来列出员工信息
    List<Employee> sortEmployeeList =
        employeeList.stream()
            .sorted((e1, e2) -> Integer.compare(e2.getSalary(), e1.getSalary()))
            .collect(Collectors.toList());
    // 按照名字的升序列出员工信息
    List<Employee> sortEmployeeByName =
        employeeList.stream()
            .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
            .collect(Collectors.toList());
    System.out.println(sortEmployeeList);
    System.out.println("按照名字的升序列出员工信息:" + sortEmployeeByName);

    // 获取工资最高的前2条员工信息
    List<Employee> top2EmployeeList =
        employeeList.stream()
            .sorted((e1, e2) -> Integer.compare(e2.getSalary(), e1.getSalary()))
            .limit(2)
            .collect(Collectors.toList());
    System.out.println(top2EmployeeList);

    // 获取平均工资
    OptionalDouble averageSalary =
        employeeList.stream().mapToInt(employee -> employee.getSalary()).average();
    System.out.println("平均工资:" + averageSalary);

    // 查找New York
    OptionalDouble averageSalaryByOffice =
        employeeList.stream()
            .filter(employee -> employee.getOffice().equals("New York"))
            .mapToInt(employee -> employee.getSalary())
            .average();
    System.out.println("New York办公室平均工资:" + averageSalaryByOffice);
  }
}

class Employee {

  private String name;
  private int salary;
  private String office;

  public Employee(String name, int salary, String office) {
    this.name = name;
    this.salary = salary;
    this.office = office;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public String getOffice() {
    return office;
  }

  public void setOffice(String office) {
    this.office = office;
  }
}
