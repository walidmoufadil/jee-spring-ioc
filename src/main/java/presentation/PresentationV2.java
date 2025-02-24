package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.util.Scanner;


/**
 * Instanciation dynamique via un fichier .txt
 **/

public class PresentationV2 {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("config.txt"));
            String daoClassName = sc.nextLine();
            System.out.println(daoClassName);

            Class<?> cDao = Class.forName(daoClassName);
            IDao dao = (IDao) cDao.getConstructor().newInstance();

            String metierClassName = sc.nextLine();
            System.out.println(metierClassName);
            Class<?> cMetier = Class.forName(metierClassName);
            IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

            System.out.println(metier.calcul());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
