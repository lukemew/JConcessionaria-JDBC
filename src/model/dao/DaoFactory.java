package model.dao;

import db.DB;
import model.dao.impl.;
import model.dao.impl.ProdutoDaoJDBC;
import model.dao.impl.VendedorDaoJDBC;

public class DaoFactory {

    public static VendedorDao createVendedorDao() {
        return new ProdutoDaoJDBC(DB.getConnection());
        return new ClienteDaoJDBC(DB.getConnection());
        return new VendedorDaoJDBC(DB.getConnection()); {
        }
    }
}
