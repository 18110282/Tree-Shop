package com.treeshop.service;

import com.treeshop.entity.*;
import com.treeshop.entity.lineitem.LineItemEntity;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.util.List;

public interface OrdersService {
    List<OrdersEntity> findAll();

    List<OrdersEntity> findByStatus(String status);

    List<OrdersEntity> findShipmentOrderList();

    OrdersEntity findOrderEntityById(String orderId);

    List<LineItemEntity> findLineItemOfOrderId(String orderId);

    List<OrdersEntity> findListOrderOfClient(String username);

    void addOrder(OrdersEntity ordersEntity, String username, HttpSession session) throws MessagingException, IOException;

    void updateStatusOfOrder(StatusOfOrder status, String orderId);

    void updateStatusOfListOrder(StatusOfOrder status, List<String> listOrderId);

    void assignShipperForOrder(Integer shipperId, List<String> listOrderId);

    //random orderId
    String createOrderId();

    //copy source HTML code from Processed Invoice Web Page
    String copySourceHTMLCodeFromWebPage(URL url) throws IOException;

    //Send Mail Invoice from processed mail-invoice-detail.html
    void sendMailInvoice(String orderId, String email, HttpServletRequest request) throws MessagingException, IOException;

    Integer getSubToTalOfOrder(String orderId);

    void setTransientOfOrder(String orderId);

    //Check orderId and status with requirement
    boolean  existsByStatusAndOrderId(StatusOfOrder status, String orderId);
}
