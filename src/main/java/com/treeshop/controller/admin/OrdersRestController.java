package com.treeshop.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.treeshop.entity.LineItemEntity;
import com.treeshop.entity.OrdersEntity;
import com.treeshop.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class OrdersRestController {
    @Autowired
    private OrdersService ordersService;
    @GetMapping("/admin/order/detail/{orderId}")
    public OrdersEntity getOrderDetail(@PathVariable(name = "orderId") String orderId){
//    public List<LineItemEntity> getOrderDetail(@PathVariable(name = "orderId") String orderId) {
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        OrdersEntity ordersEntity = ordersService.findOrderEntityById(orderId);
//        String json = ow.writeValueAsString(ordersService.findOrderEntityById(orderId));
        OrdersEntity ordersEntity = ordersService.findOrderEntityById(orderId);
        ordersService.setTransientOfOrder(orderId);
//        String result = new ObjectMapper().writeValueAsString(ordersEntity);
//        List<LineItemEntity> lineItemEntityList = ordersService.findLineItemOfOrderId(orderId);
        return ordersEntity;
    }
}
