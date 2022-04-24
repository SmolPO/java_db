package com.stackabuse.postgresql;
import java.util.Scanner;

import com.stackabuse.postgresql.api.*;
import com.stackabuse.postgresql.core.NonExistentCustomerException;
import com.stackabuse.postgresql.core.CustomerDao;
import com.stackabuse.postgresql.core.TransDao;
import com.stackabuse.postgresql.core.TypesDao;
import com.stackabuse.postgresql.core.GenderDao;
import com.stackabuse.postgresql.core.MCCDao;
import com.stackabuse.postgresql.spi.Dao;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomerApplication {
    private static final Logger LOGGER = Logger.getLogger(CustomerApplication.class.getName());

    private static final Dao<Customer, Integer> CUSTOMER_DAO = new CustomerDao();

    private static final Dao<Trans, Integer> TRANS_DAO = new TransDao();

    private static final Dao<MCC_code, Integer> MCC_DAO = new MCCDao();
    private static final Dao<Gender_train, Integer> GENDER_DAO = new GenderDao();
    private static final Dao<Tr_type, Integer> TYPE_DAO = new TypesDao();
    static String[] table_names  = new String[] {"table_1", "table_2", "table_3", "table_4"};

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
    public static Integer input_int(String table, String field, String type)
    {
        String buff = "";
        Scanner in = new Scanner(System.in);
        boolean check = false;
        System.out.println("Введите id");
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

    public static void add_note(Integer table_id)
    {
        Scanner in = new Scanner(System.in);
        String descripion;
        Integer trans_id;
        switch (table_id)
        {
            case 1:
                System.out.println("Введи код МСС");
                Integer code = in.nextInt();
                // проверка, что id есть в базе
                System.out.println("Введи описание кода");
                descripion = in.nextLine();
                MCC_code item = new MCC_code(code, descripion);
                addMCCCode(item);
                // запрос на обновление
                break;
            case 2:
                System.out.println("Введи код типа транзакции");
                trans_id = in.nextInt();
                // проверка, что id есть в базе
                System.out.println("Введи описание кода");
                descripion = in.nextLine();
                Tr_type type = new Tr_type(trans_id, descripion);
                addTrType(type);
                // запрос на обновление
                break;
            case 3:
                System.out.println("Введи код клиента");
                Integer people_id = in.nextInt();
                // проверка, что id есть в базе
                System.out.println("Введи пол 1 - М, 2 - Ж");
                Integer sex = in.nextInt();
                Gender_train people = new Gender_train(people_id, sex);
                addGender(people);
                // запрос на обновление
                break;
            case 4:
                System.out.println("Введи код транзакции");
                trans_id = in.nextInt();
                // проверка, что id есть в базе
                // вывести на экран данные по транзакции
                // Transaction trans = new Transaction();
                // запрос на обновление
                // addTransaction(trans);
                break;
        }
    }
    public static void delete_note(Integer table_id) throws NonExistentEntityException {
        Scanner in = new Scanner(System.in);
        Integer id = in.nextInt();
        Object item;
        switch (table_id)
        {
            case 1:
                item = getMCC(id);
                deleteMCCCode((MCC_code)item);
                break;
            case 2:
                item = getType(id);
                deleteTrType((Tr_type) item);
                break;
            case 3:
                item = getGender(id);
                deleteGender((Gender_train) item);
                break;
            case 4:
                item = getTrans(id);
                deleteTransaction((Trans) item);
                break;
        }
    }
    public static void update_note(Integer table_id)
    {
        Scanner in = new Scanner(System.in);
        String descripion;
        Integer trans_id;
        switch (table_id)
        {
            case 1:
                System.out.println("Введи код МСС");
                Integer code = in.nextInt();
                // проверка, что id есть в базе
                System.out.println("Введи описание кода");
                descripion = in.nextLine();
                MCC_code item = new MCC_code(code, descripion);
                updateMCCCode(item);
                // запрос на обновление
                break;
            case 2:
                System.out.println("Введи код типа транзакции");
                trans_id = in.nextInt();
                // проверка, что id есть в базе
                System.out.println("Введи описание кода");
                descripion = in.nextLine();
                Tr_type type = new Tr_type(trans_id, descripion);
                updateTrType(type);
                // запрос на обновление
                break;
            case 3:
                System.out.println("Введи код клиента");
                Integer people_id = in.nextInt();
                // проверка, что id есть в базе
                System.out.println("Введи пол 1 - М, 2 - Ж");
                Integer sex = in.nextInt();
                Gender_train people = new Gender_train(people_id, sex);
                updateGender(people);
                break;
            case 4:
                System.out.println("Введи код транзакции");
                trans_id = in.nextInt();
                // проверка, что id есть в базе
                // вывести на экран данные по транзакции
                // Transaction trans = new Transaction();
                // запрос на обновление
                // updateTransaction(trans);
                break;
        }
    }
    public static void get_note(Integer table_id)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите id записи");
        Integer id = in.nextInt();
        String cmd = "SELECT * FROM " + table_names[table_id] + " WHERE id=" + id;
        // запрос в БД
    }
    public static void load_db(Integer table) throws IOException {
        String path = "";
        String row = "";
        System.out.println("Введите путь к файлу");
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(path));
        if (file.isFile()) {
            while ((row = reader.readLine()) != null) {
                String[] data = row.split(",");
                switch (table)
                {
                    case 1:
                        Tr_type type = new Tr_type(data);
                        addTrType(type);
                        break;
                    case 2:
                        MCC_code mcc_code = new MCC_code(data);
                        addMCCCode(mcc_code);
                        break;
                    case 3:
                        Gender_train people = new Gender_train(data);
                        addGender(people);
                        break;
                    case 4:
                        Trans trans = new Trans(data);
                        addTransaction(trans);
                        break;
                }
            }
            reader.close();
        }
    }
    public static void save_db(Integer table) throws IOException {
        String row;
        FileWriter file;
        switch (table)
        {
            case 1:
                Collection<Tr_type> list_1 = getAllTr_types();
                file = new FileWriter("tr_type_db.csv");
                for (Tr_type item : list_1)
                {
                    row = item.to_csv();
                    file.write(row);
                }
                file.close();
                break;
            case 2:
                Collection<MCC_code> list_2 = getAllMCC_codes();
                file = new FileWriter("mcc_code_db.csv");
                for (MCC_code item : list_2)
                {
                    row = item.to_csv();
                    file.write(row);
                }
                file.close();
                break;
            case 3:
                Collection<Gender_train> list_3 = getAllGender_train();
                file = new FileWriter("gender_train_db.csv");
                for (Gender_train item : list_3)
                {
                    row = item.to_csv();
                    file.write(row);
                }
                file.close();
                break;
            case 4:
                Collection<Trans> list_4 = getAllTransaction();
                file = new FileWriter("transaction_db.csv");
                for (Trans item : list_4)
                {
                    row = item.to_csv();
                    file.write(row);
                }
                file.close();
                break;
        }
    }

    public static Integer select_menu()
    {
        Scanner in = new Scanner(System.in);
        // меню
        System.out.println("Меню");
        System.out.println("Выберите таблицу");
        System.out.println("Таблицы");
        System.out.println("1 - таблица Транзакций");
        System.out.println("2 - таблица Кодов МСС");
        System.out.println("3 - таблица Типы транзакций");
        System.out.println("4 - таблица Пол клиентов");
        System.out.println("5 - Отчет");
        String menu = "";
        Integer n = 0;
        while(true) {
            n = in.nextInt();
            break;
        }
        return n;
    }

    public static Integer select_point()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Выберите дейтвие");
        System.out.println("Таблицы");
        System.out.println("1 - Добавить запись");
        System.out.println("2 - Удалить запись");
        System.out.println("3 - Изменить запись");
        System.out.println("4 - Получить запись");
        System.out.println("5 - Сохранить БД");
        System.out.println("6 - Загрузить БД");
        String menu = "";
        Integer n = 0;
        while(true) {
            n = in.nextInt();
            break;
        }
        return n;
    }
    public static void main(String[] args) throws IOException, NonExistentEntityException {
        try {
            Customer customer = getCustomer(1);
        } catch (NonExistentEntityException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }
        while(true) {
            Integer table_id = select_menu();
            if (table_id == 5)
            {
                // report
            }
            Integer point = select_point();
            switch (point)
            {
                case 1:
                    add_note(table_id);
                    break;
                case 2:
                    delete_note(table_id);
                    break;
                case 3:
                    get_note(table_id);
                    break;
                case 4:
                    update_note(table_id);
                    break;
                case 5:
                    load_db(table_id);
                    break;
                case 6:
                    save_db(table_id);
                    break;
                default:
                    System.out.println("Некорректный ввод");
            }
        }

        //Test whether a customer can be added to the database
       /*Customer firstCustomer = new Customer("Manuel", "Kelley", "ManuelMKelley@jourrapide.com");
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
        deleteCustomer(secondCustomer);*/
    }
    
    public static Customer getCustomer(int id) throws NonExistentEntityException {
        Optional<Customer> customer = CUSTOMER_DAO.get(id);
        return customer.orElseThrow(NonExistentCustomerException::new);
    }

    public static Trans getTrans(int id) throws NonExistentEntityException {
        Optional<Trans> item = TRANS_DAO.get(id);
        return item.orElseThrow(NonExistentCustomerException::new);
    }
    public static MCC_code getMCC(int id) throws NonExistentEntityException {
        Optional<MCC_code> item = MCC_DAO.get(id);
        return item.orElseThrow(NonExistentCustomerException::new);
    }
    public static Tr_type getType(int id) throws NonExistentEntityException {
        Optional<Tr_type> item = TYPE_DAO.get(id);
        return item.orElseThrow(NonExistentCustomerException::new);
    }
    public static Gender_train getGender(int id) throws NonExistentEntityException {
        Optional<Gender_train> item = GENDER_DAO.get(id);
        return item.orElseThrow(NonExistentCustomerException::new);
    }

    ///////////////////////////////////////////
    public static Collection<Tr_type> getAllTr_types() {
        return TYPE_DAO.getAll();
    }
    public static Collection<Trans> getAllTransaction() {
        return TRANS_DAO.getAll();
    }
    public static Collection<Gender_train> getAllGender_train() {
        return GENDER_DAO.getAll();
    }
    public static Collection<MCC_code> getAllMCC_codes() {
        return MCC_DAO.getAll();
    }

    public static void updateCustomer(Customer customer) {
        CUSTOMER_DAO.update(customer);
    }
    ////////////////////////////////////
    public static void updateTrType(Tr_type item) {
        TYPE_DAO.update(item);
    }
    public static void updateMCCCode(MCC_code item) {
        MCC_DAO.update(item);
    }
    public static void updateGender(Gender_train item) {
        GENDER_DAO.update(item);
    }
    public static void updateTransaction(Trans item) {
        TRANS_DAO.update(item);
    }
    ////////////////////////////////////
    public static Optional<Integer> addCustomer(Customer item) {
        return CUSTOMER_DAO.save(item);
    }
    public static Optional<Integer> addTrType(Tr_type item) {
        return TYPE_DAO.save(item);
    }
    public static Optional<Integer> addMCCCode(MCC_code item) {
        return MCC_DAO.save(item);
    }
    public static Optional<Integer> addTransaction(Trans item) {
        return TRANS_DAO.save(item);
    }
    ////////////////////////////////////
    public static Optional<Integer> addGender(Gender_train item) {
        return GENDER_DAO.save(item);
    }
    public static void deleteCustomer(Customer customer) {
        CUSTOMER_DAO.delete(customer);
    }
    public static void deleteTrType(Tr_type item) {
        TYPE_DAO.delete(item);
    }
    public static void deleteGender(Gender_train item) {
        GENDER_DAO.delete(item);
    }
    public static void deleteMCCCode(MCC_code item) {
        MCC_DAO.delete(item);
    }
    public static void deleteTransaction(Trans item) {
        TRANS_DAO.delete(item);
    }
}
