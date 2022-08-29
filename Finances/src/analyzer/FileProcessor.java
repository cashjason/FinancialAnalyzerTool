package analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileProcessor {
	private static ArrayList<Record> transactions = new ArrayList<Record>();

	public void processFile(File file) throws InterruptedException, ParseException {
		try {
			String delimiter = ",";
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = " ";
			String[] tempArr;
			br.readLine();
			while ((line = br.readLine()) != null) {
				tempArr = line.split(delimiter);
				if(tempArr.length > 3)
				{
					//first item is date
					StringBuilder sb = new StringBuilder(tempArr[0].trim());
					sb.deleteCharAt(tempArr[0].trim().length()-1);
					sb.deleteCharAt(0);
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					Date date = df.parse(tempArr[0]);
					
					//second item is name
					String name = tempArr[1];
					
					//third item is amount
					float value = Float.parseFloat(tempArr[2]);
					
					Record record = new Record(date, name, value);
					transactions.add(record);
				}
			}
			br.close();
	    }
	    catch(IOException ioe) {
	    	ioe.printStackTrace();
	    }
	}
	
	public static ArrayList<Record> getTransactions() {
		return transactions;
	}
	
	public void printRecords() {
		for(Record record: transactions) {
			System.out.println(record.getDate() + "\t" + record.getValue() + "\t" + record.getName());
		}
	}
}
