package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.EntityDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args)
	{
		
		EntityDAO<Seller> sellerDao = DaoFactory.createSellerDao();
		
		Seller seller = sellerDao.findById(7);
		
		System.out.println(seller);
				
		
	}
	
}
