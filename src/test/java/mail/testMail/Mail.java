package mail.testMail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.testng.annotations.Test;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class Mail {
//#163邮箱
  static int port = 25;
  static String server = "smtp.sina.com";
  static String from = "来自友助理的测试报告~~~";
  static String user = "hxm154@sina.com";
  static String password = "3fdec3d38d9527ba";//此处是pop密码

  
    public static void sendEmailAttach(String subject, String body, String Attachpath) throws UnsupportedEncodingException {
        try {
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp" );
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.port", String.valueOf(port));
            props.put("mail.smtp.auth", "true");
//            props.put("mail.transport.protocol", "smtp" );  
            Transport transport = null;
            Session session = Session.getDefaultInstance(props, null);
            transport = session.getTransport("smtp");
            transport.connect(server, user, password);
            MimeMessage msg = new MimeMessage(session);
            msg.setSentDate(new Date());
            InternetAddress fromAddress = new InternetAddress(user, from, "UTF-8");
            msg.setFrom(fromAddress);

            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(body, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);

            //添加附件
            // 创建一新的MimeBodyPart
            MimeBodyPart mdp = new MimeBodyPart();
            //得到文件数据源
            FileDataSource fds = new FileDataSource(Attachpath);
            //得到附件本身并至入BodyPart
            mdp.setDataHandler(new DataHandler(fds));
            //得到文件名同样至入BodyPart
            mdp.setFileName(fds.getName());
            mainPart.addBodyPart(mdp);

            //收件人的邮箱地址写这里
            String email = "huxiaom@yonyou.com";
//                String email="wanghongpeng@yixincapital.com,zhangyanli@yixincapital.com,geshengyan@yixincapital.com,lvyz@yixincapital.com,jialili@yixincapital.com,liyan@yixincapital.com,liyanhua@yixincapital.com,huxiaoming@yixincapital.com,wuweidong@yixincapital.com";

            if (email != null && email.trim().length() > 0) {
                String[] arr = email.split(",");
                int receiverCount = arr.length;
                if (receiverCount > 0) {
                    InternetAddress[] toAddress = new InternetAddress[receiverCount];
                    for (int i = 0; i < receiverCount; i++) {
                        toAddress[i] = new InternetAddress(arr[i]);
//                            System.out.println(toAddress[i]);
                    }
                    msg.setRecipients(Message.RecipientType.TO, toAddress);
                }
            }

            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            //附件添加到内容
            msg.setContent(mainPart);
            msg.saveChanges();
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    
@Test
public void sendMail() throws UnsupportedEncodingException{
	Mail.sendEmailAttach("这是一封有情怀的邮件","请查看附件，有问题随时联系我哦~", "E:\\yzl_jmeter\\jmeter-jmx\\test_res\\yzl-test-results.html");
} 
//    public static void main(String[] args) throws IOException {
////            Mail.email_send("app","df df",);
//        Mail.sendEmailAttach("测试伐木累", "Hello EVBD",  "E:/test_jmeter/jmeter-jmx/test_res/plus_medplus-android-test-results.html");
//    }
}