package dang.cmsbase;

import java.io.InputStream;

import openplatform.database.dbean.DBCmsInbox;
import openplatform.database.dbean.DBCmsInboxAttachment;
import openplatform.database.dbean.DBCmsPreferences;
import openplatform.database.dbean.DBFileBase;
import openplatform.database.dbean.DBUser;
import openplatform.file.Repository;
import openplatform.http.NavigCodec;
import openplatform.mail.MailAttachmentPart;
import openplatform.mail.MailMessage;
import openplatform.mail.MailSender;
import openplatform.mail.MailTextPart;
import openplatform.mail.SmtpHost;
import openplatform.regexp.Regexp;
import openplatform.sms.Sms;
import openplatform.taglibs.Out;
import openplatform.tools.Debug;
import openplatform.tools.SQLTime;
import openplatform.tools.StringArrayList;



/**
 * Class Notificator
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Notificator
{
	public static String PRIORITY_NORMAL = DBCmsInbox.PRIORITY_NORMAL;
	public static String PRIORITY_HIGH = DBCmsInbox.PRIORITY_HIGH;

	public static boolean send(String fromId, String toId, String priority, String subject, String text)
	{
		if(fromId == null || toId == null) return false;

		DBCmsInbox inbox = new DBCmsInbox();
		inbox.setIsRead("0");
		inbox.setFromUserId(fromId);
		inbox.setToUserId(toId);
		inbox.setPriority(priority);
		inbox.setSubject(subject);
		inbox.setText(text);
		inbox.setDate(SQLTime.getSQLTime(System.currentTimeMillis()));
		inbox.store();

		process(inbox);

		return !inbox.isInError();
	}


	protected static void process(DBCmsInbox in)
	{
		try
		{
			if(in == null || in.getFromUserId() == null || in.getToUserId() == null)
				return;

			DBUser umask = new DBUser();
			umask.setUserId(in.getToUserId());
			DBUser to = DBUser.loadByKey(umask);

			umask.clear();
			umask.setUserId(in.getFromUserId());
			DBUser from = DBUser.loadByKey(umask);

			DBCmsPreferences pref = DBCmsPreferences.loadByKey(null);
			if("1".equals(to.getFwdMsgToMail()))
			{
				String smtpStr = pref.getSmtpHost();
				MailTextPart t = new MailTextPart();
				t.setText(toHTML(in.getText()));

				MailMessage mail = new MailMessage();
				mail.setSubject(in.getSubject());
				mail.setFrom(from.getMail());
				mail.addTo(to.getMail());
				mail.addPart(t);

				DBCmsInboxAttachment iamask = new DBCmsInboxAttachment();
				iamask.setInboxId(in.getId());
				DBCmsInboxAttachment[] ia = DBCmsInboxAttachment.load(iamask);
				if(ia != null)
				{
					int len = ia.length;
					DBFileBase fbmask = new DBFileBase();
					for(int i=0; i<len; i++)
					{
						fbmask.clear();
						fbmask.setFileBaseId(ia[i].getFileBaseId());
						DBFileBase fb = DBFileBase.loadByKey(fbmask);

						if(fb == null) continue;

						InputStream input = Repository.getStream(fb.getFileBaseId());
						MailAttachmentPart ap = new MailAttachmentPart();
						ap.setData(input, fb.getFilename());
						ap.setFileName(fb.getFilename());

						mail.addPart(ap);
					}
				}


				SmtpHost host = new SmtpHost(smtpStr);
				MailSender sender = new MailSender(host);
				sender.setMailMessage(mail);
				sender.send();

				in.delete(); // delete the inbox
			}



			if("1".equals(to.getCpMsgToSMS()) && DBCmsInbox.PRIORITY_HIGH.equals(in.getPriority()))
			{
				String phone = to.getMobilePhone();
				if(phone != null && !phone.trim().equals(""))
				{
					Sms sms = new Sms();
					sms.send(from.getLogin(), phone, removeTaglibs("["+in.getSubject()+"] "+ in.getText()));
				}
			}

		}
		catch(Exception e)
		{
			Notificator n = new Notificator();
			Debug.println(n, Debug.ERROR, e);
			Debug.println(n, Debug.ERROR, "DBCmsInbox="+in);
		}
	}


	private static String toHTML(String message)
	{
		String ip = null;
		DBCmsPreferences p = DBCmsPreferences.loadByKey(null);
		if(p != null && p.getIpAddress() != null)
		{
			if(!p.getIpAddress().trim().equals(""))
				ip = p.getIpAddress();
		}

		StringBuffer sb = new StringBuffer();
		Regexp urlreg = new Regexp(Out.URL_STR);
		if(urlreg.matches(message))
		{
			StringArrayList sal = urlreg.getMatchingResults();
			sb.append(sal.getString(1));
			sb.append("<a href=\"");

			StringBuffer urlsb = new StringBuffer();

			if(ip != null)
			{
				urlsb.append(ip);
			}

			urlsb.append(sal.getString(2));
			String navig = NavigCodec.code(urlsb.toString());

			sb.append(navig).append("\">");
			sb.append(sal.getString(3));
			sb.append("</a>");
			sb.append(sal.getString(4));
		}
		else
			sb.append(message);

		return sb.toString();
	}


	private static String removeTaglibs(String message)
	{
		StringBuffer sb = new StringBuffer();
		Regexp urlreg = new Regexp(Out.URL_STR);
		if(urlreg.matches(message))
		{
			StringArrayList sal = urlreg.getMatchingResults();
			sb.append(sal.getString(1));
			sb.append(sal.getString(3));
			sb.append(sal.getString(4));
		}
		else
			sb.append(message);

		return sb.toString();
	}
}
