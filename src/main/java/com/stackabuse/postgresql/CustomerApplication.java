package com.stackabuse.postgresql;
import java.lang.reflect.Array;
import java.util.Scanner;
import com.stackabuse.postgresql.api.Customer;
import com.stackabuse.postgresql.api.NonExistentEntityException;
import com.stackabuse.postgresql.api.Transaction;
import com.stackabuse.postgresql.core.NonExistentCustomerException;
import com.stackabuse.postgresql.core.PostgreSqlDao;
import com.stackabuse.postgresql.spi.Dao;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class CustomerApplication {
    
    private static final Logger LOGGER = Logger.getLogger(CustomerApplication.class.getName());
    private static final Dao<Customer, Integer> CUSTOMER_DAO = new PostgreSqlDao();

    public static boolean have_in_table(String table, String field, String val, String type)
    {
        String cmd = "SELECT * FROM " + table + " WHERE " + field;
        // сделать запрос select в базу и узнать
        if (type == "int") {
            cmd += "=" + val;
        }
        else
            cmd += "='" + val + "'";
        // запрос
        return true;
    }
    public static String select_table()
    {
        String[] table_names = new String[4];
        table_names[0] = "table_1";
        table_names[1] = "table_2";
        table_names[2] = "table_3";
        table_names[3] = "table_4";
        Scanner in = new Scanner(System.in);
        // меню
        System.out.println("Меню");
        System.out.println("Выберите таблицу");
        System.out.println("1. " + table_names[0]);
        System.out.println("2. " + table_names[1]);
        System.out.println("3. " + table_names[2]);
        System.out.println("4. " + table_names[3]);

        String menu = "";
        Integer n = 0;

        while(true) {
            menu = in.nextLine();
            if (menu.equals("1") || menu.equals("2") || menu.equals("3") || menu.equals("4")) {
                n = Integer.parseInt(menu);
                break;
            }
            System.out.println("Некорректный ввод");
        }
        return table_names[n];
    }
    ///////////////

    public static Integer input_int(String table, String field, String type)
    {
        String buff = "";
        Scanner in = new Scanner(System.in);
        boolean check = false;
        System.out.println("Введите id работника");
        while (!check) {
            buff = in.nextLine();
            check = have_in_table(table, field, buff, "int");
            if (!check)
                System.out.println("Некорректный ввод. Либо введено не корректное значение, " +
                        "либо в базе данных отсуствуюет запись согласно введенному значению: " + buff);
        }
        Integer result = Integer.parseInt(buff);
        return result;
    }
    public static void add_trans()
    {
        String buff = "";
        Scanner in = new Scanner(System.in);
        Integer customer_id = 0;
        String tr_datetime = "";
        Integer mcc_code = 0;
        Integer tr_type = 0;
        Integer amount = 0;
        Integer term_id = 0;
        boolean check = false;

        System.out.println("Введите id работника");
        customer_id = input_int("table_1", "customer_id", "int");
        System.out.println("Введите дату транзакции");
        System.out.println("Введите МСС код");
        mcc_code = input_int("table_1", "mcc_code", "int");
        System.out.println("Введите тип транзакции");
        tr_type = input_int("table_1", "tr_type", "int");
        System.out.println("Введите сумму операции");
        System.out.println("Введите id терминала");
        term_id = input_int("table_1", "term_id", "int");
        // проверить, что такой id есть

        Transaction trans = new Transaction(customer_id, tr_datetime, mcc_code, tr_type, amount, term_id);

        // добавить транзакция
    }
    public static Transaction get_trans(int id)
    {
        // получить транзакцию
        Transaction trans = new Transaction(); // get
        return trans;
    }
    public static void update_trans(int id)
    {
        // получить транзакцию
        String buff = "";
        Scanner in = new Scanner(System.in);
        Integer customer_id = 0;
        String tr_datetime = "";
        Integer mcc_code = 0;
        Integer tr_type = 0;
        Integer amount = 0;
        Integer term_id = 0;
        boolean check = false;

        System.out.println("Введите id работника");
        customer_id = input_int("table_1", "customer_id", "int");
        System.out.println("Введите дату транзакции");
        System.out.println("Введите МСС код");
        mcc_code = input_int("table_1", "mcc_code", "int");
        System.out.println("Введите тип транзакции");
        tr_type = input_int("table_1", "tr_type", "int");
        System.out.println("Введите сумму операции");
        System.out.println("Введите id терминала");
        term_id = input_int("table_1", "term_id", "int");
    }
    public static boolean kill_trans(int id)
    {
        // удалить транзакцию
        Integer customer_id = 0;
        System.out.println("Введите id работника");
        customer_id = input_int("table_1", "customer_id", "int");
        // delete
        return true;
    }
    /////////
    public static void set_gender()
    {
        // добавить транзакция
        Integer customer_id = 0;
        Integer gender = 0;
        String buff = "";
        System.out.println("Введите id работника");
        customer_id = input_int("table_1", "customer_id", "int");
        System.out.println("Введите пол работника");
        Scanner in = new Scanner(System.in);
        while(!(buff == "1" || buff == "0")) {
            System.out.println("Введите пол работника (0 - М, 1 - Ж)");
            buff = in.nextLine();
        }
        gender = Integer.parseInt(buff);
        // изменить пол
    }

    public static int get_gender()
    {
        // добавить транзакция
        Integer customer_id = 0;
        System.out.println("Введите id работника");
        customer_id = input_int("table_1", "customer_id", "int");
        // получить пол сотрудника
    }
    /////////////
    public static void get_mcc()
    {
        // добавить транзакция

    }

    public static int set_mcc()
    {
        // добавить транзакция
    }
    public static void set_mcc_description(int code)
    {
        // добавить транзакция
    }

    public static void main(String[] args) {

        try {
            Customer customer = getCustomer(1);
        } catch (NonExistentEntityException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }

        String table = select_table();

        //Test whether a customer can be added to the database
        Customer firstCustomer = new Customer("Manuel", "Kelley", "ManuelMKelley@jourrapide.com");
        Customer secondCustomer = new Customer("Joshua", "Daulton", "JoshuaMDaulton@teleworm.us");
        Customer thirdCustomer = new Customer("April", "Ellis", "AprilMellis@jourrapide.com");
        addCustomer(firstCustomer).ifPresent(firstCustomer::setId);
        addCustomer(secondCustomer).ifPresent(secondCustomer::setId);
        addCustomer(thirdCustomer).ifPresent(thirdCustomer::setId);
        //Test whether the new customer's details can be edited
        firstCustomer.setFirstName("Franklin");
        firstCustomer.setLastName("Hudson");
        firstCustomer.setEmail("FranklinLHudson@jourrapide.com");
        updateCustomer(firstCustomer);
        //Test whether all customers can be read from database
        getAllCustomers().forEach(System.out::println);
        //Test whether a customer can be deleted
        deleteCustomer(secondCustomer);
    }
    
    public static Customer getCustomer(int id) throws NonExistentEntityException {
        Optional<Customer> customer = CUSTOMER_DAO.get(id);
        return customer.orElseThrow(NonExistentCustomerException::new);
    }
    
    public static Collection<Customer> getAllCustomers() {
        return CUSTOMER_DAO.getAll();
    }
    public static void updateCustomer(Customer customer) {
        CUSTOMER_DAO.update(customer);
    }
    public static Optional<Integer> addCustomer(Customer customer) {
        return CUSTOMER_DAO.save(customer);
    }
    public static void deleteCustomer(Customer customer) {
        CUSTOMER_DAO.delete(customer);
    }
}
