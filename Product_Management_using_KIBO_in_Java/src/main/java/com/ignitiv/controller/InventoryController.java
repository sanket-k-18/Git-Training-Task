package com.ignitiv.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ignitiv.service.InventoryService;
import com.kibocommerce.sdk.common.ApiException;
import com.kibocommerce.sdk.inventory.models.AllocateInventoryRequest;
import com.kibocommerce.sdk.inventory.models.BaseResponse;
import com.kibocommerce.sdk.inventory.models.JobQueueResponse;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
	
	@Autowired
	InventoryService service;
	
	@PostMapping("/{tenantId}")
	public ResponseEntity<?> createInventory(@PathVariable int tenantId, @RequestBody AllocateInventoryRequest allocateInv) {
		try {
			System.out.println(allocateInv);
			JobQueueResponse response = service.createInventory(tenantId, allocateInv);
			return ResponseEntity.ok(response);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	
	@DeleteMapping("/{tenantId}")
	public ResponseEntity<?> deallocateInventory(@PathVariable int tenantId, @RequestBody AllocateInventoryRequest allocateInv){
		try {
			BaseResponse response = service.deallocateInventory(tenantId, allocateInv);
			return ResponseEntity.ok(response);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	@PostMapping("/fulfill/{tenantId}")
	public ResponseEntity<?> fulfillInventory(@PathVariable int tenantId, AllocateInventoryRequest allocateInv){
		try {
			BaseResponse response = service.fulfillInventory(tenantId, allocateInv);
			return ResponseEntity.ok(response);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	
	@GetMapping("/job")
	public ResponseEntity<?> getJob(@RequestParam long jobId, @RequestParam int tenantId) {
		try {
			 int castedTenant = (int) tenantId;
			 JobQueueResponse response =  service.getJob(jobId, castedTenant);
			 return ResponseEntity.ok(response);
		}catch(ApiException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
	
	
	
}
