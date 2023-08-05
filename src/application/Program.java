package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDaoInterface;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args)
	{
		
		SellerDaoInterface sellerDao = DaoFactory.createSellerDao();
		
		//Seller seller = sellerDao.findById(7);
//		Department department = new Department(1,null);
//		
//		List<Seller> sellers = sellerDao.findByDepartment(department);
//		
//		for(int i = 0;i<sellers.size();i++)
//		{
//			System.out.println(sellers.get(i));
//		}
		
		List<Seller> sellers = sellerDao.findAll();
		
		for(Seller s : sellers)
		{
			System.out.println(s);
		}
		
		
	}
	
}
