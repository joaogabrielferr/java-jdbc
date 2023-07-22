package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.EntityDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args)
	{
		Department department = new Department(1,"Books");
		
		System.out.println(department);
		
		Seller seller = new Seller(2,"fulano","fulano@gmail.com",new Date(),5000.0,department);
		
		System.out.println(seller);
		
		EntityDAO<Seller> sellerDao = DaoFactory.createSellerDao();
		
		
		
		
	}
	
}
