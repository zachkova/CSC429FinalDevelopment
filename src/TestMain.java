import model.Worker;

import java.util.Properties;
import java.util.Scanner;

public class TestMain {

        public static void main(String [] args){


                Properties p = new Properties();
                p.setProperty("bannerId", "800732338");
                p.setProperty("password", "Hgt998557");
                p.setProperty("firstName", "Hunter");
                p.setProperty("lastName", "Thomas");
                p.setProperty("contactPhone", "5857389454");
                p.setProperty("email", "huntergthomas1998@gmail.com");
                p.setProperty("credentials", "Administrator");
                p.setProperty("dateOfLatestCredentialsStatus", "2021-04-10");
                p.setProperty("dateOfHire", "2020-04-09");
                p.setProperty("status", "Active");

               // Worker w = new Worker(p);
               // System.out.println("Hunter Thomas lol");
               // w.update();



        }
}

