package com.ignitiv.webhook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignitiv.config.KiboConfig;
import com.ignitiv.dto.EventDTO;
import com.ignitiv.service.EventHandler;

@RestController
@RequestMapping("/api/webhooks")
public class WebhookController {

	@Autowired
	KiboConfig config;

	@Autowired
	EventHandler eventHandler;

	@PostMapping("/kibo")
	public ResponseEntity<?> receiveEvent(@RequestBody String payload) {

		try {
			System.out.println("=== KIBO WEBHOOK RECEIVED ===");
			ObjectMapper mapper = config.mapper();

			System.out.println(payload);

			EventDTO event = mapper.readValue(payload, EventDTO.class);

			switch (event.getTopic()) {

			case "order.imported":
			case "order.created":
				eventHandler.handleOrderCreated(event);
				break;
			}
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
