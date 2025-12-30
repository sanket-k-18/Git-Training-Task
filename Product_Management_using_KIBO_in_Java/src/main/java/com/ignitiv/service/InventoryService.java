package com.ignitiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignitiv.config.KiboConfig;
import com.kibocommerce.sdk.common.ApiException;
import com.kibocommerce.sdk.inventory.api.InventoryAllocationApi;
import com.kibocommerce.sdk.inventory.api.InventoryJobApi;
import com.kibocommerce.sdk.inventory.models.AllocateInventoryRequest;
import com.kibocommerce.sdk.inventory.models.BaseResponse;
import com.kibocommerce.sdk.inventory.models.JobQueueResponse;


@Service
public class InventoryService {
	
	@Autowired
	KiboConfig config;
	
	

	public JobQueueResponse createInventory(int tenantId, AllocateInventoryRequest allocateInventory) throws ApiException {
		InventoryAllocationApi api = InventoryAllocationApi.builder().withConfig(config.getConfiguration()).build();
		JobQueueResponse inv = api.allocateInventory(tenantId, allocateInventory);	
		System.out.println(inv);
		return inv;
	}
	
	public BaseResponse deallocateInventory(int tenantId, AllocateInventoryRequest allocateInventory) throws ApiException{
		InventoryAllocationApi api = InventoryAllocationApi.builder().withConfig(config.getConfiguration()).build();
		return api.deallocateInventory(tenantId, allocateInventory);
	}
	
	
	public BaseResponse fulfillInventory(int tenantId, AllocateInventoryRequest allocateInventory) throws ApiException{
		InventoryAllocationApi api = InventoryAllocationApi.builder().withConfig(config.getConfiguration()).build();
		return api.fulfillInventory(tenantId, allocateInventory);
	}
	
	public JobQueueResponse getJob(long jobId, int tenantId) throws ApiException {
		InventoryJobApi api = InventoryJobApi.builder().withConfig(config.getConfiguration()).build();
		return api.getJob(tenantId, jobId);
	}
	
	
}
