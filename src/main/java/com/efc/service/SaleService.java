package com.efc.service;

import com.efc.dto.ProductSaleDTO;
import com.efc.dto.ProductInfoDTO;
import com.efc.dto.SaleDTO;
import com.efc.dto.SaleInfoDTO;
import com.efc.entity.ItemSale;
import com.efc.entity.Product;
import com.efc.entity.Sale;
import com.efc.entity.User;
import com.efc.exception.NotFoundException;
import com.efc.exception.SaleException;
import com.efc.repository.ItemSaleRepository;
import com.efc.repository.ProductRepository;
import com.efc.repository.SaleRepository;
import com.efc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ItemSaleRepository itemSaleRepository;

    @Autowired
    public SaleService(UserRepository userRepository, ProductRepository productRepository,
                       SaleRepository saleRepository, ItemSaleRepository itemSaleRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.itemSaleRepository = itemSaleRepository;
    }

    public List<SaleInfoDTO> getAll() {
        return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
    }

    public SaleInfoDTO getById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new SaleException("Sale not found"));
        return getSaleInfo(sale);
    }

    private SaleInfoDTO getSaleInfo(Sale sale) {

        List<ProductInfoDTO> products = getProductInfo(sale.getItems());
        BigDecimal total = getTotal(products);

        return SaleInfoDTO.builder()
                .user(sale.getUser().getName())
                .date(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .products(products)
                .total(total)
                .build();
    }

    private List<ProductInfoDTO> getProductInfo(List<ItemSale> items) {

        if(CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }

        return items.stream().map(
                item -> ProductInfoDTO.builder()
                        .id(item.getId())
                        .description(item.getProduct().getDescription())
                        .quantity(item.getQuantity())
                        .price(item.getProduct().getPrice())
                        .build()
                ).collect(Collectors.toList());
    }

    private BigDecimal getTotal(List<ProductInfoDTO> products) {
        BigDecimal total = new BigDecimal(0);
        for(ProductInfoDTO p : products) {
            total = total.add(p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())));
        }
        return total;
    }

    @Transactional
    public Long save(SaleDTO saleDTO) {
        User user = userRepository.findById(saleDTO.getUser_id())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Sale sale = new Sale();
        sale.setUser(user);
        sale.setDate(LocalDate.now());
        List<ItemSale> items = getItemSale(saleDTO.getItems());

        sale = saleRepository.save(sale);
        saveItemSale(items, sale);

        return sale.getId();
    }

    private void saveItemSale(List<ItemSale> items, Sale sale) {
        for (ItemSale item : items) {
            item.setSale(sale);
            itemSaleRepository.save(item);
        }
    }

    private List<ItemSale> getItemSale(List<ProductSaleDTO> products) {
        if(products.isEmpty()){
            throw new SaleException("Order without products");
        }
        return products.stream().map(item -> {
            Product product = productRepository.findById(item.getProduct_id())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            if(product.getQuantity() == 0){
                throw new SaleException("Product out of stock: " + product.getDescription());
            }
            else if(product.getQuantity() < item.getQuantity()) {
                throw new SaleException(String.format("Quantity of: %s (%s) exceeds stock quantity (%s)",
                                            product.getDescription(), item.getQuantity(), product.getQuantity()));
            }
            Integer total = product.getQuantity() - item.getQuantity();
            product.setQuantity(total);
            productRepository.save(product);

            return itemSale;
        }).collect(Collectors.toList());
    }
}
