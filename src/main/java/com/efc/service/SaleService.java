package com.efc.service;

import com.efc.dto.ProductDTO;
import com.efc.dto.ProductInfoDTO;
import com.efc.dto.SaleDTO;
import com.efc.dto.SaleInfoDTO;
import com.efc.entity.ItemSale;
import com.efc.entity.Product;
import com.efc.entity.Sale;
import com.efc.entity.User;
import com.efc.repository.ItemSaleRepository;
import com.efc.repository.ProductRepository;
import com.efc.repository.SaleRepository;
import com.efc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    private SaleInfoDTO getSaleInfo(Sale sale) {
        SaleInfoDTO saleInfoDTO = new SaleInfoDTO();
        saleInfoDTO.setUser(sale.getUser().getName());
        saleInfoDTO.setDate(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        saleInfoDTO.setProducts(getProductInfo(sale.getItems()));

        return saleInfoDTO;
    }

    private List<ProductInfoDTO> getProductInfo(List<ItemSale> items) {
        return items.stream().map(item -> {
           ProductInfoDTO productInfoDTO = new ProductInfoDTO();
           productInfoDTO.setDescription(item.getProduct().getDescription());
           productInfoDTO.setQuantity(item.getQuantity());
           return productInfoDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public Long save(SaleDTO saleDTO) {

        User user = userRepository.findById(saleDTO.getUser_id()).get();

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

    private List<ItemSale> getItemSale(List<ProductDTO> products) {
        return products.stream().map(item -> {
            Product product = productRepository.getReferenceById(item.getProduct_id());
            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());
            return itemSale;
        }).collect(Collectors.toList());
    }

}