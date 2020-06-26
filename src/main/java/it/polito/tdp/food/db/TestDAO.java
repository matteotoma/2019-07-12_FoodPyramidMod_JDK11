package it.polito.tdp.food.db;

public class TestDAO {

	public static void main(String[] args) {
		FoodDAO dao = new FoodDAO();
		
		System.out.println("Printing all the condiments...");
		System.out.println(dao.listAllCondiments());
	
		
		System.out.println("Printing all the portions...");
		System.out.println(dao.listAllPortions());
	}

}