package model.dao;

import db.DB;
import model.dao.impl.ClienteDaoJDBC;
import model.dao.impl.VendedorDaoJDBC;

public class DaoFactory {

    public static VendedorDao createVendedorDao() {
        return new VendedorDaoJDBC(DB.getConnection());
    }

    public static ClienteDao createClienteDao() {
        return new ClienteDaoJDBC(DB.getConnection());
    }

}
