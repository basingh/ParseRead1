//package com.kate.gmail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * 
 * Get Mime Message
 * @author basingh
 *
 */
 
 
import javax.mail.internet.MimeMessage; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException;
/**
 * Eng get Mime Message
 * 
 * @author basingh
 *
 */

public class MyClass {
	/** Application name. */
	private static final String APPLICATION_NAME = "Gmail API Java";

	/** Directory to store user credentials. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"),
			".credentials/gmail-api");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the scopes required. */
	private static final List<String> SCOPES = Arrays
			.asList(GmailScopes.GMAIL_READONLY);

	/** Quantity to check */
	private static final int EMAIL_QTY = 3;

	/** File to Client secret */
	private static final String CLIENT_SECRET_FILE = "/client_secret.json";
	


	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 */
	public static Credential authorize() throws IOException {
		// Load client secrets.
 		InputStream in = MyClass.class.getResourceAsStream(CLIENT_SECRET_FILE);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(DATA_STORE_FACTORY)
				.setAccessType("offline").build();

		Credential credential = new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to "
				+ DATA_STORE_DIR.getAbsolutePath());

		return credential;
	}
	
	/**
	 * Get Mime message
	 * 
	 */
	  
	/**
	 * End Get Mime Message
	 * 
	 */

	/**
	 * Build and return an authorized Gmail client service.
	 */
	public static Gmail getGmailService() throws IOException {
		Credential credential = authorize();
		return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}
	
	 public static Message getMessage(Gmail service, String userId, String messageId)
		      throws IOException {
		    Message message = service.users().messages().get(userId, messageId).setFormat("RAW").execute();
		    String mailBody;
		//	System.out.println("Message snippet to String: " + message);
			
		//    System.out.println("Message snippet: " + message.getSnippet());
		//    System.out.println("Message Info: " + message.toPrettyString());
		//	Message message = service.users().messages().get("me", messages.get(i).getId()).setFormat("full").execute();
		//	System.out.println("Message Snippet: " + message.getSnippet());
		//	System.out.println("Message Payload: " + message.getPayload());
			//get Mime Message
			
		
			//end get Mime Message
		    
		    /**
		     * new deocode start
		     
		    
		    String mimeType = message.getPayload().getMimeType();
		    List<MessagePart> parts = message.getPayload().getParts();
		    if (mimeType.contains("alternative")) {
		      //  log.info("entering alternative loop");
		        for (MessagePart part : parts) {
		             mailBody = new String(Base64.decodeBase64(part.getBody()
		                    .getData().getBytes()));
		             
		        }
		        
		       // log.info(mailBody);
		    }
		   
		    /
		     * new decode end
		     */
		    return message;
		  }
	 
	 public static MimeMessage getMimeMessage(Gmail service, String userId, String messageId)
		      throws IOException, MessagingException {
		    Message message = service.users().messages().get(userId, messageId).setFormat("raw").execute();

		    Base64 base64Url = new Base64(true);
		    byte[] emailBytes = base64Url.decodeBase64(message.getRaw());

		    Properties props = new Properties();
		    Session session = Session.getDefaultInstance(props, null);

		    MimeMessage email = new MimeMessage(session, new ByteArrayInputStream(emailBytes));
		   
		    System.out.println("Message email parsed: " + email.getFrom());
		    System.out.println("Message email: " + email);
		    

		    return email;
		  }


	public static void main(String[] args) throws IOException {
		try {
			// Build a new authorized API client service.
			Gmail service = getGmailService();
			
			//Get a Message and use it to create a MimeMessage.
			//getMimeMessage (service, "me", "15ccd7c2feec35d7");
			getMessage ( service, "me", "15ccd7c2feec35d7");
			// Get list of messages
			ListMessagesResponse response = service.users().messages()
					.list("me").execute();
			System.out.println("DONE: " + response); 
			List<Message> messages = new ArrayList<Message>();
			while (response.getMessages() != null) {
				messages.addAll(response.getMessages());
				if (response.getNextPageToken() != null) {
					String pageToken = response.getNextPageToken();
					response = service.users().messages().list("me")
							.setPageToken(pageToken).execute();
					System.out.println("DONE 1: " + response); 
				} else {
					break;
				}
			}
			// Get message content
			for (int i = 0; i < messages.size() && i < EMAIL_QTY; i++) {
		//		System.out.println("Message Info: " + messages.get(i).toPrettyString());
				Message message = service.users().messages().get("me", messages.get(i).getId()).setFormat("full").execute();
		//		System.out.println("Message Snippet: " + message.getSnippet());
		//		System.out.println("Message Payload: " + message.getPayload().getMimeType());
				String mimeType = message.getPayload().getMimeType();
				 List<MessagePart> parts = message.getPayload().getParts();
				    if (mimeType.contains("alternative")) {
				      //  log.info("entering alternative loop");
				        for (MessagePart part : parts) {
				             String mailBody = new String(Base64.decodeBase64(part.getBody()
				                    .getData().getBytes()));
				             System.out.println("Body content  :"+mailBody);
				        }
				        
				       // log.info(mailBody);
				    }
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
