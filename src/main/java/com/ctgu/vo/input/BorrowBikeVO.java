package com.ctgu.vo.input;

import com.ctgu.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowBikeVO
{
	private String token;

	private String userCode;

	private Double lon;

	private Double lat;

	private Integer mapType;

	private Boolean ble;

	private Integer orderSource;

	private User user;

	// private Machine machine;

	// private AccountConfig accountConfig;
}
