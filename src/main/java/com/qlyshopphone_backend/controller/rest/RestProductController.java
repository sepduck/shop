package com.qlyshopphone_backend.controller.rest;

import com.github.javafaker.Faker;
import com.qlyshopphone_backend.dto.ProductDTO;
import com.qlyshopphone_backend.model.Users;
import com.qlyshopphone_backend.service.ProductService;
import com.qlyshopphone_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Validated
public class RestProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT', 'ROLE_USER')")
    @GetMapping("/product")
    public ResponseEntity<?> getAllProducts() {

        return productService.getAllProducts();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@Validated @ModelAttribute ProductDTO productDTO,
                                        BindingResult result,
                                        Principal principal) {

        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errors.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
//            Map<String, String> errors = new HashMap<>();
//            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
//            return ResponseEntity.badRequest().body(errors);
        }
        try {
            Users users = userService.findByUsername(principal.getName());
            productDTO.setDeleteProduct(false);
            return ResponseEntity.ok(productService.saveProduct(productDTO, users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
//    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> uploadFile(@PathVariable("id") int id,
//                                        @ModelAttribute("files") List<MultipartFile> files) {
//        try {
//            Product existingProduct = productService.getProductById(id);
//            files = files == null ? new ArrayList<MultipartFile>() : files;
//            if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
//                return ResponseEntity.badRequest().body("You can only upload 5 files");
//            }
//            List<ProductImage> productImages = new ArrayList<>();
//            for (MultipartFile file : files) {
//                // Bỏ qua nếu file mà không có ảnh
//                if (file.getSize() == 0) {
//                    continue;
//                }
//                // Kiểm tra kích thước ảnh nhỏ hơn 10MB
//                if (file.getSize() > 10 * 1024 * 1024) {
//                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
//                            .body("File is too large! Max size is 10MB");
//                }
//                // Kiểm tra có phải là ảnh không ?
//                String contentType = file.getContentType();
//                if (contentType == null || !contentType.startsWith("image/")) {
//                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//                            .body("File must be an image");
//                }
//                String fileName = storeFile(file);
//                ProductImage productImage = productService.saveProductImage(
//                        ProductImageDTO.builder().imageUrl(fileName).build(),
//                        existingProduct.getProductId());
//                productImages.add(productImage);
//            }
//            return ResponseEntity.ok().body(productImages);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//
//    // Nơi lưu file ảnh
//    private String storeFile(MultipartFile file) throws IOException {
//        if (!isImageFile(file) || file.getOriginalFilename() == null) {
//            throw new IOException("Invalid image file");
//        }
//        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//        // Thêm thư viện UUID vào trước file để đảm bảo tên file là duy nhất
//        String uniqueFilename = UUID.randomUUID().toString() + "_" + fileName;
//        // Đường dẫn đến thư mục muốn lưu file
//        Path uploadDir = Paths.get("uploads");
//        // Kiểm tra và tạo thư mục nếu nó tồn tại
//        if (!Files.exists(uploadDir)) {
//            Files.createDirectory(uploadDir);
//        }
//        // Đường dẫn đầy đủ đến file
//        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
//        // Sao chép file đến thư mục đích
//        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//        return uniqueFilename;
//    }
//
//    private boolean isImageFile(MultipartFile file) {
//        String contentType = file.getContentType();
//        return contentType != null && contentType.startsWith("image/");
//    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @PutMapping("product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") int productId,
                                           @ModelAttribute ProductDTO productDTO,
                                           Principal principal) throws Exception {
        Users users = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(productService.updateProduct(productId, productDTO, users));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')")
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId, Principal principal) {
        Users users = userService.findByUsername(principal.getName());
        return productService.deleteProduct(productId, users);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT','ROLE_USER')")
    @GetMapping("/product/name/{productName}")
    public ResponseEntity<?> searchAllByProductName(@PathVariable("productName") String productName) {
        return productService.searchAllByProductName(productName);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT','ROLE_USER')")
    @GetMapping("/product/id/{productId}")
    public ResponseEntity<?> searchAllByProductId(@PathVariable("productId") int productId) {
        return productService.searchAllByProductId(productId);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("/product/generateFakeProducts")
//    public ResponseEntity<String> generateFakeProducts() {
//        Faker faker = new Faker();
//        for (int i = 0; i < 1000; i++) {
//            String productName = faker.commerce().productName();
//            if (productService.existsByProductName(productName)) {
//                continue;
//            }
//            ProductDTO productDTO = ProductDTO.builder().productName(productName)
////                    .price(faker.number().numberBetween(10, 100000000))
////                    .capitalPrice(faker.number().numberBetween(10, 100000000))
////                    .inventory(faker.number().numberBetween(0, 1000))
//                    .groupProductId(faker.number().numberBetween(1, 100)).trademarkId(faker.number().numberBetween(1, 100)).locationId(faker.number().numberBetween(1, 16))
////                    .weight(faker.number().numberBetween(1, 10))
//                    .propertiesId(faker.number().numberBetween(1, 100)).unitId(faker.number().numberBetween(1, 100)).deleteProduct(faker.bool().bool()).build();
//            try {
//                productService.saveProduct(productDTO);
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body(e.getMessage());
//            }
//        }
//        return ResponseEntity.ok("Fake products generated");
//    }

    // Tìm kiếm tồn kho
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT','ROLE_USER')")
    @GetMapping("/product/search-inventory/{number}")
    public ResponseEntity<?> searchInventory(@PathVariable("number") int number) {
        return productService.searchInventory(number);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT','ROLE_USER')")
    @GetMapping("/product/search-active/{number}")
    public ResponseEntity<?> searchActive(@PathVariable("number") int number) {
        return productService.searchActive(number);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT','ROLE_USER')")
    @GetMapping("product/search-group-product/{id}")
    public ResponseEntity<?> searchGroupProductId(@PathVariable int id) {
        return productService.searchGroupProductId(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT','ROLE_USER')")
    @GetMapping("/product/search-location/{locationId}")
    public ResponseEntity<?> searchByLocationId(@PathVariable int locationId) {
        return productService.searchByLocationId(locationId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT','ROLE_USER')")
    @GetMapping("/product/findById/{id}")
    public ResponseEntity<?> getFindByProductId(@PathVariable int id) {
        return productService.findByIdProduct(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT','ROLE_USER')")
    @GetMapping("/product/search-category/{number}")
    public ResponseEntity<?> searchCategory(@PathVariable int number) {
        return productService.searchCategory(number);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT','ROLE_USER')")
    @GetMapping("/product/search-direct-sales/{number}")
    public ResponseEntity<?> searchDirectSales(@PathVariable int number) {
        return productService.searchDirectSales(number);
    }
}

