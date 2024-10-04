package model.dao.impl;

import model.dao.VendedorDao;

import java.sql.Connection;

public class VendedorDaoJDBC implements VendedorDao {

    private Connection conn;

    public VendedorDaoJDBC(Connection conn) {
        this.conn = conn;
    }




}
