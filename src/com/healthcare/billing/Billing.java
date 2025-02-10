package com.healthcare.billing;
import java.util.List;

import com.healthcare.models.Service;

/**
 * The {@code Billing} class handles the calculation of tax, discount, 
 * and the total amount for a given list of services.
 */
public class Billing {
    private double totalAmount;
    private int discount;
    private int taxRate;

    /**
     * Constructs a Billing object with the specified discount and tax rate.
     * 
     * @param discount The discount percentage applied to the total amount.
     * @param taxRate The tax rate percentage applied after the discount.
     */
    public Billing(int discount, int taxRate) {
        this.discount = discount;
        this.taxRate = taxRate;
        this.totalAmount = 0;
    }

    /**
     * Sets the billing details including discount and tax rate.
     * 
     * @param discount The discount percentage to be applied.
     * @param taxRate The tax rate percentage to be applied.
     */
    public void setBillingDetails(int discount, int taxRate) {
        this.discount = discount;
        this.taxRate = taxRate;
    }

    /**
     * Retrieves the discount percentage.
     * 
     * @return The discount percentage.
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * Retrieves the tax rate percentage.
     * 
     * @return The tax rate percentage.
     */
    public int getTaxRate() {
        return taxRate;
    }

    /**
     * Retrieves the total amount before discount and tax calculations.
     * 
     * @return The total amount.
     */
    public double getTotalAmount() {
        return this.totalAmount;
    }

    /**
     * Calculates the discount amount based on the total amount and discount percentage.
     * 
     * @return The discount amount.
     */
    public double calculateDiscountAmount() {
        return (totalAmount * discount) / 100.0;
    }

    /**
     * Calculates the total amount based on the list of services provided.
     * 
     * @param services The list of services to be billed.
     * @return The total amount before applying discounts and taxes.
     */
    public double calculateTotalAmount(List<Service> services) {
        if (services.isEmpty()) {
            return 0.0;
        }

        for (Service service : services) {
            this.totalAmount += service.calculatePrice();
        }

        return this.totalAmount;
    }

    /**
     * Calculates the tax amount based on the total amount after applying the discount.
     * 
     * @return The tax amount to be added to the bill.
     */
    public double calculateTaxAmount() {
        double amountAfterDiscount = totalAmount - calculateDiscountAmount();
        return (amountAfterDiscount * taxRate) / 100.0;
    }

    /**
     * Calculates the grand total after applying the discount and tax.
     * 
     * @return The final amount to be paid after applying all deductions and taxes.
     */
    public double calculateGrandTotal() {
        double amountAfterDiscount = totalAmount - calculateDiscountAmount();
        return amountAfterDiscount + calculateTaxAmount();
    }
}
