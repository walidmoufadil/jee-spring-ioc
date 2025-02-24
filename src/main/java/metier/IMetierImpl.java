package metier;

import dao.IDao;

public class IMetierImpl implements IMetier {
    private IDao dao ;

    public IMetierImpl() {
    }

    //injection statique via le constructeur
    public IMetierImpl(IDao dao) {
        this.dao = dao ;
    }
    @Override
    public double calcul() {
        return dao.getData() * 3;
    }

    //injection statique via le setter
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
