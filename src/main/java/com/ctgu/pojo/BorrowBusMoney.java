package com.ctgu.pojo;

/**
 * 借车业务金额
 */
public class BorrowBusMoney
{
	/** 押金 */
	private Double depositMoeny;
	/** 首充金额 */
	private Double rechargeMoney;
	/** 最少金额 */
	private Double miniMoney;

	public BorrowBusMoney()
	{
		super();
	}

	public BorrowBusMoney(Double depositMoeny, Double rechargeMoney)
	{
		super();
		this.depositMoeny = depositMoeny;
		this.rechargeMoney = rechargeMoney;
	}

	public BorrowBusMoney(Double miniMoney)
	{
		this.miniMoney = miniMoney;
	}

	public Double getDepositMoeny()
	{
		return depositMoeny;
	}

	public void setDepositMoeny(Double depositMoeny)
	{
		this.depositMoeny = depositMoeny;
	}

	public Double getRechargeMoney()
	{
		return rechargeMoney;
	}

	public void setRechargeMoney(Double rechargeMoney)
	{
		this.rechargeMoney = rechargeMoney;
	}

	public Double getMiniMoney()
	{
		return miniMoney;
	}

	public void setMiniMoney(Double miniMoney)
	{
		this.miniMoney = miniMoney;
	}

	@Override
	public String toString()
	{
		return "BorrowBusMoney [depositMoeny=" + depositMoeny + ", rechargeMoney=" + rechargeMoney + "]";
	}
}
