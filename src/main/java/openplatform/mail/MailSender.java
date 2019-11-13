package openplatform.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import openplatform.tools.Debug;
import openplatform.tools.StringArrayList;

/**
 * Class MailSender
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class MailSender
{
	private MailMessage msg;
	private Properties props = new Properties();
	private Transport transport;
	private SendHostInterface host;
	private Session session;

	public MailSender(SendHostInterface host)
	{
		this.host = host;

		try
		{
			if(host instanceof SmtpHost)
			{
				SmtpHost smtp = (SmtpHost)host;

				if(smtp.getAddress() != null)
					props.put("mail.smtp.host", smtp.getAddress().getHostAddress());

				props.put("mail.smtp.port", Integer.toString(smtp.getPort()));

				session = Session.getInstance(props, null);
				if(Debug.DEBUGON)
				{
					session.setDebug(true);
					session.setDebugOut(Debug.debugStream());
				}

				transport = session.getTransport("smtp");
				transport.addTransportListener(new MailSenderListener());
			}
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
		}
	}

	public void setMailMessage(MailMessage msg)
	{
		this.msg = msg;
	}

	public void send()
	throws Exception
	{
		// INIT MAIL
		MimeMessage message = new MimeMessage(session);
		message.setSentDate(new Date());
		message.setSubject(msg.getSubject());
		message.setFrom(new InternetAddress(msg.getFrom()));
		message.setHeader("X-Mailer", "http://www.dangconsulting.fr");

		StringArrayList toArray = msg.getTo();
		int len = toArray.size();
		for(int i=0; i<len; i++)
		{
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(toArray.getString(i)));
		}

		StringArrayList ccArray = msg.getCC();
		len = ccArray.size();
		for(int i=0; i<len; i++)
		{
			message.addRecipient(Message.RecipientType.CC,
					new InternetAddress(ccArray.getString(i)));
		}            

		StringArrayList bccArray = msg.getBCC();
		len = bccArray.size();
		for(int i=0; i<len; i++)
		{
			message.addRecipient(Message.RecipientType.BCC,
					new InternetAddress(bccArray.getString(i)));
		}             

		// ADD CONTENT
		Multipart multipart = new MimeMultipart();
		len = msg.size();
		for(int i=0; i<len; i++)
		{
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			MailPartInterface pi = msg.getPart(i);

			if(pi.getFileName() != null)
				messageBodyPart.setFileName(pi.getFileName());

			if(pi.getDescription() != null)
				messageBodyPart.setDescription(pi.getDescription());

			if(pi.getContentID() != null)
				messageBodyPart.setContentID(pi.getContentID());

			if(pi.getDisposition() != null)
				messageBodyPart.setDisposition(pi.getDisposition());

			if(pi instanceof MailTextPart)
			{
				MailTextPart text = (MailTextPart)pi;

				messageBodyPart.setContent(text.getText(), "text/html"); // to send link in email
				//messageBodyPart.setText(text.getText());

				multipart.addBodyPart(messageBodyPart);
			}
			else if(pi instanceof MailAttachmentPart)
			{
				MailAttachmentPart map = (MailAttachmentPart)pi;
				BinaryDataSource ds = new BinaryDataSource(map.getFileName(), map.getData());
				DataHandler dh = new DataHandler(ds);
				messageBodyPart.setDataHandler(dh);
				multipart.addBodyPart(messageBodyPart);
			}
		}
		message.setContent(multipart);
		message.saveChanges(); // to send link in email (usefull ??)

		transport.connect(host.getAddress().getHostAddress(),
				host.getPort(),
				null,
				null);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
}
