package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDaoInterface createSellerDao()
	{
		return new SellerDaoJDBC(DB.getConnection());
		
	}
	
}
