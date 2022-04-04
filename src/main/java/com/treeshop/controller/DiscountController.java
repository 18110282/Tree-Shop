package com.treeshop.controller;

import com.treeshop.entity.DiscountCodeEntity;
import com.treeshop.entity.ProductsEntity;
import com.treeshop.service.DiscountCodeService;
import com.treeshop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/discount")
public class DiscountController {
    @Autowired
    private ProductsService productsService;

    @Autowired
    private DiscountCodeService discountCodeService;

    //Begin Discount By Product
    @GetMapping("/list-by-product")
    public String showListDiscountByProduct(Model model) {
        List<ProductsEntity> productsEntityList = productsService.findAll();
        model.addAttribute("listProduct", productsEntityList);
        return "/views/admin/discount/listDiscountByProduct";
    }

    @GetMapping("/delete-by-product/{productId}")
    public String deletePercentByProduct(@PathVariable("productId") String productId,
                                         RedirectAttributes ra){
        ProductsEntity productsEntity = productsService.findByProductId(productId);
        productsEntity.setDiscountPercent(0);
        productsService.saveProduct(productsEntity);
        ra.addFlashAttribute("successMessage", "Xoá % giảm giá của <strong>" + productId  + "</strong> thành công!");
        return "redirect:/admin/discount/list-by-product";
    }
    @PostMapping("/add-percent")
    public String addPercentByProduct(@RequestParam(value = "inputPercent",required = false) Integer inputValue,
                                      @RequestParam(value = "productId", required = false) String productId,
                                      RedirectAttributes ra){
        if(inputValue > 100){
            ra.addFlashAttribute("errorMessage",  productId+" Thất bại! Phần trăm giảm giá phải nhỏ hơn 100!");
        }
        else {
            ProductsEntity productsEntity = productsService.findByProductId(productId);
            productsEntity.setDiscountPercent(inputValue);
            productsService.saveProduct(productsEntity);
            ra.addFlashAttribute("successMessage", "Thêm giảm giá cho sản phẩm <strong>" + productId + "</strong> thành công!");
        }
        return "redirect:/admin/discount/list-by-product";

    }

    //End Discount By Product

    //Begin Discount By Code
    @GetMapping("/list-by-code")
    public String showListDiscountByCode(Model model){
        List<DiscountCodeEntity> discountCodeEntityList = discountCodeService.findAll();
        model.addAttribute("listDiscount", discountCodeEntityList);
        return "/views/admin/discount/listDiscountByCode";
    }

    @GetMapping("/add-by-code")
    public String showAddDiscountByCodeForm(Model model){
        model.addAttribute("discountCode", new DiscountCodeEntity());
        model.addAttribute("titlePage", "Thêm mã giảm giá");
        return "/views/admin/discount/manageDiscountByCode";
    }
    @GetMapping("/edit-by-code/{codeId}")
    public String showEditDiscountByCodeForm(@PathVariable("codeId") String codeId,
                                             Model model){
        DiscountCodeEntity discountCodeEntity = discountCodeService.findByCodeId(codeId);
        model.addAttribute("discountCode", discountCodeEntity);
        model.addAttribute("titlePage", "Chỉnh sửa mã giảm giá");
        model.addAttribute("edit", " ");
        return "/views/admin/discount/manageDiscountByCode";
    }

    @GetMapping("/delete-by-code/{codeId}")
    public String deleteDiscountByCode(@PathVariable("codeId")String codeId,
                                       RedirectAttributes ra){
        discountCodeService.deleteDiscountCode(codeId);
        ra.addFlashAttribute("successMessage", "Xóa mã <strong> " + codeId + " </strong> thành công!");
        return "redirect:/admin/discount/list-by-code";
    }

    @PostMapping("/save-code")
    public String saveDiscountByCode(DiscountCodeEntity discountCodeEntity,
                                     RedirectAttributes ra,
                                     HttpServletRequest request){
        Date currentDate = new Date();
        boolean checkDate = currentDate.before(discountCodeEntity.getEndDate());
        if(checkDate){
            discountCodeEntity.setStatus("Còn hạng");
        }
        else {
            discountCodeEntity.setStatus("Hết hạng");
        }
        String codeId = discountCodeEntity.getCodeId();
        String previousUrl = request.getHeader("referer");
        String url = previousUrl.substring((request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()).length());
        if(url.equals("/admin/discount/add-by-code")){
            if(discountCodeService.checkCodeId(codeId)){
                ra.addFlashAttribute("errorMessage", codeId);
                return "redirect:/admin/discount/add-by-code";
            }
            else {
                ra.addFlashAttribute("successMessage", "Thêm mã <strong>" + codeId + "</strong> thành công");
            }
        }
        else if(url.contains("/admin/discount/edit-by-code")){
            ra.addFlashAttribute("successMessage", "Chỉnh sửa mã <strong> " + codeId + "</strong> thành công!");
        }
        discountCodeService.saveDiscoutCode(discountCodeEntity);
        return "redirect:/admin/discount/list-by-code";
    }
    //End Discount By Code
}
