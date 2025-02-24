package dao;

import org.springframework.stereotype.Repository;

@Repository("dao")
public class IDaoImpl implements IDao {
    @Override
    public double getData() {
        return 20;
    }
}
