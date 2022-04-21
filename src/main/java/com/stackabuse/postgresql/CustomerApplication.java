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
import java.util.HashMap;
import java.util.Map;
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
        // добавить транзакция
    }
    public static Transaction get_trans()
    {
        // получить транзакцию
        Transaction trans = new Transaction(); // get
        return trans;
    }
    public static void update_trans()
    {
        // получить транзакцию

    }
    public static void kill_trans()
    {

    }

    public static void load_trans()
    {

    }
    public static void save_trans()
    {

    }
    /////////
    public static void set_gender()
    {

    }

    public static int get_gender()
    {
        return 0;
    }
    /////////////
    public static void get_mcc()
    {
        // добавить транзакция
    }
    public static void add_mcc()
    {
        // добавить транзакция
    }
    public static void kill_mcc()
    {
        // добавить транзакция
    }
    public static void update_mcc()
    {
        // добавить транзакция
    }
    public static int set_mcc()
    {
        // добавить транзакция
        return 0;
    }
    public static void set_mcc_description(int code)
    {
        // добавить транзакция
    }
    public static void load_mcc()
    {

    }
    public static void save_mcc()
    {

    }

    public static void add_people()
    {

    }
    public static void kill_people()
    {

    }
    public static void update_people()
    {

    }
    public static void get_people()
    {

    }
    public static void load_people()
    {

    }
    public static void save_people()
    {

    }

    public static Integer select_menu()
    {
        Scanner in = new Scanner(System.in);
        // меню
        System.out.println("Меню");
        System.out.println("Выберите действие (первая цифра номер таблицы, вторая - номер действия");
        System.out.println("Таблицы");
        System.out.println("1 - таблица Транзакций");
        System.out.println("2 - таблица Кодов МСС");
        System.out.println("3 - таблица Сотрудников");
        System.out.println("4 - таблица Сотрудников");
        System.out.println("5 - таблица Отчет");
        System.out.println("Действия");
        System.out.println("1. Добавить");
        System.out.println("2. Удалить по id");
        System.out.println("3. Изменить по id");
        System.out.println("4. Получить по id");
        System.out.println("5. Загрузить из файла");
        System.out.println("6. Сохранить в файл");
        System.out.println("7. Запрос 1");
        System.out.println("8. Запрос 2");
        System.out.println("9  Запрос 3");
        System.out.println("0. Запрос 4");
        String menu = "";
        Integer n = 0;
        while(true) {
            n = in.nextInt();
            break;
        }
        return n;
    }
    ///////////////
    public static void main(String[] args) {
        try {
            Customer customer = getCustomer(1);
        } catch (NonExistentEntityException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }
        while(true) {
            Integer menu = select_menu();
            System.out.println("1. Добавить");
            System.out.println("2. Удалить по id");
            System.out.println("3. Изменить по id");
            System.out.println("4. Получить по id");
            System.out.println("5. Загрузить из файла");
            System.out.println("6. Сохранить в файл");
            System.out.println("7. Изменить пол");
            System.out.println("8. Узнать пол");
            System.out.println("9. Запрос 1");
            System.out.println("10. Запрос 2");
            System.out.println("11. Запрос 3");
            System.out.println("12. Запрос 4");
            System.out.println("0. Выход");
            switch (menu)
            {
                case 11:
                    add_trans();
                    break;
                case 12:
                    kill_trans();
                    break;
                case 13:
                    update_trans();
                    break;
                case 14:
                    get_trans();
                    break;
                case 15:
                    load_trans();
                    break;
                case 16:
                    save_trans();
                    break;
                case 21:
                    add_mcc();
                    break;
                case 22:
                    kill_mcc();
                    break;
                case 23:
                    update_mcc();;
                    break;
                case 24:
                    get_mcc();
                    break;
                case 25:
                    load_mcc();
                    break;
                case 26:
                    save_mcc();
                    break;
                case 31:
                    add_people();
                    break;
                case 32:
                    kill_people();
                    break;
                case 33:
                    update_people();
                    break;
                case 34:
                    get_people();
                    break;
                case 35:
                    load_people();
                    break;
                case 36:
                    save_people();
                    break;
                case 47:
                    //
                    break;
                case 48:
                    //
                    break;
                case 49:
                    //
                    break;
                case 40:
                    //
                    break;
                case 0:
                    save_people();
                    save_trans();
                    save_mcc();
                    return;
                default:
                    System.out.println("Некорректный ввод");
            }
            break;
        }
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
