package com.qlyshopphone_backend.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    @Value("${mail.email}")
    private String email;

    private void sendEmail(String userEmail, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlMsg = "<body style='font-family: Arial, sans-serif; background-color: #f5f5f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0;'>"
                + "<div style='background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 100%; text-align: left;'>"
                + content
                + "<footer style='margin-top: 20px; font-size: 12px; color: #777; text-align: center;'>"
                + "<p><a href='#' style='color: #007bff; text-decoration: none'>Trợ giúp</a>  |  <a href='#' style='color: #007bff; text-decoration: none'>Mẹo bảo mật email</a></p>"
                + "<p>X Corp. 1355 Market Street, Suite 900 San Francisco, CA 94103</p>"
                + "</footer>"
                + "</div>"
                + "</body>";

        helper.setText(htmlMsg, true);
        helper.setTo(userEmail);
        helper.setSubject(subject);
        helper.setFrom(email);
        mailSender.send(mimeMessage);
    }

    @Async
    public void sendVerificationMail(String userEmail, String verificationCode) throws MessagingException {
        String subject = verificationCode + " là mã xác nhận tài khoản Cuccushop của bạn";
        String content = "<h1 style='font-size: 24px; margin-bottom: 10px;'>Xác nhận địa chỉ email của bạn</h1>"
                + "<p style='margin: 10px 0;'>Trước khi tạo tài khoản, bạn còn cần hoàn thành một bước nhỏ nữa. Hãy chắc chắn rằng đây là địa chỉ email chính xác của bạn — vui lòng xác nhận đây là địa chỉ chính xác để sử dụng cho tài khoản mới của bạn.</p>"
                + "<p style='font-weight: bold; margin: 20px 0;'>Vui lòng nhập mã xác nhận này để bắt đầu trên CUCCUSHOP:</p>"
                + "<h2 style='font-size: 30px; color: #007bff; margin: 10px 0;'>" + verificationCode + "</h2>"
                + "<p style='margin: 10px 0;'>Mã xác nhận hết hạn sau hai giờ.</p>"
                + "<p style='margin: 20px 0;'>Xin cảm ơn,</p>";

        sendEmail(userEmail, subject, content);
    }

    @Async
    public void sendNewPasswordMail(String userEmail, String newPassword) throws MessagingException {
        String subject = newPassword + " là mã khôi phục tài khoản Cuccushop của bạn";
        String content = "<h1 style='font-size: 24px; margin-bottom: 10px;'>Khôi phục mật khẩu</h1>"
                + "<p style='margin: 10px 0;'>Đây là mật khẩu mới của bạn cho tài khoản Cuccushop:</p>"
                + "<h2 style='font-size: 30px; color: #007bff; margin: 10px 0;'>" + newPassword + "</h2>"
                + "<p style='margin: 10px 0;'>Hãy đăng nhập và đổi mật khẩu ngay để bảo vệ tài khoản của bạn.</p>"
                + "<p style='margin: 20px 0;'>Xin cảm ơn,</p>";

        sendEmail(userEmail, subject, content);
    }
}
