package com.unisa.tesi.persistence;

import com.unisa.tesi.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder implements CommandLineRunner {

    private UserRepo userRepo;

    public UserSeeder(UserRepo userRepo){
        this.userRepo = userRepo;
        try{
            this.run("","","");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User("nome1", "data1", 12, "1cbafe2b");

        User u2 = new User("nome2", "data2", 12, "5a69460a");

        User u3 = new User("nome3", "data3", 36, "453bfbc2");

        User u4 = new User("nome4", "data4", 30, "2ef46501");

        User u5 = new User("nome5", "data5", 17, "8582fcc2");

        User u6 = new User("nome6", "data6", 22, "f914d5ab");

        User u7 = new User("nome7", "data7", 20, "f515fcc2");

        User u8 = new User("nome8", "data8", 13, "7984cedb");

        User u9 = new User("nome9", "data9", 14, "5860fe03");

        User u10 = new User("nome10", "data10", 19, "24fd6601");


        u1.setInside(true);

        u2.setInside(true);

        u3.setInside(true);


        this.userRepo.deleteAll();

        this.userRepo.save(u1);
        this.userRepo.save(u2);
        this.userRepo.save(u3);
        this.userRepo.save(u4);
        this.userRepo.save(u5);
        this.userRepo.save(u6);
        this.userRepo.save(u7);
        this.userRepo.save(u8);
        this.userRepo.save(u9);
        this.userRepo.save(u10);

    }
}
