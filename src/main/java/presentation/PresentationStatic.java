package presentation;


import dao.IDao;
import dao.IDaoImpl;
import metier.IMetier;
import metier.IMetierImpl;

/**
 * Instanciation statique via le constructeur
**/
public class PresentationStatic {
    public static void main(String[] args) {
        IDao dao = new IDaoImpl();
        IMetier metier = new IMetierImpl(dao);
        System.out.println(metier.calcul());
    }
}
