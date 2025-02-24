package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("metier")
public class IMetierImpl implements IMetier {
    @Autowired
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
