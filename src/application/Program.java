package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDaoInterface;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args)
	{
		
		SellerDaoInterface sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("find by id:");
		Seller seller = sellerDao.findById(7);
		System.out.println(seller);
		
		System.out.println("find by department:");
		
		Department department = new Department(1,null);
		
		List<Seller> sellers = sellerDao.findByDepartment(department);
		
		for(int i = 0;i<sellers.size();i++)
		{
			System.out.println(sellers.get(i));
		}
		
		System.out.println("find all:");
		sellers = sellerDao.findAll();
		
		for(Seller s : sellers)
		{
			System.out.println(s);
		}
		
		System.out.println("insert:");
		
		seller = new Seller(null,"inserting with jdbc","insert@sellerdao.com",new Date(),5000.0,department);
			
		sellerDao.insert(seller);
		
		seller = new Seller(14,"editing with jdbc","insertingwithjdbc@sellerdao.com",new Date(),7000.0,new Department(3,null));
		
		sellerDao.update(seller);
		
		
		
		
	}
	
}
