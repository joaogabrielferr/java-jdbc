package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.EntityDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements EntityDAO<Seller> {

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn)
	{
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT seller.*,department.Name as DepartmentName "
					+ "FROM seller INNER JOIN department ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
		
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next())
			{				
				Department department = new Department();
				department.setId(rs.getInt("DepartmentId"));
				department.setName(rs.getString("DepartmentName"));
				
				Seller seller = new Seller();
				seller.setId(rs.getInt("Id"));
				seller.setName(rs.getString("Name"));
				seller.setEmail(rs.getString("Email"));
				seller.setBaseSalary(rs.getDouble("BaseSalary"));
				seller.setBirthDate(rs.getDate("BirthDate"));
				seller.setDepartment(department);
			
				return seller;
				
			}
			
				
			
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return null;
	
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
