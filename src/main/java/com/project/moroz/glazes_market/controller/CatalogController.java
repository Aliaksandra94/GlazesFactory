package com.project.moroz.glazes_market.controller;

import com.project.moroz.glazes_market.entity.*;
import com.project.moroz.glazes_market.service.interfaces.*;
import com.project.moroz.glazes_market.utils.Utils;
import com.project.moroz.glazes_market.validator.FormErrorMessage;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    private ProductService productService;
    private GlazesTypeService glazesTypeService;
    private ManagerService managerService;
    private MessageSource messageSource;
    private OrderService orderService;
    private FormErrorMessage formErrorMessage;

    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setGlazesTypeService(GlazesTypeService glazesTypeService) {
        this.glazesTypeService = glazesTypeService;
    }

    @Autowired
    public void setFormErrorMessage(FormErrorMessage formErrorMessage) {
        this.formErrorMessage = formErrorMessage;
    }

    @GetMapping
    public String getCatalogPage(Model model) {
        List<Product> products = productService.returnAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("glazeTypes", glazesTypeService.returnAllGlazesType());
        Map<Integer, Integer> orderedQuantities = new HashMap<>();
        for (Product product : products) {
            if (productService.returnOrderedProductQuantity(product.getId(), 1) == null) {
                orderedQuantities.put(product.getId(), 0);
            } else {
                orderedQuantities.put(product.getId(), productService.returnOrderedProductQuantity(product.getId(), 1));
            }
        }
        model.addAttribute("orderedQuantity", orderedQuantities);
        boolean hasAnyOrders = false;
        for (Product product : products) {
            if (orderService.returnOrdersIncludeProduct(product.getId()).size() > 0) {
                hasAnyOrders = true;
            }
        }
        model.addAttribute("hasOrders", hasAnyOrders);
        return "products/catalogPage";
    }

    @PostMapping()
    public String getProductsByGlazesTypePage(@RequestParam(value = "glazeTypeId") int glazeTypeId, Model model) {
        List<Product> products = productService.returnAllByGlazesTypeId(glazeTypeId);
        model.addAttribute("products", products);
        model.addAttribute("glazeTypes", glazesTypeService.returnAllGlazesType());
        Map<Integer, Integer> orderedQuantities = new HashMap<>();
        boolean hasAnyOrders = false;
        for (Product product : products) {
            if (productService.returnOrderedProductQuantity(product.getId(), 1) == null) {
                orderedQuantities.put(product.getId(), 0);
            } else {
                orderedQuantities.put(product.getId(), productService.returnOrderedProductQuantity(product.getId(), 1));
            }
            if (orderService.returnOrdersIncludeProduct(product.getId()).size() > 0) {
                hasAnyOrders = true;
            }
        }
        model.addAttribute("hasOrders", hasAnyOrders);
        if (products.isEmpty() || products.size() <= 0 || products == null) {
            String emptyMessage = messageSource.getMessage("productsNotFound.message", new Object[]{"productsNotFound.message"}, LocaleContextHolder.getLocale());
            model.addAttribute("msg", emptyMessage);
        }
        model.addAttribute("orderedQuantity", orderedQuantities);
        return "products/catalogPage";
    }

    @GetMapping("/create")
    public String createCustomRecipePage(Model model, @ModelAttribute("product") Product product) {
        List glazesTypeList = glazesTypeService.returnAllGlazesType();
        model.addAttribute("glazesType", glazesTypeList);
        return "products/addProductPage";
    }

    @PostMapping("/create")
    public String createProduct(HttpServletRequest request, Model model,
                                @RequestParam(value = "name") String name,
                                @RequestParam(value = "typeID") @Valid int glazesType,
                                @RequestParam(value = "colorId") String colorId,
                                @ModelAttribute("product") Product product, BindingResult result) {
        int quantity = -1;
        try {
            String number = request.getParameter("quantity");
            quantity = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            model.addAttribute("glazesType", glazesTypeService.returnAllGlazesType());
        }
        if (result.hasErrors()) {
            model.addAttribute("glazesType", glazesTypeService.returnAllGlazesType());
        }
        String fieldProductName = formErrorMessage.checkFieldProductName(request.getParameter("name"));
        model.addAttribute("fieldProductName", fieldProductName);
        String fieldProductQuantity = formErrorMessage.checkFieldProductQuantity("quantity", quantity);
        model.addAttribute("fieldProductQuantity", fieldProductQuantity);
        if (!formErrorMessage.checkCreateProductFormValid(fieldProductName, fieldProductQuantity)) {
            model.addAttribute("glazesType", glazesTypeService.returnAllGlazesType());
            return "products/addProductPage";
        }
        if (!formErrorMessage.checkCreateProductFormValid(fieldProductName, fieldProductQuantity)) {
            model.addAttribute("glazesType", glazesTypeService.returnAllGlazesType());
            return "products/addProductPage";
        }
        GlazesType glazesType1 = glazesTypeService.returnGlazesTypeById(glazesType);
        String description = colorId + " " + glazesType1.getName().toLowerCase() + " glazes";
        Double price = Precision.round(glazesType1.getCost() * 1.3, 2);
        Product product2 = new Product(name, description, price, quantity, 5, glazesType1.getCost(), glazesType1);
        model.addAttribute("product", product);
        model.addAttribute("product", product2);
        return "products/checkPrice";
    }

    @PostMapping("/create/checkPrice")
    public String saveProductAndPlaceOrder(HttpServletRequest request, Model model,
                                           @ModelAttribute("product") Product product,
                                           @RequestParam(value = "typeID") int glazesType) {
        model.addAttribute("product", product);
        product.setGlazesType(glazesTypeService.returnGlazesTypeById(glazesType));
        product.setCost(glazesTypeService.returnGlazesTypeById(glazesType).getCost());
        productService.saveProductWithGlazeType(product, glazesType);
        User user = Utils.getUserInSession(request);
        model.addAttribute("user", user);
        orderService.savePersonalOrder(product, user);
        return "redirect:/userPage/orderHistory";
    }

    @GetMapping("/add")
    public String addPage(Model model, @ModelAttribute("product") Product product) {
        List glazesTypeList = glazesTypeService.returnAllGlazesType();
        model.addAttribute("glazesType", glazesTypeList);
        return "products/addProductPage";
    }

    @PostMapping("/add")
    public String addProduct(Model model,
                             @RequestParam(value = "typeID") @Valid int glazesType,
                             @ModelAttribute("product") @Valid Product product,
                             BindingResult bindingResult) {
        model.addAttribute("product", product);
        if (bindingResult.hasErrors()) {
            List glazesTypeList = glazesTypeService.returnAllGlazesType();
            model.addAttribute("glazesType", glazesTypeList);
            return "products/addProductPage";
        }
        productService.saveProductWithGlazeType(product, glazesType);
        return "redirect:/catalog";
    }

    @GetMapping("/addToBasket")
    public String addProduct(HttpServletRequest request,
                             Model model, HttpSession session, @ModelAttribute("user") User user,
                             @RequestParam(name = "idProduct") int idProduct) {
        Product product = null;
        if (idProduct != 0) {
            product = productService.returnProductById(idProduct);
        }
        if (product != null) {
            com.project.moroz.glazes_market.model.Basket basket = Utils.getBasketInSession(request);
            com.project.moroz.glazes_market.model.Product product1 = new com.project.moroz.glazes_market.model.Product(product);
            basket.addProduct(product1, 1);
        }
        return "redirect:/catalog";
    }

    @GetMapping("/edit/{id}")
    public String editPage(Model model, @PathVariable("id") int id) {
        Product product = productService.returnProductById(id);
        model.addAttribute("product", product);
        List glazesTypeList = glazesTypeService.returnAllGlazesType();
        model.addAttribute("glazesType", glazesTypeList);
        return "products/editProductPage";
    }

    @PostMapping("/edit")
    public String editProduct(HttpServletRequest request, Model model, @ModelAttribute("product") @Valid Product product,
                              BindingResult bindingResult,
                              @RequestParam("id") int productId, @RequestParam("name") String productName,
                              @RequestParam("typeID") int glazesType, @RequestParam("description") String description,
                              @RequestParam("price") double productPrice, @RequestParam("quantity") int quantity,
                              @RequestParam("productionTime") int productionTime) {
        model.addAttribute("product", product);
        if (!request.isUserInRole("ROLE_SELLER")) {
            if (bindingResult.hasErrors()) {
                List glazesTypeList = glazesTypeService.returnAllGlazesType();
                model.addAttribute("glazesType", glazesTypeList);
                return "products/editProductPage";
            }
        }
        productService.updateProductWithNewData(productId, productName, glazesType, description, productPrice, quantity, productionTime);
        return "redirect:/catalog";
    }

    @GetMapping("/changeProductQuantity/{id}")
    public String editProductQuantityPage(Model model, @PathVariable("id") int id) {
        Product product = productService.returnProductById(id);
        model.addAttribute("product", product);
        Map<Integer, Integer> orderedQuantity = new HashMap<>();
        if (productService.returnOrderedProductQuantity(product.getId(), 1) == null) {
            orderedQuantity.put(product.getId(), 0);
        } else {
            orderedQuantity.put(product.getId(), productService.returnOrderedProductQuantity(product.getId(), 1));
        }
        model.addAttribute("orderedQuantity", orderedQuantity);
        return "products/changeProductQuantity";
    }

    @PostMapping("/changeProductQuantity")
    public String editProductQuantity(HttpServletRequest request, Model model,
                                      @RequestParam("id") int productId,
                                      @RequestParam("quantity") int quantity) {
        productService.updateProductQuantityWithPlacedOrder(productId, quantity);
        return "redirect:/catalog";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        Product product = productService.returnProductById(id);
        productService.deleteProduct(product);
        return "redirect:/catalog";
    }

    @GetMapping("/getOrders/{id}")
    public String getOrders(HttpServletRequest request, Model model, @PathVariable("id") int id) {
        List<Order> orders = orderService.returnOrdersIncludeProduct(id);
        model.addAttribute("orders", orders);
        User user = Utils.getUserInSession(request);
        Manager manager = managerService.returnManagerByLogin(user.getLogin());
        Set<Role> role = manager.getRoles();
        model.addAttribute("manager", manager);
        model.addAttribute("roles", role);
        return "products/orders";
    }

    @GetMapping("/changeGlazeComposition/{id}")
    public String getCompositionPage(Model model, @PathVariable("id") int id) {
        GlazesType glazesType = glazesTypeService.returnGlazesTypeById(id);
        model.addAttribute("glazesType", glazesType);
        return "products/changeComposition";
    }

    @PostMapping("/changeGlazeComposition")
    public String editComposition(Model model, @ModelAttribute("glazesType") @Valid GlazesType glazesType,
                                  BindingResult bindingResult,
                                  @RequestParam("id") int glazesTypeId) {
        if (glazesType.getName() == null || glazesType.getName().equalsIgnoreCase("")) {
            String errorMessage = messageSource.getMessage("error.emptyUserName", new Object[]{"error.emptyUserName"}, LocaleContextHolder.getLocale());
            bindingResult.rejectValue("name", "error.emptyUserName", errorMessage);
        }
        if (bindingResult.hasErrors()) {
            return "products/changeComposition";
        }
        double quantity = 0;
        for (RawMaterialItem rawMaterialItem : glazesType.getRawMaterialItems()) {
            quantity += rawMaterialItem.getQuantity();
        }
        if (quantity < 1) {
            String errorMessage = messageSource.getMessage("error.composition", new Object[]{"error.composition"}, LocaleContextHolder.getLocale());
            model.addAttribute("msg", errorMessage);
            return "products/changeComposition";
        }
        glazesTypeService.updateComposition(glazesTypeId, glazesType);
        return "redirect:/catalog";
    }

    @GetMapping("/getRawMaterials/{id}")
    public String getRawMaterialsPage(HttpServletRequest request, Model model, @PathVariable("id") int id) {
        GlazesType glazesType = glazesTypeService.returnGlazesTypeById(id);
        model.addAttribute("glazesType", glazesType);
        return "products/productRawMaterials";
    }
}
