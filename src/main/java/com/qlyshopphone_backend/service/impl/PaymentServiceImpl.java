package com.qlyshopphone_backend.service.impl;

import com.qlyshopphone_backend.model.Orders;
import com.qlyshopphone_backend.service.PaymentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
//    @Getter
//    @Value("${payment.vnPay.url}")
//    private String vnp_PayUrl;
//    @Value("${payment.vnPay.returnUrl}")
//    private String vnp_ReturnUrl;
//    @Value("${payment.vnPay.tmnCode}")
//    private String vnp_TmnCode;
//    @Getter
//    @Value("${payment.vnPay.secretKey}")
//    private String secretKey;
//    @Value("${payment.vnPay.version}")
//    private String vnp_Version;
//    @Value("${payment.vnPay.command}")
//    private String vnp_Command;
//    @Value("${payment.vnPay.orderType}")
//    private String orderType;

    @Override
    public String createPaymentUrl(Orders order, BigDecimal totalAmount) throws Exception {
        // Chuẩn bị các tham số để gửi đến VNPay
//        Map<String, String> vnp_Params = new HashMap<>();
//        vnp_Params.put("vnp_Version", "2.1.0");
//        vnp_Params.put("vnp_Command", "pay");
//        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//        vnp_Params.put("vnp_Amount", String.valueOf(totalAmount.multiply(BigDecimal.valueOf(100)).longValue())); // VNPAY sử dụng đơn vị VND x100
//        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("vnp_TxnRef", String.valueOf(order.getId())); // Mã đơn hàng
//        vnp_Params.put("vnp_OrderInfo", "Order payment #" + order.getId()); // Thông tin đơn hàng
//        vnp_Params.put("vnp_Locale", "vn");
//        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
//        vnp_Params.put("vnp_IpAddr", "127.0.0.1"); // Địa chỉ IP của người dùng
//
//        // Thời gian hết hạn của link thanh toán
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        Date expiration = new Date(cld.getTimeInMillis() + 15 * 60 * 1000); // Hết hạn sau 15 phút
//        vnp_Params.put("vnp_CreateDate", formatDate(cld.getTime()));
//        vnp_Params.put("vnp_ExpireDate", formatDate(expiration));
//
//        // Tạo chuỗi để mã hóa SHA256
//        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder hashData = new StringBuilder();
//        StringBuilder query = new StringBuilder();
//        for (String fieldName : fieldNames) {
//            String fieldValue = vnp_Params.get(fieldName);
//            if (!hashData.isEmpty()) {
//                hashData.append("&");
//                query.append("&");
//            }
//            hashData.append(fieldName).append("=").append(fieldValue);
//            query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
//                    .append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
//        }
//
//        // Mã hóa chuỗi với SHA256
//        String vnp_SecureHash = hmacSHA512(secretKey, hashData.toString());
//        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
//
//        // Tạo URL hoàn chỉnh
//        return vnp_PayUrl + "?" + query;
        return "Success";
    }

    // Hàm hỗ trợ format ngày tháng theo yêu cầu của VNPay
    private String formatDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.format("%tY%tm%td%tH%tM%tS", cal, cal, cal, cal, cal, cal);
    }

    // Hàm mã hóa SHA-512
    private String hmacSHA512(String key, String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(key.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = md.digest(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
