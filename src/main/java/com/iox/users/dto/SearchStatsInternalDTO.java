package com.iox.users.dto;

import java.util.List;

//	{
//		"responseCode": 0,
//		"description": "OK",
//		"result": {
//		"items": []
//		}
//	}
public class SearchStatsInternalDTO {
	
	public static class Result {
		private List<Object> items;

		public List<Object> getItems() {
			return this.items;
		}
	}

	private int responseCode = 0;
	private String description = "OK";
	private Result result;
	
	public SearchStatsInternalDTO() {
	}

	public SearchStatsInternalDTO(List<Object> items) {
		this.result = new Result();
		this.result.items = items;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Result getResult() {
		return result;
	}

}
