package com.maybezone.productservice.global.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybezone.productservice.domain.product.dto.request.RequestStockDto;
import com.maybezone.productservice.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class StockEventConsumer {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @Transactional
    @KafkaListener(topics = "${kafka.topic.stock}", containerFactory = "stockKafkaListenerContainerFactory")
    public void listener(String message) throws JsonProcessingException {
        log.info("message = {}", message);
        RequestStockDto requestStockDto = objectMapper.readValue(message, RequestStockDto.class);
        log.info("requestBidDto = {}, {}", requestStockDto.getProductId());
        productService.updateStockCount(requestStockDto.getProductId());
    }
}
