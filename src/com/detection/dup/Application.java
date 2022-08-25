package com.detection.dup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Application {
	public static void main(String[] args) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test", "root", "1234");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from temp_batch_excel_info order by bulk_seq, affiliation");
			
			List<Element> elementList = new ArrayList<Element>();
			
			while(rs.next()) {
				int bulkSeq = rs.getInt("bulk_seq");
				String affiliation = rs.getString("affiliation");
				
				Element element = new Element();
				
				element.setBulkSeq(bulkSeq);
				element.setAffiliation(affiliation);
				
				elementList.add(element);
			}
			
			for (int i = 0; i < elementList.size() - 1; i++) {
				Element elmt = elementList.get(i);
				Element nextElmt = elementList.get(i+1);
				
				if (elmt.getBulkSeq() == nextElmt.getBulkSeq() && elmt.getAffiliation().equals(nextElmt.getAffiliation())) {
					System.out.println("bulkSeq : " + elmt.getBulkSeq() + ", affiliation : " + elmt.getAffiliation());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
