package com.qait.MAPS.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qait.automation.utils.YamlReader;

import com.qait.automation.utils.DataProvider;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class MyTest {


   public static void check(String host, String storeType, String user,
      String password) 
   {
      try {

      //create properties field
      Properties properties = new Properties();

      properties.put("mail.pop3.host", host);
      properties.put("mail.pop3.port", "995");
      properties.put("mail.pop3.starttls.enable", "true");
      Session emailSession = Session.getDefaultInstance(properties);
  
      //create the POP3 store object and connect with the pop server
      Store store = emailSession.getStore("pop3s");

      store.connect(host, user, password);

      //create the folder object and open it
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_ONLY);

      // retrieve the messages from the folder in an array and print it
      Message[] messages = emailFolder.getMessages();
      System.out.println("messages.length---" + messages.length);

      for (int i = 0, n = messages.length ; i < 10; i++) {
         Message message = messages[i];
         System.out.println("---------------------------------");
         System.out.println("Email Number " + (i + 1));
         System.out.println("Subject: " + message.getSubject());
         System.out.println("From: " + message.getFrom()[0]);
         System.out.println("Text: " + message.getContent().toString());

      }

      //close the store and folder objects
      emailFolder.close(false);
      store.close();

      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}  

//   public static void main(String[] args) {
//
//      String host = "pop.gmail.com";// change accordingly
//      String mailStoreType = "pop3";
//      String username = "sil.hitashee@gmail.com";// change accordingly
//      String password = "995360755700";// change accordingly
//
//      check(host, mailStoreType, username, password);
//
//   }}
//=======
//	public static void main(String... s) {
//		List<String> dataOfFile = new ArrayList<String>();
//		dataOfFile.add(",");
//		dataOfFile.add("session_title");
//		dataOfFile.add(",,Symposium,Poster,,,,,,,,,,,,,,,,,");
//		YamlReader.getYamlValues("Symposium_FileData");
//		getDataForImportedFile(YamlReader.getYamlValues("Symposium_FileData"));
//		String csvFile = "C:\\Users\\hitasheesil\\Downloads\\symposias.csv";
//		String data= DataProvider.csvReaderRowSpecific(csvFile, "No", "1");
//		System.out.println("data**********"+data);
//		//DataProvider.writeDataInAlreadyExistingCSVFile(downloadedFilePath, dataOfFile);
		
//		
//		List<String> dataBeforeSorting=new ArrayList<>();
//		dataBeforeSorting.add("2538697");
//		dataBeforeSorting.add("2538699");
//		dataBeforeSorting.add("2538685");
//		dataBeforeSorting.add("2538313");
//		dataBeforeSorting.add("2538679");
//		
//		Collections.sort(dataBeforeSorting);
//		System.out.println("-------:"+dataBeforeSorting);
//	}
//
//	private static void getDataForImportedFile(Map<String, Object> map) {
//		Set keys = map.keySet();
//		for (Iterator i = keys.iterator(); i.hasNext();) {
//			String key = (String) i.next();
//			String value = (String) map.get(key);
//			System.err.println(key + " = " + value);
//		}
//
//	}
	
//		String str="var"+" "+"aTags=document.getElementsByTagName(\"button\");var"+" " +"searchText=\"Cancel\";"+
//		"var"+" "+"found;"+"for(var"+" "+"i=0;i<aTags.length;i++){"+ "(aTags[i].textContent===searchText ? found=aTags[i] : '')"+" "+"}"+
//		"var"+" "+"style=window.getComputedStyle(found);"+"console.log(style.display);";
//	System.out.println("---------:"+str);
//	}
//}
//	
//>>>>>>> 5c38ac663844cd67dfa88e3386970624cbf0f760
//
//}
////public class MyTest {
////
////	public static void main(String... s) {
//////		List<String> dataOfFile = new ArrayList<String>();
//////		dataOfFile.add(",");
//////		dataOfFile.add("session_title");
//////		dataOfFile.add(",,Symposium,Poster,,,,,,,,,,,,,,,,,");
//////		YamlReader.getYamlValues("Symposium_FileData");
//////		getDataForImportedFile(YamlReader.getYamlValues("Symposium_FileData"));
////		String csvFile = "C:\\Users\\hitasheesil\\Downloads\\symposias.csv";
////		String data= DataProvider.csvReaderRowSpecific(csvFile, "No", "1");
////		System.out.println("data**********"+data);
////		//DataProvider.writeDataInAlreadyExistingCSVFile(downloadedFilePath, dataOfFile);
////	}
////
////	private static void getDataForImportedFile(Map<String, Object> map) {
////		Set keys = map.keySet();
////		for (Iterator i = keys.iterator(); i.hasNext();) {
////			String key = (String) i.next();
////			String value = (String) map.get(key);
////			System.err.println(key + " = " + value);
////		}
////
////	}
////	
////
////}
