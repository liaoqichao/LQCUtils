package lqcUtils.mail;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * ���ӣ�
 * Session session = MailUtils.createSession("smtp.163.com","lqcdayin","123");
 * Mail mail = new Mail("������","�ռ���","����","����(�ı�)");
 * AttachBean ab1 = new AttachBean(file,filename);	//�½���������1 ��ѡ
 * AttachBean ab2 = new AttachBean(file,filename);	//�½���������2 ��ѡ
 * mail.addAttach(ab1);	//��Ӹ���1	��ѡ
 * mail.addAttach(ab2);	//��Ӹ���2	��ѡ
 * mail.addCcAddress("abc@163.com,123@qq.com");//��ӳ���	��ѡ
 * mail.addBccAddress("mamamia@163.com,250@qq.com");//��Ӱ���	��ѡ
 * MailUtils.send(session,mail);
 */
public class MailUtils {

	public static Session createSession(String host, final String username, final String password) {
		Properties prop = new Properties();
		prop.setProperty("mail.host", host);// ָ������
		prop.setProperty("mail.smtp.auth", "true");// ָ����֤Ϊtrue

		// ������֤��
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		
		// ��ȡsession����
		return Session.getInstance(prop, auth);
	}
	
	/**
	 * ����ָ�����ʼ�
	 * 
	 * @param mail
	 */
	public static void send(Session session, final Mail mail) throws MessagingException,
			IOException {

		MimeMessage msg = new MimeMessage(session);// �����ʼ�����
		msg.setFrom(new InternetAddress(mail.getFrom()));// ���÷�����
		msg.addRecipients(RecipientType.TO, mail.getToAddress());// �����ռ���

		// ���ó���
		String cc = mail.getCcAddress();
		if (!cc.isEmpty()) {
			msg.addRecipients(RecipientType.CC, cc);
		}

		// ���ð���
		String bcc = mail.getBccAddress();
		if (!bcc.isEmpty()) {
			msg.addRecipients(RecipientType.BCC, bcc);
		}

		msg.setSubject(mail.getSubject());// ��������

		MimeMultipart parts = new MimeMultipart();// ��������������

		MimeBodyPart part = new MimeBodyPart();// ����һ������
		part.setContent(mail.getContent(), "text/html;charset=utf-8");// �����ʼ��ı�����
		parts.addBodyPart(part);// �Ѳ�����ӵ���������
		
		///////////////////////////////////////////

		// ��Ӹ���
		List<AttachBean> attachBeanList = mail.getAttachs();// ��ȡ���и���
		if (attachBeanList != null) {
			for (AttachBean attach : attachBeanList) {
				MimeBodyPart attachPart = new MimeBodyPart();// ����һ������
				attachPart.attachFile(attach.getFile());// ���ø����ļ�
				attachPart.setFileName(MimeUtility.encodeText(attach
						.getFileName()));// ���ø����ļ���
				parts.addBodyPart(attachPart);
			}
		}

		msg.setContent(parts);// ���ʼ���������
		Transport.send(msg);// ���ʼ�
	}
}
