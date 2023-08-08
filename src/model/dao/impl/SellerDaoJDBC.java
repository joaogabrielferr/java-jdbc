package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("INSERT INTO seller (Name,Email,BirthDate,BaseSalary,DepartmentId) VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, entity.getName());
			ps.setString(2, entity.getEmail());
			ps.setDate(3, new java.sql.Date(entity.getBirthDate().getTime()));
			ps.setDouble(4, entity.getBaseSalary());
			ps.setInt(5, entity.getDepartment().getId());
		
			int rowsAffected = ps.executeUpdate();
		
			if(rowsAffected > 0)
			{
				rs = ps.getGeneratedKeys();
				if(rs.next())
				{
					int id = rs.getInt(1);
					entity.setId(id);
				}
			}else
			{
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		}catch(SQLException e)
		{
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public void update(Seller entity) {
			
		PreparedStatement ps = null;
			
		try {
		
			ps = conn.prepareStatement("UPDATE seller SET Name = ?,Email = ?,BirthDate = ?,BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, entity.getName());
			ps.setString(2, entity.getEmail());
			ps.setDate(3, new java.sql.Date(entity.getBirthDate().getTime()));
			ps.setDouble(4, entity.getBaseSalary());
			ps.setInt(5, entity.getDepartment().getId());
			ps.setInt(6, entity.getId());
			
			ps.executeUpdate();	
		}catch(SQLException e)
		{
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void delete(Integer id) {
		
		PreparedStatement ps = null;
			
		try {
				
			ps = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
		}catch(SQLException e)
		{
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}
		
		
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
	public List<Seller> findByDepartment(Department d) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
				
			ps = conn.prepareStatement("SELECT seller.*,department.Name as departmentName "
					+ "FROM seller JOIN department ON seller.DepartmentId = department.Id "
					+ "WHERE department.Id = ? "
					+ "ORDER BY seller.Name");
			
			ps.setInt(1, d.getId());
			
			rs = ps.executeQuery();
			
			List<Seller> sellers = new ArrayList<>();
						
			Map<Integer,Department> map = new HashMap<>();
			
			while(rs.next())
			{

				Department department = map.get(rs.getInt("DepartmentId"));
				
				if(department == null)
				{
					department = createDepartment(rs);
					map.put(rs.getInt("DepartmentId"), department);
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
			
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
				
			ps = conn.prepareStatement("SELECT seller.*,department.Name as departmentName "
					+ "FROM seller JOIN department ON seller.departmentId = department.Id "
					+ "ORDER BY seller.Name");
			
			rs = ps.executeQuery();
			
			List<Seller> sellers = new ArrayList<>();
			
			Map<Integer,Department> map = new HashMap<>();
			
			while(rs.next())
			{
				Department department = map.get(rs.getInt("DepartmentId"));
				
				if(department == null)
				{
					department = createDepartment(rs);
					map.put(rs.getInt("DepartmentId"), department);
				}
					
				sellers.add(createSeller(rs,department));
				
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
