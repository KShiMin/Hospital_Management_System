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
 * Displays detailed information about a patient's invoice and allows payment recording.
 * <p>
 * This page:
 * <ul>
 *     <li>Displays invoice details using an adapter</li>
 *     <li>Supports payment processing (full/partial)</li>
 *     <li>Handles payment method and amount input from user</li>
 *     <li>Refreshes view and updates billing status after payment</li>
 * </ul>
 */
public class InvoiceDetailsPage extends UiBase {

    /** The bill being displayed */
    private final Bill bill;

    /** Adapter for rendering bill details */
    private final IObjectDetailsAdapter<Bill> adapter;

    /** Callback triggered when changes are made */
    private final Runnable onChangeCallback;

    /** Controller for managing bill data */
    private static final BillController billController = BillController.getInstance();

    /**
     * Constructor to create the invoice details page
     * @param bill The bill to display
     * @param adapter Adapter used to present bill data
     * @param onChangeCallback Callback triggered after payment updates
     */
    public InvoiceDetailsPage(Bill bill, IObjectDetailsAdapter<Bill> adapter, Runnable onChangeCallback) {
        this.bill = bill;
        this.adapter = adapter;
        this.onChangeCallback = onChangeCallback;
    }

    /**
     * Creates the view showing the bill details
     * @return View instance displaying invoice information
     */
    @Override
    protected View createView() {
        ObjectDetailsPage<Bill> detailsPage = new ObjectDetailsPage<>(bill, adapter);
        return detailsPage.createView();
    }

    /**
     * Initializes the view and adds payment options based on billing status
     * @param parentView The parent view container
     */
    @Override
    public void OnViewCreated(View parentView) {
        BillingStatus status = bill.getStatus();
        setUpActionButtons(parentView, status);
        canvas.setRequireRedraw(true);
    }

    /**
     * Adds "Record Payment" option for eligible billing statuses
     * @param parentView The view to attach actions to
     */
    private void setupPaymentOptions(View parentView) {
        parentView.attachUserInput("Record Payment", input -> {
            promptForPaymentMethod(paymentMethod -> {
                promptForPaymentAmount(bill.getOutstandingBalance(), paymentMethod);
            });
        });
    }

    /**
     * Prompts user to select a payment method and triggers callback
     * @param callback Consumer that handles selected payment method
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
     * Prompts user for payment amount and processes full/partial payment
     * @param maxAmount Maximum allowable payment amount
     * @param paymentMethod The selected method of payment
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
     * Saves changes, refreshes view, and displays a success message
     * @param message Message to display after update
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
     * Attaches payment options to view based on billing status
     * @param parentView View to attach to
     * @param status Current billing status
     */
    private void setUpActionButtons(View parentView, BillingStatus status) {
        if ((status == BillingStatus.OVERDUE) || (status == BillingStatus.PARTIALLY_PAID) || (status == BillingStatus.PAYMENT_PENDING)){
            setupPaymentOptions(parentView);
        }
    }

    /**
     * Saves changes and triggers callback with success message
     * @param message Message to display after save
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
     * Displays an error message to the user
     * @param message The message to show
     * @param e The exception causing the error
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
