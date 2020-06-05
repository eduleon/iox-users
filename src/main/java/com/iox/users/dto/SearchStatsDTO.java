package com.iox.users.dto;

public class SearchStatsDTO {
//	{
//		responseCode: 0
//		description: “OK”,
//		elapsedTime: 245,
//		result: {
//		registerCount: NN
//		}
//		}

	private static class Result {
		private int registerCount = 0;

		public int getRegisterCount() {
			return registerCount;
		}

	}

	private int responseCode = 0;
	private String description = "OK";
	private long elapsedTime = 0;
	private Result result;

	public SearchStatsDTO(int registerCount, long elapsedTime) {
		this.result = new Result();
		this.result.registerCount = registerCount;
		this.elapsedTime = elapsedTime;
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

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public Result getResult() {
		return result;
	}

}
