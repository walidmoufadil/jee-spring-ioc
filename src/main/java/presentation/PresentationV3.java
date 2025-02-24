package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Instanciation dynamique version XML en utilisant Spring
 **/

public class PresentationV3 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        IMetier metier= (IMetier) context.getBean("metier");
        System.out.println(metier.calcul());
    }
}
