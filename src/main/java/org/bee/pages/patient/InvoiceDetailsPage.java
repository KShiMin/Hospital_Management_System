package org.bee.pages.patient;

import org.bee.controllers.BillController;
import org.bee.hms.billing.Bill;
import org.bee.hms.billing.BillingStatus;
import org.bee.hms.billing.PaymentMethod;
import org.bee.pages.ObjectDetailsPage;
import org.bee.ui.InputHelper;
import org.bee.ui.SystemMessageStatus;
import org.bee.ui.UiBase;
import org.bee.ui.View;
import org.bee.ui.details.IObjectDetailsAdapter;
import org.bee.utils.detailAdapters.BillDetailsAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Displays the detailed invoice page for a specific {@link Bill} object.
 * <p>
 * Allows the patient to review billing details and make full or partial payments using various payment methods.
 * Updates the billing status accordingly and handles user input and view refresh logic.
 */
public class InvoiceDetailsPage extends UiBase {

    private final Bill bill;
    private final IObjectDetailsAdapter<Bill> adapter;
    private final Runnable onChangeCallback;
    private final static BillController billController = BillController.getInstance();

    /**
     * Constructs a new {@code InvoiceDetailsPage} with the given bill, adapter, and change callback.
     *
     * @param bill              the {@link Bill} to be displayed
     * @param adapter           the adapter for rendering bill details
     * @param onChangeCallback  a callback to invoke when the bill is modified
     */
    public InvoiceDetailsPage(Bill bill, IObjectDetailsAdapter<Bill> adapter, Runnable onChangeCallback) {
        this.bill = bill;
        this.adapter = adapter;
        this.onChangeCallback = onChangeCallback;
    }

    /**
     * Creates the main view for the invoice details using an {@link ObjectDetailsPage}.
     *
     * @return a {@link View} displaying the bill details
     */
    @Override
    protected View createView() {
        ObjectDetailsPage<Bill> detailsPage = new ObjectDetailsPage<>(bill, adapter);
        return detailsPage.createView();
    }

    /**
     * Called after the view is created. Sets up available action buttons depending on bill status.
     *
     * @param parentView the parent view component
     */
    @Override
    public void OnViewCreated(View parentView) {
        BillingStatus status = bill.getStatus();
        setUpActionButtons(parentView, status);
        canvas.setRequireRedraw(true);
    }

    /**
     * Attaches payment input flow to the given parent view.
     * Prompts the user to choose a payment method and enter a payment amount.
     *
     * @param parentView the parent view that receives the payment input option
     */
    private void setupPaymentOptions(View parentView) {
        parentView.attachUserInput("Record Payment", input -> {
            promptForPaymentMethod(paymentMethod -> {
                promptForPaymentAmount(bill.getOutstandingBalance(), paymentMethod);
            });
        });
    }

    /**
     * Prompts the user to select a payment method from a predefined list.
     *
     * @param callback the callback to execute with the selected {@link PaymentMethod}
     */
    private void promptForPaymentMethod(Consumer<PaymentMethod> callback) {
        String[] paymentMethods = {"CASH", "CREDIT_CARD", "PAYNOW"};

        try {
            int methodIndex = InputHelper.getValidIndex(canvas.getTerminal(),
                    "Select payment method:", 1, paymentMethods.length);

            String selectedMethod = paymentMethods[methodIndex - 1];
            PaymentMethod paymentMethod = PaymentMethod.valueOf(selectedMethod);

            callback.accept(paymentMethod);
        } catch (Exception e) {
            showError("Error selecting payment method", e);
        }
    }

    /**
     * Prompts the user to enter a payment amount and records it as full or partial payment.
     *
     * @param maxAmount      the maximum payable amount (outstanding balance)
     * @param paymentMethod  the selected payment method
     */
    private void promptForPaymentAmount(BigDecimal maxAmount, PaymentMethod paymentMethod) {
        try {
            maxAmount = BigDecimal.valueOf(maxAmount.setScale(2, RoundingMode.HALF_UP).doubleValue());
            double amount = InputHelper.getValidDouble(canvas.getTerminal(),
                    "Enter payment amount (up to $" + formatCurrency(maxAmount) + "):",
                    0.01, maxAmount.doubleValue());

            BigDecimal paymentAmount = new BigDecimal(amount);

            if (paymentAmount.compareTo(bill.getOutstandingBalance()) >= 0) {
                bill.recordFullPayment(paymentMethod);
                saveChangesAndRefresh("Full payment of $" + formatCurrency(paymentAmount) + " recorded. Bill status updated to PAID.");
            } else {
                bill.recordPartialPayment(paymentAmount, paymentMethod);
                saveChangesAndRefresh("Partial payment of $" + formatCurrency(paymentAmount) + " recorded. " +
                        "Outstanding balance: $" + formatCurrency(bill.getOutstandingBalance()));
            }
        } catch (Exception e) {
            showError("Error recording payment", e);
        }
    }

    /**
     * Saves changes to the bill, updates the UI, and refreshes the current view.
     *
     * @param message a success message to display to the user
     */
    private void saveChangesAndRefresh(String message) {
        billController.saveData();

        View refreshedView = createView();
        navigateToView(refreshedView);

        setUpActionButtons(refreshedView, bill.getStatus());

        canvas.setSystemMessage(message, SystemMessageStatus.SUCCESS);
        canvas.setRequireRedraw(true);

        if (onChangeCallback != null) {
            onChangeCallback.run();
        }
    }

    /**
     * Sets up available action buttons (e.g., payment option) based on the bill status.
     *
     * @param parentView the view to attach the actions to
     * @param status     the current {@link BillingStatus} of the bill
     */
    private void setUpActionButtons(View parentView, BillingStatus status) {
        if ((status == BillingStatus.OVERDUE) || (status == BillingStatus.PARTIALLY_PAID) || (status == BillingStatus.PAYMENT_PENDING)){
            setupPaymentOptions(parentView);
        }
    }

    /**
     * Saves billing changes and notifies the user without refreshing the full view.
     *
     * @param message success message to display
     */
    private void saveChangesAndNotify(String message) {
        billController.saveData();

        canvas.setSystemMessage(message, SystemMessageStatus.SUCCESS);
        canvas.setRequireRedraw(true);

        if (onChangeCallback != null) {
            onChangeCallback.run();
        }
    }

    /**
     * Displays an error message on the UI.
     *
     * @param message a brief error description
     * @param e       the exception that caused the error
     */
    private void showError(String message, Exception e) {
        canvas.setSystemMessage(message + ": " + e.getMessage(),
                SystemMessageStatus.ERROR);
        canvas.setRequireRedraw(true);
    }

    /**
     * Formats a currency amount to 2 decimal places
     * @param amount The amount to format
     * @return Formatted currency string
     */
    private String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            return "$0.00";
        }
        return String.format("$%.2f", amount.doubleValue());
    }

}
