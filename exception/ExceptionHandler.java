package com.bskcare.ch.base.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import com.bskcare.ch.mail.util.MailSenderInfo;
import com.bskcare.ch.mail.util.SimpleMailSender;
import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;

/**
 * 异常邮件提醒
 * 
 * @author hzq
 *
 */
@Aspect
public class ExceptionHandler {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	@AfterThrowing(throwing = "ex", pointcut = "execution(* com.bskcare.ch.service.*.*.*(..))")
	public void sendExceptionMsg(Throwable ex) {
		if (!StringUtils.isDevelopment()) {
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setToAddress("764253804@qq.com");
			mailInfo.setSubject(DateUtils.longDate(new Date()) + ": "
					+ ex.getMessage());

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw, true);
			ex.printStackTrace(pw);
			pw.flush();
			sw.flush();

			mailInfo.setContent(sw.toString());

			boolean result = SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式

			if (result) {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> 已经发送异常邮件");
			} else {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> 异常邮件发送失败");
			}
		}
	}

}
