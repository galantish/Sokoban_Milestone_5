package model.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import commons.Level;

@Entity(name="Users")
public class User 
{	
	@Id
	@Column(name="UserName")
	private String name;

	@OneToMany
	@JoinColumn(name="UserName")
	private List<Level> levels = new ArrayList<Level>();
	
	public User() {}
	
	public User(String name)
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public List<Level> getLevels() 
	{
		return levels;
	}

	public void setLevels(List<Level> levels) 
	{
		this.levels = levels;
	}

	@Override
	public String toString() 
	{
		return "UserName=" + name + "]";
	}
	
	
	
}
