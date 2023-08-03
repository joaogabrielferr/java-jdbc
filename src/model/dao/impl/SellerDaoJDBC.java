package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDaoInterface;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDaoInterface {

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
				Department department = createDepartment(rs);
				
				Seller seller = createSeller(rs,department);
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
	public List<Seller> findByDepartment(Integer departmentId) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
				
			ps = conn.prepareStatement("SELECT seller.*,department.Name as departmentName "
					+ "FROM seller JOIN department ON seller.DepartmentId = department.Id "
					+ "WHERE department.Id = ? "
					+ "ORDER BY seller.Name");
			
			ps.setInt(1, departmentId);
			
			rs = ps.executeQuery();
			
			List<Seller> sellers = new ArrayList<>();
			
//			Department department = null;
			
			Map<Integer,Department> map = new HashMap<>();
			
			while(rs.next())
			{
//				if(department == null)
//				{
//					department = createDepartment(rs);					
//				}
				
				Department department = map.get(rs.getInt("DepartmentInt"));
				
				if(department == null)
				{
					department = createDepartment(rs);
					map.put(rs.getInt("DepartmentInt"), department);
				}
				
				Seller seller = createSeller(rs,department);
				sellers.add(seller);
			}
			
			return sellers;
			
		}catch(SQLException e)
		{
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		
		
		
	}

	
	@Override
	public List<Seller> findAll() {
		return null;
	}
	

	private Department createDepartment(ResultSet rs) throws SQLException
	{
		Department department = new Department();
		department.setId(rs.getInt("DepartmentId"));
		department.setName(rs.getString("DepartmentName"));
		return department;
	}
	
	private Seller createSeller(ResultSet rs, Department department) throws SQLException
	{
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setDepartment(department);
		
		return seller;
	}

}
