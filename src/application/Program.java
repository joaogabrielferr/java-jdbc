package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDaoInterface;
import model.entities.Seller;

public class Program {

	public static void main(String[] args)
	{
		
		SellerDaoInterface sellerDao = DaoFactory.createSellerDao();
		
		//Seller seller = sellerDao.findById(7);
		
		List<Seller> sellers = sellerDao.findByDepartment(1);
		
		for(int i = 0;i<sellers.size();i++)
		{
			System.out.println(sellers.get(i));
		}
				
		
	}
	
}
