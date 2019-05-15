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

        User u1 = new User("nome1", "data1", 10, "1cbafe2b");
        User u2 = new User("nome2", "data2", 20, "uid2");
        User u3 = new User("nome3", "data3", 30, "uid3");


        this.userRepo.deleteAll();

        this.userRepo.save(u1);
        this.userRepo.save(u2);
        this.userRepo.save(u3);

    }
}
