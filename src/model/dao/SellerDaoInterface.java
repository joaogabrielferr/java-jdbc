package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDaoInterface extends EntityDAO<Seller> {

	
	List<Seller> findByDepartment(Integer departmentId);
	
}
